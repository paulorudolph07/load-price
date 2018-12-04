package br.com.softplan.load.price.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.softplan.load.price.domain.enums.TipoViaEnum;
import br.com.softplan.load.price.domain.model.Rodovia;

@Repository
public interface IRodoviaRepository extends CrudRepository<Rodovia, Long> {

	@Override
	List<Rodovia> findAll();
	
	Rodovia findByTipoVia(TipoViaEnum tipoVia);
	
}
