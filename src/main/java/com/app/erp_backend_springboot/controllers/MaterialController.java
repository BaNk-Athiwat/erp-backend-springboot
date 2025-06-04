package com.app.erp_backend_springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.erp_backend_springboot.entity.MaterialEntity;
import com.app.erp_backend_springboot.repository.MaterialRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    @Autowired
    private MaterialRepository materialRepository;

    @PostMapping
    public ResponseEntity<MaterialEntity> createMaterial(@RequestBody MaterialEntity material) {
        return ResponseEntity.ok(materialRepository.save(material));
    }

    @GetMapping
    public ResponseEntity<List<MaterialEntity>>  getAllMaterial() {
        return ResponseEntity.ok(materialRepository.findAllByOrderByIdDesc());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MaterialEntity> updateMaterial(@PathVariable Long id, @RequestBody MaterialEntity material) {
        try {
            MaterialEntity existingMaterial = materialRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Material not found with id: " + id));
            existingMaterial.setName(material.getName());
            existingMaterial.setUnitName(material.getUnitName());
            existingMaterial.setQty(material.getQty());
            return ResponseEntity.ok(materialRepository.save(existingMaterial));
        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalArgumentException("Error updating material: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public void deleteMaterial(@PathVariable Long id) {
        materialRepository.deleteById(id);
    }
}
