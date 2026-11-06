public class Cliente {

    //Clase entidad que representa a un cliente de la tienda.
    //Cada cliente tiene su carrito de compras, que es una ListaProductos.

    //Prioridades de atención.
    public static final int PRIORIDAD_BASICA = 1;
    public static final int PRIORIDAD_AFILIADO = 2;
    public static final int PRIORIDAD_PREMIUM = 3;

    //Atributos.
    private String nombre;
    private int prioridad; //Entero entre 1 y 3: 1 básico, 2 afiliado, 3 premium.
    private ListaProductos carrito;

    //Constructor.

    //Crea un cliente con carrito vacío. Lanza una excepción si la prioridad no está entre 1 y 3.
    public Cliente(String nombre, int prioridad) {
        if (prioridad < PRIORIDAD_BASICA || prioridad > PRIORIDAD_PREMIUM) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 3.");
        }
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.carrito = new ListaProductos();
    }

    //Getters y setters.
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        if (prioridad < PRIORIDAD_BASICA || prioridad > PRIORIDAD_PREMIUM) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 3.");
        }
        this.prioridad = prioridad;
    }

    public ListaProductos getCarrito() {
        return carrito;
    }

    //Otros métodos.

    //Retorna el tipo de cliente según su prioridad.
    public String getTipoCliente() {
        switch (prioridad) {
            case PRIORIDAD_PREMIUM:
                return "Premium";
            case PRIORIDAD_AFILIADO:
                return "Afiliado";
            default:
                return "Básico";
        }
    }

    //Agrega un producto al carrito. Si el carrito ya tiene un producto con ese nombre,
    //solo se suma la cantidad (la lista no admite nombres repetidos).
    public void agregarAlCarrito(Producto producto) {
        Nodo existente = carrito.buscarNodo(producto.getNombre());
        if (existente != null) {
            Producto enCarrito = existente.getDato();
            enCarrito.setCantidad(enCarrito.getCantidad() + producto.getCantidad());
        } else {
            carrito.insertarNodoFinal(producto);
        }
    }

    //Retorna el costo total acumulado de los productos del carrito.
    public double calcularTotal() {
        return carrito.calcularCostoTotal();
    }

    @Override
    public String toString() {
        return String.format("%s | Tipo: %s (prioridad %d) | Productos distintos en carrito: %d",
                nombre, getTipoCliente(), prioridad, carrito.getTamano());
    }
}
