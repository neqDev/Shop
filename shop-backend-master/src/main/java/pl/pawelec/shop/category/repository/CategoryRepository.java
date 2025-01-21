package pl.pawelec.shop.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pawelec.shop.common.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    //JPQL - działamy na obiektach
    // zapytanie zwraca kategorie wraz z produktami w jednym zapytaniu
//    @Query("select c from Category c " +
//            "left join fetch c.products " + // fetch - pozwala na pobranie produktow (bez dodatkowego zapytania)
//            "where c.slug=:slug")
    Category findBySlug(String slug);
}
