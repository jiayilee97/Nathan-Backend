package stacs.nathan.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class JWTUtils {

  @Value("${secret.key}")
  private String secretKey;

  @Value("${issuer.name}")
  private String issuerName;

  @Value("${issuer.role}")
  private String issuerRole;

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

}
