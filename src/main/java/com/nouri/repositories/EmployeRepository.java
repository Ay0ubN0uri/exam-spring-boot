package com.nouri.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nouri.entities.Employe;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

    public List<Employe> findByServiceId(Long serviceId);
}
