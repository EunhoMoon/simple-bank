package com.motivank.accounts.controller;

import com.motivank.accounts.dto.CustomerDetailsDto;
import com.motivank.accounts.dto.ErrorResponseDto;
import com.motivank.accounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/customer", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(
    name = "CRUD REST APIs for Customers in SimpleBank",
    description = "CRUD REST APIs for managing customer accounts in SimpleBank"
)
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @Operation(
        summary = "Fetch customer details",
        description = "Fetch customer details for the given mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP status code 200 (OK) returned after successfully fetching customer details"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP status code 500 (Internal Server Error) returned if an error occurred",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be a 10-digit number")
        String mobileNumber
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(customerService.fetchCustomerDetails(mobileNumber));
    }

}
