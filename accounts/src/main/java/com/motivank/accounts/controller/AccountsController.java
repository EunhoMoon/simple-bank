package com.motivank.accounts.controller;

import com.motivank.accounts.constants.AccountsConstants;
import com.motivank.accounts.dto.CustomerDto;
import com.motivank.accounts.dto.ResponseDto;
import com.motivank.accounts.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    private final AccountsService accountsService;

    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.fetchAccountDetails(mobileNumber));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);

        return ResponseEntity
                .status(isUpdated ? HttpStatus.OK : HttpStatus.NOT_MODIFIED)
                .body(new ResponseDto(
                        isUpdated ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_417,
                        isUpdated ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_417_UPDATE
                ));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String mobileNumber) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);

        return ResponseEntity
                .status(isDeleted ? HttpStatus.OK : HttpStatus.NOT_MODIFIED)
                .body(new ResponseDto(
                        isDeleted ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_417,
                        isDeleted ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_417_DELETE
                ));
    }

}
