package com.projectboard.controller;

import com.projectboard.domain.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/articles")
@Controller
public class ArticleController {

    @GetMapping
    public String articles(@ModelAttribute("articles") Article articles) {
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, Model model) {
        model.addAttribute("article", "article"); // TODO: 구현할 때 실제 데이터 넣어야 한다
        model.addAttribute("articleComments", List.of());

        return "articles/detail";
    }
}
