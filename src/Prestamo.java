import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Prestamo {

    private static final int DIAS_PRESTAMO = 14;

    private int idPrestamo;
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada;
    private LocalDate fechaDevolucionReal;
    private String estadoPrestamo;

    public Prestamo(int idPrestamo, Libro libro, Usuario usuario) {
        this.idPrestamo = idPrestamo;
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEstimada = fechaPrestamo.plusDays(DIAS_PRESTAMO);
        this.fechaDevolucionReal = null;
        this.estadoPrestamo = "ACTIVO";
    }

    public boolean registrarDevolucion() {
        if (estadoPrestamo.equals("DEVUELTO")) {
            System.out.println("  [Error] Este prestamo ya fue devuelto.");
            return false;
        }
        this.fechaDevolucionReal = LocalDate.now();
        this.estadoPrestamo = "DEVUELTO";
        libro.devolver();
        usuario.devolverPrestamo();
        return true;
    }

    public boolean estaVencido() {
        if (estadoPrestamo.equals("DEVUELTO")) return false;
        return LocalDate.now().isAfter(fechaDevolucionEstimada);
    }

    public long calcularDiasRetraso() {
        if (!estaVencido()) return 0;
        return ChronoUnit.DAYS.between(fechaDevolucionEstimada, LocalDate.now());
    }

    public void actualizarEstado() {
        if (estadoPrestamo.equals("ACTIVO") && estaVencido()) {
            estadoPrestamo = "VENCIDO";
        }
    }

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

    public int getIdPrestamo() { return idPrestamo; }
    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucionEstimada() { return fechaDevolucionEstimada; }
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public String getEstadoPrestamo() { actualizarEstado(); return estadoPrestamo; }
}
