package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcExpRosea;
import fcweb.backend.repositories.ExpRoseARepository;

@Controller
public class ExpRoseAController{

	@Autowired
	private ExpRoseARepository expRoseARepository;

	@RequestMapping(value = "/findAllExpRoseA", method = RequestMethod.POST)
	@ResponseBody
	public List<FcExpRosea> findAll() {
		List<FcExpRosea> l = (List<FcExpRosea>) expRoseARepository.findAll(sortByIdAsc());
		return l;
	}

	private Sort sortByIdAsc() {
		return Sort.by(Sort.Direction.ASC,"id");
	}
}