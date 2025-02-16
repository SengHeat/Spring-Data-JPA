package com.project.api.model.base;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;

@JsonPropertyOrder({"status", "message", "data"}) // Ensures status appears first
public class ApiResponse {
    private int status;
    private String message;
    private Object data;

    public ApiResponse(String message, int status, Object data) {
        this.status = status;
        this.message = message.isEmpty() ? (data == null) ? "Not Found" : "successfully" : message;
        this.data = (data == null) ? new HashMap<>() : data;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
