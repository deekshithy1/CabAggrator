package com.cabAggregator.Utils;

import com.cabAggregator.DTO.UserDetailsDTO;
import com.cabAggregator.Service.JWTservice;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTfilter extends OncePerRequestFilter {


    private  final JWTservice jwTservice;
    public  JWTfilter( JWTservice jwTservice  ){
        this.jwTservice=jwTservice;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization=request.getHeader("Authorization");
        String token=null;
        String userid=null;
        UserDetailsDTO user=null;
        if(authorization!=null && authorization.startsWith("Bearer")){
            token=authorization.substring(7);
            userid= String.valueOf(jwTservice.extractUserDetails(token).id());
            user=jwTservice.extractUserDetails(token);
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,null,null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
filterChain.doFilter(request,response);
    }


}
