package com.springproject.service;

import com.springproject.model.Evaluation;
import com.springproject.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Transactional
    public Evaluation write(Evaluation evaluation, Long userNumber) {
        evaluation.setUserNumber(userNumber);
        return evaluationRepository.save(evaluation);
    }

    public List<Evaluation> getListAll() {
        return evaluationRepository.findAll();
    }

    public Page<Evaluation> getListAllByPage(Pageable pageable) {
        return evaluationRepository.findAllByOrderByIdDesc(pageable);
    }

    @Transactional
    public void delete(Long id) {
        evaluationRepository.deleteById(id);
    }


//    public Page<xEvaluation> getListPaging(Pageable pageable, String searchText) {
////        return evaluationRepository.findAll(pageable);
//        return evaluationRepository.findByEvaluationTitleContainingOrEvaluationContentContaining(searchText, searchText, pageable);
//    }

//    public Page<Evaluation> getList(String lectureDivide, String searchType, String search, Integer pageNumber, int pageSize) {
//        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "evaluationID");
//        Sort sort = Sort.by(order);
//
//        Specification<Evaluation> spec = Specification.where(null);
//
//        if (lectureDivide != null && !lectureDivide.equals("전체")) {
//            spec = spec.and((root, query, builder) -> builder.equal(root.get("lectureDivide"), lectureDivide));
//        }
//
//        if ("최신순".equals(searchType)) {
//            Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "evaluationID");
//            sort = Sort.by(order2);
//        } else if ("추천순".equals(searchType)) {
//            Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "likeCount");
//            sort = Sort.by(order2);
//        }
//
//        if (search != null && !search.isEmpty()) {
//            // 검색어가 입력된 경우, 필드를 OR로 검색하도록 조건 추가
//            spec = spec.and((root, query, builder) -> builder.or(
//                    builder.like(root.get("lectureName"), "%" + search + "%"),
//                    builder.like(root.get("professorName"), "%" + search + "%"),
//                    builder.like(root.get("evaluationTitle"), "%" + search + "%"),
//                    builder.like(root.get("evaluationContent"), "%" + search + "%")
//            ));
//        }
//
//        // 페이지 번호에 따른 결과 가져오기
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//        return evaluationRepository.findAll(spec, pageable);
//    }

//    public int getTotalItems(String lectureDivide, String searchType, String search) {
//        Specification<Evaluation> spec = Specification.where(null);
//
//        if (lectureDivide != null && !lectureDivide.equals("전체")) {
//            spec = spec.and((root, query, builder) -> builder.equal(root.get("lectureDivide"), lectureDivide));
//        }
//
//        if (search != null && !search.isEmpty()) {
//            // 검색어가 입력된 경우, 필드를 OR로 검색하도록 조건 추가
//            spec = spec.and((root, query, builder) -> builder.or(
//                    builder.like(root.get("lectureName"), "%" + search + "%"),
//                    builder.like(root.get("professorName"), "%" + search + "%"),
//                    builder.like(root.get("evaluationTitle"), "%" + search + "%"),
//                    builder.like(root.get("evaluationContent"), "%" + search + "%")
//            ));
//        }
//        return (int) evaluationRepository.count(spec);
//    }

//    @Transactional
//    public boolean likeyCountUpdate(Integer evaluationID) {
//        Optional<xEvaluation> findEvalution = evaluationRepository.findByEvaluationID(evaluationID);
//        if(findEvalution.isPresent()) {
//            xEvaluation find = findEvalution.get();
//            int count = find.getLikeCount() + 1;
//            find.setLikeCount(count);
//            evaluationRepository.save(find);
//            return true;
//        }
//        return false;
//    }


}
