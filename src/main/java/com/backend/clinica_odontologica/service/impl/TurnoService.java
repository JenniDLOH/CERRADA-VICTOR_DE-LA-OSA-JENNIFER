package com.backend.clinica_odontologica.service.impl;

import com.backend.clinica_odontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica_odontologica.dto.salida.DomicilioSalidaDto;
import com.backend.clinica_odontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica_odontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinica_odontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinica_odontologica.entity.Odontologo;
import com.backend.clinica_odontologica.entity.Paciente;
import com.backend.clinica_odontologica.entity.Turno;
import com.backend.clinica_odontologica.exceptions.BadRequestException;
import com.backend.clinica_odontologica.exceptions.ResourceNotFoundException;
import com.backend.clinica_odontologica.repository.TurnoRepository;
import com.backend.clinica_odontologica.service.ITurnoService;
import com.backend.clinica_odontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class TurnoService implements ITurnoService{


    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final OdontologoService odontologoService;
    private final PacienteService pacienteService;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository, OdontologoService odontologoService, PacienteService pacienteService, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.modelMapper = modelMapper;
    }

    private TurnoSalidaDto convertToDto(Turno turno) {
        TurnoSalidaDto turnoDtoSalida = modelMapper.map(turno, TurnoSalidaDto.class);
        turnoDtoSalida.setOdontologoSalidaDto(modelMapper.map(turno.getOdontologo(), OdontologoSalidaDto.class));
        turnoDtoSalida.setPacienteSalidaDto(modelMapper.map(turno.getPaciente(), PacienteSalidaDto.class));
        return turnoDtoSalida;
    }


    @Override
    @Transactional
    public TurnoSalidaDto guardarTurno(TurnoEntradaDto turnoEntradaDto) {
        Turno turnoAGuardar = new Turno();
        turnoAGuardar.setFechaYHora(turnoEntradaDto.getFechaYHora());

        // Buscar y manejar excepciones para odontólogo
        OdontologoSalidaDto odontologoDto;
        try {
            odontologoDto = odontologoService.buscarOdontologo(turnoEntradaDto.getOdontologoId());
        } catch (ResourceNotFoundException e) {
            throw new BadRequestException("No existe el odontólogo con id: " + turnoEntradaDto.getOdontologoId());
        }

        // Buscar y manejar excepciones para paciente
        PacienteSalidaDto pacienteDto = pacienteService.buscarPacientePorId(turnoEntradaDto.getPacienteId());
        if (pacienteDto == null) {
            throw new BadRequestException("No existe el paciente con id: " + turnoEntradaDto.getPacienteId());
        }

        Odontologo odontologo = modelMapper.map(odontologoDto, Odontologo.class);
        Paciente paciente = modelMapper.map(pacienteDto, Paciente.class);

        odontologo = entityManager.merge(odontologo);
        paciente = entityManager.merge(paciente);

        turnoAGuardar.setOdontologo(odontologo);
        turnoAGuardar.setPaciente(paciente);

        Turno turnoGuardado = turnoRepository.save(turnoAGuardar);
        LOGGER.info("Turno guardado con éxito: " + JsonPrinter.toString(turnoGuardado));
        return convertToDto(turnoGuardado);
    }


    @Override
    @Transactional
    public List<TurnoSalidaDto> listarTodosLosTurnos() {
        List<TurnoSalidaDto> turnos = turnoRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();

        LOGGER.info("Listado de todos los turnos: {}" + JsonPrinter.toString(turnos));

        if (turnos.isEmpty()) {
            LOGGER.warn("No se encontraron turnos");
        }
        return turnos;
    }

    @Override
    @Transactional
    public TurnoSalidaDto buscarTurno(Long id) throws ResourceNotFoundException {
        Turno turno = turnoRepository.findById(id).orElse(null);
        if (turno == null) {
            throw new ResourceNotFoundException("No existe el registro con turno id: " + id);
        }
        LOGGER.info("Turno encontrado con éxito: " + JsonPrinter.toString(turno));
        return convertToDto(turno);
    }

    @Override
    @Transactional
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if (turnoRepository.existsById(id)) {
            turnoRepository.deleteById(id);
        } else {
            LOGGER.warn("No se encontró el turno con ID: " + id);
            throw new ResourceNotFoundException("No existe el registro con turno id: " + id);
        }
    }

    @Override
    @Transactional
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) throws ResourceNotFoundException {
        Turno turnoExistente = turnoRepository.findById(id).orElse(null);
        if (turnoExistente == null) {
            throw new ResourceNotFoundException("No existe el registro con turno id: " + id);
        }

        turnoExistente.setFechaYHora(turnoEntradaDto.getFechaYHora());

        OdontologoSalidaDto odontologo = null;

        try {
            odontologo = odontologoService.buscarOdontologo(turnoEntradaDto.getOdontologoId());
        } catch (ResourceNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }

        turnoExistente.setOdontologo(modelMapper.map(odontologo, Odontologo.class));

        PacienteSalidaDto paciente = null;

        try {
            paciente = pacienteService.buscarPacientePorId(turnoEntradaDto.getPacienteId());
        } catch (ResourceNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }

        turnoExistente.setPaciente(modelMapper.map(paciente, Paciente.class));

        turnoExistente = turnoRepository.save(turnoExistente);
        LOGGER.info("Turno actualizado con éxito: " + JsonPrinter.toString(turnoExistente));
        return convertToDto(turnoExistente);
    }
}
