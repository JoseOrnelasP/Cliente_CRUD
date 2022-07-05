package test.entityserviceclientesv1.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import test.entityserviceclientesv1.entity.Cliente;
import test.entityserviceclientesv1.service.ClienteService;

@Service
public class ClienteImpl implements ClienteService {

	File file = new File("C:\\jSon\\clientes.json");
	HashMap<Integer, Cliente> clientes = new HashMap<Integer, Cliente>();
	ObjectMapper mapper = new ObjectMapper();

	int id = 0;
	@Override
	public List<Cliente> listar() {
		List<Cliente> list = new ArrayList<Cliente>();
		for (Integer key : this.leerJson().keySet()) {
			list.add(this.leerJson().get(key));
		}
		return list;
	}

	@Override
	public Cliente añadir(Cliente c) {
		id++;
		c.setClienteId(id);
		clientes.put(id, c);
		this.grabar(file, clientes);
		return new Cliente(id, c.getNombre(), c.getCorreo());
	}

	@Override
	public Cliente actualizar(Cliente c) {
		clientes.put(c.getClienteId(), c);
		this.grabar(file, clientes);
		return new Cliente(c.getClienteId(), c.getNombre(), c.getCorreo());
	}

	@Override
	public Cliente buscar(int id) {
		//Recupero el elemento buscado del estado actual del json
		return this.leerJson().get(id);
	}

	@Override
	public String eliminar(int id) {
		HashMap<Integer, Cliente> c = this.leerJson();
		c.remove(id);
		this.grabar(file, c);
		return "Eliminado";
	}

	public HashMap<Integer, Cliente> leerJson() {
		HashMap<Integer, Cliente> clientesExistentes = new HashMap<Integer, Cliente>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream inStr = new FileInputStream(file);
			TypeReference<HashMap<Integer, Cliente>> typeReference = new TypeReference<HashMap<Integer, Cliente>>() {
			};
			clientesExistentes = mapper.readValue(inStr, typeReference);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return clientesExistentes;
	}

	public void grabar(File file, HashMap<Integer, Cliente> hash) {
		try {
			mapper.writeValue(file, hash);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
