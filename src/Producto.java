import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Producto {

    //Clase entidad que representa un producto de la tienda de tecnología.
    //Cada producto se almacena dentro de un Nodo de la ListaProductos.

    //Formato con el que se leen y muestran las fechas de vencimiento (ej. 31/12/2026).
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //Atributos.
    private String nombre;
    private double precio;
    private String categoria;
    private LocalDate fechaVencimiento; //Es null cuando el producto no vence (no aplica).
    private int cantidad; //Unidades del producto (en el carrito o en el inventario).
    private ArrayList<String> listaImagenes; //Rutas de las imágenes dentro de la carpeta imagenes/.

    //Constructores.

    //Crea un producto con todos sus datos. La lista de imágenes inicia vacía.
    public Producto(String nombre, double precio, String categoria,
                    LocalDate fechaVencimiento, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.listaImagenes = new ArrayList<>();
    }

    //Crea un producto que no tiene fecha de vencimiento (la mayoría de productos de tecnología).
    public Producto(String nombre, double precio, String categoria, int cantidad) {
        this(nombre, precio, categoria, null, cantidad);
    }

    //Getters y setters.
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ArrayList<String> getListaImagenes() {
        return listaImagenes;
    }

    public void setListaImagenes(ArrayList<String> listaImagenes) {
        this.listaImagenes = listaImagenes;
    }

    //Otros métodos.

    //Agrega la ruta de una imagen a la lista de imágenes del producto.
    public void agregarImagen(String ruta) {
        listaImagenes.add(ruta);
    }

    //Calcula el costo total del producto en función de su cantidad (precio x cantidad).
    public double getCostoTotal() {
        return precio * cantidad;
    }

    //Retorna la información del producto en una sola línea.
    @Override
    public String toString() {
        String fecha;
        if (fechaVencimiento == null) {
            fecha = "No aplica";
        } else {
            fecha = fechaVencimiento.format(FORMATO_FECHA);
        }
        return String.format("%s | Categoría: %s | Precio: CRC %,.2f | Cantidad: %d | Vence: %s | Imágenes: %d",
                nombre, categoria, precio, cantidad, fecha, listaImagenes.size());
    }
}
