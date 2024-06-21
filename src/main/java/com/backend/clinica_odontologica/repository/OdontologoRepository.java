package com.backend.clinica_odontologica.repository;

import com.backend.clinica_odontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.List;

public interface OdontologoRepository extends JpaRepository<Odontologo, Long > {

    // Encuentra un odontólogo por matrícula
    Optional<Odontologo> findByMatricula(String matricula);

    // Encuentra un odontólogo por nombre y apellido
    List<Odontologo> findByNombreAndApellido(String nombre, String apellido);

    // Encuentra un odontólogo cuyo nombre contiene la cadena proporcionada, utilizando @Query
    @Query("SELECT o FROM Odontologo o WHERE o.nombre LIKE %?1%")
    List<Odontologo> findByNombreContaining(String nombre);

    // Encuentra todos los odontólogos ordenados por apellido
    @Query("SELECT o FROM Odontologo o ORDER BY o.apellido ASC")
    List<Odontologo> findAllOrderByApellidoAsc();



}
