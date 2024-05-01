package com.marcos.demoparkapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.demoparkapi.entities.Vaga;

public interface VagasRepository extends JpaRepository<Vaga, Long>{

	Optional<Vaga> findById(String code);

}
