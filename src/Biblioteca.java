import java.util.ArrayList;

public class Biblioteca {

    private ArrayList<Libro> catalogoLibros;
    private ArrayList<Usuario> usuariosRegistrados;
    private ArrayList<Prestamo> historialPrestamos;
    private int contadorPrestamos;

    public Biblioteca() {
        this.catalogoLibros = new ArrayList<>();
        this.usuariosRegistrados = new ArrayList<>();
        this.historialPrestamos = new ArrayList<>();
        this.contadorPrestamos = 1;
    }

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

    public Libro buscarLibroPorISBN(String isbn) {
        for (Libro l : catalogoLibros) {
            if (l.getIsbn().equals(isbn)) return l;
        }
        return null;
    }

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

    public Usuario buscarUsuario(String identificacion) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getNumeroIdentificacion().equals(identificacion)
                    || u.getNombreCompleto().equalsIgnoreCase(identificacion)) {
                return u;
            }
        }
        return null;
    }

    public boolean realizarPrestamo(String isbnLibro, String idUsuario) {
        Libro libro = buscarLibroPorISBN(isbnLibro);
        if (libro == null) {
            System.out.println("  [Error] No se encontro libro con ISBN: " + isbnLibro);
            return false;
        }
        if (!libro.estaDisponible()) {
            System.out.println("  [Error] El libro '" + libro.getTitulo() + "' no tiene ejemplares disponibles.");
            return false;
        }
        Usuario usuario = buscarUsuario(idUsuario);
        if (usuario == null) {
            System.out.println("  [Error] No se encontro usuario con ID: " + idUsuario);
            return false;
        }
        if (tieneVencidos(usuario)) {
            System.out.println("  [Error] El usuario '" + usuario.getNombreCompleto() + "' tiene prestamos vencidos.");
            return false;
        }
        if (!usuario.puedePrestar()) {
            System.out.println("  [Error] El usuario '" + usuario.getNombreCompleto() + "' ya tiene " + usuario.getPrestamosActivos() + " prestamos activos (limite: 2).");
            return false;
        }
        Prestamo nuevoPrestamo = new Prestamo(contadorPrestamos++, libro, usuario);
        historialPrestamos.add(nuevoPrestamo);
        libro.prestar();
        usuario.agregarPrestamo();
        System.out.println("  [OK] Prestamo registrado. ID: " + nuevoPrestamo.getIdPrestamo()
                + " | Libro: " + libro.getTitulo()
                + " | Usuario: " + usuario.getNombreCompleto()
                + " | Devolucion estimada: " + nuevoPrestamo.getFechaDevolucionEstimada());
        return true;
    }

    public boolean registrarDevolucion(int idPrestamo) {
        Prestamo prestamo = buscarPrestamoPorId(idPrestamo);
        if (prestamo == null) {
            System.out.println("  [Error] No se encontro prestamo con ID: " + idPrestamo);
            return false;
        }
        boolean resultado = prestamo.registrarDevolucion();
        if (resultado) {
            System.out.println("  [OK] Devolucion registrada para prestamo ID " + idPrestamo
                    + " | Libro: " + prestamo.getLibro().getTitulo());
        }
        return resultado;
    }

    public Prestamo buscarPrestamoPorId(int idPrestamo) {
        for (Prestamo p : historialPrestamos) {
            if (p.getIdPrestamo() == idPrestamo) return p;
        }
        return null;
    }

    private boolean tieneVencidos(Usuario usuario) {
        for (Prestamo p : historialPrestamos) {
            if (p.getUsuario().getNumeroIdentificacion().equals(usuario.getNumeroIdentificacion())
                    && p.estaVencido()) {
                return true;
            }
        }
        return false;
    }

    public void generarReporteGeneral() {
        int totalActivos = listarPrestamosActivos().size();
        int totalVencidos = listarPrestamosVencidos().size();
        System.out.println("\n========== REPORTE GENERAL ==========");
        System.out.println("  Libros en catalogo:    " + catalogoLibros.size());
        System.out.println("  Usuarios registrados:  " + usuariosRegistrados.size());
        System.out.println("  Total prestamos:       " + historialPrestamos.size());
        System.out.println("  Prestamos activos:     " + totalActivos);
        System.out.println("  Prestamos vencidos:    " + totalVencidos);
        System.out.println("======================================\n");
    }

    public ArrayList<Prestamo> listarPrestamosActivos() {
        ArrayList<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : historialPrestamos) {
            String estado = p.getEstadoPrestamo();
            if (estado.equals("ACTIVO") || estado.equals("VENCIDO")) {
                activos.add(p);
            }
        }
        return activos;
    }

    public ArrayList<Prestamo> listarPrestamosVencidos() {
        ArrayList<Prestamo> vencidos = new ArrayList<>();
        for (Prestamo p : historialPrestamos) {
            if (p.estaVencido()) vencidos.add(p);
        }
        return vencidos;
    }

    public void listarLibros() {
        if (catalogoLibros.isEmpty()) {
            System.out.println("  No hay libros en el catalogo.");
            return;
        }
        System.out.println("\n===== CATALOGO DE LIBROS =====");
        for (Libro l : catalogoLibros) l.mostrarInformacion();
    }

    public void listarUsuarios() {
        if (usuariosRegistrados.isEmpty()) {
            System.out.println("  No hay usuarios registrados.");
            return;
        }
        System.out.println("\n===== USUARIOS REGISTRADOS =====");
        for (Usuario u : usuariosRegistrados) u.mostrarInformacion();
    }

    public ArrayList<Libro> getCatalogoLibros() { return catalogoLibros; }
    public ArrayList<Usuario> getUsuariosRegistrados() { return usuariosRegistrados; }
    public ArrayList<Prestamo> getHistorialPrestamos() { return historialPrestamos; }
}
