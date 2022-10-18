package com.sweep.formduo.web.dto.users;

import com.sweep.formduo.domain.users.Users;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link Users} entity
 */
@Data
public class UsersResponseDto implements Serializable {

    private final String email;
    private final Instant regDt;

    @Builder
    public UsersResponseDto(Users entity){
        this.email = entity.getEmail();
        this.regDt = entity.getRegDt();
    }
}