package com.cabAggregator.Service;

import com.cabAggregator.Model.Captain;
import com.cabAggregator.Model.User;
import com.cabAggregator.Repo.ICaptainRepository;
import com.cabAggregator.Repo.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository iUserRepository;
    private final ICaptainRepository captainRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1️⃣ Try finding a normal user first
        User user = iUserRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return new CustomUsers(user);  // your wrapper for UserDetails
        }

        // 2️⃣ If not found, try Captain
        Captain captain = captainRepository.findByEmail(email).orElse(null);
        if (captain != null) {
            return new CustomUsers(captain);  // overload constructor for Captain
        }

        // 3️⃣ If neither found, throw exception
        throw new UsernameNotFoundException("No user or captain found with email: " + email);
    }
}
