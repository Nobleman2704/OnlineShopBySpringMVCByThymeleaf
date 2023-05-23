package uz.jk.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.jk.domain.dto.UserCreateDto;
import uz.jk.domain.dto.UserReadDto;
import uz.jk.domain.response.BaseResponse;
import uz.jk.service.user.UserService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/sign_up")
    public String sendToSignUpHtml() {
        return "sign-up";
    }

    @PostMapping("/sign_up")
    public String signUp(
            @ModelAttribute UserCreateDto createDto,
            Model model
    ) {
        BaseResponse baseResponse = userService.create(createDto);
        int status = baseResponse.getStatus();
        model.addAttribute("message", baseResponse.getMessage());
        if (status == 200) {
            return "sign-in";
        }
        return "sign-up";
    }

    @GetMapping("/sign_in")
    public String sendToSignIpHtml() {
        return "sign-in";
    }

    @PostMapping("/sign_in")
    public String signIn(
            @ModelAttribute UserCreateDto createDto,
            Model model,
            HttpSession session
    ) {
        BaseResponse<UserReadDto> baseResponse = userService.signIn(createDto);
        int status = baseResponse.getStatus();
        model.addAttribute("message", baseResponse.getMessage());
        if (status == 200) {
            session.setAttribute("userId", baseResponse.getData().getId());
            if (createDto.getRole().equals("SELLER"))return "seller";
            return "customer";
        }
        return "sign-in";
    }
}
