package com.rbservicios.challenge_literatura.services;

import com.rbservicios.challenge_literatura.models.Libro;
import com.rbservicios.challenge_literatura.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> obtenerLibros(){
        return libroRepository.findAll();
    }

    public List<Libro> obtenerLibrosPorIdiomas(String idioma) {
        return libroRepository.findByIdiomasContaining(idioma);
    }


}
