package com.sweep.formduo.domain.qbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QboxRepository extends JpaRepository<Qbox, Integer> {
//    List<Surveys> findAllByEmail(String email);


}