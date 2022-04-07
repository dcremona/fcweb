package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcExpStat;
import fcweb.backend.repositories.AlboRepository;

@Controller
public class AlboController{

	@Autowired
	private AlboRepository alboRepository;

	@RequestMapping(value = "/findAllAlbo", method = RequestMethod.POST)
	@ResponseBody
	public List<FcExpStat> findAll() {
		List<FcExpStat> l = (List<FcExpStat>) alboRepository.findAll(sortByIdAsc());
		return l;
	}

	private Sort sortByIdAsc() {
		return Sort.by(Sort.Direction.DESC,"id");
	}

}