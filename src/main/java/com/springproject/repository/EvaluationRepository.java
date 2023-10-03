package com.springproject.repository;

import com.springproject.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Override
    List<Evaluation> findAll();

    void deleteByEvaluationID(Integer evaluationID);
}
