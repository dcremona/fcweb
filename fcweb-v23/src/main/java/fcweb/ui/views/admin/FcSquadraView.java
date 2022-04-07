package fcweb.ui.views.admin;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import common.util.Utils;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.data.entity.FcSquadra;
import fcweb.backend.service.AccessoController;
import fcweb.backend.service.SquadraController;
import fcweb.ui.MainAppLayout;
import fcweb.utils.CustomMessageDialog;

@Route(value = "squadra", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("Squadre Tim")
public class FcSquadraView extends VerticalLayout
		implements ComponentEventListener<ClickEvent<Button>>{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SquadraController squadraController;

	@Autowired
	public Environment env;

	private Button initDb;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private AccessoController accessoController;

	public FcSquadraView() {
		LOG.info("FcSquadraView()");
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

		initDb = new Button("Update Img Squadre");
		initDb.setIcon(VaadinIcon.START_COG.create());
		initDb.addClickListener(this);

		this.setMargin(true);
		this.setSpacing(true);
		this.setSizeFull();

		GridCrud<FcSquadra> crud = new GridCrud<>(FcSquadra.class,new HorizontalSplitCrudLayout());

		DefaultCrudFormFactory<FcSquadra> formFactory = new DefaultCrudFormFactory<>(FcSquadra.class);
		crud.setCrudFormFactory(formFactory);
		formFactory.setUseBeanValidation(false);

		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "idSquadra", "nomeSquadra");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "idSquadra", "nomeSquadra");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "idSquadra", "nomeSquadra");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "idSquadra");

		// crud.getGrid().setColumns("idSquadra", "nomeSquadra");
		crud.getGrid().removeAllColumns();

		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : "" + g.getIdSquadra())).setHeader("Id");

		crud.getGrid().addColumn(new ComponentRenderer<>(f -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.setAlignItems(Alignment.STRETCH);
			// cellLayout.setSizeFull();

			if (f != null) {
				if (f.getImg() != null) {
					try {
						Image img = Utils.getImage(f.getNomeSquadra(), f.getImg().getBinaryStream());
						cellLayout.add(img);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				Label lblSquadra = new Label(f.getNomeSquadra());
				cellLayout.add(lblSquadra);
			}
			return cellLayout;
		}));

		crud.getGrid().setColumnReorderingAllowed(true);

		crud.setRowCountCaption("%d squadra(s) found");
		crud.setClickRowToUpdate(true);
		crud.setUpdateOperationVisible(true);

		crud.setFindAllOperation(() -> squadraController.findAll());
		crud.setAddOperation(p -> squadraController.updateSquadra(p));
		crud.setUpdateOperation(p -> squadraController.updateSquadra(p));
		crud.setDeleteOperation(p -> squadraController.deleteSquadra(p));

		add(initDb);
		add(crud);
	}

	@Override
	public void onComponentEvent(ClickEvent<Button> event) {

		FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");

		try {
			if (event.getSource() == initDb) {
				List<FcSquadra> squadreSerieA = squadraController.findAll();
				for (FcSquadra s : squadreSerieA) {
					Resource r = null;
					if ("1".equals(campionato.getType())) {
						r = resourceLoader.getResource("classpath:/img/squadre/" + s.getNomeSquadra() + ".png");
					} else {
						r = resourceLoader.getResource("classpath:/img/nazioni/" + s.getNomeSquadra() + ".png");
					}

					InputStream inputStream = null;
					inputStream = r.getInputStream();
					byte[] targetArray = IOUtils.toByteArray(inputStream);
					s.setImg(BlobProxy.generateProxy(targetArray));
					squadraController.updateSquadra(s);
				}
				CustomMessageDialog.showMessageInfo(CustomMessageDialog.MSG_OK);
			}
		} catch (Exception e) {
			CustomMessageDialog.showMessageErrorDetails(CustomMessageDialog.MSG_ERROR_GENERIC,e.getMessage());
		}
	}
}