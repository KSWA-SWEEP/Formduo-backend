package com.sweep.formduo.web.dto.users;

import com.sweep.formduo.domain.users.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;

/**
 * A DTO for the {@link Users} entity
 */
@Setter
@Getter
@Schema(description = "유저 생성 요청 DTO")
@Data
public class UsersRequestDto {
//    @Schema(description = "고유 아이디")
//    private final Integer id;
    @Schema(description = "이메일", nullable = false, example = "abc@jiniworld.me")
    private String email;

    @Schema(description = "패스워드", nullable = false)
    private String password;

    @Builder
    public UsersRequestDto(String email, String password){
        this.email = email;
        this.password = password;

    }

    public UsersRequestDto(){};

    public Users toEntity() {
        return Users.builder()
                .email(email)
                .password(password)
                .delYn('N')
                .regDt(Instant.now())
                .updDt(Instant.now())
                .build();
    }
}