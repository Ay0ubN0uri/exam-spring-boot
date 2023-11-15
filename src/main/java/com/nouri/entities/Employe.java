package com.nouri.entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    @Temporal(TemporalType.DATE)
    private LocalDate dateNaissance;
    // private String photo;

    @ManyToOne
    @JsonIgnoreProperties({ "employees", "chef" })
    private Employe chef;

    @OneToMany(mappedBy = "chef")
    @Default
    @JsonIgnoreProperties("chef")
    private List<Employe> employees = new ArrayList<>();

    @ManyToOne
    private Service service;

    @Lob
    @JsonIgnore
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    private String url;

    @PostPersist
    public void beforePersist() throws IOException {
        if (this.image == null) {

            this.image = Files.readAllBytes(
                    Paths.get(
                            "/home/ay0ub/Desktop/spring projects/spring-crud-image/src/main/resources/static/user.png"));
        }
        this.url = "http://192.168.43.106:8080/api/v1/employes/" + this.id + "/image";
    }

    @PreUpdate
    public void beforeUpdate() throws IOException {
        System.out.println("hello from update");
        if (this.image == null) {
            this.image = Files.readAllBytes(
                    Paths.get(
                            "/home/ay0ub/Desktop/spring projects/spring-crud-image/src/main/resources/static/user.png"));
        }
        this.url = "http://192.168.43.106:8080/api/v1/employes/" + this.id + "/image";
    }
}
