package uz.jk.domain.entity.order;

import jakarta.persistence.*;
import lombok.*;
import uz.jk.domain.entity.BaseEntity;
import uz.jk.domain.entity.product.ProductEntity;
import uz.jk.domain.entity.user.UserEntity;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class OrderEntity extends BaseEntity {

    @Column(name = "product_amount")
    private int amount;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private ProductEntity products;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity users;
}
