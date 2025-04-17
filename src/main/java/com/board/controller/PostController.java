package com.board.controller;

import com.board.entity.PostEntity;
import com.board.service.PostService;
import com.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/list";
    }

    @GetMapping("/new")
    public String createForm() {
        return "posts/create";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute PostEntity post) {
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("comments", commentService.findAllByPostId(id));
        return "posts/detail";
    }

    @GetMapping("/{id}/edit")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "posts/update";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute PostEntity post) {
        postService.update(id, post);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }
} 