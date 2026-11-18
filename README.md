# Tienda Campus con JPA

Aplicación de consola hecha con **Java**, **Spring Boot** y **Spring Data JPA** que representa una **relación muchos a muchos** usando una **entidad intermedia** creada a mano, sin usar `@ManyToMany`.

## Temática

**Tienda: productos y pedidos**, con la entidad intermedia **detalles del pedido**.

- Un **pedido** puede tener **muchos productos**.
- Un **producto** puede aparecer en **muchos pedidos**.
- La relación se resuelve con la entidad **`DetallePedido`**, que guarda qué producto va en qué pedido y en qué **cantidad**.

Además, la tienda administra **clientes** (quién hace el pedido) y **estados de pedido** (Pendiente, Entregado, Cancelado).

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java (JDK) | 25 |
| Spring Boot | 4.1.2 |
| Spring Data JPA / Hibernate | incluido en Spring Boot |
| MySQL | 8.x |
| Maven | Maven Wrapper (`mvnw`) incluido |

## Estructura del proyecto

```
tiendacampusjpa
├── db                     # Script para crear la base de datos
    ├──data.sql # Para los estados
    ├──shecma.sql # Para crear la base de datos
└── src/main/java/com/alangomez/tiendacampusjpa
    ├── TiendacampusjpaApplication.java   # Punto de entrada (CommandLineRunner)
    ├── entity/        # Entidades JPA (tablas de la base de datos)
    ├── repository/    # Interfaces JpaRepository para acceder a los datos
    ├── view/          # Vistas de consola: menús, captura y muestra de datos
    ├── execute/       # Controlan el flujo de cada menú
    └── util/          # EntradaConsola: lectura y validación de datos del usuario
```

## Modelo de datos

### Relación muchos a muchos

```
 PRODUCTO  1 ──────< DETALLE_PEDIDO >────── 1  PEDIDO
                     (tabla intermedia)
```

| Rol | Entidad | Tabla | Campos |
|---|---|---|---|
| Entidad principal 1 | `Producto` | `producto` | `id`, `nombre` (único), `precio` |
| Entidad principal 2 | `Pedido` | `pedido` | `id`, `cliente_id`, `estado_pedido_id`, `fecha` |
| **Entidad intermedia** | `DetallePedido` | `detalle_pedido` | `id`, `pedido_id`, `producto_id`, **`cantidad`** |

### Entidades de apoyo

| Entidad | Tabla | Campos | Relación |
|---|---|---|---|
| `Cliente` | `cliente` | `id`, `identificacion` (única), `nombre` | Un cliente tiene muchos pedidos |
| `EstadoPedido` | `estado_pedido` | `id`, `estado` | Un estado puede estar en muchos pedidos |

### Cómo se mapea en JPA

**`DetallePedido` (entidad intermedia):**
- Tiene su **propio identificador** (`id`).
- `@ManyToOne` hacia `Pedido` (columna `pedido_id`).
- `@ManyToOne` hacia `Producto` (columna `producto_id`).
- Atributo adicional: **`cantidad`**, que indica cuántas unidades del producto lleva el pedido.
- Es el **lado propietario** de la relación, porque tiene las llaves foráneas (`@JoinColumn`).

**`Pedido` y `Producto` (entidades principales):**
- Cada una tiene `@OneToMany(mappedBy = "...")` hacia `DetallePedido`.
- `mappedBy` indica que la relación **ya está mapeada en `DetallePedido`**, así que estas entidades no crean columnas extra. Solo permiten navegar a sus detalles.

## Configuración

### 1. Crear la base de datos con `schema.sql`

En la raíz del proyecto está el archivo **`schema.sql`**. Crea la base de datos `tiendacampusjpa`, todas las tablas con sus llaves foráneas y los estados de pedido iniciales.

Ejecútalo con **una** de estas opciones:

**Opción A: MySQL Workbench.**
1. Abre MySQL Workbench y conéctate a tu servidor.
2. Ve a **File → Open SQL Script** y selecciona `schema.sql`.
3. Ejecuta todo el script con el rayo ⚡.

