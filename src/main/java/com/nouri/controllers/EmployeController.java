package com.nouri.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nouri.entities.Employe;
import com.nouri.entities.EmployeDTO;
import com.nouri.entities.Service;
import com.nouri.exceptions.ApiRequestException;
import com.nouri.services.EmployeService;
import com.nouri.services.ServiceService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/employes")
@CrossOrigin("*")
public class EmployeController {
    @Autowired
    private EmployeService employeService;
    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public List<Employe> findAllEmployes() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOneEmploye(@PathVariable Long id) {
        Employe employe = employeService.findById(id);
        if (employe == null) {
            throw new ApiRequestException("Employe with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(employe);
        }
    }

    @GetMapping("/{id}/services")
    public EmployeDTO getEmployeesByService(@PathVariable Long id) {
        Service service = serviceService.findById(id);
        List<Employe> employes = employeService.findByServiceId(id);
        Employe chef = null;
        for (Employe emp : employes) {
            if (emp.getChef() != null) {
                chef = emp.getChef();
            }
        }
        return EmployeDTO.builder().chef(chef).employees(employes).service(service).build();
    }

    @PostMapping
    public Employe createEmploye(@Valid @RequestBody Employe employe, Errors errors) {
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        try {
            return employeService.create(employe);
        } catch (Exception ex) {
            throw new ApiRequestException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public Employe updateEmploye(@PathVariable Long id, @Valid @RequestBody Employe employe, Errors errors) {
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        if (employeService.findById(id) == null) {
            throw new ApiRequestException("Employe with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            employe.setId(id);
            return employeService.update(employe);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmploye(@PathVariable Long id) {
        Employe employe = employeService.findById(id);
        if (employe == null) {
            throw new ApiRequestException("Employe with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            employeService.delete(employe);
            return ResponseEntity.ok("Employe with ID " + id + " deleted successfully.");
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Employe employe = employeService.findById(id);
        if (employe != null) {
            if (employe.getImage() == null) {
                throw new ApiRequestException("Employe with ID " + id + " does not have an image.",
                        HttpStatus.BAD_REQUEST);
            } else {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(employe.getImage());
            }
        } else {
            throw new ApiRequestException("Employe with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<Object> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile file)
            throws IOException {
        Employe employe = employeService.findById(id);
        if (employe != null) {
            if (file.isEmpty()) {
                throw new ApiRequestException("no image found", HttpStatus.BAD_REQUEST);
            } else {
                employe.setImage(file.getBytes());
                employeService.update(employe);
                return ResponseEntity.ok("Employe with ID " + id + " image updated successfully.");
            }
        } else {
            throw new ApiRequestException("Employe with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        }
    }
}