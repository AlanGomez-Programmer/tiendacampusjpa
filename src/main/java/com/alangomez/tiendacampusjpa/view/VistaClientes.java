package com.alangomez.tiendacampusjpa.view;

import com.alangomez.tiendacampusjpa.entity.Cliente;
import com.alangomez.tiendacampusjpa.repository.ClienteRepsitory;
import com.alangomez.tiendacampusjpa.util.EntradaConsola;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VistaClientes {

    private final EntradaConsola entradaConsola;
    private final ClienteRepsitory repo;

    private final String linea = "----------------------------------------";
    private final String formato = "%-6s %-18s %-30s%n";

    public VistaClientes(EntradaConsola entradaConsola, ClienteRepsitory repo){
        this.entradaConsola = entradaConsola;
        this.repo = repo;
    }

    public int menu(){
        System.out.print("""
                =_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_
                             Menu Clientes
                =_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_=_
                1. Ingresar Cliente
                2. Listar Clientes
                3. Buscar Cliente
                4. Eliminar Cliente
                0. regresar
                """);
        return entradaConsola.ingresarOpcion("Ingrese opción: ");
    }

    public void ingresarCliente(){
        System.out.println("========== Nuevo Cliente ==========");
        String identificacion = entradaConsola.ingresarTexto("Identificación: ");
        String nombre = entradaConsola.ingresarTexto("Nombre: ");
        Cliente cliente = new Cliente(identificacion, nombre);
        try {
            repo.save(cliente);
            System.out.println("Cliente registrado Exitosamente");
        } catch (Exception e){
            System.out.println("Error: Ya existe cliente con esa identificación");
        }
    }

    public void listarClientes(){
        System.out.println("==========  Clientes ==========");
        List<Cliente> clientes = repo.findAll();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            System.out.println(linea);
            System.out.printf(formato, "ID", "DPI", "NOMBRE");
            System.out.println(linea);
            clientes.forEach(cliente ->  System.out.printf(formato, cliente.getId(), cliente.getIdentificacion(), cliente.getNombre()));
            System.out.println("----------------------------------------");
        }
    }

    public void buscarCliente(){
        System.out.println("========== Buscar Cliente ==========");
        long id = entradaConsola.ingresarEntero("Id Cliente: ");
        Cliente cliente = buscarIdCliente(id);
            System.out.println("¡Cliente Encontrado!");
        if (cliente != null) {
            System.out.println(linea);
            System.out.printf(formato, "ID", "DPI", "nombre");
            System.out.println(linea);
            System.out.printf(formato, cliente.getId(), cliente.getIdentificacion(), cliente.getNombre());
        }
    }

    public Cliente buscarIdCliente(Long id){
        Optional<Cliente> opt = repo.findById(id);
        if (opt.isPresent()){
            return opt.get();
        } else {
            System.out.println("No se encontro el Cliente");
            return null;
        }
    }

    public void eliminarCliente(){
        System.out.println("========== Eliminar Cliente ==========");
        Long id = (long) entradaConsola.ingresarEntero("Id del cliente: ");
        Optional<Cliente> opt = repo.findById(id);
        if (opt.isEmpty()){
            System.out.println("No se encontro el cliente");
        } else {
            Cliente cliente = opt.get();
            System.out.println("¡Cliente encontrado!");
            System.out.println(linea);
            System.out.printf(formato, "Id", "Documento", "Nombre");
            System.out.println(linea);
            System.out.printf(formato, cliente.getId(), cliente.getIdentificacion(), cliente.getNombre());
            System.out.println(linea);
            boolean confirmacion = entradaConsola.confirmar("¿Desea elimiar a " + cliente.getNombre() + " S/N? ");
            if (confirmacion){
                repo.delete(cliente);
                System.out.println("Se elimino correctamente al cliente");
            } else {
                System.out.println("No se elimino al cliente");
            }
        }
    }
}
