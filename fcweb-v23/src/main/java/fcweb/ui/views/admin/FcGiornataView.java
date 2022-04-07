package fcweb.ui.views.admin;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

import common.util.Utils;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcGiornata;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.data.entity.FcTipoGiornata;
import fcweb.backend.service.AccessoController;
import fcweb.backend.service.AttoreController;
import fcweb.backend.service.GiornataController;
import fcweb.backend.service.GiornataInfoController;
import fcweb.backend.service.TipoGiornataController;
import fcweb.ui.MainAppLayout;

@Route(value = "giornata", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("Giornata")
public class FcGiornataView extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiornataController giornataController;

	@Autowired
	private AttoreController attoreController;

	@Autowired
	private GiornataInfoController giornataInfoController;

	@Autowired
	private TipoGiornataController tipoGiornataController;

	@Autowired
	public Environment env;

	@Autowired
	private AccessoController accessoController;

	public FcGiornataView() {
		LOG.info("FcGiornataView()");
	}

	@PostConstruct
	void init() {
		LOG.info("init");
		if (!Utils.isValidVaadinSession()) {
			return;
		}
		accessoController.insertAccesso(this.getClass().getName());
		initLayout();
	}

	private void initLayout() {

		this.setMargin(true);
		this.setSpacing(true);
		this.setSizeFull();

		GridCrud<FcGiornata> crud = new GridCrud<>(FcGiornata.class,new HorizontalSplitCrudLayout());

		DefaultCrudFormFactory<FcGiornata> formFactory = new DefaultCrudFormFactory<>(FcGiornata.class);
		crud.setCrudFormFactory(formFactory);
		formFactory.setUseBeanValidation(false);

		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "id", "fcTipoGiornata", "fcGiornataInfo", "fcAttoreByIdAttoreCasa", "fcAttoreByIdAttoreFuori", "golCasa", "golFuori", "totCasa", "totFuori");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "id", "fcTipoGiornata", "fcGiornataInfo", "fcAttoreByIdAttoreCasa", "fcAttoreByIdAttoreFuori", "golCasa", "golFuori", "totCasa", "totFuori");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "id", "fcTipoGiornata", "fcGiornataInfo", "fcAttoreByIdAttoreCasa", "fcAttoreByIdAttoreFuori", "golCasa", "golFuori", "totCasa", "totFuori");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "id", "fcTipoGiornata", "fcGiornataInfo", "fcAttoreByIdAttoreCasa");

		// crud.getGrid().setColumns("id", "fcAttoreByIdAttoreCasa",
		// "fcAttoreByIdAttoreFuori", "golCasa", "golFuori");

		crud.getGrid().removeAllColumns();
		crud.getGrid().addColumn(new TextRenderer<>(f -> f != null ? "" + f.getId().getIdGiornata() : "")).setHeader("Id Giornata");
		crud.getGrid().addColumn(new TextRenderer<>(f -> f != null && f.getFcGiornataInfo() != null ? f.getFcGiornataInfo().getDescGiornataFc() : "")).setHeader("Giornata");
		crud.getGrid().addColumn(new TextRenderer<>(f -> f != null && f.getFcTipoGiornata() != null ? f.getFcTipoGiornata().getDescTipoGiornata() : "")).setHeader("Tipo Giornata");
		crud.getGrid().addColumn(new TextRenderer<>(f -> f != null && f.getFcAttoreByIdAttoreCasa() != null ? f.getFcAttoreByIdAttoreCasa().getDescAttore() : "")).setHeader("Attore Casa");
		crud.getGrid().addColumn(new TextRenderer<>(f -> f != null && f.getFcAttoreByIdAttoreFuori() != null ? f.getFcAttoreByIdAttoreFuori().getDescAttore() : "")).setHeader("Attore Fuori");
		crud.getGrid().addColumn(new TextRenderer<>(f -> f != null && f.getGolCasa() != null ? f.getGolCasa().toString() : "")).setHeader("Gol Casa");
		crud.getGrid().addColumn(new TextRenderer<>(f -> f != null && f.getGolFuori() != null ? f.getGolFuori().toString() : "")).setHeader("Gol Fuori");
		crud.getGrid().addColumn(new TextRenderer<>(f -> f != null && f.getTotCasa() != null ? f.getTotCasa().toString() : "")).setHeader("Tot Casa");
		crud.getGrid().addColumn(new TextRenderer<>(f -> f != null && f.getTotFuori() != null ? f.getTotFuori().toString() : "")).setHeader("Tot Fuori");

		crud.getGrid().setColumnReorderingAllowed(true);

		crud.getCrudFormFactory().setFieldProvider("fcGiornataInfo", new ComboBoxProvider<>("Giornata",giornataInfoController.findAll(),new TextRenderer<>(FcGiornataInfo::getDescGiornataFc),FcGiornataInfo::getDescGiornataFc));
		crud.getCrudFormFactory().setFieldProvider("fcAttoreByIdAttoreCasa", new ComboBoxProvider<>("Attore Casa",attoreController.findByActive(true),new TextRenderer<>(FcAttore::getDescAttore),FcAttore::getDescAttore));
		crud.getCrudFormFactory().setFieldProvider("fcAttoreByIdAttoreFuori", new ComboBoxProvider<>("Attore Fuori",attoreController.findByActive(true),new TextRenderer<>(FcAttore::getDescAttore),FcAttore::getDescAttore));
		crud.getCrudFormFactory().setFieldProvider("fcTipoGiornata", new ComboBoxProvider<>("Tipo Giornata",tipoGiornataController.findAll(),new TextRenderer<>(FcTipoGiornata::getDescTipoGiornata),FcTipoGiornata::getDescTipoGiornata));

		crud.setRowCountCaption("%d Giornata(s) found");
		crud.setClickRowToUpdate(true);
		crud.setUpdateOperationVisible(true);

		crud.setFindAllOperation(() -> giornataController.findAll());
		crud.setAddOperation(user -> giornataController.updateGiornata(user));
		crud.setUpdateOperation(user -> giornataController.updateGiornata(user));
		crud.setDeleteOperation(user -> giornataController.deleteGiornata(user));

		add(crud);
	}

}