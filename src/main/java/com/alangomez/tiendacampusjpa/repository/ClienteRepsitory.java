package com.alangomez.tiendacampusjpa.repository;

import com.alangomez.tiendacampusjpa.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepsitory extends JpaRepository<Cliente, Long> {
}
