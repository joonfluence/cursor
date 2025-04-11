package com.board.controller;

import com.board.entity.Post;
import com.board.service.PostService;
import com.board.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postService, commentService))
                .build();
    }

    @Test
    @DisplayName("GET /posts 요청 시 정상 200 응답과 JSON 응답이 와야 함")
    void list() throws Exception {
        // given
        Post post1 = new Post(null, "제목1", "내용1", null);
        Post post2 = new Post(null, "제목2", "내용2", null);

        given(postService.findAll()).willReturn(Arrays.asList(post1, post2));

        // when & then
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/list"))
                .andExpect(model().attributeExists("posts"));
    }

    @Test
    @DisplayName("게시글 상세 조회")
    void detail() throws Exception {
        // given
        Post post = new Post(1L, "제목", "내용", null);

        given(postService.findById(1L)).willReturn(post);

        // when & then
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/detail"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("comments"));
    }

    @Test
    @DisplayName("게시글 수정 폼")
    void updateForm() throws Exception {
        // given
        Post post = new Post(1L, "제목", "내용", null);

        given(postService.findById(1L)).willReturn(post);

        // when & then
        mockMvc.perform(get("/posts/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/update"))
                .andExpect(model().attributeExists("post"));
    }
}