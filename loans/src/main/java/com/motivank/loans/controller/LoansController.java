package com.motivank.loans.controller;

import com.motivank.loans.constants.LoansConstants;
import com.motivank.loans.dto.ErrorResponseDto;
import com.motivank.loans.dto.LoansDto;
import com.motivank.loans.dto.ResponseDto;
import com.motivank.loans.service.LoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(
    name = "CRUD REST APIs for Loans in SimpleBank",
    description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoansController {

    private final LoansService loansService;

    @Operation(
        summary = "Create Loan REST API",
        description = "REST API to create new loan inside SimpleBank"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createLoan(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        String mobileNumber) {
        loansService.createLoan(mobileNumber);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @Operation(
        summary = "Fetch Loan Details REST API",
        description = "REST API to fetch loan details based on a mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    @GetMapping
    public ResponseEntity<LoansDto> fetchLoanDetails(
        @RequestHeader String correlationId,
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        String mobileNumber) {
        log.info("Correlation ID: {}", correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(loansService.getLoan(mobileNumber));
    }

    @Operation(
        summary = "Update Loan Details REST API",
        description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "417",
            description = "Expectation Failed"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    @PutMapping
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
        boolean isUpdated = loansService.updateLoan(loansDto);
        return ResponseEntity
            .status(isUpdated ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED)
            .body(new ResponseDto(
                isUpdated ? LoansConstants.STATUS_200 : LoansConstants.STATUS_417,
                isUpdated ? LoansConstants.MESSAGE_200 : LoansConstants.MESSAGE_417_UPDATE
            ));
    }

    @Operation(
        summary = "Delete Loan Details REST API",
        description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "417",
            description = "Expectation Failed"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteLoanDetails(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        String mobileNumber
    ) {
        boolean isDeleted = loansService.deleteLoan(mobileNumber);

        return ResponseEntity
            .status(isDeleted ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED)
            .body(new ResponseDto(
                isDeleted ? LoansConstants.STATUS_200 : LoansConstants.STATUS_417,
                isDeleted ? LoansConstants.MESSAGE_200 : LoansConstants.MESSAGE_417_DELETE
            ));
    }

}
