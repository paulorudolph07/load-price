package br.com.softplan.load.price.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.softplan.load.price.domain.model.Percurso;

@Repository
public interface IPercursoRepository extends CrudRepository<Percurso, Long> {

}
