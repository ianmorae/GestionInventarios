public class NodoCliente {

    //Clase nodo para la cola de clientes.
    //El último atributo es de la misma clase y representa la referencia al siguiente cliente en la cola.

    //Atributos.
    private Cliente dato;
    private NodoCliente siguiente;

    //Constructor.

    //Crea un nodo con el cliente recibido. Al crearse no apunta a ningún otro nodo.
    public NodoCliente(Cliente dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    //Getters y setters.
    public Cliente getDato() {
        return dato;
    }

    public void setDato(Cliente dato) {
        this.dato = dato;
    }

    public NodoCliente getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCliente siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public String toString() {
        return dato.toString();
    }
}
