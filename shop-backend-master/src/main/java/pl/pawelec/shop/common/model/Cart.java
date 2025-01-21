package pl.pawelec.shop.common.model;

import lombok.*;
import pl.pawelec.shop.cart.model.CartItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    //PERSIST - aby wraz z zapisaniem koszyka zapisal liste items
    //CascadeType.REMOVE - do usuwania, bardzo nieefektywne usuwanie
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cartId")
    private List<CartItem> items;

    public void addProduct(CartItem cartItem){
        if(items == null){
            items = new ArrayList<>();
        }
        items.stream()
                .filter(item -> Objects.equals(cartItem.getProduct().getId(), item.getProduct().getId()))
                .findFirst()
                .ifPresentOrElse(item -> item.setQuantity(item.getQuantity() + 1),
                        () -> items.add(cartItem));
    }

}
