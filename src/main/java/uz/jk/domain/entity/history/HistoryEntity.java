package uz.jk.domain.entity.history;

import jakarta.persistence.*;
import lombok.*;
import uz.jk.domain.entity.BaseEntity;
import uz.jk.domain.entity.product.ProductCategory;
import uz.jk.domain.entity.user.UserEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "histories")

public class HistoryEntity extends BaseEntity {
    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_price")
    private Double productPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_category")
    private ProductCategory productCategory;

    @Column(name = "total_amount")
    private int totalAmount;

    @Column(name = "seller_name")
    private String sellerName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity users;
}
