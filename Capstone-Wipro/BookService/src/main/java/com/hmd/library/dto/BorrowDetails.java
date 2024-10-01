package com.hmd.library.dto;

import java.time.LocalDate;

public class BorrowDetails {
    private Long userId;
    private LocalDate borrowDate;

    // Constructors
    public BorrowDetails() {}

    public BorrowDetails(Long userId, LocalDate borrowDate) {
        this.userId = userId;
        this.borrowDate = borrowDate;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
}
