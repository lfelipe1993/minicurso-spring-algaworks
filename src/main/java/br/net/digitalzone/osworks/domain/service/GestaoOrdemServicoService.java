package br.net.digitalzone.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.digitalzone.osworks.api.model.Comentario;
import br.net.digitalzone.osworks.domain.model.Cliente;
import br.net.digitalzone.osworks.domain.model.OrdemServico;
import br.net.digitalzone.osworks.domain.model.StatusOrdemServico;
import br.net.digitalzone.osworks.domain.repository.ClienteRepository;
import br.net.digitalzone.osworks.domain.repository.ComentarioRepository;
import br.net.digitalzone.osworks.domain.repository.OrdemServicoRepository;
import br.net.digitalzone.osworks.exception.EntidadeNaoEncontradaException;
import br.net.digitalzone.osworks.exception.NegocioException;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;

	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente nao encontrado"));

		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());

		return ordemServicoRepository.save(ordemServico);
	}

	@Autowired
	private ComentarioRepository comentarioRepository;

	public Comentario adicionarComentario(Long OrdemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(OrdemServicoId);

		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);

		return comentarioRepository.save(comentario);

	}

	public void finalizar(long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
		
	}

	private OrdemServico buscar(Long OrdemServicoId) {
		return ordemServicoRepository.findById(OrdemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de Serviço não encontrada"));
	}

}
