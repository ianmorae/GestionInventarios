public class ArbolProductos {

    //Clase estructura: árbol binario de búsqueda de productos.
    //El nombre del producto (sin distinguir mayúsculas) es la llave del árbol:
    //los nombres menores van al subárbol izquierdo y los mayores al derecho.
    //Es el inventario de la Tienda.

    //Atributos.
    private NodoArbol raiz;
    private int tamano;

    //Constructor.

    //Crea un árbol vacío.
    public ArbolProductos() {
        this.raiz = null;
        this.tamano = 0;
    }

    //Getters.
    public NodoArbol getRaiz() {
        return raiz;
    }

    public int getTamano() {
        return tamano;
    }

    //Métodos de consulta.

    //Retorna true si el árbol no tiene nodos.
    public boolean estaVacio() {
        return raiz == null;
    }

    //Retorna la altura del árbol (un árbol vacío tiene altura 0; solo la raíz, 1).
    public int altura() {
        return altura(raiz);
    }

    private int altura(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    //Método de inserción.

    //Inserta el producto en la posición que le corresponde según su nombre.
    //Retorna false si ya existe un producto con ese nombre.
    public boolean insertar(Producto producto) {
        NodoArbol nuevoNodo = new NodoArbol(producto);
        if (raiz == null) {
            raiz = nuevoNodo;
            tamano++;
            return true;
        }
        //Se desciende por el árbol hasta encontrar el lugar vacío donde va el nuevo nodo.
        NodoArbol nodoTemp = raiz;
        while (true) {
            int comparacion = producto.getNombre().compareToIgnoreCase(nodoTemp.getDato().getNombre());
            if (comparacion == 0) {
                return false; //Nombre repetido.
            }
            if (comparacion < 0) {
                if (nodoTemp.getIzquierdo() == null) {
                    nodoTemp.setIzquierdo(nuevoNodo);
                    tamano++;
                    return true;
                }
                nodoTemp = nodoTemp.getIzquierdo();
            } else {
                if (nodoTemp.getDerecho() == null) {
                    nodoTemp.setDerecho(nuevoNodo);
                    tamano++;
                    return true;
                }
                nodoTemp = nodoTemp.getDerecho();
            }
        }
    }

    //Método de búsqueda.

    //Busca un nodo por el nombre del producto (sin importar mayúsculas).
    //Retorna el nodo encontrado, o null si el árbol está vacío o no existe.
    public NodoArbol buscar(String nombreBuscar) {
        NodoArbol nodoTemp = raiz;
        while (nodoTemp != null) {
            int comparacion = nombreBuscar.compareToIgnoreCase(nodoTemp.getDato().getNombre());
            if (comparacion == 0) {
                return nodoTemp;
            }
            if (comparacion < 0) {
                nodoTemp = nodoTemp.getIzquierdo();
            } else {
                nodoTemp = nodoTemp.getDerecho();
            }
        }
        return null;
    }

    //Método de eliminación.

    //Elimina el nodo cuyo producto tenga el nombre indicado.
    //Retorna el producto eliminado, o null si no existe.
    public Producto eliminar(String nombreEliminar) {
        NodoArbol nodoBuscado = buscar(nombreEliminar);
        if (nodoBuscado == null) {
            return null;
        }
        Producto eliminado = nodoBuscado.getDato();
        raiz = eliminar(raiz, nombreEliminar);
        tamano--;
        return eliminado;
    }

    //Elimina de forma recursiva y retorna la nueva raíz del subárbol.
    private NodoArbol eliminar(NodoArbol nodo, String nombreEliminar) {
        if (nodo == null) {
            return null;
        }
        int comparacion = nombreEliminar.compareToIgnoreCase(nodo.getDato().getNombre());
        if (comparacion < 0) {
            nodo.setIzquierdo(eliminar(nodo.getIzquierdo(), nombreEliminar));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminar(nodo.getDerecho(), nombreEliminar));
        } else {
            //Caso 1 y 2: el nodo tiene a lo sumo un hijo; ese hijo ocupa su lugar.
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }
            //Caso 3: tiene dos hijos. Se reemplaza por su sucesor inorden
            //(el menor del subárbol derecho) y luego se elimina ese sucesor.
            NodoArbol sucesor = nodo.getDerecho();
            while (sucesor.getIzquierdo() != null) {
                sucesor = sucesor.getIzquierdo();
            }
            nodo.setDato(sucesor.getDato());
            nodo.setDerecho(eliminar(nodo.getDerecho(), sucesor.getDato().getNombre()));
        }
        return nodo;
    }

    //Métodos de recorrido.

    //Recorre el árbol en inorden (orden alfabético por nombre) e imprime cada producto y sus imágenes.
    public void mostrarInventario() {
        if (raiz == null) {
            System.out.println("El inventario se encuentra vacío.");
            return;
        }
        mostrarInorden(raiz, new int[]{1});
    }

    //El contador va dentro de un arreglo para poder compartirlo entre las llamadas recursivas.
    private void mostrarInorden(NodoArbol nodo, int[] contador) {
        if (nodo == null) {
            return;
        }
        mostrarInorden(nodo.getIzquierdo(), contador);
        System.out.println(contador[0] + ". " + nodo);
        for (String ruta : nodo.getDato().getListaImagenes()) {
            System.out.println("      Imagen: " + ruta);
        }
        contador[0]++;
        mostrarInorden(nodo.getDerecho(), contador);
    }

    //Retorna el valor total del inventario (suma de precio x cantidad de todos los productos).
    public double calcularValorInventario() {
        return calcularValor(raiz);
    }

    private double calcularValor(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.getDato().getCostoTotal()
                + calcularValor(nodo.getIzquierdo())
                + calcularValor(nodo.getDerecho());
    }
}
