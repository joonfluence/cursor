package com.board.service;

import com.board.dto.CommentDto;
import com.board.entity.CommentEntity;
import com.board.entity.PostEntity;
import com.board.repository.CommentRepository;
import com.board.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("대댓글 생성 시 parentId가 null이 아니면 저장 가능해야 함")
    void createReply() {
        // given
        PostEntity post = new PostEntity(null, "테스트 게시글", "테스트 내용", null);
        PostEntity savedPost = postRepository.save(post);

        CommentDto parentCommentDto = CommentDto.builder()
                .content("부모 댓글")
                .postId(savedPost.getId())
                .parentId(null)
                .build();
        CommentEntity parentComment = commentService.create(parentCommentDto);

        CommentDto replyCommentDto = CommentDto.builder()
                .content("대댓글")
                .postId(savedPost.getId())
                .parentId(parentComment.getId())
                .build();

        // when
        CommentEntity replyComment = commentService.create(replyCommentDto);

        // then
        assertThat(replyComment.getParentId()).isEqualTo(parentComment.getId());
        assertThat(replyComment.getContent()).isEqualTo("대댓글");
    }

    @Test
    @DisplayName("게시글의 댓글 목록 조회")
    void findByPostId() {
        // given
        PostEntity post = new PostEntity(null, "테스트 게시글", "테스트 내용", null);
        PostEntity savedPost = postRepository.save(post);

        CommentDto commentDto1 = CommentDto.builder()
                .content("댓글1")
                .postId(savedPost.getId())
                .parentId(null)
                .build();
        commentService.create(commentDto1);

        CommentDto commentDto2 = CommentDto.builder()
                .content("댓글2")
                .postId(savedPost.getId())
                .parentId(null)
                .build();
        commentService.create(commentDto2);

        // when
        List<CommentEntity> comments = commentService.findByPostId(savedPost.getId(), "desc");

        // then
        assertThat(comments).hasSize(2);
    }

    @Test
    @DisplayName("댓글 수정")
    void update() {
        // given
        PostEntity post = new PostEntity(null, "테스트 게시글", "테스트 내용", null);
        PostEntity savedPost = postRepository.save(post);

        CommentDto commentDto = CommentDto.builder()
                .content("원래 댓글")
                .postId(savedPost.getId())
                .parentId(null)
                .build();
        CommentEntity savedComment = commentService.create(commentDto);

        // when
        commentService.update(savedComment.getId(), "수정된 댓글");

        // then
        CommentEntity updatedComment = commentRepository.findById(savedComment.getId())
                .orElseThrow();
        assertThat(updatedComment.getContent()).isEqualTo("수정된 댓글");
    }

    @Test
    @DisplayName("댓글 삭제")
    void delete() {
        // given
        PostEntity post = new PostEntity(null, "테스트 게시글", "테스트 내용", null);
        PostEntity savedPost = postRepository.save(post);

        CommentDto commentDto = CommentDto.builder()
                .content("삭제할 댓글")
                .postId(savedPost.getId())
                .parentId(null)
                .build();
        CommentEntity savedComment = commentService.create(commentDto);

        // when
        commentService.delete(savedComment.getId());

        // then
        assertThat(commentRepository.findById(savedComment.getId())).isEmpty();
    }
}