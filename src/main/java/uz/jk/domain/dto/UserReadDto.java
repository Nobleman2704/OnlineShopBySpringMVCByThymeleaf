package uz.jk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jk.domain.entity.history.HistoryEntity;
import uz.jk.domain.entity.product.ProductEntity;
import uz.jk.domain.entity.user.UserRole;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadDto {
    private UUID id;
    private String name;
    private String password;
    private String email;
    private UserRole role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<HistoryEntity> histories;
    private List<ProductEntity> productEntities;
}
