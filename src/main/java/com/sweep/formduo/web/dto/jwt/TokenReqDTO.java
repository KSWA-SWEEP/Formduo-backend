package com.sweep.formduo.web.dto.jwt;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenReqDTO {
    private String accessToken;
    private String refreshToken;
}
