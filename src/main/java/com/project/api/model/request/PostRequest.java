package com.project.api.model.request;

import com.project.api.model.entity.CommentEntity;
import com.project.api.model.entity.PostEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class PostRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 30, message = "Title must be at most 30 characters long")
    private String title;

    @Size(max = 30, message = "Content must be at most 30 characters long")
    private String content;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostEntity toEntity() {  // Remove 'static' to access instance variables
        PostEntity entity = new PostEntity();
        entity.setTitle(this.title);
        entity.setContent(this.content);
        return entity;
    }
}
