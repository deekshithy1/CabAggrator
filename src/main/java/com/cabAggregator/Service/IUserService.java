package com.cabAggregator.Service;

import com.cabAggregator.DTO.UserLoginDTO;
import com.cabAggregator.DTO.UserRegDTO;

public interface IUserService {

    public String LoginUser(UserLoginDTO userLoginDTO);

   public String RegisterUser(UserRegDTO userRegDTO);
}
