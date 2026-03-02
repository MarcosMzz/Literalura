package com.example.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias("name") String nombreAutor,

        @JsonAlias("birth_year")Integer anioNacimiento,

        @JsonAlias("death_year")Integer anioFallecimiento

) {
    @Override
    public String toString() {
        return "Nombre del autor: " + this.nombreAutor + " Fecha de nacimiento: " + this.anioNacimiento + " Fecha de fallecimiento: " + this.anioFallecimiento;
    }
}
