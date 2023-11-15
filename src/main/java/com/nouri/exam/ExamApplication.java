package com.nouri.exam;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.unit.DataSize;

import com.nouri.entities.Employe;
import com.nouri.entities.Service;
import com.nouri.repositories.EmployeRepository;
import com.nouri.repositories.ServiceRepository;

import jakarta.servlet.MultipartConfigElement;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
// @OpenAPIDefinition(servers = {
// @Server(url = "https://quack-act-production.up.railway.app", description =
// "Default Server URL")
// })
@EntityScan(basePackages = { "com.nouri.entities" })
@ComponentScan(basePackages = {
		"com.nouri.controllers",
		"com.nouri.services",
		"com.nouri.exceptions"
})
@EnableJpaRepositories(basePackages = { "com.nouri.repositories" })
@EnableJpaAuditing
@RequiredArgsConstructor
public class ExamApplication {
	private final ServiceRepository serviceRepository;
	private final EmployeRepository employeRepository;

	public static void main(String[] args) {
		SpringApplication.run(ExamApplication.class, args);
	}

	@Bean
	public CommandLineRunner a00n() {
		return args -> {
			byte[] image = Files.readAllBytes(Paths.get("/home/ay0ub/Desktop/ayoub nouri.jpg"));
			Service service1 = Service.builder().nom("service 1").build();
			Service service2 = Service.builder().nom("service 2").build();
			Employe employe1 = Employe.builder().nom("ayoub").prenom("Nouri").dateNaissance(LocalDate.of(2001,
					10, 10)).image(image).service(service1).build();
			Employe employe2 = Employe.builder().nom("kali").prenom("kali").dateNaissance(LocalDate.of(2001,
					10, 10)).service(service1).chef(employe1).build();
			Employe employe3 = Employe.builder().nom("karim").prenom("hassan").dateNaissance(LocalDate.of(2001,
					10, 10)).service(service1).chef(employe1).build();
			Employe employe4 = Employe.builder().nom("karima").prenom("hassini").dateNaissance(LocalDate.of(2001,
					10, 10)).service(service2).build();
			serviceRepository.save(service1);
			serviceRepository.save(service2);
			employeRepository.save(employe1);
			employeRepository.save(employe2);
			employeRepository.save(employe3);
			employeRepository.save(employe4);
		};
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofMegabytes(30));
		factory.setMaxRequestSize(DataSize.ofMegabytes(100));
		return factory.createMultipartConfig();
	}

}
