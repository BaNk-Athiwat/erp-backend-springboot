package com.app.erp_backend_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.erp_backend_springboot.entity.ProductionLogEntity;

public interface ProductionLogRepository extends JpaRepository<ProductionLogEntity, Long> {

}
