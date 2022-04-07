package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcGiornata;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.repositories.GiornataRepository;

@Controller
public class GiornataController{

	@Autowired
	private GiornataRepository giornataRepository;

	private Sort sortBy() {
		return Sort.by(Sort.Direction.ASC, "fcGiornataInfo", "fcTipoGiornata", "fcAttoreByIdAttoreCasa");
	}

	@RequestMapping(value = "/findAllGiornata", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornata> findAll() {
		// List<FcGiornata> l = (List<FcGiornata>)
		// giornataRepository.findAll(new Sort(new
		// Order(Direction.ASC,"fcGiornataInfo.codiceGiornata"),new
		// Order(Direction.ASC,"fcTipoGiornata.idTipoGiornata"),new
		// Order(Direction.ASC,"fcAttoreByIdAttoreCasa.idAttore")));
		List<FcGiornata> l = (List<FcGiornata>) giornataRepository.findAll(sortBy());
		return l;
	}

	@RequestMapping(value = "/findByGiornataInfo", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornata> findByFcGiornataInfo(FcGiornataInfo giornataInfo) {
		List<FcGiornata> l = giornataRepository.findByFcGiornataInfoOrderByFcTipoGiornata(giornataInfo);
		return l;
	}

	@RequestMapping(value = "/findByGiornataInfoGreaterThanEqualAndGiornataInfoLessThanEqual", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornata> findByFcGiornataInfoGreaterThanEqualAndFcGiornataInfoLessThanEqualOrderByFcGiornataInfo(
			FcGiornataInfo start, FcGiornataInfo end) {
		List<FcGiornata> l = giornataRepository.findByFcGiornataInfoGreaterThanEqualAndFcGiornataInfoLessThanEqualOrderByFcGiornataInfo(start, end);
		return l;
	}

	@RequestMapping(path = "/updateGiornata", method = RequestMethod.POST)
	public FcGiornata updateGiornata(FcGiornata giornata) {
		FcGiornata fcGiornata = null;
		try {
			fcGiornata = giornataRepository.save(giornata);
		} catch (Exception ex) {

		}
		return fcGiornata;
	}

	@RequestMapping(path = "/deleteGiornata", method = RequestMethod.POST)
	public String deleteGiornata(FcGiornata giornata) {
		String id = "";
		try {
			giornataRepository.delete(giornata);
			id = "" + giornata.getId().getIdGiornata();
		} catch (Exception ex) {
			return "Error delete giornata: " + ex.toString();
		}
		return "giornata succesfully delete with id = " + id;
	}

}