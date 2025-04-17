package com.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDto {
    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 500, message = "댓글은 최대 500자까지 입력 가능합니다.")
    private String content;

    private Long postId;
    private Long parentId;
    private boolean isPinned;
    private String sortOrder; // "asc" 또는 "desc"

    public CommentDto(String content, Long postId, Long parentId, boolean isPinned, String sortOrder) {
        this.content = content;
        this.postId = postId;
        this.parentId = parentId;
        this.isPinned = isPinned;
        this.sortOrder = sortOrder;
    }
} 