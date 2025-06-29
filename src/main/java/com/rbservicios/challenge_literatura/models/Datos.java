package com.rbservicios.challenge_literatura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("count") int contador,
                    @JsonAlias("results")List<DatosLibros> resultados) {
}
