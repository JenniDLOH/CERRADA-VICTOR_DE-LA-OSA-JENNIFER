package com.backend.clinica_odontologica.controller;

import com.backend.clinica_odontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica_odontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica_odontologica.exceptions.ResourceNotFoundException;
import com.backend.clinica_odontologica.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
@CrossOrigin("*")
public class OdontologoController {
    private final IOdontologoService odontologoService;

    @Autowired
    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    // POST
    @PostMapping("/registrar")
    public OdontologoSalidaDto registrarOdontologo(@RequestBody OdontologoEntradaDto odontologoDtoEntrada) {
        return odontologoService.guardarOdontologo(odontologoDtoEntrada);
    }

    // GET
    @GetMapping("/listar")
    public List<OdontologoSalidaDto> listarOdontologos() {
        return odontologoService.listarTodosLosOdontologos();
    }

    @GetMapping("/{id}")
    public OdontologoSalidaDto buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        return odontologoService.buscarOdontologo(id);
    }

    @PutMapping("/{id}")
    public OdontologoSalidaDto actualizarOdontologo(@PathVariable Long id, @RequestBody OdontologoEntradaDto odontologoDtoEntrada) throws ResourceNotFoundException {
        return odontologoService.actualizarOdontologo(id, odontologoDtoEntrada);
    }

    @DeleteMapping("/{id}")
    public void eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
    }
}
