package com.backend.clinica_odontologica.dto.salida;

import java.time.LocalDateTime;
public class TurnoSalidaDto {

    private Long id;
    private LocalDateTime fechaYHora;
    private Long odontologoId;
    private String nombreOdontologo;
    private Long pacienteId;
    private String nombrePaciente;

    public TurnoSalidaDto() {}

    public TurnoSalidaDto(Long id, LocalDateTime fechaYHora, Long odontologoId, String nombreOdontologo, Long pacienteId, String nombrePaciente) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.odontologoId = odontologoId;
        this.nombreOdontologo = nombreOdontologo;
        this.pacienteId = pacienteId;
        this.nombrePaciente = nombrePaciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Long getOdontologoId() {
        return odontologoId;
    }

    public void setOdontologoId(Long odontologoId) {
        this.odontologoId = odontologoId;
    }

    public String getNombreOdontologo() {
        return nombreOdontologo;
    }

    public void setNombreOdontologo(String nombreOdontologo) {
        this.nombreOdontologo = nombreOdontologo;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public void setPacienteSalidaDto(PacienteSalidaDto pacienteDtoSalida) {
    }

    public void setOdontologoSalidaDto(OdontologoSalidaDto odontologoDtoSalida) {
    }
}
