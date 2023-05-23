package uz.jk.domain.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BaseResponse<T> {
    private int status;
    private String message;
    private int totalPages;
    private T data;
}
