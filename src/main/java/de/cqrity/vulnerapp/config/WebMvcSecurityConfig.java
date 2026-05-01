package de.cqrity.vulnerapp.config;

import de.cqrity.vulnerapp.tfa.TfaAuthenticationProvider;
import de.cqrity.vulnerapp.tfa.authdetails.TfaWebAuthenticationDetailsSource;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebMvcSecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR).permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/cors/*").permitAll()
                .requestMatchers("/login**").permitAll()
                .requestMatchers("/ads").permitAll()
                .requestMatchers("/photo").permitAll()
                .requestMatchers("/register").anonymous()
                .requestMatchers("/resources/**").permitAll()
                .requestMatchers("/.well-known/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/admin/users/list").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/admin/defaultphoto").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin/defaultphoto").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/user/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
        );
        http.formLogin(form -> form
                .authenticationDetailsSource(new TfaWebAuthenticationDetailsSource())
                .loginPage("/login").failureUrl("/login?error")
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/")
                .permitAll()
        );
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll()
        );
        http.headers(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session
                .sessionFixation(sf -> sf.none())
                .enableSessionUrlRewriting(false)
        );
        http.csrf(AbstractHttpConfigurer::disable);
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new TfaAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new Md5PasswordEncoder());
        return authenticationProvider;
    }
}