package uz.jk.service.history;

import org.springframework.stereotype.Service;
import uz.jk.domain.entity.history.HistoryEntity;
import uz.jk.domain.response.BaseResponse;
import uz.jk.service.BaseService;

import java.util.List;
import java.util.UUID;

@Service
public interface HistoryService extends BaseService<HistoryEntity, HistoryEntity> {
    BaseResponse<List<HistoryEntity>> getMyOrderHistory(UUID userId, int page);
}
