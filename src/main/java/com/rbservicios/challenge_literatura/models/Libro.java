package com.rbservicios.challenge_literatura.models;

import jakarta.persistence.*;

@Entity
@Table(name="libros")
public class Libro {

    public Libro(){}

    public Libro(String titulo, String idioma, Double numeroDeDescargas, Autor autor) {
        this.titulo = titulo;
        this.idiomas = idioma;
        this.numeroDeDescargas = numeroDeDescargas;
        this.autor = autor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idiomas;
    private Double numeroDeDescargas;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdioma(String idioma) {
        this.idiomas = idioma;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro: " +
                "Titulo: '" + titulo + '\'' +
                ", Autor: " + (autor != null ? autor.getNombre() : "null") +
                ", Idiomas: " + idiomas +
                ", Numero de descargas: " + numeroDeDescargas +
                '}';
    }
}
