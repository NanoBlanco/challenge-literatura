package com.rbservicios.challenge_literatura.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.rbservicios.challenge_literatura.models.Libro;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainingIgnoreCase(String tituloDelLibro);
    List<Libro> findByIdiomasContaining(String idioma);
    @Query(value="SELECT * FROM libros WHERE ? = ANY(string_to_array(idiomas, ','))", nativeQuery = true)
    List<Libro> buscarPorIdioma(String idioma);

}
