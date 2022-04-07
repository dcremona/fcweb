package fcweb.backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fcweb.backend.data.entity.FcRegolamento;
import fcweb.backend.repositories.RegolamentoRepository;

@Controller
public class RegolamentoController{

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RegolamentoRepository regolamentoRepository;

	@RequestMapping(value = "/findAllRegolamento", method = RequestMethod.POST)
	@ResponseBody
	public List<FcRegolamento> findAll() {
		List<FcRegolamento> l = (List<FcRegolamento>) regolamentoRepository.findAll(sortByIdDesc());
		return l;
	}

	private Sort sortByIdDesc() {
		return Sort.by(Sort.Direction.DESC, "id");
	}

	@RequestMapping(path = "/insertRegolamento", method = RequestMethod.POST)
	public FcRegolamento insertRegolamento(FcRegolamento r) {

		FcRegolamento fcRegolamento = null;
		try {
			fcRegolamento = regolamentoRepository.save(r);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
		return fcRegolamento;
	}

	@RequestMapping(path = "/updateRegolamento", method = RequestMethod.POST)
	public FcRegolamento updateRegolamento(FcRegolamento r) {

		FcRegolamento fcRegolamento = null;
		try {
			fcRegolamento = regolamentoRepository.save(r);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
		return fcRegolamento;
	}

	@RequestMapping(path = "/deleteRegolamento", method = RequestMethod.POST)
	public String deleteRegolamento(FcRegolamento r) {
		String id = "";
		try {
			regolamentoRepository.delete(r);
			id = "" + r.getId();
		} catch (Exception ex) {
			return "Error delete Regolamento: " + ex.toString();
		}
		return "Regolamento succesfully delete with id = " + id;
	}

}