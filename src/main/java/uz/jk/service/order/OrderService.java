package uz.jk.service.order;

import org.springframework.stereotype.Service;
import uz.jk.domain.dto.OrderCreateDto;
import uz.jk.domain.entity.order.OrderEntity;
import uz.jk.domain.entity.order.OrderStatus;
import uz.jk.domain.response.BaseResponse;
import uz.jk.service.BaseService;

import java.util.List;
import java.util.UUID;

@Service
public interface OrderService extends BaseService<OrderCreateDto, OrderEntity> {
    BaseResponse<List<OrderEntity>> findMyOrdersById(UUID userId, int page);

    void changeOrderAmount(UUID orderId, int amount);

    BaseResponse<List<OrderEntity>> getSellerOrders(UUID userId, int page);

    BaseResponse changeOrderStatus(UUID orderId, OrderStatus status);
}
