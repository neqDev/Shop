package pl.pawelec.shop.security.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users") // w springu domyslna tabela to 'users'
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable { // musimy dodac bo ustawiamy referencedColumnName
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private String hash;
    private LocalDateTime HashDate;
    /**
     * Nistandardowe mapowanie @ElementCollection
     */
    @ElementCollection //  role z tabeli Authorities beda mapowane na tą kolekcję, trzeba odpowiednieo zmpaowac dla hibernate,
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))// wskazujemy tablele gdzie znajduja sie role
    @Column(name = "authority") //  kolumne z ktorej bedziemy je wyciagac
    @Enumerated(EnumType.STRING)
    private List<UserRole> authorities; // lista Stringow na role
}
