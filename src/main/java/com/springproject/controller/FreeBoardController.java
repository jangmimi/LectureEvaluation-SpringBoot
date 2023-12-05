package com.springproject.controller;

import com.springproject.model.Evaluation;
import com.springproject.model.Freeboard;
import com.springproject.model.User;
import com.springproject.service.EvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class FreeBoardController {
    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/freeboard")
    public String freeBoard(HttpSession session, Model model, Pageable pageable) {
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            Long userNumber = user.getUserNumber();
            model.addAttribute("userNumber", userNumber);
        }

/*        Page<Evaluation> evaluationList = evaluationService.getListAllByPage(pageable);

        int startPage = Math.max(1, evaluationList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(evaluationList.getTotalPages(), evaluationList.getPageable().getPageNumber() + 4);
        long totalItems = evaluationList.getTotalElements(); // 총 항목 수

        model.addAttribute("evaluationList",evaluationList);
        model.addAttribute("evaluationListSize",totalItems);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);*/

        return "freeboard/freeboard";
    }

    @GetMapping("/write")
    public String freeboard(@ModelAttribute Freeboard freeboard) {
        log.info("freeboard : " + freeboard);
        return "freeboard/write";
    }
}
