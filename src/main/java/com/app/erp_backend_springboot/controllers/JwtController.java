package com.app.erp_backend_springboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.erp_backend_springboot.models.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.*;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/jwt")
public class JwtController {
    private static final long EXP_TIME = 60 * 60 * 1000 * 24;

    public String getSecret() {
        return Dotenv.configure()
        .directory(System.getProperty("user.dir"))
        .load()
        .get("JWT_SECRET");
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(getSecret());
    }

    @PostMapping("/create")
    public String createToken(@RequestBody UserModel user) {
        return JWT.create()
        .withSubject(String.valueOf(user.getId()))
        .withExpiresAt(new Date(System.currentTimeMillis() + EXP_TIME))
        .withIssuedAt(new Date())
        .sign(getAlgorithm());
    }
    
    @GetMapping("/check/{token}")
    public String checkToken(@PathVariable String token) {
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm()).build();
            String id = verifier.verify(token).getSubject();

            return "Token is valid for user id: " + id;
        } catch (Exception e) {
            // TODO: handle exception
            return "Token is invalid or expired: " + e.getMessage();
        }
    }

    @GetMapping("/checks")
    public String checkTokens(@RequestHeader("Authorization") String BearerToken) {
        try {
            String token = BearerToken.substring(7, BearerToken.length());
            JWTVerifier verifier = JWT.require(getAlgorithm()).build();
            String id = verifier.verify(token).getSubject();

            return "Token is valid for user id: " + id;
        } catch (Exception e) {
            // TODO: handle exception
            return "Token is invalid or expired: " + e.getMessage();
        }
    }
    
}
