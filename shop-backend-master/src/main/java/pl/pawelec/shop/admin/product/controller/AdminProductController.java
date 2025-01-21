package pl.pawelec.shop.admin.product.controller;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pawelec.shop.admin.product.controller.dto.AdminProductDto;
import pl.pawelec.shop.admin.product.controller.dto.UploadResponse;
import pl.pawelec.shop.admin.product.model.AdminProduct;
import pl.pawelec.shop.admin.product.service.AdminProductImageService;
import pl.pawelec.shop.admin.product.service.AdminProductService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static pl.pawelec.shop.admin.common.utils.SlugifyUtils.slugifySlug;

@RestController
public class AdminProductController {

    public static final Long EMPTY_ID = null;
    private final AdminProductService productService;
    private final AdminProductImageService productImageService;

    public AdminProductController(AdminProductService productService, AdminProductImageService productImageService) {
        this.productService = productService;
        this.productImageService = productImageService;
    }
    @GetMapping("/admin/products")
    public Page<AdminProduct> getProducts(Pageable pageable){
        return productService.getProducts(pageable);
    }

    @GetMapping("/admin/products/{id}")
    public AdminProduct getProducts(@PathVariable Long id){
        return productService.getProduct(id);
    }

    // @Valid - Bez tego Hibernate Validator nie sprawdzi nam naszej klasy.
    @PostMapping("/admin/products")
    public AdminProduct createProduct(@RequestBody @Valid AdminProductDto adminProductDto){ // Data Transfer Object - sluzy tylko do przekzazywania danych
        return productService.createProduct(mapAdminProduct(adminProductDto, EMPTY_ID)
        );
    }

    @PutMapping("/admin/products/{id}")
    public AdminProduct updateProduct(@RequestBody @Valid AdminProductDto adminProductDto, @PathVariable Long id){ // Data Transfer Object - sluzy tylko do przekzazywania danych, danych z frontendu
        return productService.updateProduct(mapAdminProduct(adminProductDto, id));
    }

    @DeleteMapping("/admin/products/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @PostMapping("/admin/products/upload-image")
    public UploadResponse uploadImage(@RequestParam("file") MultipartFile multipartFile){
        String filename = multipartFile.getOriginalFilename(); // nazwa przeslanego pliku

        try(InputStream inputStream = multipartFile.getInputStream()){ // to co dostajemy
            String savedFileName = productImageService.uploadImage(filename, inputStream);
            return new UploadResponse(savedFileName);
        } catch (IOException e){
            throw new RuntimeException("Coś poszło źle podczas wgrywania pliku", e);
        }

    }

    @GetMapping("/data/productImage/{filename}")
    public ResponseEntity<Resource> serveFiles(@PathVariable String filename) throws IOException {
        Resource file = productImageService.serveFiles(filename);
        return ResponseEntity.ok() // status OK 200
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Path.of(filename))) //dodanie odpoidnich naglowkow
                .body(file);// ustawienie body naszej odpowiedzi (nasz plik) -> zwraca nasz plik
    }


    // przemapowywanie danych z encji na DTO za pomocą @Builder
    private static AdminProduct mapAdminProduct(AdminProductDto adminProductDto, Long id) {
        return AdminProduct.builder()
                .id(id)
                .name(adminProductDto.getName())
                .description(adminProductDto.getDescription())
                .fullDescription(adminProductDto.getFullDescription())
                .categoryId(adminProductDto.getCategoryId())
                .price(adminProductDto.getPrice())
                .currency(adminProductDto.getCurrency())
                .image(adminProductDto.getImage())
                .slug(slugifySlug(adminProductDto.getSlug()))
                .build();
    }


}
