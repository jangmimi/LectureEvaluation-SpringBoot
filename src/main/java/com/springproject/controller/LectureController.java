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

    @RequestMapping("/lectures")
    public String lectures(@PageableDefault(page = 0, size = 12, direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(value = "page", defaultValue = "0") int page, Model model) throws Exception {
        Page<Lecture> lectureList = lectureService.getLectureDataByPage(page, pageable);

        int totalPages = lectureList.getTotalPages() - 1;
        int previousPage = page - 1;
        int nextPage = page + 1;
        int startPage = (page - 1) / 10 * 10;
        int endPage = Math.min(startPage + 9, totalPages - 1);

        model.addAttribute("lectures", lectureList);
        model.addAttribute("previous", lectureList.hasPrevious() ? previousPage : -1);
        model.addAttribute("next", lectureList.hasNext() ? nextPage : totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("currentPage", page);

        log.info("현재 페이지 : " + page);
        log.info("시작 페이지 : " + startPage);
        log.info("끝 페이지 : " + endPage);

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
