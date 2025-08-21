package com.example.movieworld.security;

import com.example.movieworld.user.domain.User;
import com.example.movieworld.user.domain.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        String userEmail = annotation.userEmail();
        String userName = annotation.userName();

        User user = User.builder()
                .userName(userName)
                .userEmail(userEmail)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
