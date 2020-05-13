package stacs.nathan.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import stacs.nathan.dto.LoggedInUser;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private static String ACCESS_TOKEN_HEADER = "x-access-token";

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    String accessToken = httpServletRequest.getHeader(ACCESS_TOKEN_HEADER);

//    if (!StringUtils.isEmpty(accessToken)) {
    LoggedInUser loggedInUser = new LoggedInUser("test");
    List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority("TestUser"));
//    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loggedInUser, null, Collections.emptyList());

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loggedInUser, null, roles);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//    }

//    httpServletResponse.setHeader("x-id-token", "adfweegevr eaerfever");
//    httpServletResponse.setHeader("x-access-token", "aefelkj;divhselha;lek");
//    httpServletResponse.setHeader("x-refresh-token", "ev;eaj'eka'l;kkdhfuheu");
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
