package com.motivank.accounts.controller;

import com.motivank.accounts.constants.AccountsConstants;
import com.motivank.accounts.dto.CustomerDto;
import com.motivank.accounts.dto.ResponseDto;
import com.motivank.accounts.service.AccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    private final AccountsService accountsService;

    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping
    public ResponseEntity<CustomerDto> fetchAccountDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be a 10-digit number")
            String mobileNumber
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.fetchAccountDetails(mobileNumber));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);

        return ResponseEntity
                .status(isUpdated ? HttpStatus.OK : HttpStatus.NOT_MODIFIED)
                .body(new ResponseDto(
                        isUpdated ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_417,
                        isUpdated ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_417_UPDATE
                ));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAccount(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be a 10-digit number")
            String mobileNumber
    ) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);

        return ResponseEntity
                .status(isDeleted ? HttpStatus.OK : HttpStatus.NOT_MODIFIED)
                .body(new ResponseDto(
                        isDeleted ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_417,
                        isDeleted ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_417_DELETE
                ));
    }

}
