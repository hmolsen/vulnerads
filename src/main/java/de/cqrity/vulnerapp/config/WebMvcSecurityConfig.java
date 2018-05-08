package de.cqrity.vulnerapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.cqrity.vulnerapp.tfa.TfaAuthenticationProvider;
import de.cqrity.vulnerapp.tfa.authdetails.TfaWebAuthenticationDetailsSource;

@Configuration
@EnableWebSecurity
public class WebMvcSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("plaintextAuthenticationProvider")
    AuthenticationProvider plaintextAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/cors/*").permitAll()
                .antMatchers("/login**").permitAll()
                .antMatchers("/ads").permitAll()
                .antMatchers("/photo").permitAll()
                .antMatchers("/register").anonymous()
                .antMatchers("/resources/**").permitAll()
                .antMatchers(HttpMethod.GET, "/admin/users/list").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/admin/defaultphoto").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/admin/defaultphoto").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/user/**").hasAuthority("ADMIN")
                .anyRequest().authenticated();
        http.formLogin()
                .authenticationDetailsSource(new TfaWebAuthenticationDetailsSource())
                .loginPage("/login").failureUrl("/login?error")
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/")
                .permitAll();
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll();

        http.headers().disable();
        http.sessionManagement().sessionFixation().none();
        http.sessionManagement().enableSessionUrlRewriting(true);
        http.csrf().disable();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Die AuthenticationProvider werden in der angegebenen Reihenfolge abgearbeitet.
        // Dadurch ist es auch bereits registrierten Nutzern weiterhin möglich, sich anzumelden.
        auth.authenticationProvider(plaintextAuthenticationProvider);
    }

    @Bean
    public AuthenticationProvider plaintextAuthenticationProvider() {
        DaoAuthenticationProvider plaintextAuthenticationProvider = new TfaAuthenticationProvider();
        plaintextAuthenticationProvider.setUserDetailsService(userDetailsService);
        plaintextAuthenticationProvider.setPasswordEncoder(new PlaintextPasswordEncoder());
        return plaintextAuthenticationProvider;
    }
}
