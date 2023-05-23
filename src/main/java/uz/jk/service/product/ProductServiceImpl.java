package uz.jk.service.product;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jk.dao.product.ProductDao;
import uz.jk.dao.user.UserDao;
import uz.jk.domain.dto.ProductCreateDto;
import uz.jk.domain.entity.product.ProductCategory;
import uz.jk.domain.entity.product.ProductEntity;
import uz.jk.domain.entity.user.UserEntity;
import uz.jk.domain.response.BaseResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private final ModelMapper modelMapper;

    private final UserDao userDao;

    @Override
    public BaseResponse create(ProductCreateDto productCreateDto) {
        ProductEntity product = modelMapper.map(productCreateDto, ProductEntity.class);
        UserEntity user = userDao.findById(productCreateDto.getUserId()).get();
        product.setUsers(user);
        productDao.save(product);
        return BaseResponse.builder()
                .status(200)
                .message("Successfully added")
                .build();
    }

    @Override
    public ProductEntity findById(UUID id) {
        return productDao.findById(id).get();
    }

    @Override
    public BaseResponse delete(UUID id) {
        try {
            productDao.deleteProductEntityById(id);
            return BaseResponse.builder()
                    .status(200)
                    .message("deleted")
                    .build();
        }catch (DataIntegrityViolationException e){
            return BaseResponse.builder()
                    .status(400)
                    .message("You cannot delete ordered product")
                    .build();
        }
    }

    @Override
    public void update(ProductCreateDto productCreateDto) {
        ProductEntity product1 = productDao.findById(productCreateDto.getId()).get();
        modelMapper.map(productCreateDto, product1);
        productDao.save(product1);
    }

    @Override
    public BaseResponse<List<ProductEntity>> findSellerProductsById(UUID id, int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<ProductEntity> productEntitiesPage = productDao.findProductEntitiesByUsersId(id, pageRequest);
        int totalPages = productEntitiesPage.getTotalPages();
        return BaseResponse.<List<ProductEntity>>builder()
                .data(productEntitiesPage.getContent())
                .message(productEntitiesPage.getTotalElements() + " result(s) found")
                .totalPages((totalPages==0)?0:totalPages-1)
                .build();
    }

    @Override
    public BaseResponse<List<ProductEntity>> findByCategory(ProductCategory category, int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<ProductEntity> productEntitiesPage = productDao.findProductEntitiesByCategory(category, pageRequest);
        int totalPages = productEntitiesPage.getTotalPages();
        return BaseResponse.<List<ProductEntity>>builder()
                .status(200)
                .message(productEntitiesPage.getTotalElements() + " result(s) found")
                .totalPages((totalPages==0)?0:totalPages-1)
                .data(productEntitiesPage.getContent())
                .build();
    }

    @Override
    public BaseResponse<List<ProductEntity>> findByProductName(String word, int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<ProductEntity> productEntitiesPage = productDao.findProductEntitiesByNameContainingIgnoreCase(word, pageRequest);
        int totalPages = productEntitiesPage.getTotalPages();
        return BaseResponse.<List<ProductEntity>>builder()
                .status(200)
                .message(productEntitiesPage.getTotalElements() + " result(s) found")
                .totalPages((totalPages==0)?0:totalPages-1)
                .data(productEntitiesPage.getContent())
                .build();
    }
}
