package com.app.erp_backend_springboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.erp_backend_springboot.entity.UserEntity;
import com.app.erp_backend_springboot.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final long EXP_TIME = 60 * 60 * 1000 * 24;

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userRepository.findAllByOrderByName();
    }
    
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }
    
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        UserEntity userEn = userRepository.findById(id).orElse(null);

        if (userEn == null) {
            throw new IllegalArgumentException("User not found");
        }

        userEn.setName(user.getName());
        userEn.setUsername(user.getUsername());
        userEn.setEmail(user.getEmail());
        
        userRepository.save(userEn);

        return userEn;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfuly";
    }
    
    @PostMapping("/signin")
    public UserEntity signin(@RequestBody UserEntity user) {
        String username = user.getUsername();
        String email = user.getEmail();

        UserEntity userToSignin = userRepository.findByUsernameAndEmail(username, email);

        if(userToSignin == null) {
            throw new IllegalArgumentException("User not found");
        }

        return userToSignin;
    }

    public String getSecret() {
        Dotenv dotenv = Dotenv.configure()
        .directory(System.getProperty("user.dir"))
        .load();
        return dotenv.get("JWT_SECRET");
    }
    
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(getSecret());
    }

    @PostMapping("/admin-signin")
    public String adminSignString(@RequestBody UserEntity user) {
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            
            UserEntity userForCreateToken = userRepository.findByUsernameAndPassword(username, password);

            return JWT.create()
            .withSubject(String.valueOf(userForCreateToken.getId()))
            .withExpiresAt(new Date(System.currentTimeMillis() + EXP_TIME))
            .withIssuedAt(new Date())
            .sign(getAlgorithm());
        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalArgumentException("Error creating token");
        }
    }
    
    @GetMapping("/admin-info")
    public Object adminInfObject(@RequestHeader("Authorization") String bearerToken) {
        try {
            if(bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid token format with 'Bearer '");
            }

            String token = bearerToken.replace("Bearer ", "");

            if(token.trim().isEmpty()) {
                throw new IllegalArgumentException("Token is empty");
            }

            String subject = JWT.require(getAlgorithm())
            .build()
            .verify(token)
            .getSubject();

            Long userId = Long.valueOf(subject);

            UserEntity user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }

            record UserResponse(Long id, String name, String username, String email) {}

            return new UserResponse(user.getId(), user.getName(), user.getUsername(), user.getEmail());

        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalArgumentException("Authorization error: " + e.getMessage());
        }
    }

    public Long getUserIdFromToken(String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");

        if (token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token is empty");
        }

        return Long.valueOf(
            JWT.require(getAlgorithm())
            .build()
            .verify(token)
            .getSubject()
        );
    }

    @PutMapping("/admin-edit-profile/{id}")
    public UserEntity adminEditProfilEntity(
        @RequestHeader("Authorization") String token,
        @RequestBody UserEntity user,
        @PathVariable Long id
    ) {
        try {
            // Long userId = getUserIdFromToken(token);
            UserEntity userToUpdate = userRepository.findById(id).orElse(null);
    
            if (userToUpdate == null) {
                throw new IllegalArgumentException("User not found");
            }
    
            userToUpdate.setName(user.getName());
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setEmail(user.getEmail());
    
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                userToUpdate.setPassword(user.getPassword());
            }
    
            userRepository.save(userToUpdate);
    
            return userToUpdate;
            
        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalArgumentException("Update user error: " + e.getMessage());
        }
    }
    
    @PostMapping("/admin-create")
    public UserEntity adminCreateEntity(@RequestBody UserEntity user) {
        try {
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalArgumentException("Create user error: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin-delete/{id}")
    public void adminDelete(@PathVariable Long id) {
        try {
            UserEntity userToDelete = userRepository.findById(id).orElse(null);

            if (userToDelete == null) {
                throw new IllegalArgumentException("User not found");
            }

            userRepository.deleteById(id);

        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalArgumentException("Delete user error: " + e.getMessage());
        }
    }
    
    
}
