package com.sweep.formduo.domain.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "value", nullable = false, length = 255)
    private String value;

    public void updateValue(String token) {
        this.value = token;
    }

    @Builder
    public RefreshToken(String email, String value) {
        this.email = email;
        this.value = value;
    }
}