**Opción B: terminal.**

```bash
mysql -u root -p < schema.sql
```

> ⚠️ **Importante:** el script empieza con `DROP DATABASE IF EXISTS`.

Los estados que crea el script son obligatorios para que la aplicación funcione:

| ID | Estado |
|---|---|
| 1 | Pendiente |
| 2 | Entregado |
| 3 | Cancelado |

### 2. Configurar el archivo `application.properties`

> ⚠️ **Importante:** el repositorio **no incluye** el archivo `application.properties` porque tiene credenciales (usuario y contraseña de MySQL) y está en `.gitignore`. Sin ese archivo la aplicación **no arranca**.

En su lugar se incluye un archivo de ejemplo:

```
src/main/resources/application.properties.example
```

Para poder usar la aplicación, elige **una** de estas dos opciones:

**Opción A: crear una copia del ejemplo (recomendada).** Así conservas el `.example` como plantilla.

```bash
# Linux / macOS / Git Bash
cp src/main/resources/application.properties.example src/main/resources/application.properties

# Windows (CMD)
copy src\main\resources\application.properties.example src\main\resources\application.properties
```

**Opción B: renombrar el ejemplo.** Quita la extensión `.example` para que el archivo se llame solo `application.properties`:

```
application.properties.example  →  application.properties
```

Después, abre `application.properties` y cambia el usuario y la contraseña por los de tu MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tiendacampusjpa
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 3. Ejecución

Desde la raíz del proyecto:

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

También puedes ejecutar `TiendacampusjpaApplication` directamente desde el IDE. La clase implementa **`CommandLineRunner`**, así que el menú de consola se abre automáticamente al iniciar.

### 4. Agreagar los estados de Pedidos



## Uso

Menú principal:

```
1. Clientes
2. Productos
3. Pedidos
4. Detalles Pedidos
0. Salir
```

| Módulo | Opciones |
|---|---|
| Clientes | Ingresar, listar, buscar y eliminar |
| Productos | Ingresar, listar, buscar y eliminar |
| Pedidos | Ingresar (con uno o varios productos), listar, buscar, actualizar estado y eliminar |
| Detalles Pedidos | Ver los productos, cantidades y precios de un pedido |

### Ejemplo para probar la relación muchos a muchos

1. Registra **2 clientes** y **3 productos**.
2. Crea un **pedido** con varios productos: un pedido tiene muchos productos.
3. Crea **otro pedido** que repita uno de esos productos: un producto está en muchos pedidos.
4. Entra a **Detalles Pedidos** para ver los productos de cada pedido, con su cantidad y precio. Ahí se consulta la entidad intermedia y se recuperan los datos del pedido y del producto.

### Reglas de negocio

- La identificación del cliente y el nombre del producto no se pueden repetir.
- Todo pedido nuevo inicia en estado **1 (Pendiente)**.
- No se puede cambiar un pedido al mismo estado que ya tiene.
- Un pedido **Entregado** o **Cancelado** ya no puede cambiar de estado.
- Al eliminar un cliente, producto o pedido, también se eliminan sus registros relacionados (`CascadeType.ALL`).

## Manejo de transacciones

- Las operaciones simples (`save`, `delete`) usan la transacción que Spring Data JPA ya trae por defecto.
- `@Transactional` (de `org.springframework.transaction.annotation`) se usa solo en los métodos que:
  - guardan varias cosas que deben ir juntas (un pedido con sus detalles), o
  - leen relaciones `LAZY` (cliente, estado o producto de un pedido).
- No se coloca `@Transactional` en el `run()` de la aplicación. Si se hace, todo el programa queda dentro de una sola transacción y los datos se pierden al cerrar la app sin terminar.

## Video

[Video](https://drive.google.com/file/d/1TIzCEZS6S_-N3tcSAJgFasrJFkcPS2MX/view?usp=sharing)

## Autor

Alan Gómez