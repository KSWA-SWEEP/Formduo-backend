package com.sweep.formduo.web.dto.members;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberIsMyPwDTO {
    private String email;
    private String password;
}
