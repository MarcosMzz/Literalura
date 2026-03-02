package com.example.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("download_count") Integer cantidadDeDescargas,
        @JsonAlias("languages") List<String> idioma
) {
    @Override
    public String toString() {
        return "Titulo: " + this.titulo + " Autores: " + this.autores + " Cantidad de Descargas: " + this.cantidadDeDescargas;
    }
}
