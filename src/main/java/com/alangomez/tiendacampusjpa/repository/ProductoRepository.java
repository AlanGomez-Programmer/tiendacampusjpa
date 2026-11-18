package com.alangomez.tiendacampusjpa.repository;

import com.alangomez.tiendacampusjpa.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
