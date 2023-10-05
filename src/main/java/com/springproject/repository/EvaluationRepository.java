package com.springproject.repository;

import com.springproject.model.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

//    Page<Evaluation> findAll(Specification<Evaluation> spec, Pageable pageable);

    @Query("SELECT e FROM Evaluation e " +
            "WHERE (:lectureDivide IS NULL OR e.lectureDivide = :lectureDivide) " +
            "AND ((:searchType IS NULL) OR " +
            "    (:searchType = '최신순' AND e.lectureName LIKE %:search%) " +
            "    OR (:searchType = '추천순' AND e.likeCount > 0)) " +
            "ORDER BY CASE " +
            "    WHEN :searchType = '최신순' THEN e.evaluationID " +
            "    WHEN :searchType = '추천순' THEN e.likeCount " +
            "    ELSE e.evaluationID " +
            "END DESC")
    Page<Evaluation> findWithFiltersOrderBy(@Param("lectureDivide") String lectureDivide,
                                            @Param("searchType") String searchType,
                                            @Param("search") String search,
                                            Pageable pageable);


    long count(Specification<Evaluation> spec);

    @Query("SELECT COUNT(e) FROM Evaluation e " +
            "WHERE (:lectureDivide IS NULL OR e.lectureDivide = :lectureDivide) " +
            "AND ((:searchType IS NULL) OR " +
            "    (:searchType = '최신순' AND e.lectureName LIKE %:search%) " +
            "    OR (:searchType = '추천순' AND e.likeCount > 0))")
    long countWithFilters(@Param("lectureDivide") String lectureDivide,
                          @Param("searchType") String searchType,
                          @Param("search") String search);

}
