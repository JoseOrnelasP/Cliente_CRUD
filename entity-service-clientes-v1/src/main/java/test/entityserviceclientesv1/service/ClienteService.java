package test.entityserviceclientesv1.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import test.entityserviceclientesv1.entity.Cliente;

public interface ClienteService {
	
	List<Cliente> listar();
	Cliente añadir(Cliente c);
	Cliente actualizar(Cliente c);
	Cliente buscar(int id);
	String eliminar(int id);
	
}
