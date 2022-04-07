package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcStatoGiocatore;
import fcweb.backend.repositories.StatoGiocatoreRepository;

@Controller
public class StatoGiocatoreController{

	@Autowired
	private StatoGiocatoreRepository statoGiocatoreRepository;

	@RequestMapping(value="/findAllStatoGiocatore", method = RequestMethod.POST)
	@ResponseBody
	public List<FcStatoGiocatore> findAll() {
		List<FcStatoGiocatore> l = (List<FcStatoGiocatore>) statoGiocatoreRepository.findAll();
		return l;
	}

}