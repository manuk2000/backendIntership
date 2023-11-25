package com.picsart.intership.service;

import com.picsart.intership.entity.UserProfile;
import com.picsart.intership.repo.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public Optional<UserProfile> getUserProfile(Long userId) {
        return userProfileRepository.findById(userId);
    }

    public UserProfile getUserByEmail(String email) {
        Optional<UserProfile> user = userProfileRepository.findByEmail(email);
        return user.orElse(null);
    }

    public boolean updateUserProfile(Long userId, UserProfile updatedUserProfile) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findById(userId);
        if (optionalUserProfile.isPresent()) {
            UserProfile userProfile = optionalUserProfile.get();
            userProfile.setName(updatedUserProfile.getName());
            userProfile.setEmail(updatedUserProfile.getEmail());
            userProfileRepository.save(userProfile);
            return true;
        }
        return false;
    }

    public UserProfile createUser(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public boolean deleteUser(Long userId) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findById(userId);
        if (optionalUserProfile.isPresent()) {
            UserProfile userProfile = optionalUserProfile.get();
            userProfileRepository.delete(userProfile);
            return true;
        }
        return false;
    }
}

