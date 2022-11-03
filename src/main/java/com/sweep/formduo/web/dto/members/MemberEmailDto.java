package com.sweep.formduo.web.dto.members;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberEmailDto{
    private String email;
}
