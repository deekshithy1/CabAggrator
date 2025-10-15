package com.cabAggregator.Controller;


import com.cabAggregator.DTO.RideDetailsDTO;
import com.cabAggregator.Model.Ride;
import com.cabAggregator.Service.IRideService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideController {

    @Autowired
    private IRideService rideService;

    public  RideController(IRideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/create-ride")
    public ResponseEntity<?> createRIde(@RequestBody RideDetailsDTO rideDetailsDTO, HttpServletRequest request){


        return new ResponseEntity<>( rideService.createRide(rideDetailsDTO), HttpStatus.OK);
    }
    @PostMapping("/cancel-ride/{rideId}")
    public Boolean CancelRide(@RequestParam String rideId){

        return rideService.cancelRide(rideId);
    }
}
