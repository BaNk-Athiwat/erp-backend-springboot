package com.app.erp_backend_springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.erp_backend_springboot.entity.ProductionEntity;
import com.app.erp_backend_springboot.repository.ProductionRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/productions")
public class ProductionController {

    @Autowired
    private ProductionRepository productionRepository;

    @GetMapping
    public List<ProductionEntity> getAllProductions() {
        return productionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ProductionEntity getProduction(@PathVariable Long id) {
        return productionRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ProductionEntity createProduction(@RequestBody ProductionEntity production) {
        return productionRepository.save(production);
    }
    
    @PutMapping("/{id}")
    public ProductionEntity updateProduction(@PathVariable Long id, @RequestBody ProductionEntity production) {
        try {
            ProductionEntity productionToUpdate = productionRepository
            .findById(id).orElse(null);

            if (productionToUpdate == null) {
                throw new IllegalArgumentException("Production not found");
            }

            productionToUpdate.setName(production.getName());
            productionToUpdate.setDetails(production.getDetails());

            return productionRepository.save(productionToUpdate);

        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalArgumentException("Error updating production: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduction(@PathVariable Long id) {
        productionRepository.deleteById(id);
    }
    
}
