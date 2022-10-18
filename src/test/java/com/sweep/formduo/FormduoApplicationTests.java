package com.sweep.formduo;

import com.sweep.formduo.domain.auth.MemberAuth;
import com.sweep.formduo.domain.auth.Authority;
import com.sweep.formduo.domain.auth.AuthorityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FormduoApplicationTests {

    @Autowired
    AuthorityRepository authorityRepository;

    @Test
    void contextLoads() {
        authorityRepository.save(new Authority(MemberAuth.ROLE_USER));
        authorityRepository.save(new Authority(MemberAuth.ROLE_ADMIN));
    }

}
