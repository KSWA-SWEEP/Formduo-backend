package com.sweep.formduo.web.dto.jwt;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String expTime;
}