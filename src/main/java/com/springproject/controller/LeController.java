package com.springproject.controller;

import com.springproject.model.Evaluation;
import com.springproject.model.User;
import com.springproject.service.EvaluationService;
import com.springproject.service.UserService;
import com.springproject.config.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@SessionAttributes("loginUser")
@Slf4j
@Controller
public class LeController {

    @Autowired
    private UserService userService;

    @Autowired
    private EvaluationService evaluationService;

    @RequestMapping("/")
    public String index(@PageableDefault(size = 4) Pageable pageable,
                        @RequestParam(required = false) String searchTextTop, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        Long userNumber = null;
        if (user != null) {
            userNumber = user.getUserNumber();
        }

        Page<Evaluation> evaluationList = evaluationService.getListAllByPage(pageable);

        int startPage = Math.max(1, evaluationList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(evaluationList.getTotalPages(), evaluationList.getPageable().getPageNumber() + 4);
        long totalItems = evaluationList.getTotalElements(); // 총 항목 수

        model.addAttribute("evaluationList",evaluationList);
        model.addAttribute("evaluationListSize",totalItems);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("userNumber", userNumber);

        return "index";
    }

    @RequestMapping("/emailCheckAction")
    public String emailCheckAction(@RequestParam(required = false) String code,
                                   HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        boolean isRight = SHA256.getSHA256(loginUser.getUserEmail()).equals(code);

        if(isRight) {
            userService.setUserEmailChecked(loginUser.getUserId());
        }

        return "redirect:/";
    }

    @PostMapping("/reportAction")
    public String reportAction(@RequestParam(required = false) String reportTitle,
                               @RequestParam(required = false) String reportContent, HttpSession session) {
        String userId = ((User) session.getAttribute("loginUser")).getUserId();
        userService.reportEmail(reportTitle, reportContent, userId);

        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@PageableDefault(size = 4) Pageable pageable,
                         @RequestParam(required = false) String searchTextTop,
                         Model model, HttpSession session) {
        Page<Evaluation> evaluationList = null;

        if (searchTextTop == null) {
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

        log.info("검색어 : " + searchTextTop);

        return "index";
    }

    @GetMapping("/searchByType")
    public String searchByType(@RequestParam(required = false) String searchType,
                               @RequestParam(required = false, defaultValue = "") String searchText,
                               @PageableDefault(size = 4) Pageable pageable, Model model, HttpSession session) {
        Page<Evaluation> evaluationList = null;

        if (searchText == null) {
            evaluationList = evaluationService.getListAllByPage(pageable);

        } else {
            evaluationList = evaluationService.getList(searchType, searchText, pageable);
        }
        int startPage = Math.max(1, evaluationList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(evaluationList.getTotalPages(), evaluationList.getPageable().getPageNumber() + 4);
        long totalItems = evaluationList.getTotalElements(); // 총 항목 수

        model.addAttribute("evaluationList",evaluationList);
        model.addAttribute("evaluationListSize",totalItems);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        log.info("타입: " + searchType);
        log.info("검색어: " + searchText);

        return "index";
    }

//    @RequestMapping("/")
//    public String index(Model model, HttpSession session,
//                        @RequestParam(required = false) String lectureDivide,
//                        @RequestParam(required = false) String searchType,
//                        @RequestParam(required = false) String search,
//                        @PageableDefault(size = 3) Pageable pageable,
//                        @RequestParam(required = false, defaultValue = "") String searchText) {
//        User loginUser = (User) session.getAttribute("loginUser");
//
////        if (loginUser != null) {
////            String userEmail = userService.getUserEmail(loginUser.getUserNumber());
////
////            boolean emailChecked = userService.getUserEmailChecked(loginUser.getUserId());
////            if(!emailChecked){
////                return "emailSendConfirm";
////            }
//
//            Page<Evaluation> evaluationList = evaluationService.getListPaging(pageable, searchText);
//            int startPage = Math.max(1, evaluationList.getPageable().getPageNumber() - 4);
//            int endPage = Math.min(evaluationList.getTotalPages(), evaluationList.getPageable().getPageNumber() + 4);
//
//            long totalItems = evaluationList.getTotalElements(); // 총 항목 수
//
//            int pageSize = 5; // 페이지 당 항목 수
//
//            model.addAttribute("loginUser", session.getAttribute("loginUser"));
//            model.addAttribute("evaluationList",evaluationList);
//            model.addAttribute("evaluationListSize",totalItems);
//
//            model.addAttribute("startPage", startPage);
//            model.addAttribute("endPage", endPage);
//
////        }
////        else {
////            String alertScript = "<script>alert('로그인을 해주세요.'); location.href='/login';</script>";
////            model.addAttribute("alertScript", alertScript);
////            return "login";
////        }
//        return "index";
//    }



//    @RequestMapping("/search")
//    public String search(Model model, HttpSession session,
//                         @RequestParam(required = false, defaultValue = "") String searchText,
//                         @PageableDefault(size = 3) Pageable pageable) {
//        User loginUser = (User) session.getAttribute("loginUser");
//
//        if (loginUser != null) {
//            String userEmail = userService.getUserEmail(loginUser.getUserNumber());
//
//            boolean emailChecked = userService.getUserEmailChecked(loginUser.getUserId());
//            if(!emailChecked){
//                return "emailSendConfirm";
//            }
//
//            Page<xEvaluation> evaluationList = evaluationService.getListPaging(pageable, searchText);
//            int startPage = Math.max(1, evaluationList.getPageable().getPageNumber() - 4);
//            int endPage = Math.min(evaluationList.getTotalPages(), evaluationList.getPageable().getPageNumber() + 4);
//
//            long totalItems = evaluationList.getTotalElements(); // 총 항목 수
//
//            int pageSize = 5; // 페이지 당 항목 수
//
//            model.addAttribute("loginUser", session.getAttribute("loginUser"));
//            model.addAttribute("evaluationList",evaluationList);
//            model.addAttribute("evaluationListSize",totalItems);
//
//            model.addAttribute("startPage", startPage);
//            model.addAttribute("endPage", endPage);
//        } else {
//            String alertScript = "<script>alert('로그인을 해주세요.'); location.href='/login';</script>";
//            model.addAttribute("alertScript", alertScript);
//        }
//        return "index";
//    }
//
//    @RequestMapping("/searchByType")
//    public String searchByType(Model model, HttpSession session,
//                         @RequestParam(required = false) String lectureDivide,
//                         @RequestParam(required = false) String searchType,
//                         @RequestParam(required = false) String search,
//                         @RequestParam(defaultValue = "0") Integer pageNumber) {
//        User loginUser = (User) session.getAttribute("loginUser");
//
//        if (loginUser != null) {
//            String userEmail = userService.getUserEmail(loginUser.getUserId());
//
//            boolean emailChecked = userService.getUserEmailChecked(loginUser.getUserId());
//            if(!emailChecked){
//                return "emailSendConfirm";
//            }
//            int pageSize = 6; // 페이지 당 항목 수
//
//            Page<Evaluation> evaluationList = evaluationService.getList(lectureDivide, searchType, search, pageNumber, pageSize);
//            int totalPages = (int) Math.ceil((double) evaluationList.getContent().size() / pageSize);
//
//            model.addAttribute("loginUser", session.getAttribute("loginUser"));
//            model.addAttribute("evaluationList",evaluationList.getContent());
//            model.addAttribute("evaluationListSize",evaluationList.getContent().size());
//            model.addAttribute("pageNumber", pageNumber);
//        } else {
//            String alertScript = "<script>alert('로그인을 해주세요.'); location.href='/login';</script>";
//            model.addAttribute("alertScript", alertScript);
//        }
//        return "index";
//    }
}
