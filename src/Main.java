import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("  SISTEMA DE GESTION DE BIBLIOTECA");
        System.out.println("=========================================\n");

        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }
            procesarOpcion(opcion);
        }
        System.out.println("\nSistema cerrado. Hasta pronto!");
        scanner.close();
    }

    static void mostrarMenu() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("  LIBROS");
        System.out.println("  1. Registrar libro");
        System.out.println("  2. Buscar libro");
        System.out.println("  3. Listar todos los libros");
        System.out.println("  USUARIOS");
        System.out.println("  4. Registrar usuario");
        System.out.println("  5. Buscar usuario");
        System.out.println("  6. Listar todos los usuarios");
        System.out.println("  PRESTAMOS");
        System.out.println("  7. Realizar prestamo");
        System.out.println("  8. Registrar devolucion");
        System.out.println("  9. Ver prestamos activos");
        System.out.println("  10. Ver prestamos vencidos");
        System.out.println("  REPORTES");
        System.out.println("  11. Reporte general");
        System.out.println("  0. Salir");
        System.out.println("=====================================");
        System.out.print("  Seleccione una opcion: ");
    }

    static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:  menuRegistrarLibro();      break;
            case 2:  menuBuscarLibro();         break;
            case 3:  biblioteca.listarLibros(); break;
            case 4:  menuRegistrarUsuario();    break;
            case 5:  menuBuscarUsuario();       break;
            case 6:  biblioteca.listarUsuarios(); break;
            case 7:  menuRealizarPrestamo();    break;
            case 8:  menuRegistrarDevolucion(); break;
            case 9:  menuPrestamosActivos();    break;
            case 10: menuPrestamosVencidos();   break;
            case 11: biblioteca.generarReporteGeneral(); break;
            case 0:  break;
            default:
                System.out.println("  [Error] Opcion invalida. Ingrese un numero del 0 al 11.");
        }
    }

    static void menuRegistrarLibro() {
        System.out.println("\n--- REGISTRAR LIBRO ---");
        System.out.print("  ISBN (10 o 13 digitos): ");
        String isbn = scanner.nextLine().trim();
        System.out.print("  Titulo: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("  Autor: ");
        String autor = scanner.nextLine().trim();
        System.out.print("  Editorial: ");
        String editorial = scanner.nextLine().trim();
        System.out.print("  Anio de publicacion: ");
        int anio = 0;
        try { anio = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("  [Error] Anio invalido."); return; }
        System.out.print("  Categoria: ");
        String categoria = scanner.nextLine().trim();
        System.out.print("  Numero de ejemplares: ");
        int ejemplares = 0;
        try { ejemplares = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("  [Error] Numero invalido."); return; }

        if (titulo.isEmpty() || autor.isEmpty()) {
            System.out.println("  [Error] Titulo y autor son obligatorios.");
            return;
        }
        biblioteca.registrarLibro(new Libro(isbn, titulo, autor, editorial, anio, categoria, ejemplares));
    }

    static void menuBuscarLibro() {
        System.out.println("\n--- BUSCAR LIBRO ---");
        System.out.print("  Ingrese titulo, autor o categoria: ");
        String criterio = scanner.nextLine().trim();
        ArrayList<Libro> resultados = biblioteca.buscarLibro(criterio);
        if (resultados.isEmpty()) {
            System.out.println("  No se encontraron libros con: " + criterio);
        } else {
            System.out.println("  Resultados encontrados: " + resultados.size());
            for (Libro l : resultados) l.mostrarInformacion();
        }
    }

    static void menuRegistrarUsuario() {
        System.out.println("\n--- REGISTRAR USUARIO ---");
        System.out.print("  Numero de identificacion: ");
        String id = scanner.nextLine().trim();
        System.out.print("  Nombre completo: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("  Correo electronico: ");
        String correo = scanner.nextLine().trim();
        System.out.print("  Telefono: ");
        String telefono = scanner.nextLine().trim();
        System.out.print("  Direccion: ");
        String direccion = scanner.nextLine().trim();

        if (id.isEmpty() || nombre.isEmpty()) {
            System.out.println("  [Error] ID y nombre son obligatorios.");
            return;
        }
        biblioteca.registrarUsuario(new Usuario(id, nombre, correo, telefono, direccion));
    }

    static void menuBuscarUsuario() {
        System.out.println("\n--- BUSCAR USUARIO ---");
        System.out.print("  Ingrese ID o nombre del usuario: ");
        String criterio = scanner.nextLine().trim();
        Usuario usuario = biblioteca.buscarUsuario(criterio);
        if (usuario == null) {
            System.out.println("  No se encontro usuario con: " + criterio);
        } else {
            usuario.mostrarInformacion();
        }
    }

    static void menuRealizarPrestamo() {
        System.out.println("\n--- REALIZAR PRESTAMO ---");
        System.out.print("  ISBN del libro: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("  ID del usuario: ");
        String idUsuario = scanner.nextLine().trim();
        biblioteca.realizarPrestamo(isbn, idUsuario);
    }

    static void menuRegistrarDevolucion() {
        System.out.println("\n--- REGISTRAR DEVOLUCION ---");
        System.out.print("  ID del prestamo: ");
        try {
            int idPrestamo = Integer.parseInt(scanner.nextLine().trim());
            biblioteca.registrarDevolucion(idPrestamo);
        } catch (NumberFormatException e) {
            System.out.println("  [Error] ID de prestamo invalido.");
        }
    }

    static void menuPrestamosActivos() {
        ArrayList<Prestamo> activos = biblioteca.listarPrestamosActivos();
        System.out.println("\n===== PRESTAMOS ACTIVOS (" + activos.size() + ") =====");
        if (activos.isEmpty()) {
            System.out.println("  No hay prestamos activos.");
        } else {
            for (Prestamo p : activos) p.mostrarInformacion();
        }
    }

    static void menuPrestamosVencidos() {
        ArrayList<Prestamo> vencidos = biblioteca.listarPrestamosVencidos();
        System.out.println("\n===== PRESTAMOS VENCIDOS (" + vencidos.size() + ") =====");
        if (vencidos.isEmpty()) {
            System.out.println("  No hay prestamos vencidos.");
        } else {
            for (Prestamo p : vencidos) p.mostrarInformacion();
        }
    }
}
