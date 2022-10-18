package com.sweep.formduo.domain.surveys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveysRepository extends JpaRepository<Surveys, Integer> {
//    List<Surveys> findAllByEmail(String email);

    @Query(" select s from Surveys s where s.delYn = 'N' and s.email = ?1 ")
    public List<Surveys> findAllByEmail(String email);

}