package fcweb.backend.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import fcweb.backend.data.entity.FcCalendarioTim;

public interface CalendarioTimRepository
		extends CrudRepository<FcCalendarioTim, Long>{

	Page<FcCalendarioTim> findAll(Pageable pageable);

	Iterable<FcCalendarioTim> findAll(Sort sort);

	public List<FcCalendarioTim> findByIdGiornata(int idGiornata);

	public List<FcCalendarioTim> findByIdGiornataOrderByDataAsc(int idGiornata);

	public List<FcCalendarioTim> findByIdGiornataAndDataGreaterThanEqual(
			int idGiornata, LocalDateTime data);

	public List<FcCalendarioTim> findByIdGiornataAndDataLessThanEqual(
			int idGiornata, LocalDateTime data);

}