package com.springproject.service;

import com.springproject.model.Evaluation;
import com.springproject.repository.EvaluationRepository;
import com.springproject.repository.LikeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Transactional
    public Evaluation write(Evaluation evaluation, String userId) {
        evaluation.setUserId(userId);
        return evaluationRepository.save(evaluation);
    }

    public List<Evaluation> getList() {
        return evaluationRepository.findAll();
    }

    @Transactional
    public void delete(Integer id) {
        System.out.println(id + " 번 글 삭제 실행");
        evaluationRepository.deleteByEvaluationID(id);
    }

    @Transactional
    public boolean likeyCountUpdate(Integer evaluationID) {
        Optional<Evaluation> findEvalution = evaluationRepository.findByEvaluationID(evaluationID);
        if(findEvalution.isPresent()) {
            Evaluation find = findEvalution.get();
            int count = find.getLikeCount() + 1;
            System.out.println(count);
            find.setLikeCount(count);
            evaluationRepository.save(find);
            return true;
        }
        return false;
    }
}
