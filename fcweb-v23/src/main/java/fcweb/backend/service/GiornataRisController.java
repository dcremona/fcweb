package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.data.entity.FcGiornataRis;
import fcweb.backend.repositories.GiornataRisRepository;

@Controller
public class GiornataRisController{

	@Autowired
	private GiornataRisRepository giornataRisRepository;

	@RequestMapping(value = "/findAllGiornataRis", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornataRis> findAll() {
		List<FcGiornataRis> l = (List<FcGiornataRis>) giornataRisRepository.findAll();
		return l;
	}

	@RequestMapping(value = "/findByFcAttoreOrderByFcGiornataInfoDesc", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornataRis> findByFcAttoreOrderByFcGiornataInfoDesc(
			FcAttore fcAttore) {
		List<FcGiornataRis> l = giornataRisRepository.findByFcAttoreOrderByFcGiornataInfoDesc(fcAttore);
		return l;
	}

	@RequestMapping(value = "/findByFcAttoreOrderByFcGiornataInfoAsc", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornataRis> findByFcAttoreOrderByFcGiornataInfoAsc(
			FcAttore fcAttore) {
		List<FcGiornataRis> l = giornataRisRepository.findByFcAttoreOrderByFcGiornataInfoAsc(fcAttore);
		return l;
	}
	

	@RequestMapping(value = "/findByFcGiornataInfoGreaterThanEqualAndFcGiornataInfoLessThanEqual2", method = RequestMethod.POST)
	@ResponseBody
	public List<FcGiornataRis> findByFcGiornataInfoGreaterThanEqualAndFcGiornataInfoLessThanEqual(
			FcGiornataInfo start, FcGiornataInfo end) {
		List<FcGiornataRis> l = giornataRisRepository.findByFcGiornataInfoGreaterThanEqualAndFcGiornataInfoLessThanEqual(start,end);
		return l;
	}


}