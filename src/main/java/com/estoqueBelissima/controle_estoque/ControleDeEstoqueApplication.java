package com.estoqueBelissima.controle_estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.estoqueBelissima.controle_estoque.model")
public class ControleDeEstoqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleDeEstoqueApplication.class, args);
	}
}
