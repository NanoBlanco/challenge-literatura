package com.rbservicios.challenge_literatura.controllers;

import com.rbservicios.challenge_literatura.models.Autor;
import com.rbservicios.challenge_literatura.models.Libro;
import com.rbservicios.challenge_literatura.services.AutorService;
import com.rbservicios.challenge_literatura.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @GetMapping("/autores")
    public List<Autor> getAutores(){
        return autorService.obtenerAutores();
    }

    @GetMapping("/autores/vivos/{anio}")
    public List<Autor> getAutoresVivosAnio(@PathVariable String anio) {
        return autorService.obtenerAutoresVivosEnAnio(anio);
    }

    @GetMapping("/libros")
    public List<Libro> getLibros(){
        return libroService.obtenerLibros();
    }

    @GetMapping("/libros/idioma/{idioma}")
    public List<Libro> getLibrosPorIdioma(@PathVariable String idioma) {
        return libroService.obtenerLibrosPorIdiomas(idioma);
    }
}
