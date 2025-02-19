package com.project.api.model.base;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;


@JsonPropertyOrder({"currentPage", "totalPages", "totalElements", "isFirstPage", "isLastPage" , "empty", "firstPage", "lastPage", "lists"})
public class PaginatedResponse<T> {
    private List<T> lists;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean isEmpty;

    public PaginatedResponse(List<T> lists, int currentPage, int totalPages, long totalElements, boolean isFirstPage, boolean isLastPage, boolean isEmpty) {
        this.lists = lists;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.isFirstPage = isFirstPage;
        this.isLastPage = isLastPage;
        this.isEmpty = isEmpty;
    }

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
