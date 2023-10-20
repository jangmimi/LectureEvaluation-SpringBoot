package com.springproject.controller;

import com.springproject.model.Evaluation;
import com.springproject.model.Evaluation2;
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
    public String evaluationRegisterAction(@RequestParam(required = false) String lectureId,
                                           @RequestParam(required = false) String lectureName,
                                           @RequestParam(required = false) String professorName,
                                           @RequestParam(required = false) String url,
                                           @ModelAttribute Evaluation2 evaluation, HttpSession session) {
        log.info("폼내용 : " + evaluation.toString());
        log.info(lectureName);
        log.info(professorName);
        log.info(url);
        User user = (User) session.getAttribute("loginUser");
        Long userNumber = user.getUserNumber();
        evaluationService.write(lectureId, lectureName, professorName, url, evaluation, userNumber);
        return "redirect:/lectures";
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
        Long userNumber = user.getUserNumber();
        log.info("userNumber : " + userNumber);
        likeyService.like(evaluationID, userNumber, request);
        evaluationService.likeyCountUpdate(evaluationID);

        return "redirect:/";
    }
}
