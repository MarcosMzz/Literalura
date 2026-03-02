package com.example.Literalura.principal;

import com.example.Literalura.model.*;
import com.example.Literalura.repository.AutorRepository;
import com.example.Literalura.repository.LibroRepository;
import com.example.Literalura.service.ConsumirAPI;
import com.example.Literalura.service.ConvertirDatos;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner inputUsuario = new Scanner(System.in);
    private ConsumirAPI consumoAPI = new ConsumirAPI();
    private ConvertirDatos convierteDatos = new ConvertirDatos();
    private final String URL_API = "https://gutendex.com/books/";
    private final String URL_BUSQUEDA = "?search=";
    private final String URL_LENGUAJE = "&languages=";
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;


    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.libroRepository = repository;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0){

            var menu = """
                    1 - Buscar libro por titulo
                    2 - Mostrar todos los libros registrados
                    3 - Mostrar todos los autores registrados
                    4 - Listar autores vivos en cierto anio
                    5 - Listar libros por idioma
                    0 - Salir
                    """;

            System.out.println(menu);
            opcion = inputUsuario.nextInt();
            inputUsuario.nextLine();

            switch (opcion){
                case 1: buscarLibro();
                        break;
                case 2: mostrarTodosLosLibros();
                        break;
                case 3: mostrarAutoresConLibros();
                    break;
                case 4: listarAutoresVivosEnAnio();
                    break;
                case 5: listarLibrosPorIdioma();
                    break;
                default: break;
            }
        }

    }

    private void buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var libroUsuario = URLEncoder.encode(inputUsuario.nextLine(), StandardCharsets.UTF_8)
                .replace("+", "%20");

        var jsonBusqueda = this.consumoAPI.obtenerDatos(URL_API + URL_BUSQUEDA + libroUsuario);

        System.out.println("DEBUG -> '" + jsonBusqueda + "'");

        var libroBuscado = this.convierteDatos.obtenerDatos(jsonBusqueda, DatosGutenxRaiz.class);

        System.out.println("DEBUG -> '" + libroBuscado + "'");

        Optional<DatosLibros> libroFinal =
                libroBuscado.libros().stream().findFirst();

        if (libroFinal.isEmpty()) {
            System.out.println("No se encontró ningún libro.");
            return;
        }

        DatosLibros datos = libroFinal.get();

        System.out.println("DEBUG -> '" + libroFinal + "'");
        System.out.println("DEBUG -> '" + datos + "'");

        Optional<Libro> libroExiste =
                libroRepository.findByTituloIgnoreCase(datos.titulo());

        if (libroExiste.isPresent()) {
            System.out.println("El libro ya está en la base.");
            return;
        }

//A partir de este putno estamos con certeza de que el libro existe y no pertenece a la base de datos

        Libro libro = new Libro(datos);

        DatosAutor datosAutor = datos.autores().get(0);

        Optional<Autor> autorExistente =
                autorRepository.findByNombreAutor(datosAutor.nombreAutor());

        Autor autor;

        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
        } else {
            autor = new Autor();
            autor.setNombreAutor(datosAutor.nombreAutor());
            autor.setAnioNacimiento(datosAutor.anioNacimiento());
            autor.setAnioFallecimiento(datosAutor.anioFallecimiento());
            autorRepository.save(autor);
        }

        libro.setAutor(autor);

        libroRepository.save(libro);

        System.out.println("Libro guardado correctamente.");
    }

    private void mostrarTodosLosLibros() {
        List<Libro> libros = libroRepository.findAll();

        mostrarLibros(libros);

    }

    private void mostrarAutoresConLibros() {

        List<Autor> autores = autorRepository.findAllConLibros();

        if (autores.isEmpty()) {
            System.out.println("No hay autores cargados.");
            return;
        }

        mostrarAutores(autores);

    }

    private void mostrarAutores(List<Autor> autores) {

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores.");
            return;
        }

        for (Autor autor : autores) {

            System.out.println("Autor: " + autor.getNombreAutor());
            System.out.println("Fecha de nacimiento: " + autor.getAnioNacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getAnioFallecimiento());
            System.out.println("-----------------------------------");
        }
    }

    private void listarAutoresVivosEnAnio() {

        System.out.println("Ingrese el año que desea consultar:");
        int anio = Integer.parseInt(inputUsuario.nextLine());

        List<Autor> autores = autorRepository.findAutoresVivosEnAnio(anio);

        System.out.println("\nAutores vivos en el año " + anio + ":\n");

        mostrarAutores(autores);
    }

    private void mostrarLibros(List<Libro> libros) {

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros.");
            return;
        }

        for (Libro libro : libros) {
            System.out.println("-------------Libro-----------");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombreAutor());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getNroDescargas());
            System.out.println("-----------------------------\n");
        }
    }

    private void listarLibrosPorIdioma() {

            System.out.println("""
            Seleccione un idioma:
            1 - Inglés (en)
            2 - Español (es)
            3 - Francés (fr)
            4 - Portugués (pt)
            """);

            int opcion = inputUsuario.nextInt();
            inputUsuario.nextLine();

            String idioma;

            switch (opcion) {
                case 1 -> idioma = "en";
                case 2 -> idioma = "es";
                case 3 -> idioma = "fr";
                case 4 -> idioma = "pt";
                default -> {
                    System.out.println("Opción inválida.");
                    return;
                }
            }

            List<Libro> libros = libroRepository.findByIdiomaIgnoreCase(idioma);

            mostrarLibros(libros);
    }
}
