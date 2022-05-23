package ru.sergeysemenov.webmarketspring.auth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sergeysemenov.webmarketspring.api.JwtRequest;
import ru.sergeysemenov.webmarketspring.api.JwtResponse;
import ru.sergeysemenov.webmarketspring.api.RegisterUserDto;
import ru.sergeysemenov.webmarketspring.auth.exceptions.AppError;
import ru.sergeysemenov.webmarketspring.auth.exceptions.RegistrationException;
import ru.sergeysemenov.webmarketspring.auth.services.UserService;
import ru.sergeysemenov.webmarketspring.auth.utils.JwtTokenUtil;



@RestController
@RequiredArgsConstructor
@RequestMapping()
@Slf4j
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError("CHECK_TOKEN_ERROR", "Некорректный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> signInNewUser(@RequestBody RegisterUserDto registerUserDto){
       try {
           userService.tryToSignInNewUser(registerUserDto);
       } catch (RegistrationException e){;
           return new ResponseEntity<>(new AppError("CHECK_USERNAME_OR_EMAIL", e.getMessage()), HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(HttpStatus.OK);
    }

}
