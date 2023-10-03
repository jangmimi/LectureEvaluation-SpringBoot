package com.springproject.service;

import com.springproject.model.Evaluation;
import com.springproject.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
}
