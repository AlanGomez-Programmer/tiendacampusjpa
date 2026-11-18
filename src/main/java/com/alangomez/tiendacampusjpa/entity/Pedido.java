package com.alangomez.tiendacampusjpa.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_pedido_id", nullable = false)
    private EstadoPedido estadoPedido;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetallePedido> detalles = new ArrayList<>();

    public Pedido(){

    }

    public Pedido(Cliente cliente, EstadoPedido estadoPedido, LocalDateTime fecha) {
        this.cliente = cliente;
        this.estadoPedido = estadoPedido;
        this.fecha = fecha;
    }

    public void agregarDetallePedido(DetallePedido detalle){
        detalles.add(detalle);
        detalle.setPedido(this);
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
