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

import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

import common.util.Utils;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcGiocatore;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.data.entity.FcMercatoDett;
import fcweb.backend.service.AccessoService;
import fcweb.backend.service.AttoreService;
import fcweb.backend.service.GiocatoreService;
import fcweb.backend.service.GiornataInfoService;
import fcweb.backend.service.MercatoService;
import fcweb.ui.MainAppLayout;

@Route(value = "mercatodett", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("MercatoDett")
public class FcMercatoDettView extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MercatoService mercatoController;

	@Autowired
	private GiornataInfoService giornataInfoController;

	@Autowired
	private AttoreService attoreController;

	@Autowired
	private GiocatoreService giocatoreController;

	@Autowired
	public Environment env;

	@Autowired
	private AccessoService accessoController;

	public FcMercatoDettView() {
		LOG.info("FcMercatoDettView()");
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

		GridCrud<FcMercatoDett> crud = new GridCrud<>(FcMercatoDett.class,new HorizontalSplitCrudLayout());
		DefaultCrudFormFactory<FcMercatoDett> formFactory = new DefaultCrudFormFactory<>(FcMercatoDett.class);
		crud.setCrudFormFactory(formFactory);
		formFactory.setUseBeanValidation(false);

		// formFactory.setVisibleProperties("id", "fcAttore",
		// "fcGiocatoreByIdGiocVen", "fcGiocatoreByIdGiocAcq", "fcGiornataInfo",
		// "dataCambio","nota");

		formFactory.setVisibleProperties(CrudOperation.READ, "id", "fcGiornataInfo", "fcAttore", "fcGiocatoreByIdGiocVen", "fcGiocatoreByIdGiocAcq", "dataCambio", "nota");
		formFactory.setVisibleProperties(CrudOperation.ADD, "id", "fcGiornataInfo", "fcAttore", "fcGiocatoreByIdGiocVen", "fcGiocatoreByIdGiocAcq", "dataCambio", "nota");
		formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "fcGiornataInfo", "fcAttore", "fcGiocatoreByIdGiocVen", "fcGiocatoreByIdGiocAcq", "dataCambio", "nota");
		formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "fcGiornataInfo", "fcAttore", "fcGiocatoreByIdGiocVen", "fcGiocatoreByIdGiocAcq");

		crud.getGrid().setColumns("id", "fcAttore", "fcGiocatoreByIdGiocVen", "fcGiocatoreByIdGiocAcq", "fcGiornataInfo", "dataCambio", "nota");
		crud.getGrid().removeAllColumns();
		crud.getGrid().addColumn(new TextRenderer<>(g -> g != null ? "" + g.getId() : "")).setHeader("Id");
		crud.getGrid().addColumn(new TextRenderer<>(f -> f != null && f.getFcGiornataInfo() != null ? f.getFcGiornataInfo().getDescGiornataFc() : "")).setHeader("Giornata");
		crud.getGrid().addColumn(new TextRenderer<>(g -> g != null ? g.getFcAttore().getDescAttore() : "")).setHeader("Attore");
		crud.getGrid().addColumn(new TextRenderer<>(g -> g != null && g.getFcGiocatoreByIdGiocVen() != null ? "" + g.getFcGiocatoreByIdGiocVen().getCognGiocatore() : "")).setHeader("Gioc Ven");
		crud.getGrid().addColumn(new TextRenderer<>(g -> g != null && g.getFcGiocatoreByIdGiocAcq() != null ? "" + g.getFcGiocatoreByIdGiocAcq().getCognGiocatore() : "")).setHeader("Gioc Acq");

		Column<FcMercatoDett> dataColumn = crud.getGrid().addColumn(
				//new LocalDateTimeRenderer<>(FcMercatoDett::getDataCambio,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM).withLocale(Locale.ITALY))
				new LocalDateTimeRenderer<>(FcMercatoDett::getDataCambio)
		);
		dataColumn.setHeader("Data Cambio");
		dataColumn.setSortable(true);
		dataColumn.setAutoWidth(true);
		dataColumn.setFlexGrow(2);

		crud.getGrid().addColumn(new TextRenderer<>(g -> g != null ? "" + g.getNota() : "")).setHeader("Nota");

		crud.getGrid().setColumnReorderingAllowed(true);

		formFactory.setFieldProvider("fcGiornataInfo", new ComboBoxProvider<>("Giornata",giornataInfoController.findAll(),new TextRenderer<>(FcGiornataInfo::getDescGiornataFc),FcGiornataInfo::getDescGiornataFc));
		formFactory.setFieldProvider("fcAttore", new ComboBoxProvider<>("Attore",attoreController.findByActive(true),new TextRenderer<>(FcAttore::getDescAttore),FcAttore::getDescAttore));
		formFactory.setFieldProvider("fcGiocatoreByIdGiocVen", new ComboBoxProvider<>("Gioc Acq",giocatoreController.findAll(),new TextRenderer<>(FcGiocatore::getCognGiocatore),FcGiocatore::getCognGiocatore));
		formFactory.setFieldProvider("fcGiocatoreByIdGiocAcq", new ComboBoxProvider<>("Gioc Ven",giocatoreController.findAll(),new TextRenderer<>(FcGiocatore::getCognGiocatore),FcGiocatore::getCognGiocatore));
		formFactory.setFieldProvider("dataCambio", a -> {
			DateTimePicker data = new DateTimePicker();
			return data;
		});

		crud.setRowCountCaption("%d Mercato(s) found");
		crud.setClickRowToUpdate(true);
		crud.setUpdateOperationVisible(true);

		crud.setFindAllOperation(() -> mercatoController.findAll());
		crud.setAddOperation(g -> mercatoController.insertMercatoDett(g));
		crud.setUpdateOperation(g -> mercatoController.insertMercatoDett(g));
		crud.setDeleteOperation(g -> mercatoController.deleteMercatoDett(g));

		add(crud);

		// this.add(getDefaultCrud());
		// this.add(getMinimal());
	}

	// @Override
	// public FcGiocatore add(FcGiocatore arg0) {
	// return null;
	// }
	//
	// @Override
	// public void delete(FcGiocatore arg0) {
	// }
	//
	// @Override
	// public Collection<FcGiocatore> findAll() {
	// Collection<FcGiocatore> c = giocatoreController.findAll();
	// return c;
	// }
	//
	// @Override
	// public FcGiocatore update(FcGiocatore arg0) {
	// return null;
	// }

	// private Component getDefaultCrud() {
	// return new GridCrud<>(FcGiocatore.class, this);
	// }
	//
	// private Component getMinimal() {
	// GridCrud<FcGiocatore> crud = new GridCrud<>(FcGiocatore.class);
	// crud.setCrudListener(this);
	// crud.getCrudFormFactory().setFieldProvider("fcSquadra", new
	// ComboBoxProvider<>(squadraController.findAll()));
	// crud.getCrudFormFactory().setFieldProvider("fcRuolo", new
	// CheckBoxGroupProvider<>(ruoloController.findAll()));
	// crud.getGrid().setColumns("idGiocatore", "cognGiocatore", "quotazione",
	// "flagAttivo");
	// return crud;
	// }

	// private Component getConfiguredCrud() {
	// GridCrud<User> crud = new GridCrud<>(User.class, new
	// HorizontalSplitCrudLayout());
	// crud.setCrudListener(this);
	//
	// DefaultCrudFormFactory<User> formFactory = new
	// DefaultCrudFormFactory<>(User.class);
	// crud.setCrudFormFactory(formFactory);
	//
	// formFactory.setUseBeanValidation(true);
	//
	// formFactory.setErrorListener(e -> {
	// Notification.show("Custom error message");
	// e.printStackTrace();
	// });
	//
	// formFactory.setVisibleProperties("name", "birthDate", "email",
	// "phoneNumber",
	// "maritalStatus", "groups", "active", "mainGroup");
	// formFactory.setVisibleProperties(CrudOperation.DELETE, "name", "email",
	// "mainGroup");
	//
	// formFactory.setDisabledProperties("id");
	//
	// crud.getGrid().setColumns("name", "email", "phoneNumber", "active");
	// crud.getGrid().addColumn(new LocalDateRenderer<>(
	// user -> user.getBirthDate(),
	// DateTimeFormatter.ISO_LOCAL_DATE))
	// .setHeader("Birthdate");
	//
	// crud.getGrid().addColumn(new TextRenderer<>(user -> user == null ? "" :
	// user.getMainGroup().getName()))
	// .setHeader("Main group");
	//
	// crud.getGrid().setColumnReorderingAllowed(true);
	//
	// formFactory.setFieldType("password", PasswordField.class);
	// formFactory.setFieldProvider("birthDate", () -> {
	// DatePicker datePicker = new DatePicker();
	// datePicker.setMax(LocalDate.now());
	// return datePicker;
	// });
	//
	// formFactory.setFieldProvider("maritalStatus", new
	// RadioButtonGroupProvider<>(Arrays.asList(MaritalStatus.values())));
	// formFactory.setFieldProvider("groups", new
	// CheckBoxGroupProvider<>("Groups", GroupRepository.findAll(), new
	// TextRenderer<>(Group::getName)));
	// formFactory.setFieldProvider("mainGroup",
	// new ComboBoxProvider<>("Main Group", GroupRepository.findAll(), new
	// TextRenderer<>(Group::getName), Group::getName));
	//
	// formFactory.setButtonCaption(CrudOperation.ADD, "Add new user");
	// crud.setRowCountCaption("%d user(s) found");
	//
	// crud.setClickRowToUpdate(true);
	// crud.setUpdateOperationVisible(false);
	//
	//
	// nameFilter.setPlaceholder("filter by name...");
	// nameFilter.addValueChangeListener(e -> crud.refreshGrid());
	// crud.getCrudLayout().addFilterComponent(nameFilter);
	//
	// groupFilter.setPlaceholder("Group");
	// groupFilter.setItems(GroupRepository.findAll());
	// groupFilter.setItemLabelGenerator(Group::getName);
	// groupFilter.addValueChangeListener(e -> crud.refreshGrid());
	// crud.getCrudLayout().addFilterComponent(groupFilter);
	//
	// Button clearFilters = new Button(null, VaadinIcon.ERASER.create());
	// clearFilters.addClickListener(event -> {
	// nameFilter.clear();
	// groupFilter.clear();
	// });
	// crud.getCrudLayout().addFilterComponent(clearFilters);
	//
	// crud.setFindAllOperation(
	// DataProvider.fromCallbacks(
	// query -> UserRepository.findByNameLike(nameFilter.getValue(),
	// groupFilter.getValue(), query.getOffset(), query.getLimit()).stream(),
	// query -> UserRepository.countByNameLike(nameFilter.getValue(),
	// groupFilter.getValue()))
	// );
	// return crud;
	// }

}