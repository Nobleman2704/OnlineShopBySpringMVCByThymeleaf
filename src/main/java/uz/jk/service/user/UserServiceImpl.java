package uz.jk.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.jk.dao.user.UserDao;
import uz.jk.domain.dto.UserCreateDto;
import uz.jk.domain.dto.UserReadDto;
import uz.jk.domain.entity.user.UserEntity;
import uz.jk.domain.exception.DataNotFoundException;
import uz.jk.domain.response.BaseResponse;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final ModelMapper modelMapper;


    @Override
    public BaseResponse create(UserCreateDto userCreateDto) {
        UserEntity userEntity = modelMapper.map(userCreateDto, UserEntity.class);
        int status;
        String message;
        try {
            userDao.save(userEntity);
            status = 200;
            message = "Success";
        } catch (Exception e) {
            message = e.getCause().getCause().getMessage();
            status = 400;
        }
        return BaseResponse.builder()
                .message(message)
                .status(status)
                .build();
    }

    @Override
    public UserReadDto findById(UUID id) {
        return modelMapper.map(userDao.findById(id).get(), UserReadDto.class);
    }

    @Override
    public BaseResponse delete(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<UserReadDto> signIn(UserCreateDto userCreateDto) {
        String password = userCreateDto.getPassword();
        String email = userCreateDto.getEmail();
        int status;
        String message;
        try {
            Optional<UserEntity> optionalUser = userDao.findUserEntityByEmail(email);
            UserEntity userEntity = optionalUser.get();
            if (Objects.equals(userEntity.getPassword(), password)) {
                status = 200;
                message = "Success";
                UserReadDto user = modelMapper.map(userEntity, UserReadDto.class);
                return BaseResponse.<UserReadDto>builder()
                        .status(status)
                        .message(message)
                        .data(user)
                        .build();
            }
            status = 404;
            message = "passwords wid not match";
        } catch (NoSuchElementException e) {
            status = 404;
            message = "Email not found: " + email;
        }
        return BaseResponse.<UserReadDto>builder()
                .status(status)
                .message(message)
                .build();
    }
}
