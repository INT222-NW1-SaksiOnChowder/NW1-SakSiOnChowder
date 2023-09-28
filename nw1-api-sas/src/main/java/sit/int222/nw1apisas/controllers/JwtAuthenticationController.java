package sit.int222.nw1apisas.controllers;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import sit.int222.nw1apisas.config.JwtTokenUtil;

import sit.int222.nw1apisas.dtos.jwt.AccessTokenResponse;
import sit.int222.nw1apisas.dtos.jwt.JwtRequest;
import sit.int222.nw1apisas.dtos.jwt.JwtResponse;
import sit.int222.nw1apisas.dtos.jwt.RefreshTokenDto;
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

    @GetMapping("")
    public ResponseEntity<?> requestAccessToken(@RequestHeader("Authorization") String refreshToken) {
        // Check if the Authorization header is present
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            // Extract the token from the Authorization header
            refreshToken = refreshToken.substring(7); // Remove "Bearer " prefix
            // ตรวจสอบความถูกต้องของ Refresh Token check secret-key
            if (jwtTokenUtil.isValidRefreshToken(refreshToken)) {
                // ดึงข้อมูลผู้ใช้จาก Refresh Token
                String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // สร้าง Access Token ใหม่
                String newAccessToken = jwtTokenUtil.generateToken(userDetails);

                // ส่ง Access Token ใหม่กลับไปยังผู้ใช้
                return ResponseEntity.ok(new AccessTokenResponse(newAccessToken));
            }
        }

        // If Refresh Token is missing or invalid
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or refresh token is expired");
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
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}