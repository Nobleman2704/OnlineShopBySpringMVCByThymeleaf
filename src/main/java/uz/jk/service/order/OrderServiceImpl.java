package uz.jk.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jk.dao.history.HistoryDao;
import uz.jk.dao.order.OrderDao;
import uz.jk.dao.product.ProductDao;
import uz.jk.dao.user.UserDao;
import uz.jk.domain.dto.OrderCreateDto;
import uz.jk.domain.entity.history.HistoryEntity;
import uz.jk.domain.entity.order.OrderEntity;
import uz.jk.domain.entity.order.OrderStatus;
import uz.jk.domain.entity.product.ProductEntity;
import uz.jk.domain.entity.user.UserEntity;
import uz.jk.domain.response.BaseResponse;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final HistoryDao historyDao;

    @Override
    public BaseResponse create(OrderCreateDto orderCreateDto) {
        int amount = orderCreateDto.getAmount();
        UUID productId = orderCreateDto.getId();
        UUID ownerId = orderCreateDto.getOwnerId();
        int status;
        String message;
        boolean check = orderDao.existsOrderEntitiesByUsersIdAndProductsId(ownerId, productId);
        if (check) {
            status = 400;
            message = "You have already owned this product";
        } else {
            ProductEntity product = productDao.findById(productId).get();
            UserEntity user = userDao.findById(ownerId).get();
            if (product.getUsers().getId().equals(ownerId)) {
                status = 401;
                message = "You can not order your product";
            } else {
                OrderEntity order = OrderEntity.builder()
                        .amount(amount)
                        .status(OrderStatus.ORDERED)
                        .users(user)
                        .products(product)
                        .build();
                orderDao.save(order);

                status = 200;
                message = "Successfully added to order list";
            }
        }
        return BaseResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    @Override
    public OrderEntity findById(UUID id) {
        return null;
    }

    @Override
    public BaseResponse delete(UUID id) {
        orderDao.deleteById(id);
        return null;
    }

    @Override
    public BaseResponse<List<OrderEntity>> findMyOrdersById(UUID userId, int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<OrderEntity> orderEntitiesPage = orderDao.findOrderEntitiesByUsersId(userId, pageRequest);
        int totalPages = orderEntitiesPage.getTotalPages();
        return BaseResponse.<List<OrderEntity>>builder()
                .status(200)
                .message(orderEntitiesPage.getTotalElements() + " result(s) found")
                .totalPages((totalPages==0)?0:totalPages-1)
                .data(orderEntitiesPage.getContent())
                .build();
    }

    @Override
    public void changeOrderAmount(UUID orderId, int amount) {
        if (amount == 0) {
            orderDao.deleteById(orderId);
            return;
        }
        OrderEntity orderEntity = orderDao.findById(orderId).get();
        orderEntity.setAmount(amount);
        orderDao.save(orderEntity);
    }

    @Override
    public BaseResponse<List<OrderEntity>> getSellerOrders(UUID userId, int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<OrderEntity> orderEntitiesPage = orderDao.findOrderEntitiesByProductsUsersId(userId, pageRequest);
        int totalPages = orderEntitiesPage.getTotalPages();
        return BaseResponse.<List<OrderEntity>>builder()
                .status(200)
                .message(orderEntitiesPage.getTotalElements() + " result(s) found")
                .totalPages((totalPages==0)?0:totalPages-1)
                .data(orderEntitiesPage.getContent())
                .build();
    }

    @Override
    public BaseResponse changeOrderStatus(UUID orderId, OrderStatus status) {
        OrderEntity orderEntity = orderDao.findById(orderId).get();
        orderEntity.setStatus(status);
        if (Objects.equals(status.toString(), "DELIVERED")) {
            ProductEntity product = orderEntity.getProducts();
            HistoryEntity history = HistoryEntity.builder()
                    .productCategory(product.getCategory())
                    .productPrice(product.getPrice())
                    .productDescription(product.getDescription())
                    .productName(product.getName())
                    .sellerName(product.getUsers().getName())
                    .totalAmount(orderEntity.getAmount())
                    .users(orderEntity.getUsers())
                    .build();
            historyDao.save(history);
            orderDao.deleteById(orderEntity.getId());
            return BaseResponse.builder()
                    .message("Order has been delivered")
                    .build();
        }
        orderDao.save(orderEntity);
        return BaseResponse.builder()
                .message("Order status has been changed to " + status)
                .build();
    }
}
