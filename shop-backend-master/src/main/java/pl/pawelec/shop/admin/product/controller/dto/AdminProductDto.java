package pl.pawelec.shop.admin.product.controller.dto;

import org.hibernate.validator.constraints.Length;
import pl.pawelec.shop.admin.product.model.AdminProductCurrency;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

// Bean Validation - Jakarta EE
/**
 * Mechanizm walidacji obecnie jeste dzieki adnotacjom
 *
 * !!! Aby nam zadzialala walidacja spirngowa musimy dodać odpowiednia zaleznosc w pom.xml (implementacja z hibernate validator)
 *
 * !!! @Valid trzeba dodac w kontrolerze !!! bo hibernate validator nie sprawdzi naszej klasy
 */
public class AdminProductDto {
    /**
     * Pole not null - obecnie zapewnione jest to po stronie bazy danych
     */
//    @NotEmpty // chroni przed pustym stringiem, jeśli jest @Length - nie jest to potrzebne
    @NotBlank // nie moze być samych białych znaków
    @Length(min = 4) // pole musi miec przynajmniej 4 znaki
    private String name;
    @NotNull
    private Long categoryId;
    @NotBlank
    @Length(min = 4)
    private String description;
    private String fullDescription; // pole opcjonalen dlatego nie dodajeym walidacji


    @NotNull
    @Min(4)
    private BigDecimal price;
    private AdminProductCurrency currency;
    private String image;
    @NotBlank
    @Length(min = 4)
    private String slug;


    public String getName() {
        return name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AdminProductCurrency getCurrency() {
        return currency;
    }

    public String getImage() {
        return image;
    }

    public String getSlug() {
        return slug;
    }

    public String getFullDescription() {
        return fullDescription;
    }
}
