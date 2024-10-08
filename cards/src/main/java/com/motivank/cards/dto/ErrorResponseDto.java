package com.motivank.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Error response details"
)
public class ErrorResponseDto {

    @Schema(
            description = "API path",
            example = "/api/v1/accounts"
    )
    private String apiPath;

    @Schema(
            description = "HTTP status code",
            example = "400"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message",
            example = "Invalid input data"
    )
    private String errorMessage;

    @Schema(
            description = "Error time",
            example = "2024-09-15T10:15:30"
    )
    private LocalDateTime errorTime;

}
