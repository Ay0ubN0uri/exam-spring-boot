package com.nouri.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nouri.dao.IDao;
import com.nouri.entities.Service;
import com.nouri.repositories.ServiceRepository;

@org.springframework.stereotype.Service
public class ServiceService implements IDao<Service> {
    @Autowired
    private ServiceRepository ServiceRepository;

    @Override
    public Service create(Service o) {
        o.setId(0L);
        return ServiceRepository.save(o);
    }

    @Override
    public boolean delete(Service o) {
        try {
            ServiceRepository.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Service update(Service o) {
        return ServiceRepository.save(o);
    }

    @Override
    public List<Service> findAll() {
        return ServiceRepository.findAll();
    }

    @Override
    public Service findById(Long id) {
        return ServiceRepository.findById(id).orElse(null);
    }

}
