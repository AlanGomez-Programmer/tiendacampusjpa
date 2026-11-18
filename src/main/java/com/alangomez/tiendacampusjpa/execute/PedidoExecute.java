package com.alangomez.tiendacampusjpa.execute;

import com.alangomez.tiendacampusjpa.util.EntradaConsola;
import com.alangomez.tiendacampusjpa.view.VistaPedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoExecute {

    private final VistaPedido vistaPedido;

    public PedidoExecute(VistaPedido vistaPedido){
        this.vistaPedido = vistaPedido;
    }

    public void iniciar(){
        while (true){
            int opci = vistaPedido.menu();
            switch (opci){
                case 1 -> vistaPedido.ingresarPedido();
                case 2 -> vistaPedido.listarPedidos();
                case 3 -> vistaPedido.buscarPedido();
                case 4 -> vistaPedido.actualizarEstadoPedido();
                case 5 -> vistaPedido.eliminarPedido();
                case 0 -> {
                    System.out.println("Regresando...");
                    return;
                }
                default -> System.out.println("No existe esa opción");
            }
        }
    }

}
