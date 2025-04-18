package com.board.service;

import com.board.entity.PostEntity;
import com.board.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 생성 시 createdAt이 null이 아니어야 함")
    void createPost() {
        // given
        PostEntity post = new PostEntity(null, "테스트 제목", "테스트 내용", null);

        // when
        PostEntity savedPost = postService.save(post);

        // then
        assertThat(savedPost.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void findAll() {
        // given
        PostEntity post1 = new PostEntity(null, "제목1", "내용1", null);
        postRepository.save(post1);

        PostEntity post2 = new PostEntity(null, "제목2", "내용2", null);
        postRepository.save(post2);

        // when
        List<PostEntity> posts = postService.findAll();

        // then
        assertThat(posts).hasSize(2);
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회 시 예외 발생")
    void findById_notFound() {
        // when & then
        assertThatThrownBy(() -> postService.findById(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 게시글이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("게시글 수정")
    void update() {
        // given
        PostEntity post = new PostEntity(null, "원래 제목", "원래 내용", null);
        PostEntity savedPost = postRepository.save(post);

        PostEntity updatePost = new PostEntity(null, "수정된 제목", "수정된 내용", null);

        // when
        PostEntity updatedPost = postService.update(savedPost.getId(), updatePost);

        // then
        assertThat(updatedPost.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatedPost.getContent()).isEqualTo("수정된 내용");
    }

    @Test
    @DisplayName("게시글 삭제")
    void delete() {
        // given
        PostEntity post = new PostEntity(null, "삭제할 게시글", "삭제할 내용", null);
        PostEntity savedPost = postRepository.save(post);

        // when
        postService.delete(savedPost.getId());

        // then
        assertThat(postRepository.findById(savedPost.getId())).isEmpty();
    }
}