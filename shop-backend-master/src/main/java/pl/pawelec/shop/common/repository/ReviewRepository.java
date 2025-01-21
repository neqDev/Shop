package pl.pawelec.shop.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawelec.shop.common.model.Review;

import java.util.List;

@Repository // nie jest to konieczne bo wiadomo z JpaRepository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductIdAndModerated(Long productId, boolean moderated);
}
