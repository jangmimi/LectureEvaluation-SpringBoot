package com.springproject.service;

import com.springproject.model.Evaluation;
import com.springproject.model.User;
import com.springproject.repository.EvaluationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Transactional
    public Evaluation write(Evaluation evaluation, User user) {
        evaluation.setUser(user);
        return evaluationRepository.save(evaluation);
    }

    public Page<Evaluation> getList(String searchType, String searchText, Pageable pageable) {
        return evaluationRepository.findWithFiltersOrderBy(searchType, searchText, pageable);
    }

    public Page<Evaluation> getListAllByPage(Pageable pageable) {
        return evaluationRepository.findAllByOrderByIdDesc(pageable);
    }

    @Transactional
    public Page<Evaluation> search(String searchText, Pageable pageable) {
        return evaluationRepository.findBySearchText(searchText, pageable);
    }

    @Transactional
    public void delete(Long id) {
        evaluationRepository.deleteById(id);
    }

    @Transactional
    public Evaluation update(Long id, Evaluation evaluation) {
        log.info("id : " + id);
        log.info("evaluation : " + evaluation.toString());

        Optional<Evaluation> find = evaluationRepository.findById(id);
        log.info("find : " + find);

        if (find.isPresent()) {
           Evaluation updated = find.get();

           updated.setEvaluationTitle(evaluation.getEvaluationTitle());
           updated.setEvaluationContent(evaluation.getEvaluationContent());

           return evaluationRepository.save(updated);

        } else {
            return null;
        }
    }

    @Transactional
    public boolean likeyCountUpdate(Long evaluationID) {
        Optional<Evaluation> find = evaluationRepository.findById(evaluationID);

        if(find.isPresent()) {
            Evaluation evaluation = find.get();
            int count = evaluation.getLikeCount() + 1;
            evaluation.setLikeCount(count);
            evaluationRepository.save(evaluation);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean likeyCountMinus(Long evaluationID) {
        Optional<Evaluation> find = evaluationRepository.findById(evaluationID);
        log.info("추천 취소~");
        if(find.isPresent()) {
            Evaluation evaluation = find.get();
            int count = evaluation.getLikeCount() - 1;
            evaluation.setLikeCount(count);
            evaluationRepository.save(evaluation);
            return true;
        }
        return false;
    }
}
