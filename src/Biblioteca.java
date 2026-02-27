import java.util.ArrayList;

/**
 * Clase Biblioteca
 * Controla toda la logica del sistema.
 * Administra libros, usuarios y prestamos.
 */
public class Biblioteca {

    // Lista que almacena todos los libros registrados
    private ArrayList<Libro> catalogoLibros;

    // Lista que almacena los usuarios registrados
    private ArrayList<Usuario> usuariosRegistrados;

    // Lista que guarda el historial de prestamos
    private ArrayList<Prestamo> historialPrestamos;

    // Contador autoincremental para asignar ID a los prestamos
    private int contadorPrestamos;

    /**
     * Constructor que inicializa las colecciones vacias
     */
    public Biblioteca() {
        this.catalogoLibros = new ArrayList<>();
        this.usuariosRegistrados = new ArrayList<>();
        this.historialPrestamos = new ArrayList<>();
        this.contadorPrestamos = 1;
    }

    /**
     * Registra un nuevo libro validando ISBN unico
     */
    public boolean registrarLibro(Libro libro) {
        if (!Libro.validarISBN(libro.getIsbn())) {
            System.out.println("  [Error] ISBN invalido. Debe tener 10 o 13 digitos.");
            return false;
        }
        if (buscarLibroPorISBN(libro.getIsbn()) != null) {
            System.out.println("  [Error] Ya existe un libro con ISBN: " + libro.getIsbn());
            return false;
        }
        catalogoLibros.add(libro);
        System.out.println("  [OK] Libro registrado: " + libro.getTitulo());
        return true;
    }

    /**
     * Busca un libro por su ISBN
     */
    public Libro buscarLibroPorISBN(String isbn) {
        for (Libro l : catalogoLibros) {
            if (l.getIsbn().equals(isbn)) return l;
        }
        return null;
    }

    /**
     * Busca libros por titulo, autor o categoria
     */
    public ArrayList<Libro> buscarLibro(String criterio) {
        ArrayList<Libro> resultados = new ArrayList<>();
        String criterioBaja = criterio.toLowerCase();
        for (Libro l : catalogoLibros) {
            if (l.getTitulo().toLowerCase().contains(criterioBaja)
                    || l.getAutor().toLowerCase().contains(criterioBaja)
                    || l.getCategoria().toLowerCase().contains(criterioBaja)) {
                resultados.add(l);
            }
        }
        return resultados;
    }

    /**
     * Registra un nuevo usuario validando correo e ID unico
     */
    public boolean registrarUsuario(Usuario usuario) {
        if (!Usuario.validarEmail(usuario.getCorreoElectronico())) {
            System.out.println("  [Error] Formato de correo invalido: " + usuario.getCorreoElectronico());
            return false;
        }
        if (buscarUsuario(usuario.getNumeroIdentificacion()) != null) {
            System.out.println("  [Error] Ya existe un usuario con ID: " + usuario.getNumeroIdentificacion());
            return false;
        }
        usuariosRegistrados.add(usuario);
        System.out.println("  [OK] Usuario registrado: " + usuario.getNombreCompleto());
        return true;
    }

    /**
     * Busca un usuario por ID o nombre
     */
    public Usuario buscarUsuario(String identificacion) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getNumeroIdentificacion().equals(identificacion)
                    || u.getNombreCompleto().equalsIgnoreCase(identificacion)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Realiza un prestamo aplicando reglas de negocio
     */
    public boolean realizarPrestamo(String isbnLibro, String idUsuario) {

        // Validar existencia y disponibilidad del libro
        Libro libro = buscarLibroPorISBN(isbnLibro);
        if (libro == null) return false;
        if (!libro.estaDisponible()) return false;

        // Validar usuario
        Usuario usuario = buscarUsuario(idUsuario);
        if (usuario == null) return false;

        // Validar reglas del sistema
        if (tieneVencidos(usuario)) return false;
        if (!usuario.puedePrestar()) return false;

        // Crear y registrar prestamo
        Prestamo nuevoPrestamo = new Prestamo(contadorPrestamos++, libro, usuario);
        historialPrestamos.add(nuevoPrestamo);

        libro.prestar();
        usuario.agregarPrestamo();

        return true;
    }

    /**
     * Registra la devolucion de un prestamo
     */
    public boolean registrarDevolucion(int idPrestamo) {
        Prestamo prestamo = buscarPrestamoPorId(idPrestamo);
        if (prestamo == null) return false;

        return prestamo.registrarDevolucion();
    }

    /**
     * Busca un prestamo por ID
     */
    public Prestamo buscarPrestamoPorId(int idPrestamo) {
        for (Prestamo p : historialPrestamos) {
            if (p.getIdPrestamo() == idPrestamo) return p;
        }
        return null;
    }

    /**
     * Verifica si un usuario tiene prestamos vencidos
     */
    private boolean tieneVencidos(Usuario usuario) {
        for (Prestamo p : historialPrestamos) {
            if (p.getUsuario().getNumeroIdentificacion().equals(usuario.getNumeroIdentificacion())
                    && p.estaVencido()) {
                return true;
            }
        }
        return false;
    }

    // Metodos de reporte y consulta
    public void generarReporteGeneral() { }

    public ArrayList<Prestamo> listarPrestamosActivos() { return new ArrayList<>(); }

    public ArrayList<Prestamo> listarPrestamosVencidos() { return new ArrayList<>(); }

    public void listarLibros() { }

    public void listarUsuarios() { }
}