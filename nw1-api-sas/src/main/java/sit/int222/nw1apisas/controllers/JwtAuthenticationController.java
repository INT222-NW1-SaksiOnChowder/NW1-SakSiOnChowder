package sit.int222.nw1apisas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import sit.int222.nw1apisas.config.JwtTokenUtil;

import sit.int222.nw1apisas.dtos.jwt.AccessTokenResponse;
import sit.int222.nw1apisas.dtos.jwt.JwtRequest;
import sit.int222.nw1apisas.dtos.jwt.JwtResponse;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.repositories.UserRepository;
import sit.int222.nw1apisas.services.JwtUserDetailsService;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/token")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;



    @GetMapping("")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader(name = "Authorization") String refreshTokenWithHeader) {
        String refreshToken = jwtTokenUtil.extractRefreshTokenFromHeaders(refreshTokenWithHeader);
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token is missing");
        }
        if (jwtTokenUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token has expired");
        }

        // If the refresh token is valid
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(refreshToken));
        if (jwtTokenUtil.validateToken(refreshToken, userDetails)) {
            // create new access token
            String newAccessToken = jwtTokenUtil.generateToken(userDetails);

            // Return accessTokenDto response
            return ResponseEntity.ok(new AccessTokenResponse(newAccessToken));
        }

        // If refresh token is invalid
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
    }


    @PostMapping("")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        System.out.println(authenticationRequest);
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String accessToken = jwtTokenUtil.generateToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    }



    private void authenticate(String username, String password) throws Exception {
        try {
            User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("does not exist"));
            if(user!=null){
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            }
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}