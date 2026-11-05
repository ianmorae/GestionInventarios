# GestionInventarios: TechZone CR

Segundo avance de la **Aplicación de gestión de inventarios** del curso **SOFT-10 Estructuras de Datos**, Universidad CENFOTEC.

Sistema de consola en Java para gestionar los productos de **TechZone CR**, una tienda ficticia de tecnología. Todas las estructuras de datos están implementadas desde cero:

- **Inventario de la tienda:** árbol binario de búsqueda (`ArbolProductos`), con el nombre del producto como llave.
- **Carrito de cada cliente:** lista enlazada simple (`ListaProductos`, del primer avance).
- **Clientes en espera:** cola de prioridad (`ColaClientes`).

## Integrantes

| Integrante | Primer avance | Segundo avance |
|---|---|---|
| Ian Aarón Mora Espinoza | Clases `Producto` y `Nodo`, estructura del proyecto, menú de consola (`Main`) | Clases `NodoArbol` y `ArbolProductos` (inventario), README |
| Ian Alberto Gómez Ramírez | Inserción, búsqueda y recorrido de `ListaProductos` | Clase `Cliente` y su carrito, costo total de `ListaProductos` |
| Kendall Adolfo Segura Arias | Modificación, eliminación y reporte de costos de `ListaProductos` | Clases `NodoCliente` y `ColaClientes` (cola de prioridad) |
| Luis Alonso Agüero Suárez | — | Clase `Tienda` (compra y factura) y menú del segundo avance (`Main`) |

La versión del primer avance queda guardada en el tag [`primer-avance`](https://github.com/ianmorae/GestionInventarios/tree/primer-avance).

## Estructura del proyecto

```
GestionInventarios/
├── imagenes/                Imágenes de los productos
└── src/
    ├── Producto.java        Entidad: datos de cada producto
    ├── Nodo.java            Nodo de la lista enlazada (producto + siguiente)
    ├── ListaProductos.java  Lista enlazada simple; se usa como carrito de cada cliente
    ├── NodoArbol.java       Nodo del árbol (producto + hijo izquierdo y derecho)
    ├── ArbolProductos.java  Árbol binario de búsqueda; es el inventario de la tienda
    ├── Cliente.java         Entidad: nombre, prioridad (1 a 3) y carrito
    ├── NodoCliente.java     Nodo de la cola (cliente + siguiente)
    ├── ColaClientes.java    Cola de prioridad de clientes
    ├── Tienda.java          Entidad: inventario + cola de clientes + factura
    └── Main.java            Menú de consola y rutina main()
```

## Funcionalidades

- Insertar productos al inventario (árbol), con imagen opcional desde la carpeta `imagenes/`
- Mostrar el inventario en orden alfabético (recorrido inorden), buscar y eliminar productos
- Insertar un cliente en la cola: se elige su prioridad y se llena su carrito con productos del inventario
- Mostrar la cola de clientes, indicando quién será el siguiente en ser atendido
- Atender al siguiente cliente e imprimir su factura, con el costo total acumulado

## Reglas de negocio

- **Prioridad:** 1 = cliente básico, 2 = afiliado, 3 = premium.
- **Orden de atención:** se atiende al cliente de mayor prioridad que esté en la cola; si hay empate, al que esté más cerca del frente (el que llegó primero).
- **Inventario y carrito:** al agregar un producto al carrito se guarda una copia con la cantidad elegida y esas unidades se descuentan del inventario (quedan reservadas para ese cliente). No se puede pedir más de lo disponible.
- **Carrito vacío:** un cliente que no agregó ningún producto no entra a la cola.

## Cómo ejecutarlo

1. Clonar el repositorio:
   ```
   git clone https://github.com/ianmorae/GestionInventarios.git
   ```
2. Abrir la carpeta `GestionInventarios` en VSCode o IntelliJ.
3. Ejecutar `src/Main.java`.

Requiere Java 11 o superior. El programa debe ejecutarse desde la raíz del proyecto para que encuentre la carpeta `imagenes/`.
