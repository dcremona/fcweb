package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.repositories.CampionatoRepository;

@Controller
public class CampionatoController{

	@Autowired
	private CampionatoRepository campionatoRepository;

	@RequestMapping(value = "/findAllCampionato", method = RequestMethod.POST)
	@ResponseBody
	public List<FcCampionato> findAll() {
		List<FcCampionato> l = (List<FcCampionato>) campionatoRepository.findAll();
		return l;
	}

	@RequestMapping(value = "/findByIdCampionato", method = RequestMethod.POST)
	@ResponseBody
	public FcCampionato findByIdCampionato(Integer idCampionato) {
		FcCampionato c = (FcCampionato) campionatoRepository.findByIdCampionato(idCampionato);
		return c;
	}

	@RequestMapping(value = "/findByCampionatoActive", method = RequestMethod.POST)
	@ResponseBody
	public FcCampionato findByActive(boolean active) {
		FcCampionato c = (FcCampionato) campionatoRepository.findByActive(active);
		return c;
	}

	@RequestMapping(path = "/updateCampionato", method = RequestMethod.POST)
	public FcCampionato updateCampionato(FcCampionato c) {
		FcCampionato fcCampionato = null;
		try {
			fcCampionato = campionatoRepository.save(c);
		} catch (Exception ex) {

		}
		return fcCampionato;
	}

	@RequestMapping(path = "/deleteCampionato", method = RequestMethod.POST)
	public String deleteCampionato(FcCampionato c) {
		String id = "";
		try {
			campionatoRepository.delete(c);
			id = "" + c.getIdCampionato();
		} catch (Exception ex) {
			return "Error delete : " + ex.toString();
		}
		return "campionato succesfully delete with id = " + id;
	}

}