package com.board.service;

import com.board.entity.Post;
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

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post update(Long id, Post post) {
        Post existingPost = findById(id);
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        return existingPost;
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
} 