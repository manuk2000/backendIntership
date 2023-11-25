package com.picsart.intership.config.security.user;

import com.picsart.intership.entity.UserProfile;
import com.picsart.intership.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustamUserServiceDetails implements UserDetailsService {
    private final UserProfileService userProfileService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserProfile user = userProfileService.getUserByEmail(username);

        return new CustamUserDetails(user);
    }
}
