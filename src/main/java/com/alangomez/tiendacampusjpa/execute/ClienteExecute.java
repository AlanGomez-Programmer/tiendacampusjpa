package com.alangomez.tiendacampusjpa.execute;

import com.alangomez.tiendacampusjpa.view.VistaClientes;
import org.springframework.stereotype.Component;

@Component
public class ClienteExecute {

    private final VistaClientes vista;

    public ClienteExecute(VistaClientes vista){
        this.vista = vista;
    }

    public void iniciar(){
        while (true){
            int opci = vista.menu();
            switch (opci){
                case 1 -> vista.ingresarCliente();
                case 2 -> vista.listarClientes();
                case 3 -> vista.buscarCliente();
                case 4 -> vista.eliminarCliente();
                case 0 -> {
                    System.out.println("Regresando....");
                    return;
                }
                default -> System.out.println("No existe esa opción");
            }
        }
    }
}
