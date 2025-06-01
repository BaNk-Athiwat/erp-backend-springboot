package com.app.erp_backend_springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.erp_backend_springboot.entity.MaterialEntity;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
    
    List<MaterialEntity> findAllByOrderByIdDesc();

}
