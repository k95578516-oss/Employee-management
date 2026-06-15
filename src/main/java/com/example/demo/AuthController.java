package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtService service;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/login")
    public String login(
            @RequestBody User user){

        Authentication auth =
                manager.authenticate(

                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword()
                        )
                );

        if(auth.isAuthenticated()){

            User dbuser =
                    userRepository.findByUsername(
                            user.getUsername());

            return service.generateToken(
                    dbuser.getUsername(),
                    dbuser.getRole()
            );
        }

        return "Login Failed";
    }
    @PostMapping("/register")
    public String register(@RequestBody User user){

        user.setPassword(
                encoder.encode(user.getPassword())
        );

        userRepository.save(user);

        return "User Registered Successfully";
    }
}
