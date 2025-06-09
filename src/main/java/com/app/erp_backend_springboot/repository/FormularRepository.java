package com.app.erp_backend_springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.erp_backend_springboot.entity.FormularEntity;

public interface FormularRepository extends JpaRepository<FormularEntity, Long> {

    List<FormularEntity> findAllByProductionIdOrderByIdDesc(Long productionId);
}
