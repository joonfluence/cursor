package com.board.controller;

import com.board.dto.CommentDto;
import com.board.entity.Comment;
import com.board.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentService commentService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CommentController(commentService))
                .build();
    }

    @Test
    @DisplayName("게시글의 댓글 목록 조회")
    void getComments() throws Exception {
        // given
        Comment comment1 = Comment.builder()
                .id(1L)
                .content("댓글1")
                .build();

        Comment comment2 = Comment.builder()
                .id(2L)
                .content("댓글2")
                .build();

        given(commentService.findByPostId(1L)).willReturn(Arrays.asList(comment1, comment2));

        // when & then
        mockMvc.perform(get("/api/comments/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("댓글 생성")
    void createComment() throws Exception {
        // given
        CommentDto commentDto = CommentDto.builder()
                .content("새 댓글")
                .postId(1L)
                .build();

        Comment savedComment = Comment.builder()
                .id(1L)
                .content("새 댓글")
                .build();

        given(commentService.create(any(CommentDto.class))).willReturn(savedComment);

        // when & then
        mockMvc.perform(post("/api/comments/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"새 댓글\",\"postId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("새 댓글"));
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment() throws Exception {
        // given
        CommentDto commentDto = CommentDto.builder()
                .content("수정된 댓글")
                .build();

        doNothing().when(commentService).update(eq(1L), eq("수정된 댓글"));

        // when & then
        mockMvc.perform(put("/api/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"수정된 댓글\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception {
        // given
        doNothing().when(commentService).delete(1L);

        // when & then
        mockMvc.perform(delete("/api/comments/1"))
                .andExpect(status().isOk());
    }
}