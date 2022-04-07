package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcExpFreePl;
import fcweb.backend.repositories.ExpFreePlRepository;

@Controller
public class ExpFreePlController{

	@Autowired
	private ExpFreePlRepository expFreePlRepository;

	@RequestMapping(value = "/findAllExpFreePl", method = RequestMethod.POST)
	@ResponseBody
	public List<FcExpFreePl> findAll() {
		List<FcExpFreePl> l = (List<FcExpFreePl>) expFreePlRepository.findAll(sortByIdAsc());

		return l;
	}

	private Sort sortByIdAsc() {
		return Sort.by(Sort.Direction.ASC,"id");
	}

}