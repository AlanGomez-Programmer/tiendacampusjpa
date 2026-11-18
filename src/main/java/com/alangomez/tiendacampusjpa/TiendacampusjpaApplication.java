package com.alangomez.tiendacampusjpa;

import com.alangomez.tiendacampusjpa.execute.MainExecute;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TiendacampusjpaApplication implements CommandLineRunner {

	private final MainExecute mainExecute;

	public TiendacampusjpaApplication(MainExecute mainExecute){
		this.mainExecute = mainExecute;
	}

	public static void main(String[] args) {
		SpringApplication.run(TiendacampusjpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mainExecute.iniciar();
	}
}
