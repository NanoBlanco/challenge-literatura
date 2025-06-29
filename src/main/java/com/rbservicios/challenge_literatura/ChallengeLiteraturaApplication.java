package com.rbservicios.challenge_literatura;

import com.rbservicios.challenge_literatura.main.AppMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraturaApplication implements CommandLineRunner {
	@Autowired
	private AppMain appMain;
	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		appMain.menu();
		System.exit(0); // Para finalizar SpringBoot, ya que tengo el controlador
	}
}
