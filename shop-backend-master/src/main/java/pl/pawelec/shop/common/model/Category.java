package pl.pawelec.shop.common.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String slug;
//    @OneToMany
//    @JoinColumn(name = "categoryId") // fetch = lazy, relacje sa pobierane kiedy o nie poprosimy
//    private List<Product> products;
}
