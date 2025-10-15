package com.cabAggregator.Controller;


import com.cabAggregator.DTO.UserLoginDTO;
import com.cabAggregator.DTO.UserRegDTO;
import com.cabAggregator.Model.Ride;
import com.cabAggregator.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @PostMapping("/login")
    public String welcom( @RequestBody  UserLoginDTO userLoginDTO){
        return iUserService.LoginUser(userLoginDTO) ;
    }

    @PostMapping("/register")
    public  String RegisterUser(@RequestBody UserRegDTO userRegDTO){
        return iUserService.RegisterUser(userRegDTO);
    }


}
