package pl.pawelec.shop.category.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawelec.shop.common.model.Category;
import pl.pawelec.shop.category.dto.CategoryProductsDto;
import pl.pawelec.shop.category.repository.CategoryRepository;
import pl.pawelec.shop.common.dto.ProductListDto;
import pl.pawelec.shop.common.model.Product;
import pl.pawelec.shop.common.repository.ProductRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    // readOnlny = true,  nie stosujemy dla zapisow, nie sledzi zmian w encjach, mechanizm dirtyChecking jest wyaczony
    @Transactional(readOnly = true) // dzieki temy bedziemy miec jedna tranzakcje zamiast kilku z ponizszych zapytan
    public CategoryProductsDto getCategoriesWithProducts(String slug, Pageable pageable) {
        Category category = categoryRepository.findBySlug(slug); // pobiera kategorie
        Page<Product>  page = productRepository.findByCategoryId(category.getId(), pageable); // pobiera prodykty dla kategori postronnicowane
       // problem n + 1 rowiazanie przez obiekt DTO i uzycie PageImpl<>()
        List<ProductListDto> productListDtos = page.getContent().stream()
                .map(product -> ProductListDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .image(product.getImage())
                        .slug(product.getSlug())
                        .build())
                .toList();
        return new CategoryProductsDto(category, new PageImpl<>(productListDtos, pageable, page.getTotalElements()));
    }
}
