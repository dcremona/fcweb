package fcweb.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcCalendarioTim;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.repositories.CalendarioTimRepository;

@Controller
public class CalendarioTimController{

	@Autowired
	private CalendarioTimRepository calendarioTimRepository;

	@RequestMapping(value = "/findAllCalendarioTim", method = RequestMethod.POST)
	@ResponseBody
	public List<FcCalendarioTim> findAll() {
		List<FcCalendarioTim> l = (List<FcCalendarioTim>) calendarioTimRepository.findAll(sortByIdAsc());
		return l;
	}

	private Sort sortByIdAsc() {
		return Sort.by(Sort.Direction.ASC, "id");
	}

	@RequestMapping(value = "/findCustom", method = RequestMethod.POST)
	@ResponseBody
	public List<FcCalendarioTim> findCustom(FcGiornataInfo fcGiornataInfo) {

		List<FcCalendarioTim> l = null;
		if (fcGiornataInfo == null) {
			l = (List<FcCalendarioTim>) calendarioTimRepository.findAll(sortByIdAsc());
		} else {
			l = (List<FcCalendarioTim>) calendarioTimRepository.findByIdGiornataOrderByDataAsc(fcGiornataInfo.getCodiceGiornata());
		}
		return l;
	}

	@RequestMapping(value = "/findByIdGiornata", method = RequestMethod.POST)
	@ResponseBody
	public List<FcCalendarioTim> findByIdGiornata(int idGiornata) {
		List<FcCalendarioTim> l = (List<FcCalendarioTim>) calendarioTimRepository.findByIdGiornata(idGiornata);
		return l;
	}

	@RequestMapping(value = "/findByIdGiornataOrderByDataAsc", method = RequestMethod.POST)
	@ResponseBody
	public List<FcCalendarioTim> findByIdGiornataOrderByDataAsc(
			int idGiornata) {
		List<FcCalendarioTim> l = (List<FcCalendarioTim>) calendarioTimRepository.findByIdGiornataOrderByDataAsc(idGiornata);
		return l;
	}

	@RequestMapping(value = "/findByIdGiornataAndDataLessThanEqual", method = RequestMethod.POST)
	@ResponseBody
	public List<FcCalendarioTim> findByIdGiornataAndDataLessThanEqual(
			int idGiornata, LocalDateTime data) {
		List<FcCalendarioTim> l = (List<FcCalendarioTim>) calendarioTimRepository.findByIdGiornataAndDataLessThanEqual(idGiornata, data);
		return l;
	}

	@RequestMapping(path = "/updateCalendarioTim", method = RequestMethod.POST)
	public FcCalendarioTim updateCalendarioTim(
			@RequestParam("caòemdarioTim") FcCalendarioTim caòemdarioTim) {
		FcCalendarioTim fcCalendarioTim = null;
		try {
			fcCalendarioTim = calendarioTimRepository.save(caòemdarioTim);
		} catch (Exception ex) {
		}
		return fcCalendarioTim;
	}

	@RequestMapping(path = "/deleteCalendarioTim", method = RequestMethod.POST)
	public String deleteCalendarioTim(
			@RequestParam("caòemdarioTim") FcCalendarioTim caòemdarioTim) {
		String id = "";
		try {
			calendarioTimRepository.delete(caòemdarioTim);
			id = "" + caòemdarioTim.getId();
		} catch (Exception ex) {
			return "Error delete caòemdarioTim: " + ex.toString();
		}
		return "caòemdarioTim succesfully delete with id = " + id;
	}

}