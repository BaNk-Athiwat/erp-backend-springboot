package com.app.erp_backend_springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.erp_backend_springboot.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByOrderByName();
    UserEntity findByUsernameAndEmail(String username, String email);
    UserEntity findByUsernameAndPassword(String username, String password);

}
