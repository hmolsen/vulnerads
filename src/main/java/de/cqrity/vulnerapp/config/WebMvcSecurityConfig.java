package de.cqrity.vulnerapp.config;

import de.cqrity.vulnerapp.tfa.TfaAuthenticationProvider;
import de.cqrity.vulnerapp.tfa.TfaAuthenticator;
import de.cqrity.vulnerapp.tfa.authdetails.TfaWebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebMvcSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationProvider authenticationProvider;

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
        http.authenticationProvider(authenticationProvider);

        http.headers().disable();
        http.userDetailsService(userDetailsService);
        http.sessionManagement().sessionFixation().none();
        http.sessionManagement().enableSessionUrlRewriting(true);
        http.csrf().disable();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        TfaAuthenticationProvider tfaAuthenticationProvider = new TfaAuthenticationProvider();
        tfaAuthenticationProvider.setDatabaseEncryptor(DatabaseEncryptor.getInstance());
        tfaAuthenticationProvider.setTfaAuthenticator(new TfaAuthenticator());
        tfaAuthenticationProvider.setUserDetailsService(userDetailsService);
        return tfaAuthenticationProvider;
    }
}
