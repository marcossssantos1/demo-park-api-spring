package com.marcos.demoparkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.marcos.demoparkapi.entities.Cliente;
import com.marcos.demoparkapi.exceptions.CpfUniqueViolationException;
import com.marcos.demoparkapi.repository.ClienteRepository;

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
}
