package pl.pawelec.shop.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pl.pawelec.shop.security.model.UserRole;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private String secret;

    public SecurityConfig(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationManager authenticationManager,
                                           UserDetailsService userDetailsService) throws Exception {
        http.authorizeRequests(authorize -> authorize
                .antMatchers("/admin/**").hasRole(UserRole.ROLE_ADMIN.getRole()) // blokujemy dostep do uslug adminstracyjnych
                .antMatchers(HttpMethod.GET, "/orders").authenticated() // dla metody GET do endpointu /orders
                .anyRequest().permitAll()); // reszta ma dostep bez autoryzacji

        http.csrf().disable(); // przydatne tylko dla MVC do zabezpieczenie przed zdalnym wywołaaniem akcji
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //wylaczenie sesji http, bedzie to sesja bezstanowa bo uzywamy token a nie cookies
        // podlaczamy nasz filtr
        http.addFilter(new JwtAuthorizationFilter(authenticationManager, userDetailsService, secret));


        return http.build();
    }

    @Bean // odpowiada za uwierzytelnienie w aplikacji
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService  userDetailsService(DataSource dataSource){
//
//        return new JdbcUserDetailsManager(dataSource); // baza danych
//    }
//    @Bean
//    public UserDetailsService  userDetailsService(){ // dostarcza użytkowników
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("test")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(admin); // tymczasowo trzyamamy uzytkownika w pamieci do testow
//    }

}
