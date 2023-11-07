package com.springproject.repository;

import com.springproject.model.Likey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeyRepository extends JpaRepository<Likey, Long> {
    // 추천 여부 확인 메서드
    Likey findByEvaluationIdAndUserUserNumber(Long evaluationId, Long userNumber);
}
