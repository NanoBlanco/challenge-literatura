package com.rbservicios.challenge_literatura.models;

public enum Idiomas {
    español("es"),
    inglés("en"),
    francés("fr"),
    portugés("pt"),
    desconocido("nd");

    private String idiomasLibro;

    Idiomas (String idiomasLibro) { this.idiomasLibro = idiomasLibro;}

    public static Idiomas fromString(String text) {
        for (Idiomas idiomas : Idiomas.values()){
            if(idiomas.idiomasLibro.endsWith(text)){
                return idiomas;
            }
        }
        throw new IllegalArgumentException("No se encontraron idiomas: " + text);
    }
}
