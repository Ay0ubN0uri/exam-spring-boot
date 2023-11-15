package com.nouri.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nouri.entities.Service;
import com.nouri.exceptions.ApiRequestException;
import com.nouri.services.ServiceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1/services")
@CrossOrigin("*")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public List<Service> findAllServices() {
        return serviceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOneService(@PathVariable Long id) {
        Service service = serviceService.findById(id);
        if (service == null) {
            throw new ApiRequestException("Service with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(service);
        }
    }

    @PostMapping
    public Service createService(@Valid @RequestBody Service service, Errors errors) {
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        try {
            return serviceService.create(service);
        } catch (Exception ex) {
            throw new ApiRequestException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public Service updateService(@PathVariable Long id, @Valid @RequestBody Service service, Errors errors) {
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        if (serviceService.findById(id) == null) {
            throw new ApiRequestException("Service with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            service.setId(id);
            return serviceService.update(service);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteService(@PathVariable Long id) {
        Service service = serviceService.findById(id);
        if (service == null) {
            throw new ApiRequestException("Service with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            serviceService.delete(service);
            return ResponseEntity.ok("Service with ID " + id + " deleted successfully.");
        }
    }
}