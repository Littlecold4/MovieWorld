package com.example.movieworld.security;

import com.example.movieworld.user.User;
import com.example.movieworld.user.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

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
                = new UsernamePasswordAuthenticationToken(userDetails, "password",userDetails.getAuthorities());
        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
