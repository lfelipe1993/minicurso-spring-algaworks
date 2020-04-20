package br.net.digitalzone.osworks.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.osworks.domain.model.Cliente;

@RestController
public class ClienteController {
	
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		
		Cliente cliente1 = new Cliente(1L, "Luiz Felipe Mecina Greijo", "lfelipe1993@live.com", "(18) 99818-0540");
		Cliente cliente2 = new Cliente(2L, "Matheus Tavares", "matheus@live.com", "(18) 3341-3583");
		
		return Arrays.asList(cliente1,cliente2);
	}

}
