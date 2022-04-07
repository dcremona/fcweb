package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcRuolo;
import fcweb.backend.repositories.RuoloRepository;

@Controller
public class RuoloController{

	@Autowired
	private RuoloRepository ruoloRepository;

	@RequestMapping(value="/findAllRuolo", method = RequestMethod.POST)
	@ResponseBody
	public List<FcRuolo> findAll() {
		List<FcRuolo> l = (List<FcRuolo>) ruoloRepository.findAll(sortByIdRuoloDesc());
		return l;
	}

	private Sort sortByIdRuoloDesc() {
		return Sort.by(Sort.Direction.DESC, "idRuolo");
	}

}