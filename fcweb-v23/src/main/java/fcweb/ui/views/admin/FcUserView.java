package fcweb.ui.views.admin;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

import common.util.Utils;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.service.AccessoService;
import fcweb.backend.service.AttoreService;
import fcweb.ui.MainAppLayout;

@Route(value = "user", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("Utenti")
public class FcUserView extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AttoreService attoreController;

	@Autowired
	public Environment env;

	@Autowired
	private AccessoService accessoController;

	public FcUserView() {
		LOG.info("FcUserView()");
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

		// Crud<FcAttore> crud = new
		// Crud<>(FcAttore.class,createFcAttoreEditor());

		// crud.getGrid().removeColumnByKey("id");
		// crud.setDataProvider(attoreController.findAll());
		// crud.addSaveListener(e -> dataProvider.persist(e.getItem()));
		// crud.addDeleteListener(e -> dataProvider.delete(e.getItem()));

		// add(crud);

		GridCrud<FcAttore> crud = new GridCrud<>(FcAttore.class,new HorizontalSplitCrudLayout());

		DefaultCrudFormFactory<FcAttore> formFactory = new DefaultCrudFormFactory<>(FcAttore.class);
		crud.setCrudFormFactory(formFactory);
		formFactory.setUseBeanValidation(false);

		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "idAttore", "descAttore", "cognome", "nome", "cellulare","email", "username","password", "notifiche", "admin", "active","style");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "idAttore", "descAttore", "cognome", "nome", "cellulare", "email", "username","password", "notifiche", "admin", "active","style");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "descAttore", "cognome", "nome", "cellulare", "email", "username","password", "notifiche", "admin", "active","style");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "idAttore", "descAttore");

		crud.getGrid().setColumns("idAttore", "descAttore", "username","cognome", "nome", "email","cellulare","style");

		crud.getGrid().addColumn(new ComponentRenderer<>(user -> {
			Checkbox check = new Checkbox();
			check.setValue(user.isNotifiche());
			return check;
		})).setHeader("Notifiche");

		crud.getGrid().addColumn(new ComponentRenderer<>(user -> {
			Checkbox check = new Checkbox();
			check.setValue(user.isAdmin());
			return check;
		})).setHeader("Admin");

		crud.getGrid().addColumn(new ComponentRenderer<>(user -> {
			Checkbox check = new Checkbox();
			check.setValue(user.isActive());
			return check;
		})).setHeader("Attivo");

		crud.getCrudFormFactory().setFieldType("password", PasswordField.class);

		crud.getGrid().setColumnReorderingAllowed(true);

		crud.setRowCountCaption("%d user(s) found");
		crud.setClickRowToUpdate(true);
		crud.setUpdateOperationVisible(true);

		crud.setFindAllOperation(() -> attoreController.findAll());
		crud.setAddOperation(user -> attoreController.updateAttore(user));
		crud.setUpdateOperation(user -> attoreController.updateAttore(user));
		crud.setDeleteOperation(user -> attoreController.deleteAttore(user));

		add(crud);
	}

	// private CrudEditor<FcAttore> createFcAttoreEditor() {
	// TextField descAttore = new TextField("Desc Attore");
	// TextField cognome = new TextField("Cognome");
	// FormLayout form = new FormLayout(descAttore,cognome);
	//
	// Binder<FcAttore> binder = new Binder<>(FcAttore.class);
	// binder.bind(descAttore, FcAttore::getDescAttore,
	// FcAttore::setDescAttore);
	// binder.bind(cognome, FcAttore::getCognome, FcAttore::setCognome);
	//
	// return new BinderCrudEditor<>(binder,form);
	// }

}