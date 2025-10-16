package com.cabAggregator.DTO;

import com.cabAggregator.Model.Captain;

import com.cabAggregator.Model.Captain.Vehicle;
public record CaptainRegistrationDTO(String fullname, String email, String password, String DLNO, Vehicle vehicle,String mobileNumber) {
}
