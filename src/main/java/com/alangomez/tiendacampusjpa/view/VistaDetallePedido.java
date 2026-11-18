package com.alangomez.tiendacampusjpa.view;

import com.alangomez.tiendacampusjpa.entity.DetallePedido;
import com.alangomez.tiendacampusjpa.entity.Pedido;
import com.alangomez.tiendacampusjpa.repository.DetallePedidoRepository;
import com.alangomez.tiendacampusjpa.util.EntradaConsola;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VistaDetallePedido {

    private EntradaConsola entradaConsola;
    private final DetallePedidoRepository repo;
    private final VistaPedido vistaPedido;

    private final String linea = "--------------------------------------------";
    private final String formato = "%-18s %-6s %-6s%n";

    public VistaDetallePedido(EntradaConsola entradaConsola, DetallePedidoRepository repo, VistaPedido vistaPedido){
        this.entradaConsola = entradaConsola;
        this.repo = repo;
        this.vistaPedido = vistaPedido;
    }

    @Transactional
    public void listarDetallePedido(){
        System.out.println("========== Detalles ==========");
        long id = entradaConsola.ingresarEntero("Id Pedido: ");
        Pedido pedido = vistaPedido.buscarIdPedido(id);
        if (pedido != null){
            List<DetallePedido> detallePedidos = repo.findByPedidoId(pedido.getId());
            System.out.println(linea);
            System.out.printf(formato, "Nombre Producto", "Cantidad", "Precio");
            System.out.println(linea);
            detallePedidos.forEach(detallePedido -> {
                System.out.printf(formato, detallePedido.getProducto().getNombre(),
                        detallePedido.getCantidad(),
                        "Q" + detallePedido.getProducto().getPrecio()
                );
            });
        }

    }

}
