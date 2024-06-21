package com.backend.clinica_odontologica.controller;

import com.backend.clinica_odontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica_odontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinica_odontologica.exceptions.ResourceNotFoundException;
import com.backend.clinica_odontologica.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("turnos")
@CrossOrigin

public class TurnoController {
    private ITurnoService turnoService;

    @Autowired
    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    //POST
    @PostMapping("/registrar")
    public TurnoSalidaDto registarTurno(@RequestBody TurnoEntradaDto turnoDtoEntrada) {
        return turnoService.guardarTurno(turnoDtoEntrada);
    }

    //GET
    @GetMapping("/listar")
    public List<TurnoSalidaDto> listarTurnos() {
        return turnoService.listarTodosLosTurnos();
    }

    @GetMapping("/{id}")
    public TurnoSalidaDto buscarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        return turnoService.buscarTurno(id);
    }

    @PutMapping("/{id}")
    public TurnoSalidaDto actualizarTurno(@PathVariable Long id, @RequestBody TurnoEntradaDto turnoDtoEntrada) throws ResourceNotFoundException {
        return turnoService.actualizarTurno(turnoDtoEntrada, id);
    }

    @DeleteMapping("/{id}")
    public void eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
    }
}
