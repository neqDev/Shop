package pl.pawelec.shop.category.dto;

import org.springframework.data.domain.Page;
import pl.pawelec.shop.common.model.Category;
import pl.pawelec.shop.common.dto.ProductListDto;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
