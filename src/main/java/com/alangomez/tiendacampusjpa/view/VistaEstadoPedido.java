package com.alangomez.tiendacampusjpa.view;

import com.alangomez.tiendacampusjpa.entity.EstadoPedido;
import com.alangomez.tiendacampusjpa.repository.EstadoPedidoRepository;
import com.alangomez.tiendacampusjpa.util.EntradaConsola;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VistaEstadoPedido {

    private final EntradaConsola entradaConsola;
    private final EstadoPedidoRepository repo;

    private final String linea = "----------------------------";
    private final String formato = "%-6s %-18s%n";

    public VistaEstadoPedido(EntradaConsola entradaConsola, EstadoPedidoRepository repo){
        this.entradaConsola = entradaConsola;
        this.repo = repo;
    }

    public void listarEstados(){
        System.out.println("=== Estados ===");
        List<EstadoPedido> estados = repo.findAll();
        if(!estados.isEmpty()){
            System.out.println(linea);
            System.out.printf(formato, "Id", "Estado");
            System.out.println(linea);
            estados.forEach(estado -> {
                System.out.printf(formato, estado.getId(), estado.getEstado());
            });
        }else  {
            System.out.println("Error: No hay estados guardados en la Base de datos");
        }
    }

    public EstadoPedido buscarIdEstado(String mensaje){
        long id = entradaConsola.ingresarEntero(mensaje);
        Optional<EstadoPedido> opt = repo.findById(id);
        if (opt.isPresent()){
            return opt.get();
        } else {
            System.out.println("No existe el id del estado que ingreso");
            return null;
        }
    }

    public EstadoPedido buscarId(long id){
        Optional<EstadoPedido> opt = repo.findById(id);
        if (opt.isEmpty()){
            System.out.println("Error: No hay estados almacenados");
            return null;
        }
        return opt.get();
    }

    public EstadoPedido actualizarEstado(long idEstadoAnterior, long idEstadoNuevo){
        long idEntregado = 2;
        long idCancelado = 3;
        if (idEstadoAnterior == idEstadoNuevo){
            System.out.println("Error: El estado ingresado es el mismo al del actual");
            return null;
        } else if(idEstadoAnterior == idEntregado && idEstadoNuevo == 1 || idEstadoAnterior == idEntregado && idEstadoNuevo == idCancelado){
            System.out.println("Error: No se puede modificar un Pedido Entregado");
            return null;
        } else if(idEstadoAnterior == idCancelado && idEstadoNuevo == 1 || idEstadoAnterior == idCancelado && idEstadoNuevo == idEntregado){
            System.out.println("Error: No se puede modificar un Pedido Cancelado");
            return null;
        } else {
            return buscarId(idEstadoNuevo);
        }
    }

}
