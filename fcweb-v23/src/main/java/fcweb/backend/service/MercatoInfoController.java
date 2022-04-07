package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcMercatoDettInfo;
import fcweb.backend.repositories.MercatoInfoRepository;

@Controller
public class MercatoInfoController{

	@Autowired
	private MercatoInfoRepository mercatoInfoRepository;

	@RequestMapping(value = "/findAllMercatoInfo", method = RequestMethod.POST)
	@ResponseBody
	public List<FcMercatoDettInfo> findAll() {
		List<FcMercatoDettInfo> l = (List<FcMercatoDettInfo>) mercatoInfoRepository.findAll(sortByGiornataInfoAndattoreAsc());
		return l;
	}

	private Sort sortByGiornataInfoAndattoreAsc() {
		return Sort.by(Sort.Direction.ASC, "fcGiornataInfo", "fcAttore");
	}

	@RequestMapping(value = "/findByFcAttoreOrderByFcGiornataInfoAscMercatoInfo", method = RequestMethod.POST)
	@ResponseBody
	public List<FcMercatoDettInfo> findByFcAttoreOrderByFcGiornataInfoAsc(
			FcAttore fcAttore) {
		List<FcMercatoDettInfo> l = (List<FcMercatoDettInfo>) mercatoInfoRepository.findByFcAttoreOrderByFcGiornataInfoAsc(fcAttore);

		return l;
	}

	@RequestMapping(path = "/insertMercatoDettInfo", method = RequestMethod.POST)
	public FcMercatoDettInfo insertMercatoDettInfo(FcMercatoDettInfo mercatoInfo) {
		
		FcMercatoDettInfo fcMercatoDettInfo = null;
		try {
			fcMercatoDettInfo = mercatoInfoRepository.save(mercatoInfo);
		} catch (Exception ex) {
			return fcMercatoDettInfo;
		}
		return fcMercatoDettInfo;
	}
}