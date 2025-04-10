package com.board.repository;

import com.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);
    List<Comment> findByPostIdAndParentIdIsNullOrderByCreatedAtAsc(Long postId);
    List<Comment> findByParentIdOrderByCreatedAtAsc(Long parentId);
}