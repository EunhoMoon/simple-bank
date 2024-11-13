package com.motivank.accounts.controller;

import com.motivank.accounts.constants.AccountsConstants;
import com.motivank.accounts.dto.CustomerDto;
import com.motivank.accounts.dto.ErrorResponseDto;
import com.motivank.accounts.dto.ResponseDto;
import com.motivank.accounts.service.AccountsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(
        name = "CRUD REST APIs for Accounts in SimpleBank",
        description = "CRUD REST APIs for managing customer accounts in SimpleBank"
)
public class AccountsController {

    private final AccountsService accountsService;

    @PostMapping
    @Operation(
            summary = "Create a new account",
            description = "Create a new account with the given customer details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP status code 201 (Created) returned after successfully creating a new account"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status code 500 (Internal Server Error) returned if an error occurred",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Retry(name="fetchAccountDetails", fallbackMethod = "fetchAccountDetailsFallback")
    @GetMapping
    @Operation(
            summary = "Fetch account details",
            description = "Fetch account details for the given mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status code 200 (OK) returned after successfully fetching account details"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP status code 404 (Not Found) returned if the account details are not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status code 500 (Internal Server Error) returned if an error occurred",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<CustomerDto> fetchAccountDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be a 10-digit number")
            String mobileNumber
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.fetchAccountDetails(mobileNumber));
    }


    public ResponseEntity<CustomerDto> fetchAccountDetailsFallback(String mobileNumber, Throwable throwable) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PutMapping
    @Operation(
            summary = "Update account details",
            description = "Update account details for the given customer"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status code 200 (OK) returned after successfully updating account details"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP status code 417 (Expectation Failed) returned if the update operation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status code 500 (Internal Server Error) returned if an error occurred",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);

        return ResponseEntity
                .status(isUpdated ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(
                        isUpdated ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_417,
                        isUpdated ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_417_UPDATE
                ));
    }

    @DeleteMapping
    @Operation(
            summary = "Delete account",
            description = "Delete account for the given mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status code 200 (OK) returned after successfully deleting the account"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP status code 417 (Expectation Failed) returned if the delete operation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status code 500 (Internal Server Error) returned if an error occurred",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<ResponseDto> deleteAccount(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be a 10-digit number")
            String mobileNumber
    ) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);

        return ResponseEntity
                .status(isDeleted ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(
                        isDeleted ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_417,
                        isDeleted ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_417_DELETE
                ));
    }

}
