package com.sweep.formduo.web.dto.members;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRemoveDTO {
    private String email;
    private String password;
}
