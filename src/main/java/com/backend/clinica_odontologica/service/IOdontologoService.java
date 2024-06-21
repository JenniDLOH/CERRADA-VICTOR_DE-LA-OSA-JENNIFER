package com.backend.clinica_odontologica.service;

import com.backend.clinica_odontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica_odontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica_odontologica.entity.Odontologo;
import com.backend.clinica_odontologica.entity.Paciente;
import com.backend.clinica_odontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IOdontologoService {
    OdontologoSalidaDto guardarOdontologo(OdontologoEntradaDto odontologoEntradaDto);

    List<OdontologoSalidaDto> listarTodosLosOdontologos();

    OdontologoSalidaDto buscarOdontologo(Long id)  throws ResourceNotFoundException;

    OdontologoSalidaDto actualizarOdontologo(Long id, OdontologoEntradaDto odontologoEntradaDto)  throws ResourceNotFoundException;

    void eliminarOdontologo(Long id) throws ResourceNotFoundException;
}
