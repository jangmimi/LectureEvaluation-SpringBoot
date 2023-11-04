package com.springproject.controller;

import com.springproject.model.Lecture;
import com.springproject.service.LectureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@SessionAttributes("loginUser")
@Slf4j
@Controller
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @GetMapping("/lectures")
    public String lectures(@PageableDefault(page = 0, size = 12, direction = Sort.Direction.DESC) Pageable pageable, Model model) throws Exception {
        Page<Lecture> lectureList = lectureService.getLectureDataByPage(pageable.getPageNumber(), pageable);

        int totalPages = lectureList.getTotalPages();
        int currentPage = pageable.getPageNumber();
        int startPage = currentPage / 10 * 10;
        int endPage = Math.min(startPage + 9, totalPages - 1);
        int previous = currentPage - 1;
        int next = currentPage + 1;

        model.addAttribute("lectures", lectureList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);

        return "lecture";
    }

//    public String lectures(Model model,
//                            @PageableDefault(page = 0, size = 24, direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
//        Page<Lecture> lectureList = lectureService.getLectureDataByPage(pageable);
////        model.addAttribute("loginUser", session.getAttribute("loginUser"));
//        model.addAttribute("lectures", lectureList);
//        model.addAttribute("page", lectureList);
//        model.addAttribute("previous", lectureList.hasPrevious() ? lectureList.previousPageable().getPageNumber() : -1);
//        model.addAttribute("next", lectureList.hasNext() ? lectureList.nextPageable().getPageNumber() : lectureList.getTotalPages());
//        model.addAttribute("startPage", lectureList.getNumber() / 10 * 10);
//        model.addAttribute("endPage", Math.min(lectureList.getNumber() / 10 * 10 + 9, lectureList.getTotalPages() - 1));
//
//        log.info("전체페이지수 : " + lectureList.getTotalPages());
//
//        return "lecture";
//    }
//    public String lectures(Model model, HttpSession session) throws Exception {
//        List<Lecture> lectureList = lectureService.getLectureData();
//        model.addAttribute("loginUser", session.getAttribute("loginUser"));
//        model.addAttribute("lectures", lectureList);
//        return "lecture";
//    }
}
