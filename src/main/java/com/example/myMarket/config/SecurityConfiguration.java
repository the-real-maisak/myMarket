package com.example.myMarket.config;

import com.example.myMarket.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableAspectJAutoProxy
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests( urlConfig -> urlConfig
                        .antMatchers("/marketplace", "/", "/registration").permitAll()
                      // .anyRequest().authenticated().
                        .antMatchers("/administrate**").hasAuthority(Role.ROLE_ADMIN.getAuthority())
                        .antMatchers("/product**").hasAnyAuthority(Role.ROLE_ADMIN.getAuthority(), Role.ROLE_USER.getAuthority())
                ).logout(logout-> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/marketplace")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login->login
                        .loginPage("/login")
                        .failureUrl("/err")
                        .defaultSuccessUrl("/marketplace")
                        .permitAll());
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }
}
