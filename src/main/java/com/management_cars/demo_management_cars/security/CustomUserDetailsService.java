package com.management_cars.demo_management_cars.security;

import com.management_cars.demo_management_cars.entity.User;
import com.management_cars.demo_management_cars.repository.UserRepository;
import com.management_cars.demo_management_cars.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new CustomUserDetails(user);
    }

    public String getTokenAuthenticated(String username) {
        return JwtTokenUtils.generateToken(username);
    }
}

