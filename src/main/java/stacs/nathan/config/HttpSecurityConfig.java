package stacs.nathan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import stacs.nathan.filter.JwtAuthFilter;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthFilter jwtAuthFilter;

  @Value("${server.cors.for.trinity}")
  private String trinityCorsOrigin;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().configurationSource(configurationSource()).and()
        .formLogin().disable()
        .headers().frameOptions().sameOrigin()
        .and()
        .authorizeRequests()
        .antMatchers("/public/**").permitAll()
        .anyRequest().authenticated();

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
  }

  private CorsConfigurationSource configurationSource() {
    CorsConfiguration trinityCors = new CorsConfiguration();
    trinityCors.setAllowedOrigins(Arrays.asList("https://example.com"));
    trinityCors.setAllowedMethods(Arrays.asList("GET","POST"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/trinity/**", trinityCors);
    return source;
  }
}
