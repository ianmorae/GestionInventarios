public class ColaClientes {

    //Clase estructura: cola de prioridad de clientes, implementada con nodos enlazados.
    //Los clientes se encolan siempre al final (en orden de llegada).
    //Al desencolar se atiende al cliente con la prioridad más alta; si hay empate,
    //gana el que esté más cerca del frente de la cola.

    //Atributos.
    private NodoCliente frente;
    private NodoCliente ultimo;
    private int tamano;

    //Constructor.

    //Crea una cola vacía.
    public ColaClientes() {
        this.frente = null;
        this.ultimo = null;
        this.tamano = 0;
    }

    //Getters.
    public NodoCliente getFrente() {
        return frente;
    }

    public int getTamano() {
        return tamano;
    }

    //Retorna true si no hay clientes en la cola.
    public boolean estaVacia() {
        return frente == null;
    }

    //Agrega un cliente al final de la cola.
    public void encolar(Cliente cliente) {
        NodoCliente nuevoNodo = new NodoCliente(cliente);
        if (frente == null) {
            frente = nuevoNodo;
        } else {
            ultimo.setSiguiente(nuevoNodo);
        }
        ultimo = nuevoNodo;
        tamano++;
    }

    //Retorna (sin sacarlo) el cliente que sería atendido a continuación, o null si la cola está vacía.
    public Cliente verSiguiente() {
        NodoCliente nodo = buscarSiguienteNodo()[0];
        if (nodo == null) {
            return null;
        }
        return nodo.getDato();
    }

    //Saca de la cola y retorna al cliente de mayor prioridad (el más cercano al frente si hay empate).
    //Retorna null si la cola está vacía.
    public Cliente desencolar() {
        if (frente == null) {
            return null;
        }
        NodoCliente[] resultado = buscarSiguienteNodo();
        NodoCliente elegido = resultado[0];
        NodoCliente anterior = resultado[1];

        //Se desconecta el nodo elegido, reconectando su anterior con su siguiente.
        if (anterior == null) {
            frente = elegido.getSiguiente(); //El elegido era el frente.
        } else {
            anterior.setSiguiente(elegido.getSiguiente());
        }
        //Si el elegido era el último nodo, el último pasa a ser su anterior.
        if (elegido == ultimo) {
            ultimo = anterior;
        }
        elegido.setSiguiente(null);
        tamano--;
        return elegido.getDato();
    }

    //Recorre la cola y retorna un arreglo con dos elementos: [nodo elegido, nodo anterior al elegido].
    //Solo se reemplaza al elegido cuando aparece una prioridad estrictamente mayor,
    //por eso en un empate se conserva el que está más cerca del frente.
    private NodoCliente[] buscarSiguienteNodo() {
        NodoCliente elegido = frente;
        NodoCliente anteriorElegido = null;
        NodoCliente anterior = frente;
        NodoCliente actual = (frente == null) ? null : frente.getSiguiente();
        while (actual != null) {
            if (actual.getDato().getPrioridad() > elegido.getDato().getPrioridad()) {
                elegido = actual;
                anteriorElegido = anterior;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return new NodoCliente[]{elegido, anteriorElegido};
    }

    //Imprime los clientes en el orden en que llegaron, indicando cuál será atendido de siguiente.
    public void mostrarCola() {
        if (frente == null) {
            System.out.println("No hay clientes en la cola.");
            return;
        }
        Cliente siguiente = verSiguiente();
        NodoCliente nodoTemp = frente;
        int posicion = 1;
        while (nodoTemp != null) {
            String marca = (nodoTemp.getDato() == siguiente) ? "  <-- SIGUIENTE EN SER ATENDIDO" : "";
            System.out.println(posicion + ". " + nodoTemp + marca);
            nodoTemp = nodoTemp.getSiguiente();
            posicion++;
        }
    }
}
