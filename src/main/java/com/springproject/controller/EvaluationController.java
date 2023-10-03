package com.springproject.controller;

import com.springproject.model.Evaluation;
import com.springproject.model.User;
import com.springproject.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@SessionAttributes("loginUser")
@Controller
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/evaluationRegisterAction")
    public String evaluationRegisterAction(@ModelAttribute Evaluation evaluation, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        String userId = user.getUserId();
        evaluationService.write(evaluation, userId);
        return "redirect:/";
    }

    @PostMapping("/deleteAction/{id}")
    public String delete(@RequestParam(required = false) Integer id) {
        evaluationService.delete(id);
        return "redirect:/";
    }

}
