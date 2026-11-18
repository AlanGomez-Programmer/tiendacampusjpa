package com.alangomez.tiendacampusjpa.repository;

import com.alangomez.tiendacampusjpa.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
