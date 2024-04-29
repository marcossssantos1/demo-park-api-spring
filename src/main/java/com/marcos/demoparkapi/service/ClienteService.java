package com.marcos.demoparkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.demoparkapi.entities.Cliente;
import com.marcos.demoparkapi.exceptions.CpfUniqueViolationException;
import com.marcos.demoparkapi.repository.ClienteRepository;
import com.marcos.demoparkapi.repository.projection.ClienteProjection;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteService {

	@Autowired
	 private ClienteRepository customerRepository;
	    @Transactional
	    public Cliente save(Cliente customer){
	        try{
	            return customerRepository.save(customer);
	        }catch (DataIntegrityViolationException ex){
	            throw new CpfUniqueViolationException(
	                    String.format("We cannot register the CPF '%s' because it already exists in the system", customer.getCpf()));
	        }
	    }
	    
	    @Transactional
	    public Cliente buscarPorId(Long id) {
	        return customerRepository.findById(id).orElseThrow(
	                () -> new EntityNotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))
	        );
	    }
	    
	    @Transactional
	    public Page<ClienteProjection> buscarTodos(Pageable pageable) {
	        return customerRepository.findAllPageables(pageable);
	    }

	    @Transactional
	    public Cliente buscarPorUsuarioId(Long id) {
	        return customerRepository.findByUsuarioId(id);
	    }

	
}
