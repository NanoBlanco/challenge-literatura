package com.rbservicios.challenge_literatura.services;

import com.rbservicios.challenge_literatura.models.Autor;
import com.rbservicios.challenge_literatura.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> obtenerAutores(){
        return autorRepository.findAll();
    }

    public List<Autor> obtenerAutoresVivosEnAnio(String anio){
        return autorRepository.findAutoresVivosPorAnios(anio);
    }
}
