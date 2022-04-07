package fcweb.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcTipoGiornata;
import fcweb.backend.repositories.TipoGiornataRepository;

@Controller
public class TipoGiornataController{

	@Autowired
	private TipoGiornataRepository tipoGiornataRepository;

	@RequestMapping(value="/findAllTipoGiornata", method = RequestMethod.POST)
	@ResponseBody
	public List<FcTipoGiornata> findAll() {
		List<FcTipoGiornata> l = (List<FcTipoGiornata>) tipoGiornataRepository.findAll(sortByIdTipoGiornataDesc());
		return l;
	}

	private Sort sortByIdTipoGiornataDesc() {
		return Sort.by(Sort.Direction.DESC, "idTipoGiornata");
	}

}