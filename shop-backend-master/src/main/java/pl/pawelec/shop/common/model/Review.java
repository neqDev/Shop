package pl.pawelec.shop.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // do zapisu id produktu // @JoinColumn => "productId" z Product,
    // brak relacji aby sie komentarze nie zaciagaly przy wyswietlaniu listy produktow,
    // komentarze maja byc tylko wyswitalne jak wejdziemy w dany produkt
    private Long productId;
    private String authorName;
    private String content;
    private boolean moderated;

}
