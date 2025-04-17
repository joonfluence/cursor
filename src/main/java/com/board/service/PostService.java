package com.board.service;

import com.board.entity.PostEntity;
import com.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    public PostEntity findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }

    @Transactional
    public PostEntity save(PostEntity post) {
        return postRepository.save(post);
    }

    @Transactional
    public PostEntity update(Long id, PostEntity post) {
        PostEntity existingPost = findById(id);
        existingPost.update(post.getTitle(), post.getContent());
        return existingPost;
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
} 