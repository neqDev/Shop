package pl.pawelec.shop.admin.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawelec.shop.admin.product.model.AdminProduct;

@Repository // adnotacja nie jest koneiczna po extends JpaRepository
public interface AdminProductRepository extends JpaRepository<AdminProduct, Long> {
}
