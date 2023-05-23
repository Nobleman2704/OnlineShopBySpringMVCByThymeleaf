package uz.jk.dao.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.jk.domain.entity.order.OrderEntity;

import java.util.UUID;

public interface OrderDao extends JpaRepository<OrderEntity, UUID> {
    boolean existsOrderEntitiesByUsersIdAndProductsId(UUID userId, UUID productId);

    Page<OrderEntity> findOrderEntitiesByUsersId(UUID userId, Pageable pageable);

    Page<OrderEntity> findOrderEntitiesByProductsUsersId(UUID userId, Pageable pageable);
}
