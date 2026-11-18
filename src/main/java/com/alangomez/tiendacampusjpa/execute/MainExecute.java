package com.alangomez.tiendacampusjpa.execute;

import com.alangomez.tiendacampusjpa.view.MenuPrincipalVista;
import com.alangomez.tiendacampusjpa.view.VistaDetallePedido;
import org.springframework.stereotype.Component;

@Component
public class MainExecute {

    private final MenuPrincipalVista vistaPrincipal;

    private final ClienteExecute executeCliente;
    private final ProductoExecute executeProducto;
    private final PedidoExecute executePedido;

    private final VistaDetallePedido vistaDetallePedido;

    public MainExecute(MenuPrincipalVista vistaPrincipal, ClienteExecute executeCliente, ProductoExecute executeProducto, PedidoExecute executePedido, VistaDetallePedido vistaDetallePedido){
        this.vistaPrincipal = vistaPrincipal;
        this.executeCliente = executeCliente;
        this.executeProducto = executeProducto;
        this.executePedido = executePedido;
        this.vistaDetallePedido = vistaDetallePedido;
    }

    public void iniciar(){
        while (true){
            int opci = vistaPrincipal.menu();
            switch (opci){
                case 1 -> executeCliente.iniciar();
                case 2 -> executeProducto.iniciar();
                case 3 -> executePedido.iniciar();
                case 4 -> vistaDetallePedido.listarDetallePedido();
                case 0 -> {
                    System.out.println("byee");
                    return;
                }
                default -> System.out.println("No existe esa opción");
            }
        }

    }

}
