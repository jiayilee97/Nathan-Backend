package stacs.nathan.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import static stacs.nathan.utils.constancs.CommonConstants.ID_TOKEN_HEADER;
import static stacs.nathan.utils.constancs.CommonConstants.REFRESH_TOKEN_HEADER;

@Service
public class JWTService {

  @Value("${secret.key}")
  private String secretKey;

  @Value("${issuer.name}")
  private String issuerName;

  @Value("${issuer.role}")
  private String issuerRole;

//  @Value("${app.env}")
//  private String env;
//
//  @Value("${local.env}")
//  private String localEvn;

  @Value("${cookie.x_id_token.expiry.sec}")
  private int idTokenExpiryInSec;

  public String generateJWT(){
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    byte[] signingKeyByte = DatatypeConverter.parseBase64Binary(this.secretKey);
    Key signingKey = new SecretKeySpec(signingKeyByte, signatureAlgorithm.getJcaName());
    JwtBuilder builder = Jwts.builder()
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setIssuer(this.issuerName)
        .setSubject(this.issuerRole)
        .signWith(signatureAlgorithm, signingKey);

    return builder.compact();
  }

  public Claims decodeJWT(String jwt) {
    Claims claims = Jwts.parser()
        .setSigningKey(DatatypeConverter.parseBase64Binary(this.secretKey))
        .parseClaimsJws(jwt).getBody();
    return claims;
  }

//  public void setIdTokenCookie(HttpServletRequest request, HttpServletResponse response) {
//    String idToken = request.getHeader(ID_TOKEN_HEADER);
//    Cookie jwtCookie = new Cookie(ID_TOKEN_HEADER, idToken);
//    jwtCookie.setHttpOnly(true);
//    jwtCookie.setSecure(!env.equalsIgnoreCase(localEvn));
//    jwtCookie.setPath("/");
//    jwtCookie.setMaxAge(idTokenExpiryInSec);
//    response.addCookie(jwtCookie);
//  }
//
//  public void setRefreshTokenCookie(HttpServletRequest request, HttpServletResponse response) {
//    String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
//    Cookie jwtCookie = new Cookie(REFRESH_TOKEN_HEADER, refreshToken);
//    jwtCookie.setHttpOnly(true);
//    jwtCookie.setSecure(!env.equalsIgnoreCase(localEvn));
//    jwtCookie.setPath("/");
//    jwtCookie.setMaxAge(idTokenExpiryInSec);
//    response.addCookie(jwtCookie);
//  }
//
//  public void logout(HttpServletRequest request, HttpServletResponse response) {
//    Cookie jwtCookie = new Cookie(ID_TOKEN_HEADER, "");
//    jwtCookie.setMaxAge(0);
//    jwtCookie.setHttpOnly(true);
//    jwtCookie.setSecure(!env.equalsIgnoreCase(localEvn));
//    jwtCookie.setPath("/");
//    response.addCookie(jwtCookie);
//
//    Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_HEADER, "");
//    refreshTokenCookie.setMaxAge(0);
//    refreshTokenCookie.setHttpOnly(true);
//    refreshTokenCookie.setSecure(!env.equalsIgnoreCase(localEvn));
//    refreshTokenCookie.setPath("/");
//    response.addCookie(refreshTokenCookie);
//  }
}
