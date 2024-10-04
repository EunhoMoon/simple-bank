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
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    private final AccountsService accountsService;

    @PostMapping("/accounts")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/accounts")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.fetchAccountDetails(mobileNumber));
    }

}
