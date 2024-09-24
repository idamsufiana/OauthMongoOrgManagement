package com.ida.management.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.ida.management.config.JWTUtils;
import com.ida.management.dto.IdTokenRequestDto;
import com.ida.management.entities.Account;
import com.ida.management.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final JWTUtils jwtUtils;
    private final GoogleIdTokenVerifier verifier;

    @Value("${app.googleClientId}")
    private String clientId;


    public AccountService(@Value("${app.googleClientId}")
                          String clientId, AccountRepository accountRepository,
                          JWTUtils jwtUtils) {
        this.accountRepository = accountRepository;
        this.jwtUtils = jwtUtils;
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    public Account getAccount(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    public String loginOAuthGoogle(IdTokenRequestDto requestBody) {
        Account account = verifyIDToken(requestBody.getIdToken());
        if (account == null) {
            throw new IllegalArgumentException();
        }
        account = createOrUpdateUser(account);
        return jwtUtils.createToken(account, false);
    }


    public Account createOrUpdateUser(Account account) {
        Account existingAccount = accountRepository.findByEmail(account.getEmail()).orElse(null);
        if (existingAccount == null) {
            List<String> roles = new ArrayList<>();
            roles.add(0, "ROLE_USER");
            account.setRole(roles);
            accountRepository.save(account);
            return account;
        }
        existingAccount.setFirstName(account.getFirstName());
        existingAccount.setLastName(account.getLastName());
        existingAccount.setPhone(account.getPhone());
        accountRepository.save(existingAccount);
        return existingAccount;
    }

    private Account verifyIDToken(String idToken) {
        try {
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj == null) {
                return null;
            }
            GoogleIdToken.Payload payload = idTokenObj.getPayload();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String email = payload.getEmail();
            String phone = (String) payload.get("phone");

            return new Account(firstName, lastName, email, phone);
        } catch (GeneralSecurityException | IOException e) {
            return null;
        }
    }
}
