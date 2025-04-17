package com.board.service;

import com.board.dto.CommentDto;
import com.board.entity.CommentEntity;
import com.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentEntity> findByPostId(Long postId, String sortOrder) {
        List<CommentEntity> comments = commentRepository.findByPostId(postId);
        
        // 고정된 댓글을 먼저 정렬
        Comparator<CommentEntity> pinnedComparator = (c1, c2) -> {
            if (c1.isPinned() && !c2.isPinned()) return -1;
            if (!c1.isPinned() && c2.isPinned()) return 1;
            return 0;
        };

        // 생성 시간에 따른 정렬
        Comparator<CommentEntity> timeComparator = "desc".equals(sortOrder) ?
            Comparator.comparing(CommentEntity::getCreatedAt).reversed() :
            Comparator.comparing(CommentEntity::getCreatedAt);

        return comments.stream()
            .sorted(pinnedComparator.thenComparing(timeComparator))
            .collect(Collectors.toList());
    }

    public List<CommentEntity> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Transactional
    public CommentEntity create(CommentDto commentDto) {
        CommentEntity comment = CommentEntity.create(
            commentDto.getContent(),
            commentDto.getPostId(),
            commentDto.getParentId()
        );
        return commentRepository.save(comment);
    }

    @Transactional
    public void update(Long id, String content) {
        CommentEntity comment = commentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.update(content);
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public void togglePin(Long id) {
        CommentEntity comment = commentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.togglePin();
    }
}