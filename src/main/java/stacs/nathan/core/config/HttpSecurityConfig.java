package stacs.nathan.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import stacs.nathan.core.filter.JwtAuthFilter;
import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthFilter jwtAuthFilter;

  @Value("${cors.origins.allowed}")
  private String[] allowedCorsOrigins;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .formLogin().disable()
        .headers().frameOptions().sameOrigin()
        .and()
        .cors().disable()
        .authorizeRequests()
        .antMatchers("/public/**").permitAll()
        .anyRequest().authenticated();

    CorsConfigurationSource corsConfigurationSource = configurationSource();

    if (corsConfigurationSource != null) {
      http.cors().configurationSource(configurationSource());
    }

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
  }

  private CorsConfigurationSource configurationSource() {
    if (!StringUtils.isEmpty(allowedCorsOrigins)) {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedOrigins(Arrays.asList(allowedCorsOrigins));
      config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
      config.setAllowedHeaders(Arrays.asList("x-access-token", "x-id-token"));
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config);
      return source;
    }
    return null;
  }
}
