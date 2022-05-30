package com.example.authenticationyoutube.security;

import com.example.authenticationyoutube.UserPrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserPrincipalDetailService userPrincipalDetailService;

    public SecurityConfiguration(UserPrincipalDetailService userPrincipalDetailService) {
        this.userPrincipalDetailService = userPrincipalDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
//        auth.inMemoryAuthentication()
//                .withUser("moshood").password(passwordEncoder().encode("password")).authorities("LEVEL1", "LEVEL2","ROLE_ADMIN")
//                .and()
//                .withUser("temilade").password(passwordEncoder().encode("password")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/users/welcome", "/signin", "/login").permitAll()
                .antMatchers("/api/users/index").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/users/admin").hasRole("ADMIN")
                .antMatchers("/api/users/user").hasRole("USER")
                .antMatchers("/api/users/dashboard").hasAuthority("LEVEL1")
                .antMatchers("/api/users/moshood").hasRole("ADMIN")
                .and()
                .formLogin()
                .usernameParameter("txtUsername")
                .passwordParameter("txtPassword")
                .loginPage("/signin").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/signin")
                .and()
                .rememberMe();
//                .httpBasic();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
       DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
       authenticationProvider.setUserDetailsService(this.userPrincipalDetailService);
       authenticationProvider.setPasswordEncoder(passwordEncoder());
       return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
