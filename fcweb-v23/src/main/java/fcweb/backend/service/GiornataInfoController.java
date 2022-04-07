package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.repositories.GiornataInfoRepository;

@Controller
public class GiornataInfoController{

	@Autowired
	private GiornataInfoRepository giornataInfoRepository;

	@RequestMapping(value = "/findAllGiornataInfo", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornataInfo> findAll() {
		List<FcGiornataInfo> l = (List<FcGiornataInfo>) giornataInfoRepository.findAll();
		return l;
	}

	@RequestMapping(value = "/findByCodiceGiornata", method = RequestMethod.POST)
	@ResponseBody
	public FcGiornataInfo findByCodiceGiornata(Integer gg) {
		FcGiornataInfo info = giornataInfoRepository.findByCodiceGiornata(gg);
		return info;
	}

	@RequestMapping(value = "/findByDescGiornataFc", method = RequestMethod.POST)
	@ResponseBody
	public FcGiornataInfo findByDescGiornataFc(String descGiornataFc) {
		FcGiornataInfo fcGiornataInfo = giornataInfoRepository.findByDescGiornataFc(descGiornataFc);
		return fcGiornataInfo;
	}

	@RequestMapping(value = "/findByCodiceGiornataGreaterThanEqualAndCodiceGiornataLessThanEqual", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornataInfo> findByCodiceGiornataGreaterThanEqualAndCodiceGiornataLessThanEqual(
			Integer from, Integer to) {
		List<FcGiornataInfo> l = (List<FcGiornataInfo>) giornataInfoRepository.findByCodiceGiornataGreaterThanEqualAndCodiceGiornataLessThanEqual(from, to);
		return l;
	}

	@RequestMapping(path = "/updateGiornataInfo", method = RequestMethod.POST)
	public FcGiornataInfo updateGiornataInfo(FcGiornataInfo giornataInfo) {
		FcGiornataInfo fcGiornataInfo = null;
		try {
			fcGiornataInfo = giornataInfoRepository.save(giornataInfo);
		} catch (Exception ex) {

		}
		return fcGiornataInfo;
	}

	@RequestMapping(path = "/deleteGiornataInfo", method = RequestMethod.POST)
	public String deleteGiornataInfo(FcGiornataInfo giornataInfo) {
		String id = "";
		try {
			giornataInfoRepository.delete(giornataInfo);
			id = "" + giornataInfo.getCodiceGiornata();
		} catch (Exception ex) {
			return "Error delete giornataInfo: " + ex.toString();
		}
		return "giornataInfo succesfully delete with id = " + id;
	}

}