package pl.pawelec.shop.cart.controller;

import org.springframework.web.bind.annotation.*;
import pl.pawelec.shop.cart.service.CartItemService;

@RestController
@RequestMapping("/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @DeleteMapping("/{id}")
    public void deleteCartItem(@PathVariable Long id){
        cartItemService.delete(id);
    }

    @GetMapping("/count/{cartId}")
    public Long countItemInCart(@PathVariable Long cartId){
        return cartItemService.countItemInCart(cartId);
    }
}
