package pl.pawelec.shop.cart.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawelec.shop.common.model.Cart;
import pl.pawelec.shop.cart.model.dto.CartProductDto;
import pl.pawelec.shop.common.repository.CartRepository;
import pl.pawelec.shop.common.model.Product;
import pl.pawelec.shop.common.repository.ProductRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Mokowanie bazy danych - Mockito
 */

@ExtendWith(MockitoExtension.class) // aby uruchomic mockito
class CartServiceTest {

    @Mock // mokujemy repo aby CartService mogl skorzystac z tych mokow
    private CartRepository cartRepository;
    @Mock // mokujemy repo aby CartService mogl skorzystac z tych mokow
    private ProductRepository productRepository;
    @InjectMocks // do tej klasy musi wstrzyknac Mocki
    private CartService cartService;


    @Test
    void shouldAddProductToCartWhenCartIdNotExists() {
        // given
        Long cartId = 0L;
        CartProductDto cartProductDto = new CartProductDto(1L, 1);
        // zmokowana metoda findById
        when(productRepository.findById(1L)).thenReturn(Optional.of(Product.builder()
                .id(1L).build()));
        // zmokowana metoda save
        when(cartRepository.save(any())).thenReturn(Cart.builder()
                .id(1L).build());
        // when
        Cart result = cartService.addProductToCart(cartId, cartProductDto);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldAddProductToCartWhenCartIdExists() {
        // given
        Long cartId = 1L;
        CartProductDto cartProductDto = new CartProductDto(1L, 1);
        // zmokowana metoda findById
        when(productRepository.findById(1L)).thenReturn(Optional.of(Product.builder()
                .id(1L).build()));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(Cart.builder()
                .id(1L).build()));
        // when
        Cart result = cartService.addProductToCart(cartId, cartProductDto);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }
}
