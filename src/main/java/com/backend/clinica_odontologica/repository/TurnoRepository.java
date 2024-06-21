package com.backend.clinica_odontologica.repository;

import com.backend.clinica_odontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;


public interface TurnoRepository extends JpaRepository<Turno, Long> {
    // Encuentra todos los turnos para un odontólogo específico
    List<Turno> findByOdontologoId(Long odontologoId);

    // Encuentra todos los turnos para un paciente específico
    List<Turno> findByPacienteId(Long pacienteId);

    // Encuentra todos los turnos en un rango de fechas
    @Query("SELECT t FROM Turno t WHERE t.fechaYHora BETWEEN ?1 AND ?2")
    List<Turno> findTurnosBetweenFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Encuentra todos los turnos en una fecha específica
    @Query("SELECT t FROM Turno t WHERE DATE(t.fechaYHora) = DATE(?1)")
    List<Turno> findTurnosByFecha(LocalDateTime fecha);
}
