package com.board.controller;

import com.board.dto.CommentDto;
import com.board.entity.CommentEntity;
import com.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentEntity>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        return ResponseEntity.ok(commentService.findByPostId(postId, sortOrder));
    }

    @PostMapping("/posts/{postId}")
    public ResponseEntity<CommentEntity> createComment(
            @PathVariable Long postId,
            @RequestBody @Validated CommentDto commentDto) {
        return ResponseEntity.ok(commentService.create(commentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long id,
            @RequestBody @Validated CommentDto commentDto) {
        commentService.update(id, commentDto.getContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/pin")
    public ResponseEntity<Void> togglePin(@PathVariable Long id) {
        commentService.togglePin(id);
        return ResponseEntity.ok().build();
    }
}