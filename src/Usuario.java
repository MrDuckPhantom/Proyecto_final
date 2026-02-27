/**
 * Clase Usuario
 * Representa a una persona registrada en el sistema de biblioteca.
 * Controla informacion personal y la cantidad de prestamos activos.
 */
public class Usuario {

    // Identificador unico del usuario
    private String numeroIdentificacion;

    // Datos personales del usuario
    private String nombreCompleto;
    private String correoElectronico;
    private String telefono;
    private String direccion;

    // Contador de prestamos actualmente activos
    private int prestamosActivos;

    // Limite maximo de prestamos simultaneos permitidos
    private static final int LIMITE_PRESTAMOS = 2;

    /**
     * Constructor que inicializa los datos del usuario.
     */
    public Usuario(String numeroIdentificacion, String nombreCompleto,
                   String correoElectronico, String telefono, String direccion) {

        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccion = direccion;

        // Al registrarse no tiene prestamos activos
        this.prestamosActivos = 0;
    }

    /**
     * Incrementa el contador cuando el usuario realiza un prestamo.
     */
    public void agregarPrestamo() {
        prestamosActivos++;
    }

    /**
     * Disminuye el contador cuando el usuario devuelve un libro.
     */
    public void devolverPrestamo() {
        if (prestamosActivos > 0) {
            prestamosActivos--;
        }
    }

    /**
     * Verifica si el usuario puede solicitar otro prestamo.
     */
    public boolean puedePrestar() {
        return prestamosActivos < LIMITE_PRESTAMOS;
    }

    /**
     * Valida que el correo electronico tenga un formato correcto.
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) return false;

        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    /**
     * Muestra la informacion completa del usuario.
     */
    public void mostrarInformacion() {

        System.out.println("============================================");
        System.out.println("  ID:               " + numeroIdentificacion);
        System.out.println("  Nombre:           " + nombreCompleto);
        System.out.println("  Correo:           " + correoElectronico);
        System.out.println("  Telefono:         " + telefono);
        System.out.println("  Direccion:        " + direccion);
        System.out.println("  Prestamos activos:" + prestamosActivos + " / " + LIMITE_PRESTAMOS);
        System.out.println("  Puede prestar:    " + (puedePrestar() ? "SI" : "NO (limite alcanzado)"));
        System.out.println("============================================");
    }

    // ===================== GETTERS Y SETTERS =====================

    public String getNumeroIdentificacion() { return numeroIdentificacion; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getPrestamosActivos() { return prestamosActivos; }
}