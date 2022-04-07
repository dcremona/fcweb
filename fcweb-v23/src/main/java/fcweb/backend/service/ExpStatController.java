package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcExpStat;
import fcweb.backend.repositories.ExpStatRepository;

@Controller
public class ExpStatController{

	@Autowired
	private ExpStatRepository expStatRepository;

	@RequestMapping(value = "/findAllExpStat", method = RequestMethod.POST)
	@ResponseBody
	public List<FcExpStat> findAll() {
		List<FcExpStat> l = (List<FcExpStat>) expStatRepository.findAll(sortByIdAsc());
		return l;
	}

	private Sort sortByIdAsc() {
		return Sort.by(Sort.Direction.ASC, "id");
	}

	@RequestMapping(path = "/updateExpStat", method = RequestMethod.POST)
	public FcExpStat updateExpStat(@RequestParam("expStat") FcExpStat expStat) {
		FcExpStat fcExpStat = null;
		try {
			fcExpStat = expStatRepository.save(expStat);
		} catch (Exception ex) {
		}
		return fcExpStat;
	}

	@RequestMapping(path = "/deleteExpStat", method = RequestMethod.POST)
	public String deleteExpStat(@RequestParam("expStat") FcExpStat expStat) {
		String id = "";
		try {
			expStatRepository.delete(expStat);
			id = "" + expStat.getId();
		} catch (Exception ex) {
			return "Error delete FcExpStat: " + ex.toString();
		}
		return "expStat succesfully delete with id = " + id;
	}

}