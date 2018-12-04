package br.com.softplan.load.price.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.softplan.load.price.domain.model.Veiculo;

@Repository
public interface IVeiculoRepository extends CrudRepository<Veiculo, Long> {

	Veiculo findByNome(String nome);
	
}
