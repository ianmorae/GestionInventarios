# GestionInventarios: TechZone CR

Primer avance de la **Aplicación de gestión de inventarios** del curso **SOFT-10 Estructuras de Datos**, Universidad CENFOTEC.

Sistema de consola en Java para gestionar los productos de **TechZone CR**, una tienda ficticia de tecnología. Los productos se almacenan en una **lista enlazada simple** implementada desde cero.

## Integrantes

| Integrante | Aporte |
|---|---|
| Ian Aarón Mora Espinoza | Clases `Producto` y `Nodo`, estructura del proyecto, menú de consola (`Main`) |
| Ian Alberto Gómez Ramírez | Inserción, búsqueda y recorrido de `ListaProductos` |
| Kendall Adolfo Segura Arias | Modificación, eliminación y reporte de costos de `ListaProductos` |

## Estructura del proyecto

```
GestionInventarios/
├── imagenes/              Imágenes de los productos
└── src/
    ├── Producto.java      Clase entidad: datos de cada producto
    ├── Nodo.java          Nodo de la lista enlazada (producto + siguiente)
    ├── ListaProductos.java  Lista enlazada simple y sus operaciones
    └── Main.java          Menú de consola y rutina main()
```

## Funcionalidades

- Insertar productos al inicio, al final o en una posición específica
- Buscar un producto por nombre
- Modificar los datos de un producto
- Agregar imágenes a un producto (desde la carpeta `imagenes/`)
- Eliminar el primer producto, el último, uno por nombre o vaciar la lista
- Mostrar todos los productos
- Reporte de costos: precio × cantidad de cada producto y el total acumulado

## Cómo ejecutarlo

1. Clonar el repositorio:
   ```
   git clone https://github.com/ianmorae/GestionInventarios.git
   ```
2. Abrir la carpeta `GestionInventarios` en VSCode o IntelliJ.
3. Ejecutar `src/Main.java`.

Requiere Java 11 o superior. El programa debe ejecutarse desde la raíz del proyecto para que encuentre la carpeta `imagenes/`.
