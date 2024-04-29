package com.marcos.demoparkapi.repository;

import com.marcos.demoparkapi.entities.Cliente;
import com.marcos.demoparkapi.repository.projection.ClienteProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	
    Page<ClienteProjection> findAllPageables(Pageable pageable);

	Cliente findByUsuarioId(Long id);
	
}
