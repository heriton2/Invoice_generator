package com.challenge.invoice_generator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDto {
    private String errorCode;
    private String errorMessage;
    private String errorCause;

    public ErrorResponseDto(String errorCode, String errorMessage, String errorCause) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorCause = errorCause;
    }
}
