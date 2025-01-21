package pl.pawelec.shop.cart.controller;

import org.springframework.web.bind.annotation.*;
import pl.pawelec.shop.cart.controller.dto.CartSummaryDto;
import pl.pawelec.shop.cart.controller.mapper.CartMapper;
import pl.pawelec.shop.cart.model.dto.CartProductDto;
import pl.pawelec.shop.cart.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public CartSummaryDto getCart(@PathVariable Long id){
        return CartMapper.mapToCartSummary(cartService.getCart(id));
    }

    @PutMapping("/{id}")
    public CartSummaryDto addProductToCart(@PathVariable Long id, @RequestBody CartProductDto cartProductDto){
        return CartMapper.mapToCartSummary(cartService.addProductToCart(id, cartProductDto));
    }

    @PutMapping("/{id}/update")
    public CartSummaryDto updateCart(@PathVariable Long id, @RequestBody List<CartProductDto> cartProductDtos){
        return CartMapper.mapToCartSummary(cartService.updateCart(id, cartProductDtos));
    }

}
