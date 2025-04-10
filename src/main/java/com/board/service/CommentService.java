package com.board.service;

import com.board.dto.CommentDto;
import com.board.entity.Comment;
import com.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
    }

    @Transactional
    public Comment create(CommentDto commentDto) {
        Comment comment = Comment.create(
            commentDto.getContent(),
            commentDto.getPostId(),
            commentDto.getParentId()
        );
        return commentRepository.save(comment);
    }

    @Transactional
    public void update(Long id, String content) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.update(content);
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}