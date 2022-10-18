package com.sweep.formduo.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,String> {
    Optional<Authority> findByAuthorityName(MemberAuth authorityName);
}