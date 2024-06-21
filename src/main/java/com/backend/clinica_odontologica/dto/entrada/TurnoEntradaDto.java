package com.backend.clinica_odontologica.dto.entrada;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoEntradaDto {
    @NotNull(message = "La fecha y hora son obligatorias")
    @FutureOrPresent(message = "La fecha y hora deben ser en el futuro o presente")
    private LocalDateTime fechaYHora;

    @NotNull(message = "El odontólogo es obligatorio")
    private Long odontologoId;

    @NotNull(message = "El paciente es obligatorio")
    private Long pacienteId;

    public TurnoEntradaDto() {

    }

    public TurnoEntradaDto(Long odontologoId, Long pacienteId, LocalDateTime fechaYHora) {
        this.odontologoId = odontologoId;
        this.pacienteId = pacienteId;
        this.fechaYHora = fechaYHora;
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

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
