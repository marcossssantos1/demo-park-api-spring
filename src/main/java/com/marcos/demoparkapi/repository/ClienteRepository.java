package com.marcos.demoparkapi.repository;

import com.marcos.demoparkapi.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
