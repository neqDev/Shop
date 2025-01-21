package pl.pawelec.shop.product.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.pawelec.shop.common.dto.ProductListDto;
import pl.pawelec.shop.common.model.Product;
import pl.pawelec.shop.product.service.ProductService;
import pl.pawelec.shop.product.service.dto.ProductDto;

import javax.validation.constraints.Pattern;
import java.util.List;

@Validated // dzieki temu mozemy walidowac parametry, nie tylko w klasach DTO
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping("/products")
//    public Page<Product> getProducts(){
//        return productService.getProducts(0, 25); // ustawione na sztywno
//    }

//    @GetMapping("/products")
//    public Page<Product> getProducts(@RequestParam(required = false) int page,
//                                     @RequestParam(required = false, defaultValue = "25") int size){
//        return productService.getProducts(page, size);
//    }

    @GetMapping("/products")
    public Page<ProductListDto> getProducts(Pageable pageable){
        // ProductListDto - likwidujemy problem n + 1 poniewaz w DTO pozbywamy sie pola reviews
        Page<Product> products = productService.getProducts(pageable);
        List<ProductListDto> productListDtos = products.getContent().stream()
                .map(product -> ProductListDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .image(product.getImage())
                        .slug(product.getSlug())
                        .build())
                .toList();
        return new PageImpl<>(productListDtos, pageable, products.getTotalElements());
    }

    @GetMapping("/products/{slug}")
    public ProductDto getProductBySlug(
            @PathVariable
            @Pattern(regexp = "[a-z0-9\\-]+")
            @Length(max = 255)
                                        String slug){
        return productService.getProductBySlug(slug);
    }
}
