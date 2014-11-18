package de.cqrity.vulnerapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebMvcSecurity
public class WebMcvSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login**").anonymous()
                .antMatchers("/ads").permitAll()
                .antMatchers("/photo").permitAll()
                .antMatchers("/register").anonymous()
                .antMatchers("/resources/**").permitAll()
                .antMatchers(HttpMethod.GET, "/admin/users/list").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/admin/defaultphoto").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/admin/defaultphoto").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/user/**").hasAuthority("ADMIN")
                .anyRequest().authenticated();
        http
            .formLogin()
                .loginPage("/login").failureUrl("/login?error")
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/")
                .permitAll().and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll();
        http.userDetailsService(userDetailsService);
        http.sessionManagement().sessionFixation().none();
        http.sessionManagement().enableSessionUrlRewriting(true);
        //http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(getUsersQuery())
                .authoritiesByUsernameQuery(getAuthoritiesQuery());
    }

    private String getUsersQuery() {
        return "SELECT username, password, true FROM usr WHERE username = ?";
    }

    private String getAuthoritiesQuery() {
        return "SELECT u.username AS username, a.authority AS authority "
               + "FROM usr u INNER JOIN authority a ON a.id = u.authority_id "
               + "WHERE u.username = ?";
    }
}
