package fcweb.backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcGiornataDett;
import fcweb.backend.data.entity.FcGiornataDettId;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.repositories.GiornataDettRepository;

@Controller
public class GiornataDettController{

	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GiornataDettRepository giornataDettRepository;

	@RequestMapping(value="/findAllGiornataDett", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornataDett> findAll() {
		List<FcGiornataDett> l = (List<FcGiornataDett>) giornataDettRepository.findAll();
		return l;
	}

	@RequestMapping(value="/findByAttoreAndGiornataInfoOrderByOrdinamentoAsc", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornataDett> findByFcAttoreAndFcGiornataInfoOrderByOrdinamentoAsc(
			FcAttore attore, FcGiornataInfo giornataInfo) {
		List<FcGiornataDett> l = (List<FcGiornataDett>) giornataDettRepository.findByFcAttoreAndFcGiornataInfoOrderByOrdinamentoAsc(attore, giornataInfo);
		return l;
	}

	@RequestMapping(path = "/insertGiornataDett", method = RequestMethod.POST)
	public FcGiornataDett insertGiornataDett(FcGiornataDett c) {
		FcGiornataDett fcGiornataDett = null;
		try {
			
			FcGiornataDettId id = new FcGiornataDettId();
			id.setIdGiornata(c.getFcGiornataInfo().getCodiceGiornata());
			id.setIdAttore(c.getFcAttore().getIdAttore());
			id.setIdGiocatore(c.getFcGiocatore().getIdGiocatore());
			c.setId(id);
			fcGiornataDett = giornataDettRepository.save(c);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			return fcGiornataDett;
		}
		return fcGiornataDett;
	}

	@RequestMapping(path = "/updateGiornataDett", method = RequestMethod.POST)
	public FcGiornataDett updateGiornataDett(FcGiornataDett c) {
		FcGiornataDett fcGiornataDett = null;
		try {
			fcGiornataDett = giornataDettRepository.save(c);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			return fcGiornataDett;
		}
		return fcGiornataDett;
	}

	@RequestMapping(path = "/deleteGiornataDett", method = RequestMethod.POST)
	public String deleteGiornataDett(FcGiornataDett c) {
		String id = "";
		try {
			giornataDettRepository.delete(c);
			id = "" + c.getId();
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			return "Error delete : " + ex.toString();
		}
		return "GiornataDett succesfully delete with id = " + id;
	}

}