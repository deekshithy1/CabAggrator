package com.cabAggregator.Controller;


import com.cabAggregator.DTO.CaptainLoginDTO;
import com.cabAggregator.DTO.CaptainRegistrationDTO;
import com.cabAggregator.Model.Captain;
import com.cabAggregator.Service.ICaptainService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CaptianController {


     private final ICaptainService captainService;

     @PostMapping("/captain-login")
    public ResponseEntity<?> LoginCaptain( @RequestBody  CaptainLoginDTO captainLoginDTO){

        try{
              String token=captainService.Login(captainLoginDTO);

              return new ResponseEntity<>(token, HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }
    @PostMapping("/captain-register")
    public ResponseEntity<?> RegisterCaptain( @RequestBody CaptainRegistrationDTO captainRegistrationDTO){

        try{
            Captain captain=captainService.registrerCaptain(captainRegistrationDTO);

            return new ResponseEntity<>(captain, HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

}

