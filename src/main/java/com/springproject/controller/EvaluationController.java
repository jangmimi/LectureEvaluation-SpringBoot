package com.springproject.controller;

import com.springproject.model.Evaluation;
import com.springproject.model.User;
import com.springproject.service.EvaluationService;
import com.springproject.service.LikeyService;
import com.springproject.service.UserService;
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

    @Autowired
    private UserService userService;

    @PostMapping("/evaluationRegisterAction")
    @ResponseBody
    public boolean evaluationRegisterAction(@ModelAttribute Evaluation evaluation, HttpSession session) {
    User user = (User) session.getAttribute("loginUser");
    Long userNumber = user.getUserNumber();

    Evaluation registered = evaluationService.write(evaluation, user);
    return registered != null;
}

    @PostMapping("/deleteAction/{id}")
    public String delete(@RequestParam(required = false) Long id) {
        evaluationService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/updateAction")
    @ResponseBody
    public boolean updateAction(@RequestParam(required = false) Long id, @ModelAttribute Evaluation evaluation) {
        Evaluation updated = evaluationService.update(id, evaluation);
        return updated != null;
    }

    @PostMapping("/likeyAction/{evaluationID}")
    public String likeyAction(@RequestParam(required = false) Long evaluationID,
                              HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("loginUser");
        Long userNumber = user.getUserNumber();

        likeyService.like(evaluationID, userNumber, request);
        evaluationService.likeyCountUpdate(evaluationID);

        return "redirect:/";
    }

    @PostMapping("/reportAction")
    public String reportAction(@RequestParam(required = false) String reportTitle,
                               @RequestParam(required = false) String reportContent, HttpSession session) {
        String userId = ((User) session.getAttribute("loginUser")).getUserId();
        userService.reportEmail(reportTitle, reportContent, userId);

        return "redirect:/";
    }
}
