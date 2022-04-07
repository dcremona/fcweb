package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.repositories.AttoreRepository;

@Controller
public class AttoreController{

	@Autowired
	private AttoreRepository attoreRepository;

	@RequestMapping(value = "/findAllAttore", method = RequestMethod.POST)
	@ResponseBody
	public List<FcAttore> findAll() {
		List<FcAttore> l = (List<FcAttore>) attoreRepository.findAll();
		// List<FcAttore> lNew = new ArrayList<FcAttore>();
		// for (FcAttore a : l) {
		// if (a.isActive()) {
		// lNew.add(a);
		// }
		//
		// }
		return l;
	}

	@RequestMapping(value = "/findByActive", method = RequestMethod.POST)
	@ResponseBody
	public List<FcAttore> findByActive(boolean active) {
		List<FcAttore> l = (List<FcAttore>) attoreRepository.findByActive(active);
		return l;
	}

	@RequestMapping(value = "/findByUsername", method = RequestMethod.POST)
	@ResponseBody
	public FcAttore findByUsername(String username) {
		FcAttore att = (FcAttore) attoreRepository.findByUsername(username);
		return att;
	}

	@RequestMapping(value = "/findByUsernameAndPassword", method = RequestMethod.POST)
	@ResponseBody
	public FcAttore findByUsernameAndPassword(String email, String username) {
		FcAttore att = (FcAttore) attoreRepository.findByUsernameAndPassword(email, username);
		return att;
	}

	@RequestMapping(path = "/updateAttore", method = RequestMethod.POST)
	public FcAttore updateAttore(@RequestParam("attore") FcAttore attore) {
		FcAttore fcAttore = null;
		try {
			fcAttore = attoreRepository.save(attore);
		} catch (Exception ex) {
		}
		return fcAttore;
	}

	@RequestMapping(path = "/deleteAttore", method = RequestMethod.POST)
	public String deleteAttore(@RequestParam("attore") FcAttore attore) {
		String id = "";
		try {
			attoreRepository.delete(attore);
			id = "" + attore.getIdAttore();
		} catch (Exception ex) {
			return "Error delete attore: " + ex.toString();
		}
		return "attore succesfully delete with id = " + id;
	}

}