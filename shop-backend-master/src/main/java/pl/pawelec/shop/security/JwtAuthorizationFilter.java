package pl.pawelec.shop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.pawelec.shop.security.model.ShopUserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filtr odpalany na kazdym requescie
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;
    private final String secret;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserDetailsService userDetailsService,
                                  String secret) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.secret = secret;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) { // jezeli nasza autektykacja  jest null-em, to przekazujeny wywolanie do kolejendgo filtra
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication); // ustawiamy w kontekscie Springa nasz obiekt
        filterChain.doFilter(request, response); // przekazujemy wywolanie do nastepengo filtra w lancuchu
    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER); // z request wyciagamy header tokena 'Authorization'
        if (token != null && token.startsWith(TOKEN_PREFIX)) { // sprawdzamy prefix 'Bearer', taki token musi miec taki prefix
            String userName = JWT.require(Algorithm.HMAC256(secret)) // budujemy obiekt ktorry nam zweryfikuje uzytkownika na podstawiee algorymtu i secret
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, "")) // sprawdza token
                    .getSubject(); // zwroci nam user id
            if (userName != null) {
                ShopUserDetails userDetails = (ShopUserDetails) userDetailsService.loadUserByUsername(userName); // pobiera z pamieci lub z bazy uzytkownika
                return new UsernamePasswordAuthenticationToken(userDetails.getId(), null, userDetails.getAuthorities()); // userDetails.getAuthorities() - pobieramy nasze role,
                // credential - nie potrzebujemy bo mamy juz zweryfikowany token
            }
        }
        return null;
    }
}
