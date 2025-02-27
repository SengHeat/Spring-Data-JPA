package com.project.api.service;


import com.project.api.exception.NotFoundException;
import com.project.api.model.base.PaginatedResponse;
import com.project.api.model.entity.PostEntity;
import com.project.api.model.request.PostRequest;
import com.project.api.model.response.PostResponse;
import com.project.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostEntity create(PostRequest request) throws Exception {
        PostEntity post = new PostEntity();

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        try {
            return this.postRepository.save(post);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public PostEntity findOne(String idStr) {
        try {
            Long id = Long.parseLong(idStr);
            return this.postRepository.findById(id).orElseThrow(() -> new
                    NotFoundException("No post found with the provided ID: " + id)
            );
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }

    public PaginatedResponse<PostResponse> findAll(Pageable pageable) {
        Page<PostEntity> page = postRepository.findAll(pageable);
        return new PaginatedResponse<>(
                page.getContent().stream().map(PostResponse::fromEntity).collect(Collectors.toList()),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty()
        );
    }


    public PostEntity update(PostRequest request, String idStr) {

        PostEntity post = findOne(idStr);
        post.setTitle(request.getTitle());
        if(request.getContent() != null && !request.getContent().isEmpty()) {
            post.setContent(request.getContent());
        }
        return this.postRepository.save(post);
    }


    public PostEntity delete(String idStr) {
        PostEntity post = findOne(idStr);
        this.postRepository.delete(post);
        return post;
    }

}
