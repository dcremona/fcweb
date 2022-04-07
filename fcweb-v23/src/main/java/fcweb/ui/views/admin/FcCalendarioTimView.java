package fcweb.ui.views.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.PostConstruct;

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
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import common.util.Utils;
import fcweb.backend.data.entity.FcCalendarioTim;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.job.JobProcessFileCsv;
import fcweb.backend.job.JobProcessGiornata;
import fcweb.backend.service.AccessoController;
import fcweb.backend.service.CalendarioTimController;
import fcweb.backend.service.GiornataInfoController;
import fcweb.ui.MainAppLayout;
import fcweb.utils.CustomMessageDialog;

@Route(value = "calelndarioTim", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("Calendario Tim")
public class FcCalendarioTimView extends VerticalLayout
		implements ComponentEventListener<ClickEvent<Button>>{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CalendarioTimController calendarioTimController;

	@Autowired
	public Environment env;

	private Button initDb;
	private Button updateGiornata;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private JobProcessGiornata jobProcessGiornata;

	@Autowired
	private GiornataInfoController giornataInfoController;

	private ComboBox<FcGiornataInfo> giornataInfoFilter = new ComboBox<FcGiornataInfo>();

	@Autowired
	private AccessoController accessoController;

	public FcCalendarioTimView() {
		LOG.info("FcCalendarioTimView()");
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

		FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
		FcGiornataInfo giornataInfo = (FcGiornataInfo) VaadinSession.getCurrent().getAttribute("GIORNATA_INFO");

		initDb = new Button("Init Db Calendario");
		initDb.setIcon(VaadinIcon.START_COG.create());
		initDb.addClickListener(this);

		updateGiornata = new Button("Aggiorna Giornata");
		updateGiornata.setIcon(VaadinIcon.START_COG.create());
		updateGiornata.addClickListener(this);

		GridCrud<FcCalendarioTim> crud = new GridCrud<>(FcCalendarioTim.class,new HorizontalSplitCrudLayout());

		DefaultCrudFormFactory<FcCalendarioTim> formFactory = new DefaultCrudFormFactory<>(FcCalendarioTim.class);
		crud.setCrudFormFactory(formFactory);
		formFactory.setUseBeanValidation(false);

		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "id", "idGiornata", "data", "idSquadraCasa", "squadraCasa", "idSquadraFuori", "squadraFuori", "risultato");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "id", "idGiornata", "data", "idSquadraCasa", "squadraCasa", "idSquadraFuori", "squadraFuori", "risultato");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "id", "idGiornata", "data", "idSquadraCasa", "squadraCasa", "idSquadraFuori", "squadraFuori", "risultato");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "id", "idGiornata");

		// crud.getGrid().setColumns("idGiornata", "data", "squadraCasa",
		// "squadraFuori");
		crud.getGrid().removeAllColumns();
		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null ? "" : "" + g.getIdGiornata()));
		Column<FcCalendarioTim> dataColumn = crud.getGrid().addColumn(new LocalDateTimeRenderer<>(FcCalendarioTim::getData,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).withLocale(Locale.ITALY)));
		dataColumn.setSortable(false);
		dataColumn.setAutoWidth(true);
		dataColumn.setFlexGrow(2);

		Column<FcCalendarioTim> sqCasaColumn = crud.getGrid().addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.setAlignItems(Alignment.STRETCH);
			if (s != null && s.getSquadraCasa() != null) {
				Image img = null;
				if ("1".equals(campionato.getType())) {
					img = buildImage("classpath:/img/squadre/", s.getSquadraCasa() + ".png");
				} else {
					img = buildImage("classpath:/img/nazioni/", s.getSquadraCasa() + ".png");
				}

				Label lblSquadra = new Label(s.getSquadraCasa());
				cellLayout.add(img);
				cellLayout.add(lblSquadra);
			}
			return cellLayout;
		}));
		sqCasaColumn.setSortable(false);
		sqCasaColumn.setAutoWidth(true);

		Column<FcCalendarioTim> sqFuoriColumn = crud.getGrid().addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.setAlignItems(Alignment.STRETCH);
			if (s != null && s.getSquadraFuori() != null) {
				Image img = null;
				if ("1".equals(campionato.getType())) {
					img = buildImage("classpath:/img/squadre/", s.getSquadraFuori() + ".png");
				} else {
					img = buildImage("classpath:/img/nazioni/", s.getSquadraFuori() + ".png");
				}
				Label lblSquadra = new Label(s.getSquadraFuori());

				cellLayout.add(img);
				cellLayout.add(lblSquadra);
			}
			return cellLayout;
		}));
		sqFuoriColumn.setSortable(false);
		sqFuoriColumn.setAutoWidth(true);

		crud.getGrid().setColumnReorderingAllowed(true);

		crud.getCrudFormFactory().setFieldProvider("data", a -> {
			DateTimePicker data = new DateTimePicker();
			return data;
		});

		crud.getGrid().addColumn(new TextRenderer<>(g -> g == null || g.getRisultato() == null ? "" : "" + g.getRisultato()));

		crud.setRowCountCaption("%d GiornataInfo(s) found");
		crud.setClickRowToUpdate(true);
		crud.setUpdateOperationVisible(true);

		giornataInfoFilter.setPlaceholder("Giornata");
		giornataInfoFilter.setItems(giornataInfoController.findAll());
		if ("1".equals(campionato.getType())) {
			giornataInfoFilter.setItemLabelGenerator(g -> Utils.buildInfoGiornata(g));
		} else {
			giornataInfoFilter.setItemLabelGenerator(g -> Utils.buildInfoGiornataEm(g, campionato));
		}

		giornataInfoFilter.addValueChangeListener(e -> crud.refreshGrid());
		giornataInfoFilter.setClearButtonVisible(true);
		giornataInfoFilter.setValue(giornataInfo);
		crud.getCrudLayout().addFilterComponent(giornataInfoFilter);

		Button clearFilters = new Button("clear");
		clearFilters.addClickListener(event -> {
			giornataInfoFilter.clear();
		});
		crud.getCrudLayout().addFilterComponent(clearFilters);

		crud.setFindAllOperation(() -> calendarioTimController.findCustom(giornataInfoFilter.getValue()));
		crud.setAddOperation(user -> calendarioTimController.updateCalendarioTim(user));
		crud.setUpdateOperation(user -> calendarioTimController.updateCalendarioTim(user));
		crud.setDeleteOperation(user -> calendarioTimController.deleteCalendarioTim(user));

		add(initDb);
		add(updateGiornata);
		add(crud);
	}

	@Override
	public void onComponentEvent(ClickEvent<Button> event) {

		try {
			Properties p = (Properties) VaadinSession.getCurrent().getAttribute("PROPERTIES");
			String basePathData = (String) p.get("PATH_TMP");
			LOG.info("basePathData " + basePathData);
			File f = new File(basePathData);
			if (!f.exists()) {
				CustomMessageDialog.showMessageErrorDetails(CustomMessageDialog.MSG_ERROR_GENERIC, "Impossibile trovare il percorso specificato "+basePathData);
				return;
			}

			if (event.getSource() == initDb) {

				FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");

				if ("1".equals(campionato.getType())) {

					jobProcessGiornata.deleteAllCalendarioTim();

					for (int g = 1; g <= 38; g++) {
						// **************************************
						// DOWNLOAD FILE TIM
						// **************************************
						String giornata = "" + g;
						String urlFanta = (String) p.get("URL_FANTA");
						String basePath = basePathData;
						String calendario = "Serie-A-Calendario";
						String httpUrl = urlFanta + calendario + ".asp?GiornataA=" + giornata + "&Tipolink=0";
						LOG.info("httpUrl " + httpUrl);
						String fileName = "TIM_" + giornata;
						JobProcessFileCsv jobCsv = new JobProcessFileCsv();
						jobCsv.downloadCsv(httpUrl, basePath, fileName, 0);

						fileName = basePathData + fileName + ".csv";
						jobProcessGiornata.insertCalendarioTim(fileName, g);

					}

				} else {
					jobProcessGiornata.initDbCalendarioTim(basePathData + "calendarioEuro2021.csv");
				}

			} else if (event.getSource() == updateGiornata) {

				// **************************************
				// DOWNLOAD FILE TIM
				// **************************************
				String giornata = "" + giornataInfoFilter.getValue().getCodiceGiornata();
				String urlFanta = (String) p.get("URL_FANTA");
				String basePath = basePathData;
				String quotaz = "Serie-A-Calendario";
				String httpUrl = urlFanta + quotaz + ".asp?GiornataA=" + giornata + "&Tipolink=0";
				// httpUrl
				// ="https://www.pianetafanta.it/Serie-A-Calendario.asp?GiornataA=5&Tipolink=0";
				LOG.info("httpUrl " + httpUrl);
				String fileName = "TIM_" + giornata;
				JobProcessFileCsv jobCsv = new JobProcessFileCsv();
				jobCsv.downloadCsv(httpUrl, basePath, fileName, 0);

				fileName = basePathData + fileName + ".csv";

				jobProcessGiornata.updateCalendarioTim(fileName, giornataInfoFilter.getValue().getCodiceGiornata());

			}
			CustomMessageDialog.showMessageInfo(CustomMessageDialog.MSG_OK);
		} catch (Exception e) {
			CustomMessageDialog.showMessageErrorDetails(CustomMessageDialog.MSG_ERROR_GENERIC, e.getMessage());
		}

	}

	private Image buildImage(String path, String nomeImg) {
		StreamResource resource = new StreamResource(nomeImg,() -> {
			Resource r = resourceLoader.getResource(path + nomeImg);
			InputStream inputStream = null;
			try {
				inputStream = r.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return inputStream;
		});

		Image img = new Image(resource,"");
		return img;
	}

}