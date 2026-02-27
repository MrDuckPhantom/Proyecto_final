import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Clase Prestamo
 * Representa la relacion entre un libro y un usuario cuando se realiza un prestamo.
 * Controla fechas, estado y posibles retrasos.
 */
public class Prestamo {

    // Cantidad de dias permitidos para devolver el libro
    private static final int DIAS_PRESTAMO = 14;

    // Identificador unico del prestamo
    private int idPrestamo;

    // Asociaciones con Libro y Usuario
    private Libro libro;
    private Usuario usuario;

    // Fechas relacionadas con el prestamo
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada;
    private LocalDate fechaDevolucionReal;

    // Estado actual del prestamo: ACTIVO, DEVUELTO o VENCIDO
    private String estadoPrestamo;

    /**
     * Constructor que inicializa el prestamo.
     * Calcula automaticamente la fecha estimada de devolucion.
     */
    public Prestamo(int idPrestamo, Libro libro, Usuario usuario) {

        this.idPrestamo = idPrestamo;
        this.libro = libro;
        this.usuario = usuario;

        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEstimada = fechaPrestamo.plusDays(DIAS_PRESTAMO);

        this.fechaDevolucionReal = null;
        this.estadoPrestamo = "ACTIVO";
    }

    /**
     * Registra la devolucion del libro.
     * Actualiza el estado y notifica al libro y usuario.
     */
    public boolean registrarDevolucion() {

        if (estadoPrestamo.equals("DEVUELTO")) {
            System.out.println("  [Error] Este prestamo ya fue devuelto.");
            return false;
        }

        this.fechaDevolucionReal = LocalDate.now();
        this.estadoPrestamo = "DEVUELTO";

        // Actualiza inventario y contador del usuario
        libro.devolver();
        usuario.devolverPrestamo();

        return true;
    }

    /**
     * Verifica si el prestamo esta vencido.
     */
    public boolean estaVencido() {

        if (estadoPrestamo.equals("DEVUELTO")) return false;

        return LocalDate.now().isAfter(fechaDevolucionEstimada);
    }

    /**
     * Calcula los dias de retraso si el prestamo esta vencido.
     */
    public long calcularDiasRetraso() {

        if (!estaVencido()) return 0;

        return ChronoUnit.DAYS.between(fechaDevolucionEstimada, LocalDate.now());
    }

    /**
     * Actualiza el estado a VENCIDO si corresponde.
     */
    public void actualizarEstado() {

        if (estadoPrestamo.equals("ACTIVO") && estaVencido()) {
            estadoPrestamo = "VENCIDO";
        }
    }

    /**
     * Muestra la informacion completa del prestamo.
     */
    public void mostrarInformacion() {

        actualizarEstado();

        System.out.println("--------------------------------------------");
        System.out.println("  ID Prestamo:      " + idPrestamo);
        System.out.println("  Libro:            " + libro.getTitulo() + " [ISBN: " + libro.getIsbn() + "]");
        System.out.println("  Usuario:          " + usuario.getNombreCompleto() + " [ID: " + usuario.getNumeroIdentificacion() + "]");
        System.out.println("  Fecha prestamo:   " + fechaPrestamo);
        System.out.println("  Fecha estimada:   " + fechaDevolucionEstimada);
        System.out.println("  Fecha devolucion: " + (fechaDevolucionReal != null ? fechaDevolucionReal : "Pendiente"));
        System.out.println("  Estado:           " + estadoPrestamo);

        if (estaVencido()) {
            System.out.println("  Dias de retraso:  " + calcularDiasRetraso());
        }

        System.out.println("--------------------------------------------");
    }

    // ===================== GETTERS =====================

    public int getIdPrestamo() { return idPrestamo; }
    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucionEstimada() { return fechaDevolucionEstimada; }
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }

    public String getEstadoPrestamo() {
        actualizarEstado();
        return estadoPrestamo;
    }
}