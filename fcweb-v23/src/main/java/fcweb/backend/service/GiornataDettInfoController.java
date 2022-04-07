package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcGiornataDettInfo;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.repositories.GiornataDettInfoRepository;

@Controller
public class GiornataDettInfoController{

	@Autowired
	private GiornataDettInfoRepository giornataDettInfoRepository;

	@RequestMapping(value="/findAllGiornataDettInfo", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornataDettInfo> findAll() {
		List<FcGiornataDettInfo> l = (List<FcGiornataDettInfo>) giornataDettInfoRepository.findAll();
		return l;
	}

	@RequestMapping(value="/findByAttoreAndGiornataInfo", method = RequestMethod.POST)
	@ResponseBody
	public FcGiornataDettInfo findByFcAttoreAndFcGiornataInfo(FcAttore attore,
			FcGiornataInfo giornataInfo) {
		FcGiornataDettInfo l = giornataDettInfoRepository.findByFcAttoreAndFcGiornataInfo(attore, giornataInfo);
		return l;
	}

}