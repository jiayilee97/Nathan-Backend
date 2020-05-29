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
import stacs.nathan.utils.JWTUtils;
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

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  @Value("${organization.code}")
  private String organizationCode;

  @Value("${issuer.name}")
  private String issuerName;

  @Value("${issuer.role}")
  private String issuerRole;

  @Autowired
  private JWTUtils jwtUtils;

  private static String ID_TOKEN_HEADER = "x-id-token";
  private static String ACCESS_TOKEN_HEADER = "x-access-token";

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    String idToken = httpServletRequest.getHeader(ID_TOKEN_HEADER);
    String accessToken = httpServletRequest.getHeader(ACCESS_TOKEN_HEADER);
    boolean trinityOrg = false;
    if(!StringUtils.isEmpty(accessToken)){
      Claims claims = jwtUtils.decodeJWT(accessToken);
      if(issuerName.equals(claims.getIssuer()) && issuerRole.equals(claims.getSubject())){
        trinityOrg = true;
      }
    }
    if (!StringUtils.isEmpty(idToken)) {
      JSONObject json = decode(idToken);
      String loggedInUserOrg = json.getString(AwsServiceConstants.ORGANIZATION);
      if(this.organizationCode.equals(loggedInUserOrg) || trinityOrg){
        String loggedInUserName = json.getString(AwsServiceConstants.USERNAME);
        List<String> loggedInUserRoles = Arrays.asList(json.getString(AwsServiceConstants.ROLES).split(","));
        LoggedInUser loggedInUser = new LoggedInUser(loggedInUserName, loggedInUserOrg, loggedInUserRoles);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for(String role: loggedInUserRoles){
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
