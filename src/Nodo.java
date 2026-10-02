public class Nodo {

    //Clase nodo para la lista enlazada simple de productos.
    //El último atributo es de la misma clase y representa la referencia al siguiente nodo de la lista.

    //Atributos.
    private Producto dato;
    private Nodo siguiente;

    //Constructor.

    //Crea un nodo con el producto recibido. Al crearse no apunta a ningún otro nodo.
    public Nodo(Producto dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    //Getters y setters.
    public Producto getDato() {
        return dato;
    }

    public void setDato(Producto dato) {
        this.dato = dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    //Retorna la información del producto que contiene el nodo.
    @Override
    public String toString() {
        return dato.toString();
    }
}
