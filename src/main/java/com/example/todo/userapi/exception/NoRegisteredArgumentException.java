package com.example.todo.userapi.exception;

public class NoRegisteredArgumentException extends  RuntimeException{
    // 기본 생성자

    // 에러메세지를 처리하는 생성자
    public NoRegisteredArgumentException(String message){
        super(message);
    }
}
