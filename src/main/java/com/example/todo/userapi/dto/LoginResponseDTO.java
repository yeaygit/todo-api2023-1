package com.example.todo.userapi.dto;

import com.example.todo.userapi.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Setter @Getter
@ToString @AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class LoginResponseDTO {

    private String email;
    private String userName;
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDate joinDate;

    private String token;//인증 토큰

    private String message;//응답메세지

    //엔터티를 dto 로 변경
    public LoginResponseDTO(UserEntity userEntity,String token){
        this.email=userEntity.getEmail();
        this.userName=userEntity.getUserName();
        this.joinDate= LocalDate.from(userEntity.getJoinDate());
        this.token=token;
    }
}
