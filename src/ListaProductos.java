import java.time.LocalDate;

public class ListaProductos {

    //Clase estructura: lista enlazada simple de productos.
    //Guarda la referencia al primer nodo de la lista y la cantidad de nodos que contiene.

    //Atributos.
    private Nodo primero;
    private int tamano;

    //Constructor.

    //Crea una lista vacía.
    public ListaProductos() {
        this.primero = null;
        this.tamano = 0;
    }

    //Getters.
    public Nodo getPrimero() {
        return primero;
    }

    public int getTamano() {
        return tamano;
    }

    //Métodos de consulta.

    //Retorna true si la lista no tiene nodos.
    public boolean estaVacia() {
        return primero == null;
    }

    //Métodos de inserción.
    //No se permiten dos productos con el mismo nombre, porque el nombre se usa para buscarlos.

    //Inserta un nuevo nodo al inicio de la lista.
    //Retorna false si ya existe un producto con ese nombre.
    public boolean insertarNodoInicio(Producto producto) {
        if (buscarNodo(producto.getNombre()) != null) {
            return false;
        }
        Nodo nuevoNodo = new Nodo(producto);
        nuevoNodo.setSiguiente(primero); //El nuevo nodo apunta al que antes era el primero.
        primero = nuevoNodo;
        tamano++;
        return true;
    }

    //Inserta un nuevo nodo al final de la lista.
    //Retorna false si ya existe un producto con ese nombre.
    public boolean insertarNodoFinal(Producto producto) {
        if (buscarNodo(producto.getNombre()) != null) {
            return false;
        }
        Nodo nuevoNodo = new Nodo(producto);
        //Si la lista está vacía, el nuevo nodo pasa a ser el primero.
        if (primero == null) {
            primero = nuevoNodo;
            tamano++;
            return true;
        }
        //Se recorre la lista hasta el último nodo (aquel cuyo siguiente es null).
        Nodo nodoTemp = primero;
        while (nodoTemp.getSiguiente() != null) {
            nodoTemp = nodoTemp.getSiguiente();
        }
        nodoTemp.setSiguiente(nuevoNodo);
        tamano++;
        return true;
    }

    //Inserta un nuevo nodo en la posición indicada (la primera posición es 1).
    //Retorna false si la posición no es válida o si ya existe un producto con ese nombre.
    public boolean insertarNodoEnPosicion(int posicion, Producto producto) {
        if (posicion < 1 || posicion > tamano + 1) {
            return false;
        }
        if (posicion == 1) {
            return insertarNodoInicio(producto);
        }
        if (buscarNodo(producto.getNombre()) != null) {
            return false;
        }
        //Se avanza hasta el nodo anterior a la posición donde se va a insertar.
        Nodo nodoAnterior = primero;
        for (int i = 1; i < posicion - 1; i++) {
            nodoAnterior = nodoAnterior.getSiguiente();
        }
        Nodo nuevoNodo = new Nodo(producto);
        nuevoNodo.setSiguiente(nodoAnterior.getSiguiente());
        nodoAnterior.setSiguiente(nuevoNodo);
        tamano++;
        return true;
    }

    //Métodos de búsqueda.

    //Busca un nodo por el nombre del producto (sin importar mayúsculas).
    //Retorna el nodo encontrado, o null si la lista está vacía o no existe.
    public Nodo buscarNodo(String nombreBuscar) {
        Nodo nodoTemp = primero;
        while (nodoTemp != null && !nodoTemp.getDato().getNombre().equalsIgnoreCase(nombreBuscar)) {
            nodoTemp = nodoTemp.getSiguiente();
        }
        return nodoTemp;
    }

    //Retorna el nodo que está en la posición indicada (la primera posición es 1), o null si no existe.
    public Nodo obtenerNodo(int posicion) {
        if (posicion < 1 || posicion > tamano) {
            return null;
        }
        Nodo nodoTemp = primero;
        for (int i = 1; i < posicion; i++) {
            nodoTemp = nodoTemp.getSiguiente();
        }
        return nodoTemp;
    }

    //Métodos de modificación.

    //Modifica los datos del producto con el nombre indicado.
    //Los parámetros que lleguen en null no se modifican (se conserva el valor actual).
    //Retorna false si el producto no existe o si el nuevo nombre ya lo usa otro producto.
    public boolean modificarProducto(String nombre, String nuevoNombre, Double nuevoPrecio,
                                     String nuevaCategoria, Integer nuevaCantidad) {
        //Pendiente: lo implementa la Persona 3.
        return false;
    }

    //Cambia la fecha de vencimiento del producto (null indica que no aplica).
    //Retorna false si el producto no existe.
    public boolean modificarFechaVencimiento(String nombre, LocalDate fecha) {
        //Pendiente: lo implementa la Persona 3.
        return false;
    }

    //Agrega la ruta de una imagen a la lista de imágenes del producto.
    //Retorna false si el producto no existe.
    public boolean agregarImagen(String nombre, String ruta) {
        //Pendiente: lo implementa la Persona 3.
        return false;
    }

    //Métodos de eliminación.
    //Siempre retornan el nodo eliminado, o null si no se eliminó nada.

    //Elimina el primer nodo de la lista.
    public Nodo eliminarNodoInicio() {
        //Pendiente: lo implementa la Persona 3.
        return null;
    }

    //Elimina el último nodo de la lista.
    public Nodo eliminarNodoFinal() {
        //Pendiente: lo implementa la Persona 3.
        return null;
    }

    //Elimina el nodo cuyo producto tenga el nombre indicado.
    public Nodo eliminarNodo(String nombreEliminar) {
        //Pendiente: lo implementa la Persona 3.
        return null;
    }

    //Elimina todos los nodos de la lista.
    public void vaciarLista() {
        //Pendiente: lo implementa la Persona 3.
    }

    //Métodos de recorrido.

    //Recorre la lista completa e imprime la información de cada producto y sus imágenes.
    public void mostrarLista() {
        if (primero == null) {
            System.out.println("La lista se encuentra vacía.");
            return;
        }
        Nodo nodoTemp = primero;
        int posicion = 1;
        while (nodoTemp != null) {
            System.out.println(posicion + ". " + nodoTemp);
            for (String ruta : nodoTemp.getDato().getListaImagenes()) {
                System.out.println("      Imagen: " + ruta);
            }
            nodoTemp = nodoTemp.getSiguiente();
            posicion++;
        }
    }

    //Recorre la lista e imprime el costo total de cada producto (precio x cantidad)
    //y el costo total acumulado de la lista completa.
    public void imprimirReporteCostos() {
        //Pendiente: lo implementa la Persona 3.
    }

    //Recorta un texto largo para que no desordene las columnas del reporte.
    private String recortar(String texto, int maximo) {
        //Pendiente: lo implementa la Persona 3.
        return texto;
    }
}
