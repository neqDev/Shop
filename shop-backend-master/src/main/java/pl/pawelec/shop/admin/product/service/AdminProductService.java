package pl.pawelec.shop.admin.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pawelec.shop.admin.product.model.AdminProduct;
import pl.pawelec.shop.admin.product.repository.AdminProductRepository;

@Service
public class AdminProductService {

    private final AdminProductRepository productRepository;

    public AdminProductService(AdminProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<AdminProduct> getProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public AdminProduct getProduct(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public AdminProduct createProduct(AdminProduct product) {
        return productRepository.save(product);
    }

    public AdminProduct updateProduct(AdminProduct product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
