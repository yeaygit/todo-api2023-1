package com.example.todo.userapi.service;

import com.example.todo.userapi.dto.UserSignUpDTO;
import com.example.todo.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    //회원가입 처리
    public UserSignUpResponseDTO create(final UserSignUpDTO userSignUpDTO){
        if(userSignUpDTO==null){
            throw new RuntimeException("가입정보가 없습니다.");
        }
        final String email=userSignUpDTO.getEmail();
        if(userRepository.existsByEmail(email)){
            log.warn("Email already exists - {}",email);
            throw new RuntimeException("중복된 이메일입니다.");
        }

        //패스워드 인코딩
        String rawPassword = userSignUpDTO.getPassword(); //평문 암호
        String encodedPassword = passwordEncoder.encode(rawPassword); //암호화 처리
        userSignUpDTO.setPassword(encodedPassword);


        UserEntity savedUser = userRepository.save(userSignUpDTO.toEntity());

        log.info("회원가입 성공 !! - user_id : {}",savedUser.getId());
        return new UserSignUpResponseDTO(savedUser); //dto로 변경해서 보내주기
    }


    // 이메일 중복확인
    public boolean isDuplicate(String email) {
        if (email == null) {
            throw new RuntimeException("이메일 값이 없습니다.");
        }
        return userRepository.existsByEmail(email);
    }


    ///로그인 검증
    public UserEntity getByCredentials(final String email,
                                       final String rawPassword){

        //입력한 이메일을 통해 회원정보 조회
        UserEntity originalUser = userRepository.findByEmail(email);

        if(originalUser==null){
            throw new RuntimeException("가입된 회원이 아닙니다.");
        }

        //패스워드 검증(입력비번, db에 저장된 비번)
        if(!passwordEncoder.matches(rawPassword,originalUser.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        log.info("{}님 로그인 성공!",originalUser.getUserName());

        return originalUser;
    }

}



