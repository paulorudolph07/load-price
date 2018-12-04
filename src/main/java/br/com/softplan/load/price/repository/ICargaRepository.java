package br.com.softplan.load.price.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.softplan.load.price.domain.model.Carga;

@Repository
public interface ICargaRepository extends CrudRepository<Carga, Long> {

}
