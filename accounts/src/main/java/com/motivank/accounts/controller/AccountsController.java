package com.motivank.accounts.controller;

import com.motivank.accounts.constants.AccountsConstants;
import com.motivank.accounts.dto.CustomerDto;
import com.motivank.accounts.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    @PostMapping("/accounts")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

}
