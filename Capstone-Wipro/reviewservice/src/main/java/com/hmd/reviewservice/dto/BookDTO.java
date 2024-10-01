package com.hmd.reviewservice.dto;

public class BookDTO {
    private Long BookId;
    private String title;
    private String author;
    private boolean borrowed;

    // Constructors
    public BookDTO() {}

    public BookDTO(Long BookId, String title, String author, boolean borrowed) {
        this.BookId = BookId;
        this.title = title;
        this.author = author;
        this.borrowed = borrowed;
    }

    // Getters and Setters
    public Long getId() {
        return BookId;
    }

    public void setId(Long id) {
        this.BookId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
}
