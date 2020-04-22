package br.net.digitalzone.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.digitalzone.osworks.domain.model.Cliente;
import br.net.digitalzone.osworks.domain.repository.ClienteRepository;
import br.net.digitalzone.osworks.exception.NegocioException;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvar(Cliente cliente) {
		
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
				
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail");
		}
		
		return clienteRepository.save(cliente);
	}
	
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}

}
