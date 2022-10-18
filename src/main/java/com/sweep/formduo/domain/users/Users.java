package com.sweep.formduo.domain.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_USER")
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Integer id;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 255)
    @Size(min = 10, max = 100)
    private String password;

    @Column(name = "DEL_YN")
    private Character delYn;

    @Column(name = "REG_DT")
    private Instant regDt;

    @Column(name = "UPD_DT")
    private Instant updDt;

    @Builder
    public Users(String email, String password, char delYn, Instant regDt, Instant updDt){
        this.email = email;
        this.password = password;
        this.delYn = delYn;
        this.regDt = regDt;
        this.updDt = updDt;
    }

    public void update(Instant updDt){
        this.updDt = updDt;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void remove(char delYn){
        this.delYn = delYn;
    }


}