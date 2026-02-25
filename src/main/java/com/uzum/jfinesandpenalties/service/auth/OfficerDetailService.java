package com.uzum.jfinesandpenalties.service.auth;

import com.uzum.jfinesandpenalties.repository.OfficerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OfficerDetailService implements UserDetailsService {
    OfficerRepository officerRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String badgeNumber) throws UsernameNotFoundException {
        return officerRepository.findByBudgeNumber(badgeNumber);
    }
}
