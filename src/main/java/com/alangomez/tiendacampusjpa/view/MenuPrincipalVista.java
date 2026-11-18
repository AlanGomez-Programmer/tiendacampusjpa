package com.alangomez.tiendacampusjpa.view;

import com.alangomez.tiendacampusjpa.util.EntradaConsola;
import org.springframework.stereotype.Component;

@Component
public class MenuPrincipalVista {

    private EntradaConsola entradaConsola;

    public MenuPrincipalVista(EntradaConsola entradaConsola){
        this.entradaConsola = entradaConsola;
    }

    public int menu(){
        System.out.print("""
                =_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_
                                 Menu
                =_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_
                1. Clientes
                2. Productos
                3. Pedidos
                4. Detalles Pedidos
                0. Salir
                """);
        return entradaConsola.ingresarOpcion("Ingrese Opción: ");
    }

}
