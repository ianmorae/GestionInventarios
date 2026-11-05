public class NodoArbol {

    //Clase nodo para el árbol binario de búsqueda de productos.
    //Los dos últimos atributos son de la misma clase y representan los hijos del nodo.

    //Atributos.
    private Producto dato;
    private NodoArbol izquierdo; //Hijo con nombre menor (alfabéticamente).
    private NodoArbol derecho;   //Hijo con nombre mayor (alfabéticamente).

    //Constructor.

    //Crea un nodo hoja con el producto recibido (sin hijos).
    public NodoArbol(Producto dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    //Getters y setters.
    public Producto getDato() {
        return dato;
    }

    public void setDato(Producto dato) {
        this.dato = dato;
    }

    public NodoArbol getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoArbol getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }

    //Retorna true si el nodo no tiene hijos.
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    //Retorna la información del producto que contiene el nodo.
    @Override
    public String toString() {
        return dato.toString();
    }
}
