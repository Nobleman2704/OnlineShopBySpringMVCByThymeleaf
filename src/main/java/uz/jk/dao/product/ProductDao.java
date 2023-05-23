package uz.jk.dao.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.jk.domain.entity.product.ProductCategory;
import uz.jk.domain.entity.product.ProductEntity;

import java.util.UUID;

public interface ProductDao extends JpaRepository<ProductEntity, UUID> {
    Page<ProductEntity> findProductEntitiesByUsersId(UUID userId, Pageable pageable);
    Page<ProductEntity> findProductEntitiesByCategory(ProductCategory category, Pageable pageable);
    Page<ProductEntity> findProductEntitiesByNameContainingIgnoreCase(String word, Pageable pageable);
    @Transactional
    @Modifying
    @Query("delete from products p where p.id = :uuid")
    void deleteProductEntityById(@Param("uuid") UUID uuid);
}
