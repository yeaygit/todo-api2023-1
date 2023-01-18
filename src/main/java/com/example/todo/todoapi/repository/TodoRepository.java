package com.example.todo.todoapi.repository;

import com.example.todo.todoapi.entity.TodoEntity;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity,String> {
    //string 으로 기본키 설정했기 때문에 String
    //이름에 커서 올리고 ctrl shit t 테스트 생성
    
    
    //완료되지 않은 할일들만 조회 --> 이렇게 만들었으면 테스트 무조건 필요함
    @Query("select t from TodoEntity t where t.done=0")
    List<TodoEntity> findNotYetTodos();
}
