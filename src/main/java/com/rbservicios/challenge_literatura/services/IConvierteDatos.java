package com.rbservicios.challenge_literatura.services;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
