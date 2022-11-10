package fcweb.ui.views.admin;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;

import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

import common.util.Utils;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.service.AccessoService;
import fcweb.backend.service.GiornataInfoService;
import fcweb.ui.MainAppLayout;

@Route(value = "giornataInfo", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("GiornataInfo")
public class FcGiornataInfoView extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiornataInfoService giornataInfoController;

	@Autowired
	public Environment env;

	@Autowired
	private AccessoService accessoController;

	public FcGiornataInfoView() {
		LOG.info("FcGiornataInfoView()");
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

		GridCrud<FcGiornataInfo> crud = new GridCrud<>(FcGiornataInfo.class,new HorizontalSplitCrudLayout());

		DefaultCrudFormFactory<FcGiornataInfo> formFactory = new DefaultCrudFormFactory<>(FcGiornataInfo.class);
		crud.setCrudFormFactory(formFactory);
		formFactory.setUseBeanValidation(false);

		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "codiceGiornata", "dataAnticipo", "dataGiornata", "dataPosticipo", "descGiornata", "descGiornataFc", "idGiornataFc");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "codiceGiornata", "dataAnticipo", "dataGiornata", "dataPosticipo", "descGiornata", "descGiornataFc", "idGiornataFc");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "dataAnticipo", "dataGiornata", "dataPosticipo", "descGiornata", "descGiornataFc", "idGiornataFc");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "codiceGiornata", "descGiornata");

		// crud.getGrid().setColumns("codiceGiornata", "dataAnticipo",
		// "dataGiornata", "dataPosticipo", "descGiornata", "descGiornataFc",
		// "idGiornataFc");
		// crud.getGrid().setColumnReorderingAllowed(true);

		crud.getGrid().removeAllColumns();
		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : "" + g.getCodiceGiornata()));
		Column<FcGiornataInfo> dataAnticipoColumn = crud.getGrid().addColumn(new LocalDateTimeRenderer<>(FcGiornataInfo::getDataAnticipo,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).withLocale(Locale.ITALY)));
		dataAnticipoColumn.setSortable(false);
		dataAnticipoColumn.setAutoWidth(true);
		// dataAnticipoColumn.setFlexGrow(2);

		Column<FcGiornataInfo> dataGiornataColumn = crud.getGrid().addColumn(new LocalDateTimeRenderer<>(FcGiornataInfo::getDataGiornata,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).withLocale(Locale.ITALY)));
		dataGiornataColumn.setSortable(false);
		dataGiornataColumn.setAutoWidth(true);
		// dataGiornataColumn.setFlexGrow(2);

		Column<FcGiornataInfo> dataPosticipoColumn = crud.getGrid().addColumn(new LocalDateTimeRenderer<>(FcGiornataInfo::getDataPosticipo,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).withLocale(Locale.ITALY)));
		dataPosticipoColumn.setSortable(false);
		dataPosticipoColumn.setAutoWidth(true);
		// dataGiornataColumn.setFlexGrow(2);

		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : "" + g.getDescGiornata()));
		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : "" + g.getDescGiornataFc()));
		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : "" + g.getIdGiornataFc()));

		crud.getCrudFormFactory().setFieldProvider("dataAnticipo", a -> {
			DateTimePicker data = new DateTimePicker();
			return data;
		});

		crud.getCrudFormFactory().setFieldProvider("dataGiornata", a -> {
			DateTimePicker data = new DateTimePicker();
			return data;
		});

		crud.getCrudFormFactory().setFieldProvider("dataPosticipo", a -> {
			DateTimePicker data = new DateTimePicker();
			return data;
		});

		crud.setRowCountCaption("%d GiornataInfo(s) found");
		crud.setClickRowToUpdate(true);
		crud.setUpdateOperationVisible(true);

		crud.setFindAllOperation(() -> giornataInfoController.findAll());
		crud.setAddOperation(user -> giornataInfoController.updateGiornataInfo(user));
		crud.setUpdateOperation(user -> giornataInfoController.updateGiornataInfo(user));
		crud.setDeleteOperation(user -> giornataInfoController.deleteGiornataInfo(user));

		add(crud);
	}

}