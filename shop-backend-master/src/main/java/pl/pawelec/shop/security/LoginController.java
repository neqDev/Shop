package pl.pawelec.shop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pawelec.shop.security.model.ShopUserDetails;
import pl.pawelec.shop.security.model.User;
import pl.pawelec.shop.security.model.UserRole;
import pl.pawelec.shop.security.repository.UserRepository;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private long expirationTime;
    private String secret;
    public LoginController(AuthenticationManager authenticationManager,
                           UserRepository userRepository, @Value("${jwt.expirationTime}") long expirationTime,
                           @Value("${jwt.secret}")String secret) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.expirationTime = expirationTime;
        this.secret = secret;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginCredentials loginCredentials){
        return authenticate(loginCredentials.getUsername(), loginCredentials.getPassword());

    }

    @PostMapping("/register")
    public Token register(@RequestBody @Valid RegisterCredentials registerCredentials){
        if(!registerCredentials.getPassword().equals(registerCredentials.getRepeatPassword())){
            throw new IllegalArgumentException("Hasła nie są identyczne");
        }
        if(userRepository.existsByUsername(registerCredentials.getUsername())){
            throw new IllegalArgumentException("Taki użytkownik już istnieje w bazie danych");
        }
        userRepository.save(User.builder() // zapisujemy usera
                        .username(registerCredentials.getUsername())
                        .password("{bcrypt}" + new BCryptPasswordEncoder().encode(registerCredentials.getPassword()))
                        .enabled(true)
                        .authorities(List.of(UserRole.ROLE_CUSTOMER))
                .build());
     return authenticate(registerCredentials.getUsername(), registerCredentials.getPassword());
    }

    private Token authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow();

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getId(), password)
        );
        ShopUserDetails principal = (ShopUserDetails) authenticate.getPrincipal();// pobieramy naszego użytkownia (Prinicipal)
        String token = JWT.create()
                .withSubject(String.valueOf(principal.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret)); // podpisuejmy token


        return new Token(token, principal.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .filter(s -> UserRole.ROLE_ADMIN.name().equals(s))
                .map(s -> true)
                .findFirst()
                .orElse(false));
    }

    private static class LoginCredentials{
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private static class RegisterCredentials{
        @Email
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String repeatPassword;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRepeatPassword() {
            return repeatPassword;
        }
    }

    private static class Token{
        private String token;
        private boolean adminAccess;

        private Token(String token, boolean adminAccess) {
            this.token = token;
            this.adminAccess = adminAccess;
        }

        public String getToken() {
            return token;
        }

        public boolean isAdminAccess() {
            return adminAccess;
        }
    }
}
