package uz.jk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jk.domain.entity.product.ProductCategory;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreateDto {
    private UUID id;

    private String name;

    private String description;

    private Double price;

    private ProductCategory category;

    private UUID userId;
}
