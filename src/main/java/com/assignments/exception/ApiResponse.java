package com.assignments.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;

    public static ApiResponse error(String message) {
        return new ApiResponse(false, message);
    }

    public static ApiResponse success(String message) {
        return new ApiResponse(true, message);
    }
}
