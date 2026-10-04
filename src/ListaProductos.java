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
        Nodo nodo = buscarNodo(nombre);
        if (nodo == null) {
            return false;
        }
        Producto producto = nodo.getDato();
        if (nuevoNombre != null) {
            //Se valida que el nuevo nombre no esté repetido en otro producto.
            Nodo repetido = buscarNodo(nuevoNombre);
            if (repetido != null && repetido != nodo) {
                return false;
            }
            producto.setNombre(nuevoNombre);
        }
        if (nuevoPrecio != null) {
            producto.setPrecio(nuevoPrecio);
        }
        if (nuevaCategoria != null) {
            producto.setCategoria(nuevaCategoria);
        }
        if (nuevaCantidad != null) {
            producto.setCantidad(nuevaCantidad);
        }
        return true;
    }

    //Cambia la fecha de vencimiento del producto (null indica que no aplica).
    //Retorna false si el producto no existe.
    public boolean modificarFechaVencimiento(String nombre, LocalDate fecha) {
        Nodo nodo = buscarNodo(nombre);
        if (nodo == null) {
            return false;
        }
        nodo.getDato().setFechaVencimiento(fecha);
        return true;
    }

    //Agrega la ruta de una imagen a la lista de imágenes del producto.
    //Retorna false si el producto no existe.
    public boolean agregarImagen(String nombre, String ruta) {
        Nodo nodo = buscarNodo(nombre);
        if (nodo == null) {
            return false;
        }
        nodo.getDato().agregarImagen(ruta);
        return true;
    }

    //Métodos de eliminación.
    //Siempre retornan el nodo eliminado, o null si no se eliminó nada.

    //Elimina el primer nodo de la lista.
    public Nodo eliminarNodoInicio() {
        if (primero == null) {
            return null;
        }
        Nodo nodoEliminado = primero;
        primero = primero.getSiguiente(); //El segundo nodo pasa a ser el primero.
        nodoEliminado.setSiguiente(null);
        tamano--;
        return nodoEliminado;
    }

    //Elimina el último nodo de la lista.
    public Nodo eliminarNodoFinal() {
        if (primero == null) {
            return null;
        }
        //Si solo hay un nodo, eliminar el último es lo mismo que eliminar el primero.
        if (primero.getSiguiente() == null) {
            return eliminarNodoInicio();
        }
        //Se busca el penúltimo nodo para dejarlo como el nuevo último.
        Nodo nodoAnterior = primero;
        while (nodoAnterior.getSiguiente().getSiguiente() != null) {
            nodoAnterior = nodoAnterior.getSiguiente();
        }
        Nodo nodoEliminado = nodoAnterior.getSiguiente();
        nodoAnterior.setSiguiente(null);
        tamano--;
        return nodoEliminado;
    }

    //Elimina el nodo cuyo producto tenga el nombre indicado.
    public Nodo eliminarNodo(String nombreEliminar) {
        if (primero == null) {
            return null;
        }
        if (primero.getDato().getNombre().equalsIgnoreCase(nombreEliminar)) {
            return eliminarNodoInicio();
        }
        //Se recorre la lista guardando el nodo anterior, para poder reconectar la secuencia.
        Nodo nodoAnterior = primero;
        Nodo nodoActual = primero.getSiguiente();
        while (nodoActual != null && !nodoActual.getDato().getNombre().equalsIgnoreCase(nombreEliminar)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getSiguiente();
        }
        if (nodoActual != null) {
            nodoAnterior.setSiguiente(nodoActual.getSiguiente());
            nodoActual.setSiguiente(null);
            tamano--;
        }
        return nodoActual;
    }

    //Elimina todos los nodos de la lista.
    public void vaciarLista() {
        primero = null;
        tamano = 0;
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
        if (primero == null) {
            System.out.println("La lista se encuentra vacía.");
            return;
        }
        System.out.println(String.format("%-28s %14s %8s %16s", "PRODUCTO", "PRECIO UNIT.", "CANT.", "COSTO TOTAL"));
        System.out.println("-".repeat(70));
        double acumulado = 0;
        Nodo nodoTemp = primero;
        while (nodoTemp != null) {
            Producto producto = nodoTemp.getDato();
            double total = producto.getCostoTotal();
            acumulado += total;
            System.out.println(String.format("%-28s %14s %8d %16s",
                    recortar(producto.getNombre(), 28),
                    String.format("%,.2f", producto.getPrecio()),
                    producto.getCantidad(),
                    String.format("%,.2f", total)));
            nodoTemp = nodoTemp.getSiguiente();
        }
        System.out.println("-".repeat(70));
        System.out.println(String.format("%-51s %18s", "COSTO TOTAL ACUMULADO (CRC):", String.format("%,.2f", acumulado)));
    }

    //Recorta un texto largo para que no desordene las columnas del reporte.
    private String recortar(String texto, int maximo) {
        if (texto.length() <= maximo) {
            return texto;
        }
        return texto.substring(0, maximo - 3) + "...";
    }
}
