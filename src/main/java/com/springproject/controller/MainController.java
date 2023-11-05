package com.springproject.controller;

import com.springproject.model.Evaluation;
import com.springproject.model.User;
import com.springproject.service.EvaluationService;
import com.springproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@SessionAttributes("loginUser")
@Slf4j
@Controller
public class MainController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 4) Pageable pageable, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            Long userNumber = user.getUserNumber();
            model.addAttribute("userNumber", userNumber);
        }

        Page<Evaluation> evaluationList = evaluationService.getListAllByPage(pageable);

        int startPage = Math.max(1, evaluationList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(evaluationList.getTotalPages(), evaluationList.getPageable().getPageNumber() + 4);
        long totalItems = evaluationList.getTotalElements(); // 총 항목 수

        model.addAttribute("evaluationList",evaluationList);
        model.addAttribute("evaluationListSize",totalItems);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index";
    }

    @GetMapping("/search")
    public String search(@PageableDefault(size = 4) Pageable pageable,
                         @RequestParam(required = false) String searchTextTop,
                         Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            Long userNumber = user.getUserNumber();
            model.addAttribute("userNumber", userNumber);
        }

        Page<Evaluation> evaluationList = null;

        if (searchTextTop.isEmpty()) {
            evaluationList = evaluationService.getListAllByPage(pageable);

        } else {
            evaluationList = evaluationService.search(searchTextTop, pageable);
        }
        int startPage = Math.max(1, evaluationList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(evaluationList.getTotalPages(), evaluationList.getPageable().getPageNumber() + 4);
        long totalItems = evaluationList.getTotalElements(); // 총 항목 수

        model.addAttribute("evaluationList",evaluationList);
        model.addAttribute("evaluationListSize",totalItems);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("searchTextTop", searchTextTop);

        return "index";
    }

    @GetMapping("/searchByType")
    public String searchByType(@RequestParam(required = false) String searchType,
                               @RequestParam(required = false, defaultValue = "") String searchText,
                               @PageableDefault(size = 4) Pageable pageable, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            Long userNumber = user.getUserNumber();
            model.addAttribute("userNumber", userNumber);
        }

        Page<Evaluation> evaluationList = evaluationService.getList(searchType, searchText, pageable);

        int startPage = Math.max(1, evaluationList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(evaluationList.getTotalPages(), evaluationList.getPageable().getPageNumber() + 4);
        long totalItems = evaluationList.getTotalElements(); // 총 항목 수

        model.addAttribute("evaluationList",evaluationList);
        model.addAttribute("evaluationListSize",totalItems);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("searchText", searchText);

        log.info("타입: " + searchType);
        log.info("검색어: " + searchText);

        return "index";
    }
}
