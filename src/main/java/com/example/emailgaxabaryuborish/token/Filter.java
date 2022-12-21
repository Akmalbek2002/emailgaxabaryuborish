package com.example.emailgaxabaryuborish.token;

import com.example.emailgaxabaryuborish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println(request.getHeader("Auth"));
        String auth = request.getHeader("auth");
        if(auth!=null){
            boolean b = tokenGenerator.TokenTekshirish(auth);
            if (b){
                String username = tokenGenerator.Malumot(auth);
                if (username!=null){
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(usernamePasswordAuthenticationToken);
                }
                else{
                    System.out.println("username yaratilmadi");
                }
            }
        }
        filterChain.doFilter(request,response);

    }
}
