package pl.pawelec.shop.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawelec.shop.common.model.Product;
import pl.pawelec.shop.common.model.Review;
import pl.pawelec.shop.common.repository.ProductRepository;
import pl.pawelec.shop.common.repository.ReviewRepository;
import pl.pawelec.shop.product.service.dto.ProductDto;
import pl.pawelec.shop.product.service.dto.ReviewDto;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    public Page<Product> getProducts_v1(){
        return productRepository.findAll(PageRequest.of(0, 25));
    }
    public Page<Product> getProducts_v2(int page, int size){
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Product> getProducts(Pageable pageable){ // @PageableDefault(size = 25) zmiana pageSizze. Pageable ma domyslnie pageSize=20
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public ProductDto getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug).orElseThrow();
        List<Review> reviews = reviewRepository.findAllByProductIdAndModerated(product.getId(), true);
        return mapToProductDto(product, reviews);
    }

    private ProductDto mapToProductDto(Product product, List<Review> reviews) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategoryId())
                .description(product.getDescription())
                .fullDescription(product.getFullDescription())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .image(product.getImage())
                .slug(product.getSlug())
                .reviews(reviews.stream().map(review -> ReviewDto.builder()
                                .id(review.getId())
                                .productId(review.getProductId())
                                .authorName(review.getAuthorName())
                                .content(review.getContent())
                                .moderate(review.isModerated())
                                .build())
                        .toList())
                .build();
    }
}
