package com.motivank.accounts.service.client;

import com.motivank.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/cards", consumes = "application/json")
    ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader String correlationId, @RequestParam String mobileNumber);

}
