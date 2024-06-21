package com.backend.clinica_odontologica.service.impl;

import com.backend.clinica_odontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinica_odontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica_odontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica_odontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica_odontologica.dto.salida.TurnoSalidaDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.BeforeEach;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class TurnosServiceTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;

    @BeforeEach
    void setUp() {
        // Crear y guardar odontólogos y pacientes de prueba
        OdontologoEntradaDto odontologoDto = new OdontologoEntradaDto("Juan", "Perez", "12345");
        odontologoService.guardarOdontologo(odontologoDto);

        PacienteEntradaDto pacienteDto = new PacienteEntradaDto("Maria", "Garcia", 789456, LocalDate.of(1990, 1, 1), new DomicilioEntradaDto("Calle Falsa", 123, "Ciudad", "Provincia"));
        pacienteService.registrarPaciente(pacienteDto);
    }


    @Test
    @Order(1)
    void deberiaRegistrarseUnTurno_yRetornarSuId(){
        TurnoEntradaDto turnoDtoEntrada = new TurnoEntradaDto(1L, 1L, LocalDateTime.of(2024, 6, 25, 10, 30));
        TurnoSalidaDto turnoDtoSalida = turnoService.guardarTurno(turnoDtoEntrada);

        assertNotNull(turnoDtoSalida);
        assertNotNull(turnoDtoSalida.getId());
        assertEquals(1L, turnoDtoSalida.getPacienteId());
    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDeTurnos(){
        TurnoEntradaDto turnoDtoEntrada = new TurnoEntradaDto(1L, 1L, LocalDateTime.of(2024, 6, 25, 10, 30));
        turnoService.guardarTurno(turnoDtoEntrada);

        List<TurnoSalidaDto> listadoDeTurnos = turnoService.listarTodosLosTurnos();
        assertFalse(listadoDeTurnos.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaEliminarseElTurnoConId1(){
        TurnoEntradaDto turnoDtoEntrada = new TurnoEntradaDto(1L, 1L, LocalDateTime.of(2024, 6, 25, 10, 30));
        TurnoSalidaDto turnoDtoSalida = turnoService.guardarTurno(turnoDtoEntrada);

        assertDoesNotThrow(() -> turnoService.eliminarTurno(turnoDtoSalida.getId()));
    }

    @Test
    @Order(4)
    void deberiaDevolverUnaListaVaciaDeTurnos(){
        assertTrue(turnoService.listarTodosLosTurnos().isEmpty());
    }
}
