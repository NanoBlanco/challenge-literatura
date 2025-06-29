package com.rbservicios.challenge_literatura.repositories;

import com.rbservicios.challenge_literatura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreIgnoreCase(String nombre);
    @Query(value = "SELECT * FROM autores WHERE :anio BETWEEN fecha_de_nacimiento AND fecha_de_muerte", nativeQuery = true)
    List<Autor> findAutoresVivosPorAnios(String anio);

}
