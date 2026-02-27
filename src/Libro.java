public class Libro {

    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private int anioPublicacion;
    private String categoria;
    private int ejemplaresTotales;
    private int ejemplaresDisponibles;

    public Libro(String isbn, String titulo, String autor, String editorial,
                 int anioPublicacion, String categoria, int ejemplaresTotales) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.categoria = categoria;
        this.ejemplaresTotales = ejemplaresTotales;
        this.ejemplaresDisponibles = ejemplaresTotales;
    }

    public boolean prestar() {
        if (ejemplaresDisponibles > 0) {
            ejemplaresDisponibles--;
            return true;
        }
        return false;
    }

    public boolean devolver() {
        if (ejemplaresDisponibles < ejemplaresTotales) {
            ejemplaresDisponibles++;
            return true;
        }
        return false;
    }

    public boolean estaDisponible() {
        return ejemplaresDisponibles > 0;
    }

    public static boolean validarISBN(String isbn) {
        if (isbn == null || isbn.isEmpty()) return false;
        String isbnLimpio = isbn.replace("-", "").trim();
        return isbnLimpio.matches("\\d{10}") || isbnLimpio.matches("\\d{13}");
    }

    public void mostrarInformacion() {
        System.out.println("============================================");
        System.out.println("  ISBN:             " + isbn);
        System.out.println("  Titulo:           " + titulo);
        System.out.println("  Autor:            " + autor);
        System.out.println("  Editorial:        " + editorial);
        System.out.println("  Anio publicacion: " + anioPublicacion);
        System.out.println("  Categoria:        " + categoria);
        System.out.println("  Ejemplares:       " + ejemplaresDisponibles + " / " + ejemplaresTotales);
        System.out.println("  Disponibilidad:   " + (estaDisponible() ? "DISPONIBLE" : "NO DISPONIBLE"));
        System.out.println("============================================");
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public int getEjemplaresTotales() { return ejemplaresTotales; }
    public int getEjemplaresDisponibles() { return ejemplaresDisponibles; }
}
