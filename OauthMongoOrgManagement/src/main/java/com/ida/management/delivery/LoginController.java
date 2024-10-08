package com.ida.management.delivery;

import com.ida.management.dto.IdTokenRequestDto;
import com.ida.management.entities.Account;
import com.ida.management.service.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.ida.management.dto.AccountDto.convertToDto;

@RestController
@RequestMapping("/v1/oauth")
public class LoginController {

    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity LoginWithGoogleOauth2(@RequestBody IdTokenRequestDto requestBody, HttpServletResponse response) {
        String authToken = accountService.loginOAuthGoogle(requestBody);
        final ResponseCookie cookie = ResponseCookie.from("AUTH-TOKEN", authToken)
                .httpOnly(true)
                .maxAge(7 * 24 * 3600)
                .path("/")
                .secure(false)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/user/info")
    public ResponseEntity getUserInfo(Principal principal) {
        Account account = accountService.getAccount(principal.getName());
        return ResponseEntity.ok().body(convertToDto(account));
    }
}
