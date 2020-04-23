package br.net.digitalzone.osworks.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	@Bean//indica que esse metodo instancia inicializa (configura) um bean
	//vai ser gerenciado pelo spring e ser disponibilizado para injecao
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
