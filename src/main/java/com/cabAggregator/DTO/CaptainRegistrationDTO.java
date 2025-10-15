package com.cabAggregator.DTO;

import com.cabAggregator.Model.Captain;

import com.cabAggregator.Model.Captain.Vehicle;
public record CaptainRegistrationDTO(String name, String email, String password, String DLNO, Vehicle vehicle) {
}
