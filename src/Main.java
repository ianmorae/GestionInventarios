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
        //Pendiente: lo implementa la Persona 4.
        menu();
    }

    //Muestra las opciones disponibles y ejecuta la que el usuario elija.
    public static void menu() {
        //Pendiente: lo implementa la Persona 4.
    }
}
