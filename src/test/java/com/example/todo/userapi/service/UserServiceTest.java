package com.example.todo.userapi.service;

import com.example.todo.userapi.dto.LoginResponseDTO;
import com.example.todo.userapi.dto.UserSignUpDTO;
import com.example.todo.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.userapi.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("중복된 이메일이 포함된 회원정보로 가입하면 RuntimeException이 발생해야한다.")
    void validateEmailTest(){
        //given
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .email("abc1234@def.com")
                .password("34924")
                .userName("히쭉히쭉")
                .build();

        // when
//        userService.create(dto);

        // then + when
        assertThrows(RuntimeException.class,()->{
            userService.create(dto);

        });
    }

    @Test
    @DisplayName("검증된 회원정보로 가입하면 회원가입에 성공해야한다.")
    void createTest(){
        // given
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .email("zzzz9876@def.com")
                .password("12345")
                .userName("암호맨")
                .build();

        //when
        UserSignUpResponseDTO responseDTO = userService.create(dto);
        //then
        System.out.println("responseDTO = " + responseDTO);
        assertEquals("암호맨", responseDTO.getUserName());

    }


    @Test
    @DisplayName("존재하지 않는 이메일로 로그인을 시도하면 exception이 발생해야한다.")
    void noUserTest(){
        //given
        String email="elkfjsei3@glie.com";
        String password="1234";


        //then
        assertThrows(RuntimeException.class,()->{
            //when
            userService.getByCredentials(email,password);
        });
    }




    @Test
    @DisplayName("틀린 비밀번호로 로그인을 시도하면 exception이 발생해야한다.")
    void invalidPasswordTest(){
        //given
        String email="postman@naver.com";
        String password="wer324gerg";


        //then
        assertThrows(RuntimeException.class,()->{
            //when
            userService.getByCredentials(email,password);
        });
    }


    @Test
    @DisplayName("정확한 정보로 로그인을 시도하면 회원정보가 반환되어야한다.")
    void loginTest(){
        //given
        String email="kkk123@gmail.com";
        String password="kkk12345!";


        //when
        LoginResponseDTO loginUser=userService.getByCredentials(email,password);

        //then
        assertEquals("백구",loginUser.getUserName());
        System.out.println(loginUser);
    }
}