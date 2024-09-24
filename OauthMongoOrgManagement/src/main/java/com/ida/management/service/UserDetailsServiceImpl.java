package com.ida.management.service;

import com.ida.management.entities.Account;
import com.ida.management.entities.UserPrincipal;
import com.ida.management.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Account Not Found with email: " + email));

        return new UserPrincipal(user);
    }

}