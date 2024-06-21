package com.backend.clinica_odontologica.service.impl;

import com.backend.clinica_odontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica_odontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica_odontologica.entity.Odontologo;
import com.backend.clinica_odontologica.exceptions.ResourceNotFoundException;
import com.backend.clinica_odontologica.service.IOdontologoService;
import com.backend.clinica_odontologica.repository.OdontologoRepository;
import com.backend.clinica_odontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public OdontologoSalidaDto guardarOdontologo(OdontologoEntradaDto odontologoEntradaDto) {
        Odontologo odontologo = modelMapper.map(odontologoEntradaDto, Odontologo.class);
        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
        LOGGER.info("Odontólogo guardado con éxito: " + JsonPrinter.toString(odontologoGuardado));
        return modelMapper.map(odontologoGuardado, OdontologoSalidaDto.class);
    }

    @Override
    public List<OdontologoSalidaDto> listarTodosLosOdontologos() {
        List<OdontologoSalidaDto> odontologos = odontologoRepository.findAll()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();

        LOGGER.info("Listado de todos los odontólogos: " + JsonPrinter.toString(odontologos));

        if (odontologos.isEmpty()) {
            LOGGER.warn("No se encontraron odontólogos");
        }

        return odontologos;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologo(Long id) throws ResourceNotFoundException {
        Odontologo odontologo = odontologoRepository.findById(id).orElse(null);
        if (odontologo == null) {
            throw new ResourceNotFoundException("No existe registro de odontologo con id " + id);
        }
        LOGGER.info("Odontólogo encontrado con éxito: " + JsonPrinter.toString(odontologo));
        return modelMapper.map(odontologo, OdontologoSalidaDto.class);
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(Long id, OdontologoEntradaDto odontologoEntradaDto) throws ResourceNotFoundException {
        Odontologo odontologoExistente = odontologoRepository.findById(id).orElse(null);
        if (odontologoExistente == null) {
            throw new ResourceNotFoundException("No existe registro de odontologo con id " + id);
        }

        odontologoExistente.setMatricula(odontologoEntradaDto.getMatricula());
        odontologoExistente.setNombre(odontologoEntradaDto.getNombre());
        odontologoExistente.setApellido(odontologoEntradaDto.getApellido());

        Odontologo odontologoActualizado = odontologoRepository.save(odontologoExistente);
        LOGGER.info("Odontólogo actualizado con éxito: " + JsonPrinter.toString(odontologoActualizado));

        return modelMapper.map(odontologoActualizado, OdontologoSalidaDto.class);
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        if (odontologoRepository.existsById(id)) {
            odontologoRepository.deleteById(id);
        } else {
            LOGGER.warn("No se encontró el odontólogo con ID: " + id);
            throw new ResourceNotFoundException("No existe el odontologo con id " + id);
        }
    }
}
