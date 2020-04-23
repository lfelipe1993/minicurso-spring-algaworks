package br.net.digitalzone.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.osworks.api.model.OrdemServicoInputModel;
import br.net.digitalzone.osworks.api.model.OrdemServicoRepresentationModel;
import br.net.digitalzone.osworks.domain.model.OrdemServico;
import br.net.digitalzone.osworks.domain.repository.OrdemServicoRepository;
import br.net.digitalzone.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoRepresentationModel criar(@Valid @RequestBody OrdemServicoInputModel ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);
		
		return toModel(gestaoOrdemServicoService.criar(ordemServico));
	}

	@GetMapping
	public List<OrdemServicoRepresentationModel> listar() {
		return toCollectionModel(ordemServicoRepository.findAll());
	}

	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoRepresentationModel> buscar(@PathVariable Long ordemServicoId) {
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);

		if (ordemServico.isPresent()) {
			OrdemServicoRepresentationModel ordemServicoModel = toModel(ordemServico.get());
			return ResponseEntity.ok(ordemServicoModel);
		}

		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServicoService.finalizar(ordemServicoId);
	}
	
	private OrdemServicoRepresentationModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoRepresentationModel.class);
	}
	
	private List<OrdemServicoRepresentationModel> toCollectionModel(List<OrdemServico> ordensServicos) {
		//stream -> fluxo sequencia de elementos que suporta operacoes de agregacoes e transformacoes
		// map -> vai aplicar uma funcao aos elementos um a um e retornar um novo stream como resultado.
		// Collect -> faz um reduce ou seja reduzir ao stream anterior para uma colecao
		return ordensServicos.stream()
				.map(o -> toModel(o))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInputModel ordemServicoInput) {
		return modelMapper.map(ordemServicoInput,OrdemServico.class);
	}

}
