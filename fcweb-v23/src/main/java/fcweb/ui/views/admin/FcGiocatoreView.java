package fcweb.ui.views.admin;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import common.util.Utils;
import fcweb.backend.data.entity.FcGiocatore;
import fcweb.backend.data.entity.FcRuolo;
import fcweb.backend.data.entity.FcSquadra;
import fcweb.backend.service.AccessoController;
import fcweb.backend.service.GiocatoreController;
import fcweb.backend.service.RuoloController;
import fcweb.backend.service.SquadraController;
import fcweb.ui.MainAppLayout;
import fcweb.utils.Costants;
import fcweb.utils.CustomMessageDialog;

@Route(value = "giocatore", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("Giocatore")
public class FcGiocatoreView extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiocatoreController giocatoreController;

	@Autowired
	private SquadraController squadraController;

	@Autowired
	private RuoloController ruoloController;

	@Autowired
	public Environment env;

	@Autowired
	private AccessoController accessoController;

	private ComboBox<FcRuolo> ruoloFilter = new ComboBox<FcRuolo>();
	private ComboBox<FcSquadra> squadraFilter = new ComboBox<FcSquadra>();

	public FcGiocatoreView() {
		LOG.info("FcGiocatoreView()");
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

		GridCrud<FcGiocatore> crud = new GridCrud<>(FcGiocatore.class,new HorizontalSplitCrudLayout());

		DefaultCrudFormFactory<FcGiocatore> formFactory = new DefaultCrudFormFactory<>(FcGiocatore.class);
		crud.setCrudFormFactory(formFactory);
		formFactory.setUseBeanValidation(false);

		// formFactory.setVisibleProperties("idGiocatore", "cognGiocatore",
		// "quotazione", "flagAttivo", "fcSquadra", "fcRuolo");

		formFactory.setVisibleProperties(CrudOperation.READ, "idGiocatore", "cognGiocatore", "quotazione", "nomeImg", "fcSquadra", "fcRuolo", "flagAttivo");
		formFactory.setVisibleProperties(CrudOperation.ADD, "idGiocatore", "cognGiocatore", "nomeImg", "fcSquadra", "fcRuolo", "flagAttivo");
		formFactory.setVisibleProperties(CrudOperation.UPDATE, "cognGiocatore", "quotazione", "nomeImg", "fcSquadra", "fcRuolo", "flagAttivo");
		formFactory.setVisibleProperties(CrudOperation.DELETE, "idGiocatore", "cognGiocatore");

		// crud.getGrid().setColumns("idGiocatore", "cognGiocatore",
		// "quotazione", "nomeImg");
		crud.getGrid().removeAllColumns();
		Column<FcGiocatore> giocatreColumn = crud.getGrid().addColumn(new ComponentRenderer<>(g -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setSizeFull();
			if (g != null && g.getNomeImg() != null) {
				StreamResource resource = new StreamResource(g.getNomeImg(),() -> {
					InputStream inputStream = null;
					try {
						inputStream = g.getImg().getBinaryStream();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return inputStream;
				});
				Image img = new Image(resource,"");
				img.setSrc(resource);
				cellLayout.add(img);

				Image imgOnline = new Image(Costants.HTTP_URL_IMG + g.getNomeImg(),g.getNomeImg());
				cellLayout.add(imgOnline);

				Button updateImg = new Button("Salva");
				updateImg.setIcon(VaadinIcon.DATABASE.create());
				updateImg.addClickListener(event -> {
					try {
						Properties p = (Properties) VaadinSession.getCurrent().getAttribute("PROPERTIES");
						String basePathData = (String) p.get("PATH_TMP");
						LOG.info("basePathData " + basePathData);
						
						File f = new File(basePathData);
						if (!f.exists()) {
							CustomMessageDialog.showMessageErrorDetails(CustomMessageDialog.MSG_ERROR_GENERIC, "Impossibile trovare il percorso specificato "+basePathData);
							return;
						}

						String newImg = g.getNomeImg();
						LOG.info("newImg " + newImg);
						LOG.info("httpUrlImg " + Costants.HTTP_URL_IMG);
						String imgPath = basePathData;

						boolean flag = Utils.downloadFile(Costants.HTTP_URL_IMG + newImg, imgPath + newImg);
						LOG.info("bResult 1 " + flag);
						flag = Utils.buildFileSmall(imgPath + newImg, imgPath + "small-" + newImg);
						LOG.info("bResult 2 " + flag);

						g.setImg(BlobProxy.generateProxy(Utils.getImage(imgPath + newImg)));
						g.setImgSmall(BlobProxy.generateProxy(Utils.getImage(imgPath + "small-" + newImg)));

						LOG.info("SAVE GIOCATORE ");
						giocatoreController.updateGiocatore(g);

						CustomMessageDialog.showMessageInfo(CustomMessageDialog.MSG_OK);
					} catch (Exception e) {
						CustomMessageDialog.showMessageErrorDetails(CustomMessageDialog.MSG_ERROR_GENERIC,e.getMessage());
					}
				});

				cellLayout.add(updateImg);
			}
			return cellLayout;
		}));
		giocatreColumn.setWidth("350px");

		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : "" + g.getIdGiocatore())).setHeader("Id");
		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : g.getFcRuolo().getIdRuolo())).setHeader("Ruolo");
		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : "" + g.getCognGiocatore())).setHeader("Giocatore");
		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : g.getFcSquadra().getNomeSquadra())).setHeader("Squadra");
		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : "" + g.getQuotazione())).setHeader("Quotazione");
		// crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : ""
		// + g.getNomeImg())).setHeader("Nome Img");
		// crud.getGrid().addColumn(new ComponentRenderer<>(g -> {
		// Checkbox check = new Checkbox();
		// check.setValue(g.isFlagAttivo());
		// return check;
		// })).setHeader("Attivo");

		crud.getGrid().setColumnReorderingAllowed(true);

		formFactory.setFieldProvider("fcSquadra", new ComboBoxProvider<>("fcSquadra",squadraController.findAll(),new TextRenderer<>(FcSquadra::getNomeSquadra),FcSquadra::getNomeSquadra));
		formFactory.setFieldProvider("fcRuolo", new ComboBoxProvider<>("fcRuolo",ruoloController.findAll(),new TextRenderer<>(FcRuolo::getDescRuolo),FcRuolo::getDescRuolo));

		crud.setRowCountCaption("%d Giocatore(s) found");
		crud.setClickRowToUpdate(true);
		crud.setUpdateOperationVisible(true);

		ruoloFilter.setPlaceholder("Ruolo");
		ruoloFilter.setItems(ruoloController.findAll());
		ruoloFilter.setItemLabelGenerator(FcRuolo::getIdRuolo);
		ruoloFilter.setClearButtonVisible(true);
		ruoloFilter.addValueChangeListener(e -> crud.refreshGrid());
		crud.getCrudLayout().addFilterComponent(ruoloFilter);

		squadraFilter.setPlaceholder("Squadra");
		squadraFilter.setItems(squadraController.findAll());
		squadraFilter.setItemLabelGenerator(FcSquadra::getNomeSquadra);
		squadraFilter.setClearButtonVisible(true);
		squadraFilter.addValueChangeListener(e -> crud.refreshGrid());
		crud.getCrudLayout().addFilterComponent(squadraFilter);

		// flagAttivoFilter.setPlaceholder("filter by flag...");
		// flagAttivoFilter.addValueChangeListener(e -> crud.refreshGrid());
		// crud.getCrudLayout().addFilterComponent(flagAttivoFilter);

		Button clearFilters = new Button("clear");
		clearFilters.addClickListener(event -> {
			ruoloFilter.clear();
			squadraFilter.clear();
		});
		crud.getCrudLayout().addFilterComponent(clearFilters);

		crud.setFindAllOperation(() -> giocatoreController.findByFcRuoloAndFcSquadraOrderByQuotazioneDesc(ruoloFilter.getValue(), squadraFilter.getValue()));
		crud.setAddOperation(g -> giocatoreController.updateGiocatore(g));
		crud.setUpdateOperation(g -> giocatoreController.updateGiocatore(g));
		crud.setDeleteOperation(g -> giocatoreController.deleteGiocatore(g));

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