package fcweb.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import fcweb.backend.data.entity.FcSquadra;

public interface SquadraRepository extends CrudRepository<FcSquadra, Long>{

	Page<FcSquadra> findAll(Pageable pageable);

	Iterable<FcSquadra> findAll(Sort sort);

	public FcSquadra findByNomeSquadra(String nomeSquadra);
}