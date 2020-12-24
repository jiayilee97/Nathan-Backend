package stacs.nathan.core.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import stacs.nathan.service.JWTService;
import stacs.nathan.utils.constancs.AwsServiceConstants;
import stacs.nathan.dto.request.LoggedInUser;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.json.JSONObject;
import stacs.nathan.utils.enums.UserRole;

import static stacs.nathan.utils.constancs.CommonConstants.ACCESS_TOKEN_HEADER;
import static stacs.nathan.utils.constancs.CommonConstants.ID_TOKEN_HEADER;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  @Value("${organization.code}")
  private String organizationCode;

  @Value("${issuer.name}")
  private String issuerName;

  @Value("${issuer.role}")
  private String issuerRole;

  @Autowired
  private JWTService jwtService;

  public static String TRINITY_ADMIN = "TRINITY_ADMIN";

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    String idToken = httpServletRequest.getHeader(ID_TOKEN_HEADER);
    String accessToken = httpServletRequest.getHeader(ACCESS_TOKEN_HEADER);
    boolean trinityOrg = false;
    if(!StringUtils.isEmpty(accessToken)){
      Claims claims = jwtService.decodeJWT(accessToken);
      if(issuerName.equals(claims.getIssuer()) && issuerRole.equals(claims.getSubject())){
        trinityOrg = true;
      }
    }

//    String idTokenFromCookie = null;
//
//    if (StringUtils.isEmpty(idTokenHeader)) {
//      Cookie[] allCookies = httpServletRequest.getCookies();
//      if (allCookies.length > 0) {
//        Cookie idTokenCookie =
//            Arrays.stream(allCookies).filter(x -> x.getName().equals(ID_TOKEN_HEADER))
//                .findFirst().orElse(null);
//
//        if (idTokenCookie != null) {
//          idTokenFromCookie = idTokenCookie.getValue();
//        }
//      }
//    }
//
//    String idToken = StringUtils.isEmpty(idTokenHeader) ? idTokenFromCookie: idTokenHeader;

    if (!StringUtils.isEmpty(idToken)) {
      JSONObject json = decode(idToken);
      List<String> loggedInUserOrgs = Arrays.asList(json.getString(AwsServiceConstants.ORGANIZATION).split(","));
      if(loggedInUserOrgs.contains(this.organizationCode) || (trinityOrg && loggedInUserOrgs.contains(issuerName))){
        String loggedInUserName = json.getString(AwsServiceConstants.USERNAME);
        List<String> loggedInUserRoles = trinityOrg ? Arrays.asList(TRINITY_ADMIN) : Arrays.asList(json.getString(AwsServiceConstants.ROLES).split(","));
        if(UserRole.MASTER.getCode().equals(loggedInUserRoles.get(0))) {
          loggedInUserRoles = Arrays.asList(UserRole.CRO.getCode(), UserRole.MKT.getCode(), UserRole.OPS.getCode(), UserRole.CP.getCode(), UserRole.RISK.getCode());
        }
        LoggedInUser loggedInUser = new LoggedInUser(loggedInUserName, loggedInUserOrgs, loggedInUserRoles);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for(String role: loggedInUserRoles) {
          roles.add(new SimpleGrantedAuthority((role)));
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loggedInUser, null, roles);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  public JSONObject decode(String token) {
    if (!Jwts.parser().isSigned(token)) {
      throw new SignatureException("Token is not signed");
    }
    String[] components = token.split("\\.");
    String decodedToken = new String(Base64Utils.decodeFromString(components[1]));
    return new JSONObject(decodedToken);
  }

}
