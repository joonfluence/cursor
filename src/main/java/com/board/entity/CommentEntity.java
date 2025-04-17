package com.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Long postId;

    private Long parentId;

    @Column(nullable = false)
    private boolean isPinned;

    public static CommentEntity create(String content, Long postId, Long parentId) {
        CommentEntity comment = new CommentEntity();
        comment.content = content;
        comment.postId = postId;
        comment.parentId = parentId;
        comment.isPinned = false;
        return comment;
    }

    public void update(String content) {
        this.content = content;
    }

    public void togglePin() {
        this.isPinned = !this.isPinned;
    }
}