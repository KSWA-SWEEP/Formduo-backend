package com.sweep.formduo.domain.members;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members,Long> {
    Optional<Members> findByEmail(String email);
    boolean existsByEmail(String email);
}