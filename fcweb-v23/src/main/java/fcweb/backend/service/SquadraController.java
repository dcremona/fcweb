package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcSquadra;
import fcweb.backend.repositories.SquadraRepository;

@Controller
public class SquadraController{

	@Autowired
	private SquadraRepository squadraRepository;

	@RequestMapping(value = "/findAllSqudra", method = RequestMethod.POST)
	@ResponseBody
	public List<FcSquadra> findAll() {
		List<FcSquadra> l = (List<FcSquadra>) squadraRepository.findAll(sortByIdSquadra());
		return l;
	}

	private Sort sortByIdSquadra() {
		return Sort.by(Sort.Direction.ASC, "idSquadra");
	}

	@RequestMapping(path = "/updateSquadra", method = RequestMethod.POST)
	public FcSquadra updateSquadra(FcSquadra c) {
		FcSquadra Squadra = null;
		try {
			Squadra = squadraRepository.save(c);
		} catch (Exception ex) {
			return Squadra;
		}
		return Squadra;
	}

	@RequestMapping(path = "/deleteSquadra", method = RequestMethod.POST)
	public String deleteSquadra(FcSquadra c) {
		String id = "";
		try {
			squadraRepository.delete(c);
			id = "" + c.getIdSquadra();
		} catch (Exception ex) {
			return "Error delete : " + ex.toString();
		}
		return "Squadra succesfully delete with id = " + id;
	}
	
}