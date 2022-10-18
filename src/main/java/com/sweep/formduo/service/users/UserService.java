package com.sweep.formduo.service.users;


import com.sweep.formduo.domain.users.Users;
import com.sweep.formduo.domain.users.UsersRepository;
import com.sweep.formduo.web.dto.users.UsersRequestDto;
import com.sweep.formduo.web.dto.users.UsersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UsersRepository usersRepository;

    @Transactional
    public Integer save(UsersRequestDto requestDto) {
//        Users entity = usersRepository.findByEmail(requestDto.getEmail());
//        if(entity.is){
//            throw new EmailDuplicateException("email duplicated", ErrorCode.EMAIL_DUPLICATION);
//        }
//        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return usersRepository.save(requestDto.toEntity()).getId();
    }


    public UsersResponseDto findById(int id){
        Users entity = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다. id ="+ id));
        return new UsersResponseDto(entity);}

//    public Users findByEmail(String email) {
//        Users alreadyUser = usersRepository.findByEmail(email)
//        return alreadyUser;
//    }

    @Transactional
    public String remove(int id) {
        Users entity = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다. id ="+ id));

        entity.remove('Y');
        usersRepository.save(entity);
        return entity.getEmail();
    }
}
