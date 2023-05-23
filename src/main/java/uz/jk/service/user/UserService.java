package uz.jk.service.user;

import org.springframework.stereotype.Service;
import uz.jk.domain.dto.UserCreateDto;
import uz.jk.domain.dto.UserReadDto;
import uz.jk.domain.response.BaseResponse;
import uz.jk.service.BaseService;

@Service
public interface UserService extends BaseService<UserCreateDto, UserReadDto> {
    BaseResponse<UserReadDto> signIn(UserCreateDto userCreateDto);

}
