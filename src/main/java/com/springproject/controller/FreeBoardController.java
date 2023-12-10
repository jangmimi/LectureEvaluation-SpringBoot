package com.springproject.controller;

import com.springproject.model.Evaluation;
import com.springproject.model.Freeboard;
import com.springproject.model.User;
import com.springproject.service.EvaluationService;
import com.springproject.service.FreeboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@SessionAttributes("loginUser")
@Slf4j
@Controller
public class FreeBoardController {
    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private FreeboardService freeboardService;

    @GetMapping("/freeboard")
    public String freeBoard(HttpSession session, Model model, Pageable pageable) {
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            Long userNumber = user.getUserNumber();
            model.addAttribute("userNumber", userNumber);
        }
        return "freeboard/freeboard";
    }

    @GetMapping("/write")
    public String freeboard(HttpSession session) {
        log.info("write 페이지");
        User user= (User) session.getAttribute("loginUser");
        log.info(user.getUserId());
        return "freeboard/write";
    }

    @PostMapping("/writeAction")
    @ResponseBody
    public int writeProc(@RequestParam(required = false) String title,
                         @RequestParam(required = false) String content, HttpSession session) {
        log.info("write 컨트롤러");
        User user= (User) session.getAttribute("loginUser");
        log.info("title : " + title);
        Freeboard result = freeboardService.write(title, content, user);
        return result != null ? 1 : 0;
    }
}
