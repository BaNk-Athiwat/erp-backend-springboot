package com.app.erp_backend_springboot.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class FileController {
    
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            String dir = "src/main/resources/static/upload/";
            String targetLocation = dir + fileName;
            Path uploadPath = Paths.get(dir);
            
            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path path = Paths.get(targetLocation);
            
            Files.write(path, bytes);

            return "File uploaded successfully";

        } catch (IOException e) {
            // TODO: handle exception
            return "Failed to upload file: " + e.getMessage();
        }
    }

    @DeleteMapping("/remove/{fileName}")
    public String removeFile(@PathVariable String fileName) {
        String dir = "src/main/resources/static/upload/";
        Path uploadPath = Paths.get(dir, fileName);

        if (Files.exists(uploadPath)) {
            try {
                Files.delete(uploadPath);

                return "File removed successfully";
            } catch (IOException e) {
                // TODO: handle exception
                return "Failed to remove file: " + e.getMessage();
            }
        }

        return "File not found";
    }
    
}
