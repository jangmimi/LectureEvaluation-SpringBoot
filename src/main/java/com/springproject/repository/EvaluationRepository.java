package com.springproject.repository;

import com.springproject.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    @Override
    List<Evaluation> findAll();

    Optional<Evaluation> findByEvaluationID(Integer evaluationID);

    void deleteByEvaluationID(Integer evaluationID);
}
