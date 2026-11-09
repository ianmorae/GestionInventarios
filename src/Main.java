import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    //Clase funcional del programa: contiene el menú de consola de la tienda de tecnología,
    //los métodos auxiliares que leen y validan los datos del usuario, y la rutina main().

    //Atributos.
    private static final String NOMBRE_TIENDA = "TechZone CR"; //Nombre de la empresa ficticia.
    private static final String DIRECTORIO_IMAGENES = "imagenes"; //Carpeta del proyecto con las imágenes.
    private static final Scanner scanner = new Scanner(System.in);
    private static final Tienda tienda = new Tienda(NOMBRE_TIENDA);

    //Rutina principal: muestra el menú hasta que el usuario elija salir.
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de ventas de " + NOMBRE_TIENDA + ".");

        //Se invoca el menú; el programa permanece en él hasta que se elija la opción 0.
        menu();

        //Al salir del menú se libera el Scanner y termina el programa.
        scanner.close();
        System.out.println("\nGracias por utilizar el sistema. Hasta pronto.");
    }

    // ==================================================================
    // MENÚ PRINCIPAL
    // ==================================================================

    //Muestra las opciones disponibles y ejecuta la que el usuario elija.
    public static void menu() {
        int opcion;
        do {
            System.out.println("\n==========================================");
            System.out.println("   " + NOMBRE_TIENDA + " - SISTEMA DE VENTAS");
            System.out.println("==========================================");
            System.out.println("  INVENTARIO");
            System.out.println("1. Insertar producto al inventario");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Buscar producto");
            System.out.println("4. Eliminar producto del inventario");
            System.out.println("  CLIENTES");
            System.out.println("5. Insertar cliente en la cola (llenar su carrito)");
            System.out.println("6. Mostrar cola de clientes");
            System.out.println("7. Atender al siguiente cliente (factura)");
            System.out.println("------------------------------------------");
            System.out.println("0. Salir");
            System.out.println("------------------------------------------");
            opcion = leerEntero("Seleccione una opción: ", 0, 7);

            switch (opcion) {
                case 1:
                    insertarProducto();
                    break;
                case 2:
                    System.out.println("\nProductos en el inventario: " + tienda.getInventario().getTamano());
                    tienda.getInventario().mostrarInventario();
                    break;
                case 3:
                    buscarProducto();
                    break;
                case 4:
                    eliminarProducto();
                    break;
                case 5:
                    insertarCliente();
                    break;
                case 6:
                    System.out.println("\nClientes en cola: " + tienda.getColaClientes().getTamano());
                    tienda.getColaClientes().mostrarCola();
                    break;
                case 7:
                    atenderSiguienteCliente();
                    break;
                default:
                    //Opción 0: el ciclo termina y se regresa a main().
                    break;
            }
        } while (opcion != 0);
    }

    // ==================================================================
    // OPCIONES DEL MENÚ
    // ==================================================================

    //Pide los datos de un producto nuevo y lo inserta en el inventario de la tienda.
    private static void insertarProducto() {
        System.out.println("\nNuevo producto");
        String nombre = leerTexto("Nombre: ");
        if (tienda.buscarProducto(nombre) != null) {
            System.out.println("Ya existe un producto con ese nombre en el inventario.");
            return;
        }
        double precio = leerDouble("Precio (CRC): ");
        String categoria = leerTexto("Categoría (ej. Laptops, Periféricos, Audio): ");
        LocalDate fecha = leerFechaOpcional("Fecha de vencimiento (dd/MM/yyyy) o Enter si no aplica: ");
        int cantidad = leerEntero("Cantidad en inventario: ", 0, Integer.MAX_VALUE);

        Producto producto = new Producto(nombre, precio, categoria, fecha, cantidad);
        if (!tienda.agregarProducto(producto)) {
            System.out.println("No se pudo insertar el producto.");
            return;
        }
        System.out.println("Producto insertado correctamente.");

        //Se ofrece agregar una imagen de una vez, para no tener que buscar el producto después.
        String respuesta = leerTexto("¿Desea agregarle una imagen ahora? (s/n): ");
        if (respuesta.equalsIgnoreCase("s")) {
            pedirYAgregarImagen(producto);
        }
    }

    //Busca un producto del inventario por nombre y muestra su información completa.
    private static void buscarProducto() {
        if (tienda.getInventario().estaVacio()) {
            System.out.println("El inventario se encuentra vacío.");
            return;
        }
        String nombre = leerTexto("Nombre del producto a buscar: ");
        NodoArbol nodo = tienda.buscarProducto(nombre);
        if (nodo == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        System.out.println("Producto encontrado: " + nodo);
        for (String ruta : nodo.getDato().getListaImagenes()) {
            System.out.println("      Imagen: " + ruta);
        }
    }

    //Elimina un producto del inventario por nombre.
    private static void eliminarProducto() {
        if (tienda.getInventario().estaVacio()) {
            System.out.println("El inventario se encuentra vacío.");
            return;
        }
        String nombre = leerTexto("Nombre del producto a eliminar: ");
        Producto eliminado = tienda.eliminarProducto(nombre);
        if (eliminado == null) {
            System.out.println("No se encontró el producto.");
        } else {
            System.out.println("Producto eliminado: " + eliminado.getNombre());
        }
    }

    //Crea un cliente, le llena el carrito con productos del inventario y lo agrega a la cola.
    private static void insertarCliente() {
        if (tienda.getInventario().estaVacio()) {
            System.out.println("El inventario está vacío. Inserte productos antes de registrar clientes.");
            return;
        }
        System.out.println("\nNuevo cliente");
        String nombre = leerTexto("Nombre del cliente: ");
        System.out.println("Tipo de cliente:");
        System.out.println("   1. Básico");
        System.out.println("   2. Afiliado a la tienda");
        System.out.println("   3. Premium");
        int prioridad = leerEntero("Prioridad (1 a 3): ", 1, 3);
        Cliente cliente = new Cliente(nombre, prioridad);

        llenarCarrito(cliente);

        //Un cliente sin productos no tiene nada que facturar, así que no entra a la cola.
        if (cliente.getCarrito().estaVacia()) {
            System.out.println("El carrito quedó vacío, el cliente no se agregó a la cola.");
            return;
        }
        tienda.encolarCliente(cliente);
        System.out.println("\nCliente agregado a la cola (posición " + tienda.getColaClientes().getTamano() + ").");
        System.out.println("Carrito:");
        cliente.getCarrito().imprimirReporteCostos();
    }

    //Permite al cliente elegir productos del inventario hasta que el usuario presione Enter.
    private static void llenarCarrito(Cliente cliente) {
        System.out.println("\nLlenado del carrito de " + cliente.getNombre());
        while (true) {
            System.out.println("\nInventario disponible:");
            tienda.getInventario().mostrarInventario();
            String nombreProducto = leerOpcional("\nProducto a agregar al carrito (Enter para terminar): ");
            if (nombreProducto.isEmpty()) {
                return;
            }
            NodoArbol nodo = tienda.buscarProducto(nombreProducto);
            if (nodo == null) {
                System.out.println("Ese producto no existe en el inventario.");
                continue;
            }
            int disponibles = nodo.getDato().getCantidad();
            if (disponibles == 0) {
                System.out.println("'" + nodo.getDato().getNombre() + "' está agotado.");
                continue;
            }
            int cantidad = leerEntero("Cantidad (1 a " + disponibles + "): ", 1, disponibles);
            tienda.agregarAlCarrito(cliente, nodo.getDato().getNombre(), cantidad);
            System.out.println("Agregado. Total del carrito: CRC " + String.format("%,.2f", cliente.calcularTotal()));
        }
    }

    //Atiende al cliente que le toca según su prioridad e imprime su factura.
    private static void atenderSiguienteCliente() {
        if (!tienda.hayClientes()) {
            System.out.println("No hay clientes en la cola.");
            return;
        }
        Cliente cliente = tienda.atenderSiguienteCliente();
        System.out.println("\nAtendiendo a: " + cliente.getNombre() + " (" + cliente.getTipoCliente() + ")");
        tienda.imprimirFactura(cliente);
        System.out.println("\nClientes que quedan en la cola: " + tienda.getColaClientes().getTamano());
    }

    //Muestra las imágenes disponibles en la carpeta del proyecto y agrega la elegida al producto.
    private static void pedirYAgregarImagen(Producto producto) {
        mostrarImagenesDisponibles();
        String archivo = leerTexto("Nombre del archivo (ej. laptop.png): ");
        String ruta = DIRECTORIO_IMAGENES + "/" + archivo;

        //Si el archivo no está en la carpeta, se avisa y se pide confirmación.
        if (!new File(ruta).exists()) {
            System.out.println("Aviso: el archivo '" + ruta + "' no existe en el proyecto.");
            String confirmacion = leerTexto("¿Registrar la ruta de todos modos? (s/n): ");
            if (!confirmacion.equalsIgnoreCase("s")) {
                return;
            }
        }
        producto.agregarImagen(ruta);
        System.out.println("Imagen agregada a '" + producto.getNombre() + "'.");
    }

    //Imprime los nombres de los archivos que hay en la carpeta de imágenes.
    private static void mostrarImagenesDisponibles() {
        File carpeta = new File(DIRECTORIO_IMAGENES);
        String[] archivos = carpeta.list();
        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay imágenes en la carpeta '" + DIRECTORIO_IMAGENES + "'.");
            return;
        }
        Arrays.sort(archivos); //Se ordenan alfabéticamente para que sea más fácil encontrarlas.
        System.out.println("Imágenes disponibles en '" + DIRECTORIO_IMAGENES + "':");
        for (String archivo : archivos) {
            System.out.println("   - " + archivo);
        }
    }

    // ==================================================================
    // MÉTODOS AUXILIARES PARA LEER Y VALIDAR DATOS
    // ==================================================================

    //Lee un texto obligatorio: lo vuelve a pedir mientras venga vacío.
    private static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El valor no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    //Lee un texto que puede venir vacío (se usa en el carrito: Enter significa terminar).
    private static String leerOpcional(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    //Lee un número entero dentro del rango indicado; lo vuelve a pedir si no es válido.
    private static int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            try {
                int numero = Integer.parseInt(scanner.nextLine().trim());
                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }
            } catch (NumberFormatException e) {
                //Si no se escribió un número, se muestra el mensaje de error de abajo.
            }
            if (maximo == Integer.MAX_VALUE) {
                System.out.println("Ingrese un número entero mayor o igual a " + minimo + ".");
            } else {
                System.out.println("Ingrese un número entero entre " + minimo + " y " + maximo + ".");
            }
        }
    }

    //Lee un monto decimal mayor a 0; lo vuelve a pedir si no es válido.
    //Se rechazan valores como "Infinity" o "1e999", que Java convierte en un número infinito.
    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double numero = Double.parseDouble(scanner.nextLine().trim());
                if (numero > 0 && !Double.isInfinite(numero)) {
                    return numero;
                }
            } catch (NumberFormatException e) {
                //Si no se escribió un número, se muestra el mensaje de error de abajo.
            }
            System.out.println("Ingrese un monto válido (mayor a 0).");
        }
    }

    //Lee una fecha en formato dd/MM/yyyy. Si se presiona Enter, retorna null (no aplica).
    private static LocalDate leerFechaOpcional(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(texto, Producto.FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use dd/MM/yyyy (ej. 31/12/2026).");
            }
        }
    }
}
