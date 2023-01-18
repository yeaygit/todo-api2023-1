package com.example.todo.todoapi.dto.request;

import com.example.todo.todoapi.entity.TodoEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoCreateRequestDTO {
    @NotBlank
    @Size(min=2,max=10)
    private String title;

    public TodoEntity toEntity(){
        return TodoEntity.builder().title(this.title).build();
    }
}
