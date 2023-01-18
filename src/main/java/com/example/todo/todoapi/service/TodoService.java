package com.example.todo.todoapi.service;

import com.example.todo.todoapi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoapi.dto.request.TodoModifyRequestDTO;
import com.example.todo.todoapi.dto.response.TodoDetailResponseDTO;
import com.example.todo.todoapi.dto.response.TodoListResponseDTO;
import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j

@RequiredArgsConstructor // 생성자 자동
public class TodoService {

    private final TodoRepository todoRepository;

    //할일 목록 조회
    public TodoListResponseDTO retrieve(){
        List<TodoEntity> entityList = todoRepository.findAll();

        //entitylist를 dto로
        List<TodoDetailResponseDTO> dtoList = entityList.stream()
                .map(te -> new TodoDetailResponseDTO(te))
                .collect(Collectors.toList());
        return TodoListResponseDTO.builder()
                .todos(dtoList)
                .build();
    }

    //할 일 등록
    public TodoListResponseDTO create(final TodoCreateRequestDTO createRequestDTO){
        //dto를 entity로 변환해서 저장
        todoRepository.save(createRequestDTO.toEntity());
        log.info("할 일이 저장 되었습니다. 제목 : {}", createRequestDTO.getTitle());
        return retrieve();
    }

    //할 일 수정(제목, 할일 완료여부)
    public TodoListResponseDTO update(final String id, final TodoModifyRequestDTO modifyRequestDTO){
        //수정타깃 조회를 먼저해야함
        Optional<TodoEntity> targetEntity = todoRepository.findById(id);

        targetEntity.ifPresent(entity -> {
            entity.setTitle(modifyRequestDTO.getTitle());
            entity.setDone(modifyRequestDTO.isDone());
            todoRepository.save(entity);
        });

        return  retrieve();
    }

    //할 일 삭제
    public TodoListResponseDTO delete(final String id){
        try{
            todoRepository.deleteById(id);
        }catch (Exception e){
            log.error("id가 존재하지 않아 삭제에 실패해습니다. - ID ; {},err: {}" //서버에
                        ,id,e.getMessage());

            throw new RuntimeException("id가 존재하지 않아 삭제에 실패해습니다.");//클라이언트에 알려줄 메세지
        }
        return  retrieve();
    }
}
