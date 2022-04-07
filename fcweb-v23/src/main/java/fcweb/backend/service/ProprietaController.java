package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcProperties;
import fcweb.backend.repositories.ProprietaRepository;

@Controller
public class ProprietaController{

	@Autowired
	private ProprietaRepository proprietaRepository;

	@RequestMapping(value = "/findAllProprieta", method = RequestMethod.POST)
	@ResponseBody
	public List<FcProperties> findAll() {
		List<FcProperties> l = (List<FcProperties>) proprietaRepository.findAll();
		return l;
	}

	@RequestMapping(value = "/findByKey", method = RequestMethod.POST)
	@ResponseBody
	public FcProperties findByKey(String key) {
		FcProperties l = (FcProperties) proprietaRepository.findByKey(key);
		return l;
	}

	@RequestMapping(path = "/updateProprieta", method = RequestMethod.POST)
	public FcProperties updateProprieta(FcProperties proprieta) {
		FcProperties fcProperties = null;
		try {
			fcProperties = proprietaRepository.save(proprieta);
		} catch (Exception ex) {

		}
		return fcProperties;
	}

	@RequestMapping(path = "/deleteProprieta", method = RequestMethod.POST)
	public String deleteProprieta(FcProperties proprieta) {
		String id = "";
		try {
			proprietaRepository.delete(proprieta);
			id = "" + proprieta.getKey();
		} catch (Exception ex) {
			return "Error delete proprieta: " + ex.toString();
		}
		return "proprieta succesfully delete with id = " + id;
	}

}