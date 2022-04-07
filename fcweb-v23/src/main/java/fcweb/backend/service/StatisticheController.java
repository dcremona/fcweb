package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcStatistiche;
import fcweb.backend.repositories.StatisticheRepository;

@Controller
public class StatisticheController{

	@Autowired
	private StatisticheRepository statisticheRepository;

	@RequestMapping(value = "/findAllStatistiche", method = RequestMethod.POST)
	@ResponseBody
	public List<FcStatistiche> findAll() {
		List<FcStatistiche> l = (List<FcStatistiche>) statisticheRepository.findAll(sortByIdRuoloDesc());
		return l;
	}

	@RequestMapping(value = "/findByFlagAttivoStatistiche", method = RequestMethod.POST)
	@ResponseBody
	public List<FcStatistiche> findByFlagAttivo(boolean flagAttivo) {
		List<FcStatistiche> l = (List<FcStatistiche>) statisticheRepository.findByFlagAttivo(flagAttivo);
		return l;
	}

	private Sort sortByIdRuoloDesc() {
		return Sort.by(Sort.Direction.DESC, "idRuolo");
	}

	@RequestMapping(path = "/updateStatistiche", method = RequestMethod.POST)
	public FcStatistiche updateStatistiche(
			@RequestParam("statistiche") FcStatistiche statistiche) {
		FcStatistiche fcStatistiche = null;
		try {
			fcStatistiche = statisticheRepository.save(statistiche);
		} catch (Exception ex) {
		}
		return fcStatistiche;
	}

	@RequestMapping(path = "/deleteStatistiche", method = RequestMethod.POST)
	public String deleteStatistiche(
			@RequestParam("statistiche") FcStatistiche statistiche) {
		String id = "";
		try {
			statisticheRepository.delete(statistiche);
			id = "" + statistiche.getIdGiocatore();
		} catch (Exception ex) {
			return "Error delete FcStatistiche: " + ex.toString();
		}
		return "statistiche succesfully delete with id = " + id;
	}

}