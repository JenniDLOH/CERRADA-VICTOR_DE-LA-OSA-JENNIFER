package com.backend.clinica_odontologica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class ClinicaOdontologicaApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertNotNull(applicationContext);
	}

	@Test
	void odontologoServiceBeanShouldBeLoaded() {
		assertNotNull(applicationContext.getBean("odontologoService"));
	}

	@Test
	void pacienteServiceBeanShouldBeLoaded() {
		assertNotNull(applicationContext.getBean("pacienteService"));
	}

	@Test
	void turnoServiceBeanShouldBeLoaded() {
		assertNotNull(applicationContext.getBean("turnoService"));
	}
}
