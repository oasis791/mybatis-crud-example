package com.kimhyunwook.mybatiscommunity.controller;

import com.kimhyunwook.mybatiscommunity.repository.BoardRepository;
import com.kimhyunwook.mybatiscommunity.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
