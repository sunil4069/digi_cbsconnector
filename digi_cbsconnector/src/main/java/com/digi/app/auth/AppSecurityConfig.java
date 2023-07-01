package com.digi.app.auth;

import com.digi.app.user.enums.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
POWERED BY PEEPALSOFT - SHISHIR KARKI
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService myUserDetailService;

    @Autowired
    public void setMyUserDetailService(UserDetailsService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] adminSuperAdminRoles=new String[]{String.valueOf(RolesEnum.ADMIN),String.valueOf(RolesEnum.SUPERADMIN)};
        String[] adminSuperAdminPathsArray = new String[]{"/staffs/**", "/office/**","/users/**"};
        String[] superAdminPathsArray = new String[] {"/admins/forms","/admins/master/accounts","/admins/configs"};
        String[] inputterPathsArray = new String[]{"/accounts/transfers/create-form", "/transactions/create-form"};
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/assets/externalJs/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/access/denied").permitAll()
                .antMatchers("/erf/**").hasRole(String.valueOf(RolesEnum.ADMIN))
                .antMatchers(adminSuperAdminPathsArray).hasAnyRole(adminSuperAdminRoles)
                .antMatchers(inputterPathsArray).hasRole(String.valueOf(RolesEnum.INPUTTER))
                .antMatchers(superAdminPathsArray).hasRole(String.valueOf(RolesEnum.SUPERADMIN))
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login-page")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login-page")
                // .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID");
        // .logoutSuccessHandler(logoutSuccessHandler());
        http
                .exceptionHandling()
                .accessDeniedPage("/access/denied");
    }

    //defining a bean to encrypt passwords
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
