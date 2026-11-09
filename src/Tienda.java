import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tienda {

    // Clase entidad que representa la tienda en línea.
    // Gestiona el inventario de productos y la cola de clientes por atender.

    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Atributos
    private String nombre;
    private ArbolProductos inventario;
    private ColaClientes colaClientes;

    // Constructor: Crea una tienda con inventario y cola vacíos.
    public Tienda(String nombre) {
        this.nombre = nombre;
        this.inventario = new ArbolProductos();
        this.colaClientes = new ColaClientes();
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public ArbolProductos getInventario() {
        return inventario;
    }

    public ColaClientes getColaClientes() {
        return colaClientes;
    }

    // Métodos de inventario

    // Inserta un producto en el inventario. Retorna false si ya existe uno con ese nombre.
    public boolean agregarProducto(Producto producto) {
        return inventario.insertar(producto);
    }

    // Busca un producto del inventario por nombre. Retorna null si no existe.
    public NodoArbol buscarProducto(String nombre) {
        return inventario.buscar(nombre);
    }

    // Elimina un producto del inventario. Retorna el producto eliminado o null si no existe.
    public Producto eliminarProducto(String nombre) {
        return inventario.eliminar(nombre);
    }

    // Métodos de compra

    // Pasa al carrito del cliente la cantidad indicada de un producto del inventario.
    public boolean agregarAlCarrito(Cliente cliente, String nombreProducto, int cantidad) {
        NodoArbol nodo = inventario.buscar(nombreProducto);
        if (nodo == null || cantidad < 1) {
            return false;
        }
        Producto enInventario = nodo.getDato();
        if (cantidad > enInventario.getCantidad()) {
            return false;
        }
        Producto copia = new Producto(enInventario.getNombre(), enInventario.getPrecio(),
                enInventario.getCategoria(), enInventario.getFechaVencimiento(), cantidad);
        cliente.agregarAlCarrito(copia);
        enInventario.setCantidad(enInventario.getCantidad() - cantidad);
        return true;
    }

    // Métodos de la cola de clientes

    // Agrega un cliente a la cola de atención.
    public void encolarCliente(Cliente cliente) {
        colaClientes.encolar(cliente);
    }

    // Retorna true si hay clientes esperando.
    public boolean hayClientes() {
        return !colaClientes.estaVacia();
    }

    // Saca de la cola al cliente que le toca ser atendido.
    public Cliente atenderSiguienteCliente() {
        return colaClientes.desencolar();
    }

    // Imprime la factura asociada con el carrito del cliente, incluyendo costo total acumulado.
    public void imprimirFactura(Cliente cliente) {
        System.out.println("\n======================================================================");
        System.out.println("                        FACTURA - " + nombre);
        System.out.println("======================================================================");
        System.out.println("Fecha:   " + LocalDateTime.now().format(FORMATO_FECHA_HORA));
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Tipo:    " + cliente.getTipoCliente() + " (prioridad " + cliente.getPrioridad() + ")");
        System.out.println("----------------------------------------------------------------------");
        cliente.getCarrito().imprimirReporteCostos();
        System.out.println("======================================================================");
        System.out.println("              Gracias por su compra en " + nombre + ".");
    }
}