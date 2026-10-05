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
    private static final ListaProductos listaProductos = new ListaProductos();

    //Rutina principal: muestra el menú hasta que el usuario elija salir.
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de inventario de " + NOMBRE_TIENDA + ".");

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
            System.out.println("   " + NOMBRE_TIENDA + " - GESTIÓN DE PRODUCTOS");
            System.out.println("==========================================");
            System.out.println("1. Insertar producto al inicio");
            System.out.println("2. Insertar producto al final");
            System.out.println("3. Insertar producto en una posición");
            System.out.println("4. Buscar producto");
            System.out.println("5. Modificar producto");
            System.out.println("6. Agregar imagen a un producto");
            System.out.println("7. Eliminar producto");
            System.out.println("8. Mostrar productos");
            System.out.println("9. Reporte de costos totales");
            System.out.println("0. Salir");
            System.out.println("------------------------------------------");
            opcion = leerEntero("Seleccione una opción: ", 0, 9);

            switch (opcion) {
                case 1:
                    insertarProducto(1);
                    break;
                case 2:
                    insertarProducto(2);
                    break;
                case 3:
                    insertarProducto(3);
                    break;
                case 4:
                    buscarProducto();
                    break;
                case 5:
                    modificarProducto();
                    break;
                case 6:
                    agregarImagen();
                    break;
                case 7:
                    eliminarProducto();
                    break;
                case 8:
                    System.out.println("\nProductos registrados: " + listaProductos.getTamano());
                    listaProductos.mostrarLista();
                    break;
                case 9:
                    System.out.println("\nReporte de costos totales");
                    listaProductos.imprimirReporteCostos();
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

    //Pide los datos de un producto nuevo y lo inserta en la lista.
    //modo: 1 = al inicio, 2 = al final, 3 = en una posición elegida por el usuario.
    private static void insertarProducto(int modo) {
        System.out.println("\nNuevo producto");
        String nombre = leerTexto("Nombre: ");
        if (listaProductos.buscarNodo(nombre) != null) {
            System.out.println("Ya existe un producto con ese nombre.");
            return;
        }
        double precio = leerDouble("Precio (CRC): ");
        String categoria = leerTexto("Categoría (ej. Laptops, Periféricos, Audio): ");
        LocalDate fecha = leerFechaOpcional("Fecha de vencimiento (dd/MM/yyyy) o Enter si no aplica: ");
        int cantidad = leerEntero("Cantidad: ", 0, Integer.MAX_VALUE);

        Producto producto = new Producto(nombre, precio, categoria, fecha, cantidad);

        //Se usa el método de inserción que corresponde al modo elegido.
        boolean insertado;
        if (modo == 1) {
            insertado = listaProductos.insertarNodoInicio(producto);
        } else if (modo == 2) {
            insertado = listaProductos.insertarNodoFinal(producto);
        } else {
            int maximo = listaProductos.getTamano() + 1;
            int posicion = leerEntero("Posición donde insertarlo (1 a " + maximo + "): ", 1, maximo);
            insertado = listaProductos.insertarNodoEnPosicion(posicion, producto);
        }

        if (!insertado) {
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

    //Busca un producto por nombre y muestra su información completa.
    private static void buscarProducto() {
        if (listaProductos.estaVacia()) {
            System.out.println("La lista se encuentra vacía.");
            return;
        }
        String nombre = leerTexto("Nombre del producto a buscar: ");
        Nodo nodo = listaProductos.buscarNodo(nombre);
        if (nodo == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        System.out.println("Producto encontrado: " + nodo);
        for (String ruta : nodo.getDato().getListaImagenes()) {
            System.out.println("      Imagen: " + ruta);
        }
    }

    //Permite cambiar los datos de un producto. Con Enter se conserva el valor actual.
    private static void modificarProducto() {
        if (listaProductos.estaVacia()) {
            System.out.println("La lista se encuentra vacía.");
            return;
        }
        String nombre = leerTexto("Nombre del producto a modificar: ");
        Nodo nodo = listaProductos.buscarNodo(nombre);
        if (nodo == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        Producto producto = nodo.getDato();

        System.out.println("Actual: " + producto);
        System.out.println("(Presione Enter para conservar el valor actual)");

        String nuevoNombre = leerOpcional("Nuevo nombre: ");

        //Si se escribe un precio, se valida que sea un número no negativo.
        Double nuevoPrecio = null;
        String textoPrecio = leerOpcional("Nuevo precio: ");
        if (!textoPrecio.isEmpty()) {
            try {
                nuevoPrecio = Double.parseDouble(textoPrecio);
                if (nuevoPrecio < 0) {
                    System.out.println("Precio inválido.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Precio inválido.");
                return;
            }
        }

        String nuevaCategoria = leerOpcional("Nueva categoría: ");

        //Si se escribe una cantidad, se valida que sea un entero no negativo.
        Integer nuevaCantidad = null;
        String textoCantidad = leerOpcional("Nueva cantidad: ");
        if (!textoCantidad.isEmpty()) {
            try {
                nuevaCantidad = Integer.parseInt(textoCantidad);
                if (nuevaCantidad < 0) {
                    System.out.println("Cantidad inválida.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida.");
                return;
            }
        }

        //Los campos que quedaron vacíos se envían como null para que no se modifiquen.
        if (nuevoNombre.isEmpty()) {
            nuevoNombre = null;
        }
        if (nuevaCategoria.isEmpty()) {
            nuevaCategoria = null;
        }
        boolean modificado = listaProductos.modificarProducto(nombre, nuevoNombre, nuevoPrecio,
                nuevaCategoria, nuevaCantidad);
        if (!modificado) {
            System.out.println("No se pudo modificar el producto (el nuevo nombre ya existe).");
            return;
        }

        //La fecha se maneja aparte porque también se puede quitar con '-'.
        String textoFecha = leerOpcional("Nueva fecha de vencimiento (dd/MM/yyyy), o '-' para quitarla: ");
        if (textoFecha.equals("-")) {
            listaProductos.modificarFechaVencimiento(producto.getNombre(), null);
        } else if (!textoFecha.isEmpty()) {
            try {
                LocalDate fecha = LocalDate.parse(textoFecha, Producto.FORMATO_FECHA);
                listaProductos.modificarFechaVencimiento(producto.getNombre(), fecha);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida; se conservó la anterior.");
            }
        }
        System.out.println("Producto modificado: " + producto);
    }

    //Busca un producto por nombre y le agrega una imagen.
    private static void agregarImagen() {
        if (listaProductos.estaVacia()) {
            System.out.println("La lista se encuentra vacía.");
            return;
        }
        String nombre = leerTexto("Nombre del producto: ");
        Nodo nodo = listaProductos.buscarNodo(nombre);
        if (nodo == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        pedirYAgregarImagen(nodo.getDato());
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

    //Muestra un submenú con las formas de eliminar productos y ejecuta la elegida.
    private static void eliminarProducto() {
        if (listaProductos.estaVacia()) {
            System.out.println("La lista se encuentra vacía.");
            return;
        }
        System.out.println("1. Eliminar el primero");
        System.out.println("2. Eliminar el último");
        System.out.println("3. Eliminar por nombre");
        System.out.println("4. Vaciar la lista completa");
        int opcion = leerEntero("Opción: ", 1, 4);

        //Vaciar la lista se confirma primero, porque elimina todos los productos.
        if (opcion == 4) {
            String confirmacion = leerTexto("¿Seguro que desea eliminar todos los productos? (s/n): ");
            if (confirmacion.equalsIgnoreCase("s")) {
                listaProductos.vaciarLista();
                System.out.println("Se eliminaron todos los productos.");
            }
            return;
        }

        Nodo nodoEliminado;
        if (opcion == 1) {
            nodoEliminado = listaProductos.eliminarNodoInicio();
        } else if (opcion == 2) {
            nodoEliminado = listaProductos.eliminarNodoFinal();
        } else {
            nodoEliminado = listaProductos.eliminarNodo(leerTexto("Nombre del producto: "));
        }

        if (nodoEliminado == null) {
            System.out.println("No se encontró el producto.");
        } else {
            System.out.println("Producto eliminado: " + nodoEliminado.getDato().getNombre());
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

    //Lee un texto que puede venir vacío (se usa al modificar para conservar valores).
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

    //Lee un monto decimal mayor o igual a 0; lo vuelve a pedir si no es válido.
    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double numero = Double.parseDouble(scanner.nextLine().trim());
                if (numero >= 0) {
                    return numero;
                }
            } catch (NumberFormatException e) {
                //Si no se escribió un número, se muestra el mensaje de error de abajo.
            }
            System.out.println("Ingrese un monto válido (mayor o igual a 0).");
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
