package com.marcos.demoparkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.marcos.demoparkapi.entities.Vaga;
import com.marcos.demoparkapi.exceptions.CodeUniqueViolationException;
import com.marcos.demoparkapi.repository.VagasRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public class VagasService {
	
	@Autowired 
	private VagasRepository vagasRepository;

	    @Transactional
	    public Vaga save(Vaga vacancy){
	        try{
	            return vagasRepository.save(vacancy);
	        }catch (DataIntegrityViolationException ex){
	            throw new CodeUniqueViolationException(String.format("This vacancy code '%s' exists in database", vacancy.getCode()));
	        }
	    }
	    @Transactional
	    public Vaga searchByCode(String code){
	        return vagasRepository.findById(code).orElseThrow(
	                () -> new EntityNotFoundException(String.format("The vacancy with the code '%s' was not found", code))
	        );
	    }

}
