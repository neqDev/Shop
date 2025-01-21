package pl.pawelec.shop.cart.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawelec.shop.common.model.Cart;
import pl.pawelec.shop.common.repository.CartItemRepository;
import pl.pawelec.shop.common.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CartCleanupService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartCleanupService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    //    @Scheduled(cron = "*/10 * * * * *") // <= sekundy minuty godzinu dniMiesiaca miesiace dzienTygodnia>
   @Transactional
    @Scheduled(cron = "${app.cart.cleanup.expression}")
    public void cleanupOldCarts(){
        //pobieramy wszystkie koszyki starsze niz 3 dni
        List<Cart> carts = cartRepository.findByCreatedLessThan(LocalDateTime.now().minusDays(3));
       List<Long> ids = carts.stream()
               .map(Cart::getId)
               .toList();
       log.info("Stare koszyki: " + carts.size());
//        carts.forEach(cart ->{
//            cartItemRepository.deleteByCartId(cart.getId());
//            cartRepository.deleteCartById(cart.getId());
//        });

       if(!ids.isEmpty()){
           cartItemRepository.deleteAllByCartIdIn(ids);
           cartRepository.deleteAllByCartIdIn(ids);
       }
    }
}
