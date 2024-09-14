package fcweb.ui.views.seriea;

import java.io.InputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.vaadin.olli.FileDownloadWrapper;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import common.util.Utils;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.data.entity.FcGiornata;
import fcweb.backend.service.AccessoService;
import fcweb.backend.service.GiornataService;
import fcweb.ui.MainAppLayout;
import fcweb.utils.Costants;
import fcweb.utils.JasperReporUtils;

@Route(value = "calendario", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("Calendario")
public class CalendarioView extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiornataService giornataController;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private AccessoService accessoController;
	
	private List<FcGiornata> model = new ArrayList<FcGiornata>();

	public CalendarioView() {
		LOG.info("CalendarioView()");
	}

	@PostConstruct
	void init() {
		LOG.info("init");
		if (!Utils.isValidVaadinSession()) {
			return;
		}
		FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
		FcAttore attore = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
		accessoController.insertAccesso(campionato,attore,this.getClass().getName());

		initData();
		initLayout();
	}

	private List<FcGiornata> initData() {

		FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");

		List<FcGiornata> model1 = new ArrayList<FcGiornata>();
		List<FcGiornata> model2 = new ArrayList<FcGiornata>();

		List<FcGiornata> all = giornataController.findAll();
		for (FcGiornata g : all) {
			int gg = g.getFcGiornataInfo().getCodiceGiornata();
			if (gg < 20) {
				model1.add(g);
			} else {
				model2.add(g);
			}
		}

		if (campionato.getIdCampionato() == 1) {
			model = model1;
		} else if (campionato.getIdCampionato() == 2) {
			model = model2;
		}

		return model;
	}

	private void initLayout() {

		FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");

		VerticalLayout gridPrimaFaseAndata = new VerticalLayout();
		gridPrimaFaseAndata.getStyle().set("border", Costants.BORDER_COLOR);
		gridPrimaFaseAndata.getStyle().set("background", Costants.GREEN);

		VerticalLayout gridPrimaFaseRitorno = new VerticalLayout();
		gridPrimaFaseRitorno.getStyle().set("border", Costants.BORDER_COLOR);
		gridPrimaFaseRitorno.getStyle().set("background", Costants.GREEN);

		VerticalLayout gridQuarti = new VerticalLayout();
		gridQuarti.getStyle().set("border", Costants.BORDER_COLOR);
		gridQuarti.getStyle().set("background", Costants.MISTYROSE);

		VerticalLayout gridSemi = new VerticalLayout();
		gridSemi.getStyle().set("border", Costants.BORDER_COLOR);
		gridSemi.getStyle().set("background", Costants.LIGHT_YELLOW);

		VerticalLayout gridFinali = new VerticalLayout();
		gridFinali.getStyle().set("border", Costants.BORDER_COLOR);
		gridFinali.getStyle().set("background", Costants.POWDERBLUE);

		List<FcGiornata> beanContainer = new ArrayList<FcGiornata>();
		int conta = 1;
		int partite = 4;
		for (int i = 0; i < model.size(); i++) {

			FcGiornata bean = model.get(i);
			beanContainer.add(bean);
			int gg = bean.getFcGiornataInfo().getIdGiornataFc();
			if (gg == 17 || gg == 18) {
				partite = 2;
			} else if (gg == 19) {
				partite = 1;
			}

			if (conta == partite) {

				String dataG = Utils.formatLocalDateTime(bean.getFcGiornataInfo().getDataGiornata(), "dd/MM/yyyy HH:mm");
				//String dataG = Utils.formatDate(bean.getFcGiornataInfo().getDataGiornata(), "dd/MM/yyyy HH:mm");
				
				String descG = bean.getFcGiornataInfo().getDescGiornataFc() + " - " + dataG;
				if (gg > 16) {
					descG = bean.getFcTipoGiornata().getDescTipoGiornata() + " - " + bean.getFcGiornataInfo().getDescGiornataFc() + " - " + dataG;
				}
				Grid<FcGiornata> tableGiornata = getTableCalendar(descG, beanContainer);

				final VerticalLayout layout = new VerticalLayout();
				Label lblInfoSx = new Label(descG);
				lblInfoSx.getStyle().set("font-size", "14px");
				layout.add(lblInfoSx);
				layout.add(tableGiornata);

				if (gg < 8) {
					gridPrimaFaseAndata.add(layout);
				} else if (gg > 7 && gg < 15) {
					gridPrimaFaseRitorno.add(layout);
				} else if (gg == 15 || gg == 16) {
					gridQuarti.add(layout);
				} else if (gg == 17 || gg == 18) {
					gridSemi.add(layout);
				} else if (gg == 19) {
					gridFinali.add(layout);
				}

				conta = 1;
				beanContainer = new ArrayList<FcGiornata>();

			} else {
				conta++;
			}
		}
		
		try {
			this.add(buildButtonCalendarioPdf(campionato));
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}

		Details paneldPrimaFaseAndata = new Details("Prima Fase Andata",gridPrimaFaseAndata);
		paneldPrimaFaseAndata.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
		paneldPrimaFaseAndata.setEnabled(true);
		paneldPrimaFaseAndata.setOpened(true);

		add(paneldPrimaFaseAndata);

		Details paneldPrimaFaseRitorno = new Details("Prima Fase Ritorno",gridPrimaFaseRitorno);
		paneldPrimaFaseRitorno.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
		paneldPrimaFaseRitorno.setEnabled(true);
		paneldPrimaFaseRitorno.setOpened(true);

		this.add(paneldPrimaFaseRitorno);

		if (gridQuarti.getComponentCount() > 0) {
			Details paneldQuarti = new Details("Quarti",gridQuarti);
			paneldQuarti.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
			paneldQuarti.setEnabled(true);
			paneldQuarti.setOpened(true);

			this.add(paneldQuarti);
		}

		if (gridSemi.getComponentCount() > 0) {
			Details paneldSemi = new Details("Semifinali",gridSemi);
			paneldSemi.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
			paneldSemi.setEnabled(true);
			paneldSemi.setOpened(true);

			this.add(paneldSemi);
		}

		if (gridFinali.getComponentCount() > 0) {
			Details paneldFinali = new Details("Finali",gridFinali);
			paneldFinali.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
			paneldFinali.setEnabled(true);
			paneldFinali.setOpened(true);

			this.add(paneldFinali);
		}
	}

	private HorizontalLayout buildButtonCalendarioPdf(FcCampionato campionato) {

//		LazyDownloadButton stampapdf = new LazyDownloadButton("Calendario pdf",() -> "Calendario.pdf",() -> {			
//			byte[] b = null;
//			try {
//				String START = campionato.getStart().toString();
//				String END = campionato.getEnd().toString();
//				Map<String, Object> hm = new HashMap<String, Object>();
//				hm.put("START", START);
//				hm.put("END", END);
//				Resource resource = resourceLoader.getResource("classpath:reports/calendario.jasper");
//				InputStream inputStream = resource.getInputStream();
//				Connection conn = jdbcTemplate.getDataSource().getConnection();
//				b = JasperRunManager.runReportToPdf(inputStream, hm, conn);
//			} catch (JRException ex) {
//				ex.printStackTrace();
//			} catch (Exception ex2) {
//				ex2.printStackTrace();
//			}
//			return new ByteArrayInputStream(b);
//		});
//		HorizontalLayout horLayout = new HorizontalLayout();
//		horLayout.setSpacing(true);
//		horLayout.add(stampapdf);

		Button stampapdf = new Button("Calendario pdf");
		stampapdf.setIcon(VaadinIcon.DOWNLOAD.create());
		FileDownloadWrapper button1Wrapper = new FileDownloadWrapper(new StreamResource("Calendario.pdf",() -> {
			try {
				String START = campionato.getStart().toString();
				String END = campionato.getEnd().toString();
				Map<String, Object> hm = new HashMap<String, Object>();
				hm.put("START", START);
				hm.put("END", END);
				Resource resource = resourceLoader.getResource("classpath:reports/calendario.jasper");
				InputStream inputStream = resource.getInputStream();
				Connection conn = jdbcTemplate.getDataSource().getConnection();
				return JasperReporUtils.runReportToPdf(inputStream, hm, conn);
			} catch (Exception ex2) {
			}
			return null;
		}));
		button1Wrapper.wrapComponent(stampapdf);

		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.setSpacing(true);
		horLayout.add(button1Wrapper);


		return horLayout;
	}

	private Grid<FcGiornata> getTableCalendar(String caption,
			List<FcGiornata> items) {

		Grid<FcGiornata> grid = new Grid<>();
		grid.setItems(items);
		grid.setSelectionMode(Grid.SelectionMode.NONE);
		grid.setAllRowsVisible(true);
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
		grid.setWidth("500px");

		Column<FcGiornata> attoreCasaColumn = grid.addColumn(giornata -> giornata.getFcAttoreByIdAttoreCasa().getDescAttore());
		attoreCasaColumn.setSortable(false);

		Column<FcGiornata> golColumn = grid.addColumn(giornata -> giornata.getGolCasa() != null ? giornata.getGolCasa() + " - " + giornata.getGolFuori() : "-");
		golColumn.setSortable(false);

		Column<FcGiornata> attoreFuoriColumn = grid.addColumn(giornata -> giornata.getFcAttoreByIdAttoreFuori().getDescAttore());
		attoreFuoriColumn.setSortable(false);

		Column<FcGiornata> punteggioColumn = grid.addColumn(giornata -> {
			DecimalFormat myFormatter = new DecimalFormat("#0.00");

			Double dTotCasa = giornata.getTotCasa() != null ? giornata.getTotCasa().doubleValue() / Costants.DIVISORE_100 : 0;
			String sTotCasa = myFormatter.format(dTotCasa);

			Double dTotFuori = giornata.getTotFuori() != null ? giornata.getTotFuori().doubleValue() / Costants.DIVISORE_100 : 0;
			String sTotFuori = myFormatter.format(dTotFuori);

			return sTotCasa + " - " + sTotFuori;
		});
		punteggioColumn.setSortable(false);

		return grid;

	}

}