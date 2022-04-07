package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.data.entity.FcMercatoDett;
import fcweb.backend.repositories.MercatoRepository;

@Controller
public class MercatoController{

	@Autowired
	private MercatoRepository mercatoRepository;

	@RequestMapping(value = "/findAllMercatoDett", method = RequestMethod.POST)
	@ResponseBody
	public List<FcMercatoDett> findAll() {
		List<FcMercatoDett> l = (List<FcMercatoDett>) mercatoRepository.findAll(sortByGiornataInfoAndattoreAsc());
		return l;
	}

	// @RequestMapping(value =
	// "/findByFcAttoreOrderByFcGiornataInfoAscMercatoDett")
	// @ResponseBody
	// public List<FcMercatoDett> findByFcAttoreOrderByFcGiornataInfoAsc(
	// FcAttore attore) {
	// List<FcMercatoDett> l = (List<FcMercatoDett>)
	// mercatoRepository.findByFcAttoreOrderByFcGiornataInfoAsc(attore);
	// return l;
	// }

	@RequestMapping(value = "/findByAttoreOrderByGiornataInfoDescMercatoDett", method = RequestMethod.POST)
	@ResponseBody
	public List<FcMercatoDett> findByFcAttoreOrderByFcGiornataInfoDesc(
			FcAttore attore) {
		List<FcMercatoDett> l = (List<FcMercatoDett>) mercatoRepository.findByFcAttoreOrderByFcGiornataInfoDesc(attore);
		return l;
	}

	@RequestMapping(value = "/findByGiornataInfoGreaterThanEqualAndGiornataInfoLessThanEqualAndAttoreOrderByGiornataInfoDescIdDescMercatoDett", method = RequestMethod.POST)
	@ResponseBody
	public List<FcMercatoDett> findByFcGiornataInfoGreaterThanEqualAndFcGiornataInfoLessThanEqualAndFcAttoreOrderByFcGiornataInfoDescIdDesc(
			FcGiornataInfo from, FcGiornataInfo to, FcAttore attore) {
		List<FcMercatoDett> l = (List<FcMercatoDett>) mercatoRepository.findByFcGiornataInfoGreaterThanEqualAndFcGiornataInfoLessThanEqualAndFcAttoreOrderByFcGiornataInfoDescIdDesc(from, to, attore);
		return l;
	}

	@RequestMapping(value = "/findByFcAttoreOrderByFcGiornataInfoDescDataCambioDescMercatoDett", method = RequestMethod.POST)
	@ResponseBody
	public List<FcMercatoDett> findByFcAttoreOrderByFcGiornataInfoDescDataCambioDesc(
			FcAttore attore) {
		List<FcMercatoDett> l = (List<FcMercatoDett>) mercatoRepository.findByFcAttoreOrderByFcGiornataInfoDescDataCambioDesc(attore);
		return l;
	}

	@RequestMapping(path = "/insertMercatoDett", method = RequestMethod.POST)
	public FcMercatoDett insertMercatoDett(FcMercatoDett c) {
		FcMercatoDett fcMercatoDett = null;
		try {
			fcMercatoDett = mercatoRepository.save(c);
		} catch (Exception ex) {
			return fcMercatoDett;
		}
		return fcMercatoDett;
	}

	@RequestMapping(path = "/deleteFcMercatoDett", method = RequestMethod.POST)
	public String deleteMercatoDett(FcMercatoDett c) {
		String id = "";
		try {
			mercatoRepository.delete(c);
			id = "" + c.getId();
		} catch (Exception ex) {
			return "Error delete : " + ex.toString();
		}
		return "FcMercatoDett succesfully delete with id = " + id;
	}

	private Sort sortByGiornataInfoAndattoreAsc() {
		return Sort.by(Sort.Direction.ASC, "fcGiornataInfo", "fcAttore","id");
	}

}