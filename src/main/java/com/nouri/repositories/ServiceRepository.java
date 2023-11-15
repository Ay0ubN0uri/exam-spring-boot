package com.nouri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nouri.entities.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

}
