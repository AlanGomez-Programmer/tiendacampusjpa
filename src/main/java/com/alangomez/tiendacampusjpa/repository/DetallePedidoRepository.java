package com.alangomez.tiendacampusjpa.repository;

import com.alangomez.tiendacampusjpa.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedidoId(long pedidoId);
}
