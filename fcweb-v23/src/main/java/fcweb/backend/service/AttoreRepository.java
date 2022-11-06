package fcweb.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import fcweb.backend.data.entity.FcAttore;

public interface AttoreRepository extends CrudRepository<FcAttore, Long>{

	Page<FcAttore> findAll(Pageable pageable);

	Iterable<FcAttore> findAll(Sort sort);
	
	public FcAttore findByUsername(String email);
	
	public List<FcAttore> findByActive(boolean active);

//	@Query("select u from FcAttore u where u.email = ?1")
//	public FcAttore findByEmail(String email);

	public FcAttore findByUsernameAndPassword(String username,String password);

//	@Query("select u from FcAttore u where u.email = ?1 and u.password = ?2")
//	public FcAttore findByEmailPassword(String email,String password);
	

}