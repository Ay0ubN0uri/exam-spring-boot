package com.nouri.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nouri.dao.IDao;
import com.nouri.entities.Employe;
import com.nouri.repositories.EmployeRepository;

@Service
public class EmployeService implements IDao<Employe> {
    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public Employe create(Employe o) {
        o.setId(0L);
        return employeRepository.save(o);
    }

    @Override
    public boolean delete(Employe o) {
        try {
            employeRepository.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Employe update(Employe o) {
        return employeRepository.save(o);
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }

    public List<Employe> findByServiceId(Long serviceId) {
        return employeRepository.findByServiceId(serviceId);
    }

}
