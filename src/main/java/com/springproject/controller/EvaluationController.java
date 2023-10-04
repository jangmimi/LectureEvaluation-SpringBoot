package com.springproject.controller;

import com.springproject.model.Evaluation;
import com.springproject.model.Likey;
import com.springproject.model.User;
import com.springproject.service.EvaluationService;
import com.springproject.service.LikeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@SessionAttributes("loginUser")
@Controller
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private LikeyService likeyService;

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

    @PostMapping("/likeyAction/{evaluationID}")
    public String likeyAction(@RequestParam(required = false) Integer evaluationID,
                              HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("loginUser");
        String userId = user.getUserId();
        likeyService.like(evaluationID, userId, request);
        evaluationService.likeyCountUpdate(evaluationID);

        return "redirect:/";
    }
}
