package com.board.repository;

import com.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostId(Long postId);
    List<CommentEntity> findAllByPostId(Long postId);
    List<CommentEntity> findByPostIdOrderByCreatedAtAsc(Long postId);
    List<CommentEntity> findByPostIdAndParentIdIsNullOrderByCreatedAtAsc(Long postId);
    List<CommentEntity> findByParentIdOrderByCreatedAtAsc(Long parentId);
}