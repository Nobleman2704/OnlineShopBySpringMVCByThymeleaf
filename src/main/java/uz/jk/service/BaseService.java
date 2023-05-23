package uz.jk.service;

import org.springframework.stereotype.Service;
import uz.jk.domain.response.BaseResponse;

import java.util.UUID;

/**
 * @param <CD> This is Creation DTO
 * @param <RD> This is Read DTO
 * @author Asadbek
 */
@Service
public interface BaseService<CD, RD> {
    BaseResponse create(CD cd);

    RD findById(UUID id);

    BaseResponse delete(UUID id);
}
