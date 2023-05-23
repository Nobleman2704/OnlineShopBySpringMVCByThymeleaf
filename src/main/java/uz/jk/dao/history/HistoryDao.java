package uz.jk.dao.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jk.domain.entity.history.HistoryEntity;
import uz.jk.domain.entity.user.UserEntity;

import java.util.List;
import java.util.UUID;

public interface HistoryDao extends JpaRepository<HistoryEntity, UUID> {
    Page<HistoryEntity> findHistoryEntitiesByUsersId(UUID userId, Pageable pageable);
}
