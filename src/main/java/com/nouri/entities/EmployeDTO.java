package com.nouri.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeDTO {
    private Service service;
    @JsonIgnoreProperties({ "employees", "service", "chef" })
    private Employe chef;
    @JsonIgnoreProperties({ "employees", "chef" })
    private List<Employe> employees;
}
