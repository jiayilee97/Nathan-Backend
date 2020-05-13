package stacs.nathan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import stacs.nathan.filter.JwtAuthFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthFilter jwtAuthFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .formLogin().disable()
        .headers().frameOptions().sameOrigin()
        .and()
        .authorizeRequests()
        .antMatchers("/public/**").permitAll()
        .anyRequest().authenticated();

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
