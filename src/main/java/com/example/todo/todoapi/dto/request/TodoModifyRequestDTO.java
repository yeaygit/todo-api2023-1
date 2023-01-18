package com.example.todo.todoapi.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter @Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Builder

public class TodoModifyRequestDTO {
    @NotBlank
    @Size(min=2,max=10) //이 검증은 request에서만 client가 보낸 값이기 때문에
    private String title;
    private boolean done;
}
