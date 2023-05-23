package uz.jk.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import uz.jk.domain.entity.BaseEntity;
import uz.jk.domain.entity.history.HistoryEntity;
import uz.jk.domain.entity.product.ProductEntity;

import java.util.List;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class UserEntity extends BaseEntity {

    private String name;

    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductEntity> productEntities;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<HistoryEntity> historyEntities;
}
