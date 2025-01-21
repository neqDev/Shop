package pl.pawelec.shop.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.pawelec.shop.common.model.Product;

import java.util.Optional;

// @Repository // nie musi być tej adnotacji bo dziedziczy po JpaRepository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // mechanizm spirngdata na podstawie konwecji nazewniczej
    Optional<Product> findBySlug(String slug);

    Page<Product> findByCategoryId(Long id, Pageable pageable);
}
