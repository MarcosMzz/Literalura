package com.example.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosGutenxRaiz(

        @JsonAlias("results") List<DatosLibros> libros

) {
    @Override
    public String toString() {
        return "Libros: " + this.libros;
    }
}
