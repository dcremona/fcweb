package fcweb.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcGiocatore;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.data.entity.FcPagelle;
import fcweb.backend.repositories.PagelleRepository;

@Controller
public class PagelleController{

	@Autowired
	private PagelleRepository pagelleRepository;

	@RequestMapping(value = "/findAllPagelle", method = RequestMethod.POST)
	@ResponseBody
	public List<FcPagelle> findAll() {
		List<FcPagelle> l = (List<FcPagelle>) pagelleRepository.findAll();
		return l;
	}

	@RequestMapping(value = "/findCurrentGiornata", method = RequestMethod.POST)
	@ResponseBody
	public FcPagelle findCurrentGiornata() {
		FcPagelle currentGiornata = pagelleRepository.findTopByOrderByFcGiornataInfoDesc();
		return currentGiornata;
	}

	@RequestMapping(value = "/findByCustonm", method = RequestMethod.POST)
	@ResponseBody
	public List<FcPagelle> findByCustonm(FcGiornataInfo giornataInfo,
			FcGiocatore giocatore) {

		List<FcPagelle> l = null;
		if (giornataInfo == null && giocatore == null) {
			l = (List<FcPagelle>) pagelleRepository.findAll();
		} else if (giornataInfo != null && giocatore == null) {
			l = (List<FcPagelle>) pagelleRepository.findByFcGiornataInfoOrderByFcGiocatoreFcSquadraAscFcGiocatoreFcRuoloDescFcGiocatoreAsc(giornataInfo);
		} else if (giornataInfo == null && giocatore != null) {
			l = pagelleRepository.findByFcGiocatore(giocatore);
		} else if (giornataInfo != null && giocatore != null) {
			FcPagelle fcPagelle = pagelleRepository.findByFcGiornataInfoAndFcGiocatore(giornataInfo, giocatore);
			l = new ArrayList<FcPagelle>();
			l.add(fcPagelle);
		}
		return l;
	}

	@RequestMapping(path = "/updatePagelle", method = RequestMethod.POST)
	public FcPagelle updatePagelle(FcPagelle c) {
		FcPagelle fcPagelle = null;
		try {
			fcPagelle = pagelleRepository.save(c);
		} catch (Exception ex) {
			return fcPagelle;
		}
		return fcPagelle;
	}

	@RequestMapping(path = "/deletePagelle", method = RequestMethod.POST)
	public String deletePagelle(FcPagelle c) {
		String id = "";
		try {
			pagelleRepository.delete(c);
			id = "" + c.getId();
		} catch (Exception ex) {
			return "Error delete : " + ex.toString();
		}
		return "Pagelle succesfully delete with id = " + id;
	}

}