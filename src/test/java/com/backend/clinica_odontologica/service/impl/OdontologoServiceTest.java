package com.backend.clinica_odontologica.service.impl;

import com.backend.clinica_odontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica_odontologica.dto.salida.OdontologoSalidaDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarseUnOdontologoConMatricula1234_yRetornarSuId(){
        OdontologoEntradaDto odontologoDtoEntrada = new OdontologoEntradaDto("1234", "Juan", "García");
        OdontologoSalidaDto odontologoDtoSalida = odontologoService.guardarOdontologo(odontologoDtoEntrada);

        assertNotNull(odontologoDtoSalida);
        assertNotNull(odontologoDtoSalida.getId());
        assertEquals("Juan", odontologoDtoSalida.getNombre());
    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDeOdontologos(){
        List<OdontologoSalidaDto> listadoDeOdontologos = odontologoService.listarTodosLosOdontologos();
        assertFalse(listadoDeOdontologos.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaActualizarseElOdontologoConId1(){
        OdontologoEntradaDto odontologoDtoEntrada = new OdontologoEntradaDto("5678", "Carlos", "Pérez");
        assertDoesNotThrow(() -> odontologoService.actualizarOdontologo(1L, odontologoDtoEntrada));
    }

    @Test
    @Order(4)
    void deberiaEliminarseElOdontologoConId1(){
        assertDoesNotThrow(() -> odontologoService.eliminarOdontologo(1L));
    }

}
