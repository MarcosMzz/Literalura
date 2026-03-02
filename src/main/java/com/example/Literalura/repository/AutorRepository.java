package com.example.Literalura.repository;

import com.example.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreAutor(String nombreAutor);

    @Query("SELECT DISTINCT a FROM Autor a JOIN FETCH a.libros")
    List<Autor> findAllConLibros();

    @Query("""
    SELECT DISTINCT a
    FROM Autor a
    WHERE a.anioNacimiento <= :anio
    AND (a.anioFallecimiento IS NULL OR a.anioFallecimiento >= :anio)
""")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);

}
