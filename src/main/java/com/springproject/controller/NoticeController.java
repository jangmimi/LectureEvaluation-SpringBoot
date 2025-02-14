package com.springproject.controller;

import com.springproject.model.Freeboard;
import com.springproject.model.User;
import com.springproject.service.EvaluationService;
import com.springproject.service.FreeboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@SessionAttributes("loginUser")
@Slf4j
@Controller
public class NoticeController {
    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private FreeboardService freeboardService;

    @GetMapping("/notice")
    public String freeBoard(HttpSession session, Model model, Pageable pageable) {
        User user = (User) session.getAttribute("loginUser");
        List<Freeboard> list = freeboardService.getList();
        log.info(list.toString());
        model.addAttribute("list", list);
        return "freeboard/notice";
    }

}
