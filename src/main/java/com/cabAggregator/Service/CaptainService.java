package com.cabAggregator.Service;

import com.cabAggregator.DTO.CaptainLoginDTO;
import com.cabAggregator.DTO.CaptainRegistrationDTO;
import com.cabAggregator.Model.Captain;
import com.cabAggregator.Repo.ICaptainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class CaptainService implements  ICaptainService  {


    @Autowired
    private final ICaptainRepository captainRepository;
    @Autowired
    private final ICaptainService iCaptainService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public CaptainService(ICaptainRepository captainRepository, ICaptainService iCaptainService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.captainRepository = captainRepository;
        this.iCaptainService = iCaptainService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Captain registrerCaptain(CaptainRegistrationDTO captainRegistrationDTO) {

        Captain captain=new Captain();
        captain.setEmail(captainRegistrationDTO.email());
        captain.setPassword(passwordEncoder.encode(captainRegistrationDTO.password()));
        captain.setDLNO(captainRegistrationDTO.DLNO());
        captain.setVehicle(captainRegistrationDTO.vehicle());


        return captainRepository.save(captain);
    }

    public Captain Login(CaptainLoginDTO captainLoginDTO){

        try{

            Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(captainLoginDTO.identifier(),captainLoginDTO.password()));

            if(authentication.isAuthenticated()){

            }

        }
        catch (Exception e){

        }
        return null;
    }

}
