package com.alangomez.tiendacampusjpa.view;

import com.alangomez.tiendacampusjpa.entity.*;
import com.alangomez.tiendacampusjpa.repository.DetallePedidoRepository;
import com.alangomez.tiendacampusjpa.repository.PedidoRepository;
import com.alangomez.tiendacampusjpa.util.EntradaConsola;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class VistaPedido {

    private final EntradaConsola entradaConsola;

    private final PedidoRepository repo;
    private final DetallePedidoRepository repoDetalle;

    private VistaClientes vistaClientes;
    private VistaProducto vistaProducto;
    private VistaEstadoPedido vistaEstadoPedido;

    private final String formato = "| %-4s | %-26s | %-16s | %-10s |%n";
    private final String linea = "+" + "-".repeat(6) + "+" + "-".repeat(28)
            + "+" + "-".repeat(18) + "+" + "-".repeat(12) + "+";

    public VistaPedido(EntradaConsola entradaConsola,
                       PedidoRepository repo, DetallePedidoRepository repoDetalle,
                       VistaClientes vistaClientes, VistaProducto vistaProducto, VistaEstadoPedido vistaEstadoPedido ){
        this.entradaConsola = entradaConsola;
        this.repo = repo;
        this.repoDetalle = repoDetalle;
        this.vistaClientes = vistaClientes;
        this.vistaProducto = vistaProducto;
        this.vistaEstadoPedido = vistaEstadoPedido;
    }

    public int menu(){
        System.out.print("""
                =_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_
                              Menu Pedidos
                =_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_
                1. Ingresar Pedido
                2. Listar Pedidos
                3. Buscar Pedido
                4. Actualizar Estado
                5. Eliminar Pedido
                0. regresar
                """);
        return entradaConsola.ingresarOpcion("Ingrese opción: ");
    }

    @Transactional
    public void ingresarPedido() {
        System.out.println("========== Nuevo Pedido ==========");
        Map<Producto, Integer> productos = new HashMap<>();
        vistaClientes.listarClientes();
        Long idCliente = (long) entradaConsola.ingresarEntero("Id del cliente: ");
        Cliente cliente = vistaClientes.buscarIdCliente(idCliente);
        if (cliente == null){
            System.out.println("Error: No se encontró el cliente");
            return;
        }

        boolean agregarProducto = true;
        int cantidad = 0;
        while (agregarProducto){
            vistaProducto.listarProductos();
            Producto producto = vistaProducto.buscarIdProducto("Id Producto: ");
            cantidad = entradaConsola.ingresarEntero("Cantidad: ");
            if (producto != null){
                productos.put(producto, cantidad);
            }
             agregarProducto = entradaConsola.confirmar("¿Desea agregar otro producto? S/N: ");
        }

        try {
            EstadoPedido estadoPedido = vistaEstadoPedido.buscarId(1);
            Pedido pedido = new Pedido(cliente, estadoPedido, LocalDateTime.now());
            repo.save(pedido);

            productos.forEach((producto, cantidadProducto) ->{
                DetallePedido detallePedido = new DetallePedido(pedido, producto, cantidadProducto);
                repoDetalle.save(detallePedido);
            });
        }catch (Exception e){
            System.out.println("Error: No se logro registrar el nuevo pedido" + e);
        }
    }

    public void listarPedidos(){
        System.out.println("==========  Pedidos ==========");
        List<Pedido> pedidos = repo.findAll();
        if (pedidos.isEmpty()) {
            System.out.println("No hay Pedidos registrados");
        } else {
            System.out.println(linea);
            System.out.printf(formato, "ID", "Fecha", "Nombre Cliente", "Estado");
            System.out.println(linea);
            pedidos.forEach(pedido ->  {
                Cliente cliente = vistaClientes.buscarIdCliente(pedido.getCliente().getId());
                EstadoPedido estadoPedido = vistaEstadoPedido.buscarId(pedido.getEstadoPedido().getId());
                System.out.printf(formato, pedido.getId(), pedido.getFecha(), cliente.getNombre(), estadoPedido.getEstado());
            });
            System.out.println("----------------------------------------");
        }
    }

    public void buscarPedido(){
        System.out.println("========== Buscar Pedido ==========");
        long id = entradaConsola.ingresarEntero("Id del Pedido: ");
        Pedido pedido = buscarIdPedido(id);
        if (pedido != null){
            Cliente cliente = vistaClientes.buscarIdCliente(pedido.getCliente().getId());
            EstadoPedido estadoPedido = vistaEstadoPedido.buscarId(pedido.getEstadoPedido().getId());
            System.out.println("¡Encontrado!");
            System.out.println(linea);
            System.out.printf(formato, "ID", "Fecha", "Nombre Cliente", "Estado");
            System.out.println(linea);
            System.out.printf(formato, pedido.getId(), pedido.getFecha(), cliente.getNombre(), estadoPedido.getEstado());

        }else {
            return;
        }
    }

    public Pedido buscarIdPedido(long id){
        Optional<Pedido> opt = repo.findById(id);
        if (opt.isEmpty()){
            System.out.println("Error: No se encontro el Pedido");
            return null;
        }
        return opt.get();
    }

    @Transactional
    public void actualizarEstadoPedido(){
        System.out.println("========== Actualizar Estado ==========");
        long id = entradaConsola.ingresarEntero("ID del Pedido: ");
        Pedido pedido = buscarIdPedido(id);
        if (pedido != null){
            EstadoPedido estadoActual = pedido.getEstadoPedido();
            System.out.println("Estado Actual: " + pedido.getEstadoPedido().getEstado());
            vistaEstadoPedido.listarEstados();
            EstadoPedido estadoPedidoIngresado;
            estadoPedidoIngresado = vistaEstadoPedido.buscarIdEstado("Ingrese ID nuevo Estado: ");
            if (estadoPedidoIngresado != null){
                EstadoPedido estadoPedidoNuevo = vistaEstadoPedido.actualizarEstado(estadoActual.getId(),estadoPedidoIngresado.getId());
                if (estadoPedidoNuevo != null){
                    pedido.setEstadoPedido(estadoPedidoNuevo);
                    repo.save(pedido);
                    System.out.println("Se ha cambiado es estado Exitosamente");
                }
            }
        }
    }

    @Transactional
    public void eliminarPedido(){
        System.out.println("========== Eliminar Estado ==========");
        long id = entradaConsola.ingresarEntero("ID del Pedido: ");
        Pedido pedido = buscarIdPedido(id);
        if (pedido != null){
            repo.delete(pedido);
            System.out.println("El pedido " + pedido.getId() + " de " + pedido.getCliente().getNombre() + " ha sido eliminado exitosamente");
        } else {
            return;
        }
    }
}


