package com.app.erp_backend_springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.erp_backend_springboot.entity.FormularEntity;
import com.app.erp_backend_springboot.repository.FormularRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/formulars")
public class FormularController {

    @Autowired
    FormularRepository formularRepository;

    @GetMapping
    public ResponseEntity<List<FormularEntity>> getAllFormulars() {
        return ResponseEntity.ok(formularRepository.findAll());
    }
    
    @PostMapping
    public ResponseEntity<FormularEntity> createFormular(@RequestBody FormularEntity formular) {
        return ResponseEntity.ok(formularRepository.save(formular));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormularEntity> putMethodName(@PathVariable Long id, @RequestBody FormularEntity formular) {
        try {
            FormularEntity entity = formularRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Formular not found with id: " + id));
            entity.setName(formular.getName());
            entity.setQty(formular.getQty());
            entity.setUnitName(formular.getUnitName());
            entity.setMaterial(formular.getMaterial());
            entity.setProduction(formular.getProduction());
            return ResponseEntity.ok(formularRepository.save(entity));
        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalArgumentException("Error updating formular: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public void deleteFormular(@PathVariable Long id) {
        try {
            formularRepository.deleteById(id);
        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalArgumentException("Error deleting formular: " + e.getMessage());
        }
    }

}
