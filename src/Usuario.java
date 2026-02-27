public class Usuario {

    private String numeroIdentificacion;
    private String nombreCompleto;
    private String correoElectronico;
    private String telefono;
    private String direccion;
    private int prestamosActivos;

    private static final int LIMITE_PRESTAMOS = 2;

    public Usuario(String numeroIdentificacion, String nombreCompleto,
                   String correoElectronico, String telefono, String direccion) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccion = direccion;
        this.prestamosActivos = 0;
    }

    public void agregarPrestamo() {
        prestamosActivos++;
    }

    public void devolverPrestamo() {
        if (prestamosActivos > 0) {
            prestamosActivos--;
        }
    }

    public boolean puedePrestar() {
        return prestamosActivos < LIMITE_PRESTAMOS;
    }

    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

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
