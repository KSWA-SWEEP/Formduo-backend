package com.sweep.formduo.domain.members;

import com.sweep.formduo.domain.auth.Authority;
import com.sweep.formduo.domain.auth.MemberAuth;
import com.sweep.formduo.domain.survey_resps.SurveyResps;
import com.sweep.formduo.domain.surveys.Surveys;
import com.sweep.formduo.web.dto.members.MemberUpdateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin("*")
@Table(name = "member")
@Entity
public class Members {

    @JsonIgnore
    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "username",length = 50, nullable = false)
    private String username;

    // Email 을 토큰의 ID로 관리하기 때문에 unique = True
    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    @Size(min = 5, max = 100)
    private String password;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @JsonIgnore
    @Column(name = "del_yn")
    private char delYn;

    @JsonIgnore
    @CreatedDate
    @Column(name="reg_dt", updatable = false)
    private Instant reg_dt;

    @JsonIgnore
    @LastModifiedDate
    @Column(name="upd_dt")
    private Instant upd_dt;

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name="member_id",referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name",referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "members")
    List<Surveys> surveysList = new ArrayList<Surveys>();


    @Builder
    public Members(String username, String email, String password, boolean activated, char delYn,
                   Set<Authority> authorities, Instant reg_dt, Instant upd_dt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.activated = activated;
        this.delYn = delYn;
        this.authorities = authorities;
        this.reg_dt = reg_dt;
        this.upd_dt = upd_dt;
    }

    public void addAuthority(Authority authority) {
        this.getAuthorities().add(authority);
    }

    public void removeAuthority(Authority authority) {
        this.getAuthorities().remove(authority);
    }

    public void activate(boolean flag) {
        this.activated = flag;
    }

    public String getAuthoritiesToString() {
        return this.authorities.stream()
                .map(Authority::getAuthorityName)
                .collect(Collectors.joining(","));
    }

    public void updateMember(MemberUpdateDTO dto, PasswordEncoder passwordEncoder) {
        if(dto.getPassword() != null) this.password = passwordEncoder.encode(dto.getPassword());
        if(dto.getUsername() != null) this.username = dto.getUsername();
        this.upd_dt = Instant.now();
//        if(dto.getAuthorities().size() > 0) {
//            this.authorities = dto.getAuthorities().stream()
//                    .filter(MemberAuth::containsKey)
//                    .map(MemberAuth::get)
//                    .map(Authority::new)
//                    .collect(Collectors.toSet());
//        }
    }

    public void remove(char delYn){
        this.delYn = delYn;
    }
}
