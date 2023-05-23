package uz.jk.service.product;

import org.springframework.stereotype.Service;
import uz.jk.domain.dto.ProductCreateDto;
import uz.jk.domain.entity.product.ProductCategory;
import uz.jk.domain.entity.product.ProductEntity;
import uz.jk.domain.response.BaseResponse;
import uz.jk.service.BaseService;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService extends BaseService<ProductCreateDto, ProductEntity> {
    void update(ProductCreateDto productCreateDto);

    BaseResponse<List<ProductEntity>> findSellerProductsById(UUID id, int page);

    BaseResponse<List<ProductEntity>> findByCategory(ProductCategory category, int page);

    BaseResponse<List<ProductEntity>> findByProductName(String word, int page);
}
