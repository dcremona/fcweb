package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.data.entity.FcClassifica;
import fcweb.backend.data.entity.FcClassificaId;
import fcweb.backend.repositories.ClassificaRepository;

@Controller
public class ClassificaController{

	@Autowired
	private ClassificaRepository classificaRepository;

	@RequestMapping(value = "/findAllClassifica", method = RequestMethod.POST)
	@ResponseBody
	public List<FcClassifica> findAll() {
		List<FcClassifica> l = (List<FcClassifica>) classificaRepository.findAll();
		return l;
	}

	@RequestMapping(value = "/findByCampionatoOrderByPuntiDescIdPosizAsc", method = RequestMethod.POST)
	@ResponseBody
	public List<FcClassifica> findByFcCampionatoOrderByPuntiDescIdPosizAsc(
			FcCampionato campionato) {
		List<FcClassifica> l = classificaRepository.findByFcCampionatoOrderByPuntiDescIdPosizAsc(campionato);
		return l;
	}

	@RequestMapping(value = "/findByCampionatoOrderByTotPuntiDesc", method = RequestMethod.POST)
	@ResponseBody
	public List<FcClassifica> findByFcCampionatoOrderByTotPuntiDesc(
			FcCampionato campionato) {
		List<FcClassifica> l = classificaRepository.findByFcCampionatoOrderByTotPuntiDesc(campionato);
		return l;
	}

	@RequestMapping(value = "/findByCampionatoOrderByTotPuntiRosaDesc", method = RequestMethod.POST)
	@ResponseBody
	public List<FcClassifica> findByFcCampionatoOrderByTotPuntiRosaDesc(
			FcCampionato campionato) {
		List<FcClassifica> l = classificaRepository.findByFcCampionatoOrderByTotPuntiRosaDesc(campionato);
		return l;
	}

	@RequestMapping(value = "/findByCampionatoAndAttore", method = RequestMethod.POST)
	@ResponseBody
	public FcClassifica findByFcCampionatoAndFcAttore(FcCampionato campionato,
			FcAttore attore) {
		FcClassifica l = classificaRepository.findByFcCampionatoAndFcAttore(campionato, attore);
		return l;
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(FcAttore attore, FcCampionato campionato,
			Double totPunti) {
		String id = "";
		try {
			FcClassifica clas = new FcClassifica();
			FcClassificaId classificaPK = new FcClassificaId();
			classificaPK.setIdAttore(attore.getIdAttore());
			classificaPK.setIdCampionato(campionato.getIdCampionato());
			clas.setId(classificaPK);
			clas.setTotPunti(totPunti);
			clas.setTotPuntiOld(totPunti);
			clas.setTotPuntiRosa(totPunti);
			classificaRepository.save(clas);
			id = clas.getFcAttore().toString();
		} catch (Exception ex) {
			return "Error creating the classifica: " + ex.toString();
		}
		return "classifica succesfully created with id = " + id;
	}
	
	
	@RequestMapping(path = "/updateClassifica", method = RequestMethod.POST)
	public FcClassifica updateClassifica(FcClassifica classifica) {
		FcClassifica fcClassifica = null;
		try {
			fcClassifica = classificaRepository.save(classifica);
		} catch (Exception ex) {

		}
		return fcClassifica;
	}

	@RequestMapping(path = "/deleteClassifica", method = RequestMethod.POST)
	public String deleteClassifica(FcClassifica classifica) {
		String id = "";
		try {
			classificaRepository.delete(classifica);
			id = "" + classifica.getId().getIdCampionato();
		} catch (Exception ex) {
			return "Error delete giornata: " + ex.toString();
		}
		return "classifica succesfully delete with id = " + id;
	}
	

}