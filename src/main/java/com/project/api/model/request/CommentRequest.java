package com.project.api.model.request;

import com.project.api.model.entity.CommentEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentRequest {
    @NotBlank(message = "Text is required")
    @Size(max = 30, message = "Comment must by at most 30 characters long")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommentEntity toEntity() {
        CommentEntity comment = new CommentEntity();
        comment.setText(this.text);
        return comment;
    }

}
