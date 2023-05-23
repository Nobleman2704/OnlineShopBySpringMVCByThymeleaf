package uz.jk.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.jk.domain.entity.history.HistoryEntity;
import uz.jk.domain.response.BaseResponse;
import uz.jk.service.history.HistoryService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/get_my_order_history")
    public String getMyOrderHistory(HttpSession session,
                                    Model model,
                                    @RequestParam(name = "page"
                                            , defaultValue = "0")int page){
        UUID userId = (UUID) session.getAttribute("userId");
        BaseResponse<List<HistoryEntity>> response = historyService.getMyOrderHistory(userId, page);
        model.addAttribute("histories", response.getData());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("pages", response.getTotalPages());
        return "history";
    }
}
