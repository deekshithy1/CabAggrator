package com.cabAggregator.Service;

import com.cabAggregator.DTO.UserLoginDTO;
import com.cabAggregator.DTO.UserRegDTO;
import com.cabAggregator.Model.User;
import com.cabAggregator.Repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private  final JWTservice jwTservice;
    private final AuthenticationManager authenticationManager;
   private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(AuthenticationManager authenticationManager, JWTservice jwTservice, IUserRepository iUserRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwTservice=jwTservice;
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String LoginUser(UserLoginDTO userLoginDTO) {
        try {
            // ✅ Authenticate using AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.email(),
                            userLoginDTO.password()
                    )
            );
            CustomUsers userDetails = (CustomUsers) authentication.getPrincipal();
             if(!authentication.isAuthenticated()){
                 throw new RuntimeException("Invalid Credentials");

             }

     return jwTservice.GenerateToken(userDetails);
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials or authentication failed", e);
        }

    }

    @Override
    public String RegisterUser( @RequestBody  UserRegDTO userRegDTO) {

        // Check if email already exists
        Optional<User> existingUser = iUserRepository.findByEmail(userRegDTO.email());
        if (existingUser.isPresent()) {
            return "Email already registered!";
        }

        // Create new user
        User user = new User();
        user.setEmail(userRegDTO.email());
        user.setPassword(passwordEncoder.encode(userRegDTO.password())); // ✅ Encode password
        user.setName(userRegDTO.name());

        // Save to DB
        iUserRepository.save(user);

        return "User registered successfully!";
    }
}
