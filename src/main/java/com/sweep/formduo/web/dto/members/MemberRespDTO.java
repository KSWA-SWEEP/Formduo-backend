package com.sweep.formduo.web.dto.members;

import com.sweep.formduo.domain.members.Members;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRespDTO {

    private String email;
    private String username;

    public static MemberRespDTO of(Members members) {
        return new MemberRespDTO(members.getEmail(), members.getUsername());
    }
}