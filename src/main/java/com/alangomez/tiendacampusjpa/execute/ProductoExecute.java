package com.alangomez.tiendacampusjpa.execute;

import com.alangomez.tiendacampusjpa.view.VistaProducto;
import org.springframework.stereotype.Component;

@Component
public class ProductoExecute {

    private final VistaProducto vista;

    public ProductoExecute(VistaProducto vista){
        this.vista = vista;
    }

    public void iniciar(){
        while (true){
            int opci = vista.menu();
            switch (opci){
                case 1 -> vista.ingresarPedido();
                case 2 -> vista.listarProductos();
                case 3 -> vista.buscarProducto();
                case 4 -> vista.eliminarProducto();
                case 0 -> {
                    System.out.println("Regresando....");
                    return;
                }
                default -> System.out.println("No existe esa opción");
            }
        }
    }

}
