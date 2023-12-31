package com.springproject.repository;

import com.springproject.model.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Optional<Evaluation> findById(Long id);

    Page<Evaluation> findAllByOrderByIdDesc(Pageable pageable);

    // 우상단 검색바에서 검색 쿼리 메서드 (최신순)
    @Query("SELECT e FROM Evaluation e WHERE " +
            "(:searchText IS NULL OR :searchText = '' OR " +
            "e.lectureSubject LIKE %:searchText% OR " +
            "e.lectureInfo LIKE %:searchText% OR " +
            "e.evaluationTitle LIKE %:searchText% OR " +
            "e.evaluationContent LIKE %:searchText%) " +
            "ORDER BY e.id DESC")
    Page<Evaluation> findBySearchText(@Param("searchText") String searchText, Pageable pageable);

    // 강의평가 페이지 내 검색 쿼리 메서드
    @Query("SELECT e FROM Evaluation e " +
            "WHERE ((:searchType IS NULL OR :searchText = '') OR " +
            "    (:searchType = '최신순' AND e.lectureSubject LIKE %:searchText% " +
            "       OR e.lectureInfo LIKE %:searchText% " +
            "       OR e.evaluationTitle LIKE %:searchText% " +
            "       OR e.evaluationContent LIKE %:searchText%) " +
            "     OR (:searchType = '추천순' AND " +
            "      (e.lectureSubject LIKE %:searchText% " +
            "      OR e.lectureInfo LIKE %:searchText% " +
            "      OR e.evaluationTitle LIKE %:searchText% " +
            "      OR e.evaluationContent LIKE %:searchText%))) " +
            "ORDER BY CASE " +
            "    WHEN :searchType = '최신순' THEN e.id " +
            "    WHEN :searchType = '추천순' THEN e.likeCount " +
            "    ELSE e.id " +
            "END DESC")
    Page<Evaluation> findWithFiltersOrderBy(@Param("searchType") String searchType,
                                            @Param("searchText") String searchText,
                                            Pageable pageable);
}

//    // 최근 등록된 순서대로 정렬된 리스트
////    List<Evaluation> findAllByOrderByEvaluationIDDesc();
////    List<Evaluation> findAllByOrderByLikeCountDesc();
//
//    Optional<xEvaluation> findByEvaluationID(Integer evaluationID);
//    void deleteByEvaluationID(Integer evaluationID);
//
//    Page<xEvaluation> findByEvaluationTitleContainingOrEvaluationContentContaining(String evaluationTitle, String evaluationContent, Pageable pageable);
//
////    @Query("SELECT e FROM Evaluation e " +
////            "WHERE (:lectureDivide IS NULL OR e.lectureDivide = :lectureDivide) " +
////            "AND ((:searchType IS NULL) OR " +
////            "    (:searchType = '최신순' AND e.lectureName LIKE %:search%) " +
////            "    OR (:searchType = '추천순' AND e.likeCount > 0)) " +
////            "ORDER BY CASE " +
////            "    WHEN :searchType = '최신순' THEN e.evaluationID " +
////            "    WHEN :searchType = '추천순' THEN e.likeCount " +
////            "    ELSE e.evaluationID " +
////            "END DESC")
////    Page<Evaluation> findWithFiltersOrderBy(@Param("lectureDivide") String lectureDivide,
////                                            @Param("searchType") String searchType,
////                                            @Param("search") String search,
////                                            Pageable pageable);
////
////
//
//}
