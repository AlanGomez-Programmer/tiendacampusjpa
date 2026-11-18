package com.alangomez.tiendacampusjpa.view;

import com.alangomez.tiendacampusjpa.entity.Producto;
import com.alangomez.tiendacampusjpa.repository.ProductoRepository;
import com.alangomez.tiendacampusjpa.util.EntradaConsola;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class VistaProducto {

    private final EntradaConsola entradaConsola;
    private final ProductoRepository repo;

    private final String linea = "----------------------------------------";
    private final String formato = "%-6s %-20s %-18s%n";

    public VistaProducto(EntradaConsola entradaConsola, ProductoRepository repo){
        this.entradaConsola = entradaConsola;
        this.repo = repo;
    }

    public int menu(){
        System.out.print("""
                =_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_
                           Menu Producto
                =_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_
                1. Ingresar Producto
                2. Listar Productos
                3. Buscar Producto
                4. Eliminar Producto
                0. regresar
                """);
        return entradaConsola.ingresarOpcion("Ingrese opción: ");
    }

    public void ingresarPedido(){
        System.out.println("========== Nuevo Producto ==========");
        String nombre = entradaConsola.ingresarTexto("Nombre: ");
        BigDecimal precio = entradaConsola.ingresarBigDecimal("Precio: ");
        Producto producto = new Producto(nombre, precio);
        try {
            repo.save(producto);
            System.out.println("Producto registrado Exitosamente");
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void listarProductos(){
        System.out.println("==========  Productos ==========");
        List<Producto> productos = repo.findAll();
        if (productos.isEmpty()) {
            System.out.println("No hay Productos registrados");
        } else {
            System.out.println(linea);
            System.out.printf(formato, "ID", "Nombre", "Precio");
            System.out.println(linea);
            productos.forEach(producto ->  System.out.printf(formato, producto.getId(), producto.getNombre(), "Q" + producto.getPrecio()));
            System.out.println("----------------------------------------");
        }
    }

    public void buscarProducto(){
        System.out.println("========== Buscar producto ==========");
        Producto producto = buscarIdProducto("Id del Producto: ");
        if(producto != null){
            System.out.println("¡Producto encontrado!");
            System.out.println(linea);
            System.out.printf(formato, "Id", "Nombre", "Precio");
            System.out.println(linea);
            System.out.printf(formato, producto.getId(), producto.getNombre(), "Q" + producto.getPrecio());
            System.out.println(linea);
        }
    }

    public Producto buscarIdProducto(String mensaje){
        Long id = (long) entradaConsola.ingresarEntero(mensaje);
        Optional<Producto> opt = repo.findById(id);
        if (opt.isPresent()){
            return opt.get();
        } else {
            System.out.println("No se encontro el Producto");
            return null;
        }
    }

    public void eliminarProducto(){
        System.out.println("========== Eliminar Producto ==========");
        Long id = (long) entradaConsola.ingresarEntero("Id del Producto: ");
        Optional<Producto> opt = repo.findById(id);
        if (opt.isEmpty()){
            System.out.println("No se encontro el Producto");
        } else {
            Producto producto = opt.get();
            System.out.println("¡Producto encontrado!");
            System.out.println(linea);
            System.out.printf(formato, "Id", "Nombre", "Precio");
            System.out.println(linea);
            System.out.printf(formato, producto.getId(), producto.getNombre(), "Q" + producto.getPrecio());
            System.out.println(linea);
            boolean confirmacion = entradaConsola.confirmar("¿Desea elimiar a " + producto.getNombre() + " S/N? ");
            if (confirmacion){
                repo.delete(producto);
                System.out.println("Se elimino correctamente al cliente");
            } else {
                System.out.println("No se elimino al cliente");
            }
        }
    }

}
