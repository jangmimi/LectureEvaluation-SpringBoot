package com.springproject.repository;

import com.springproject.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    // 최근 등록된 순서대로 정렬된 리스트
    List<Evaluation> findAllByOrderByEvaluationIDDesc();

    List<Evaluation> findAllByOrderByLikeCountDesc();

    Optional<Evaluation> findByEvaluationID(Integer evaluationID);

    void deleteByEvaluationID(Integer evaluationID);
}
