package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fcweb.backend.data.entity.FcAttore;

@Service
public class AttoreService{

	private final AttoreRepository attoreRepository;

	@Autowired
	public AttoreService(AttoreRepository attoreRepository) {
		this.attoreRepository = attoreRepository;
	}

	public List<FcAttore> findAll() {
		List<FcAttore> l = (List<FcAttore>) attoreRepository.findAll();
		return l;
	}

	public List<FcAttore> findByActive(boolean active) {
		List<FcAttore> l = (List<FcAttore>) attoreRepository.findByActive(active);
		return l;
	}

	public FcAttore findByUsername(String username) {
		FcAttore att = (FcAttore) attoreRepository.findByUsername(username);
		return att;
	}

	public FcAttore findByUsernameAndPassword(String email, String username) {
		FcAttore att = (FcAttore) attoreRepository.findByUsernameAndPassword(email, username);
		return att;
	}

	public FcAttore updateAttore(FcAttore attore) {
		FcAttore fcAttore = null;
		try {
			fcAttore = attoreRepository.save(attore);
		} catch (Exception ex) {
		}
		return fcAttore;
	}

	public String deleteAttore(FcAttore attore) {
		String id = "";
		try {
			attoreRepository.delete(attore);
			id = "" + attore.getIdAttore();
		} catch (Exception ex) {
			return "Error delete attore: " + ex.toString();
		}
		return "attore succesfully delete with id = " + id;
	}

}