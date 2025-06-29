package com.rbservicios.challenge_literatura.main;

import com.rbservicios.challenge_literatura.models.DatosLibros;
import com.rbservicios.challenge_literatura.repositories.AutorRepository;
import com.rbservicios.challenge_literatura.repositories.LibroRepository;
import com.rbservicios.challenge_literatura.services.ConsumoAPI;
import com.rbservicios.challenge_literatura.services.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import com.rbservicios.challenge_literatura.models.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AppMain {
    @Autowired
    private AutorRepository autorRepositorio;
    @Autowired
    private LibroRepository libroRepositorio;
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibros> librosRegistrados = new ArrayList<>();
    private Scanner entrada = new Scanner(System.in);

    public void menu() {
        boolean salir = false;
        do {
            System.out.println("Aplicación personal de libros");
            System.out.println("-----------------------------");
            System.out.println("");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar libros registrados");
            System.out.println("3. Listar autores registrados");
            System.out.println("4. Listar autores vivos en un año determinado");
            System.out.println("5. Listar libros por idiomas");
            System.out.println("0. Salir");
            System.out.println("");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = entrada.nextInt();
                entrada.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        librosRegistrados();
                        break;
                    case 3:
                        autoresRegistrados();
                        break;
                    case 4:
                        autoresVivosEnAnio();
                        break;
                    case 5:
                        librosPorIdioma();
                        break;
                    case 0:
                        salir = true;
                        System.out.println("Gracias por usar la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida, intenta de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor ingresa una opción válida.");
                entrada.nextLine();
            }
        }while(!salir);
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Buscar libro por título");
        var tituloLibro = entrada.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("Libro Encontrado: ");
            System.out.println(libroBuscado.get());

            // Obtener el autor
            String nombreAutor = libroBuscado.get().autor().get(0).nombre();
            String fechaMuerte = libroBuscado.get().autor().get(0).fechaDeMuerte();
            String fechaNacimiento = libroBuscado.get().autor().get(0).fechaDeNacimiento();
            String[] idioma = libroBuscado.get().idiomas().toArray(new String[0]);
            String lang = new ArrayList<>(Arrays.asList(idioma)).toString();
            Boolean vivo;


            System.out.println("Buscando autor: " + nombreAutor +" ...");
            Autor autor = autorRepositorio.findByNombreIgnoreCase(nombreAutor).orElse(null);

            if (autor == null) {
                System.out.println("Autor no registrado en la base de datos");
                if(fechaMuerte == null){
                    vivo = true;
                }else{
                    vivo = false;
                }
                autor = new Autor(nombreAutor, vivo, fechaNacimiento, fechaMuerte);
                autorRepositorio.save(autor);
                System.out.println("Autor registrado.");
            }

            Libro lib = new Libro(libroBuscado.get().titulo(), lang, libroBuscado.get().numeroDeDescargas(), autor);
            System.out.println(lib);

            System.out.println(libroBuscado.get().idiomas().stream().toList());
            Optional<Libro> librof = libroRepositorio.findByTituloContainingIgnoreCase(libroBuscado.get().titulo());

            if(librof.isEmpty()){
                Libro libro = new Libro(libroBuscado.get().titulo(), lang, libroBuscado.get().numeroDeDescargas(), autor);

                libroRepositorio.save(libro);
                System.out.println("Libro registrado.");
            }else{
                System.out.println("Libro ya existe");
            }

        } else {
            System.out.println("Libro No Encontrado");
        }
    }

    private void librosRegistrados() {
        List<Libro> libros = libroRepositorio.findAll();
        System.out.println("\nLista de libros registrados");
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        for (Libro libro : libros) {
            System.out.println("---- LIBRO ----");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Idioma: " + String.join(", ", libro.getIdiomas()));
            System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
            System.out.println("----------------\n");
        }
    }

    private void autoresRegistrados() {
        List<Autor> autores = autorRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        for(Autor autor : autores) {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + (autor.getFechaDeNacimiento() != null ? autor.getFechaDeNacimiento() : "Desconocido"));
            System.out.println("Fecha de fallecimiento: " + (autor.getFechaDeMuerte() != null ? autor.getFechaDeMuerte() : "Desconocido"));
            System.out.println("Libros: " + autor.getLibros().stream().map(Libro::getTitulo).collect(Collectors.joining(", ")) + "\n");
        }
    }

    private void autoresVivosEnAnio() {
        System.out.print("Año a revisar: ");
        int anio = entrada.nextInt();
        entrada.nextLine();

        List<Autor> autoresVivos = autorRepositorio.findAutoresVivosPorAnios(String.valueOf(anio));

        if (autoresVivos.isEmpty()) {
            System.out.println("No hay autores registrados vivos para el año " + anio + ".");
        } else {
            System.out.println("Autores registrados vivos para el año " + anio + ":");
            for (Autor autor : autoresVivos) {
                System.out.println(autor);
            }
        }
    }

    private void librosPorIdioma() {
        var seleccion = -1;
        var idiomaABuscar = "en";
        while(seleccion != 0) {
            var menuIdioma = """
                    Ingresa el idioma a buscar:
                    1 - Español (es).
                    2 - Inglés (en).
                    3 - Francés (fr).
                    4 - Portugués (pt).
                    5 - Desconocido (nd).
                    0 - Regresar al menú anterior
                    """;

            System.out.println(menuIdioma);
            seleccion = entrada.nextInt();
            entrada.nextLine();

            switch (seleccion) {
                case 1:
                    idiomaABuscar = "[es]";
                    break;
                case 2:
                    idiomaABuscar = "[en]";
                    break;
                case 3:
                    idiomaABuscar = "[fr]";
                    break;
                case 4:
                    idiomaABuscar = "[pt]";
                    break;
                case 5:
                    idiomaABuscar = "[nd]";
                    break;
                case 0:
                    System.out.println("Regresando al menú principal.");
                    return;
                default:
                    System.out.println("Opción inválida, intenta de nuevo");
                    continue;
            }

            List<Libro> libros = libroRepositorio.buscarPorIdioma(idiomaABuscar);

            if (libros.isEmpty()) {
                System.out.println("Aún no hay libros registrados en ese idioma.");
                continue;
            }

            for (Libro libro : libros) {
                System.out.println("--------------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor());
                System.out.println("Idioma: " + String.join(", ", libro.getIdiomas()));
                System.out.println("Número de descargas: " + libro.getNumeroDeDescargas() + "\n");
            }
        }
    }
}
