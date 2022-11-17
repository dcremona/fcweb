package fcweb.ui.views.em;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.HeaderRow.HeaderCell;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import common.util.Utils;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.data.entity.FcClassificaTotPt;
import fcweb.backend.data.entity.FcGiornataDett;
import fcweb.backend.data.entity.FcGiornataDettInfo;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.data.entity.FcSquadra;
import fcweb.backend.service.AccessoService;
import fcweb.backend.service.AttoreService;
import fcweb.backend.service.ClassificaTotalePuntiService;
import fcweb.backend.service.GiornataDettService;
import fcweb.backend.service.GiornataDettInfoService;
import fcweb.backend.service.GiornataInfoService;
import fcweb.ui.MainAppLayout;
import fcweb.utils.Costants;;

@Route(value = "emformazioni", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("Formazioni")
public class EmFormazioniView extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	private Image iconAmm_ = null;
	private Image iconEsp_ = null;
	private Image iconAssist_ = null;
	private Image iconAutogol_ = null;
	private Image iconEntrato_ = null;
	private Image iconGolfatto_ = null;
	private Image iconGolsubito_ = null;
	private Image iconUscito_ = null;
	private Image iconRigoreSbagliato_ = null;
	private Image iconRigoreSegnato_ = null;
	private Image iconRigoreParato_ = null;
	private Image iconGolVittoria_ = null;

	@Autowired
	private GiornataInfoService giornataInfoController;

	@Autowired
	private GiornataDettService giornataDettController;

	@Autowired
	private GiornataDettInfoService giornataDettInfoController;

	@Autowired
	private ClassificaTotalePuntiService classificaTotalePuntiController;

	@Autowired
	private ResourceLoader resourceLoader;

	// @Autowired
	// private JobProcessSendMail jobProcessSendMail;

	private VerticalLayout mainLayout = new VerticalLayout();
	private ComboBox<FcGiornataInfo> comboGiornata;

	@Autowired
	private AttoreService attoreController;

	public List<FcAttore> squadre = new ArrayList<FcAttore>();

	@Autowired
	private AccessoService accessoController;

	public EmFormazioniView() throws Exception {
		LOG.info("EmFormazioniView()");
		initImg();
	}

	@PostConstruct
	void init() throws Exception {
		LOG.info("init");
		if (!Utils.isValidVaadinSession()) {
			return;
		}
		accessoController.insertAccesso(this.getClass().getName());

		initData();

		initLayout();
	}

	private FcGiornataInfo giornataInfo = null;
	private FcCampionato campionato = null;
	private List<FcGiornataInfo> giornate = null;

	private void initData() throws Exception {

		giornataInfo = (FcGiornataInfo) VaadinSession.getCurrent().getAttribute("GIORNATA_INFO");
		campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");

		Integer from = campionato.getStart();
		Integer to = campionato.getEnd();
		giornate = giornataInfoController.findByCodiceGiornataGreaterThanEqualAndCodiceGiornataLessThanEqual(from, to);

		squadre = attoreController.findAll();
	}

	private void initImg() throws Exception {

		LOG.info("initImg()");

		iconAmm_ = buildImage("classpath:images/", "amm.png", "Ammonizione (-0.5)");
		iconEsp_ = buildImage("classpath:images/", "esp.png", "Espulso (-1)");
		iconAssist_ = buildImage("classpath:images/", "assist.png", "Assist (+1)");
		iconAutogol_ = buildImage("classpath:images/", "autogol.png", "Autogol (-2)");
		iconEntrato_ = buildImage("classpath:images/", "entrato.png", "Entrato");
		iconGolfatto_ = buildImage("classpath:images/", "golfatto.png", "Gol Fatto (+3)");
		iconGolsubito_ = buildImage("classpath:images/", "golsubito.png", "Gol subito (-1)");
		iconUscito_ = buildImage("classpath:images/", "uscito.png", "Uscito");
		iconRigoreSbagliato_ = buildImage("classpath:images/", "rigoresbagliato.png", "Rigore sbagliato (-3)");
		iconRigoreSegnato_ = buildImage("classpath:images/", "rigoresegnato.png", "Rigore segnato (+3)");
		iconRigoreParato_ = buildImage("classpath:images/", "rigoreparato.png", "Rigore parato (+3)");
		iconGolVittoria_ = buildImage("classpath:images/", "golvittoria.png", "Bonus goal vittoria (+1)");

	}

	private void initLayout() {

		LOG.info("initLayout()");

		Button stampapdf = new Button("Risultati pdf");
		stampapdf.setIcon(VaadinIcon.DOWNLOAD.create());

		comboGiornata = new ComboBox<>();
		comboGiornata.setItemLabelGenerator(g -> Utils.buildInfoGiornataEm(g, campionato));
		comboGiornata.setItems(giornate);
		comboGiornata.setClearButtonVisible(true);
		comboGiornata.setPlaceholder("Seleziona la giornata");
		comboGiornata.addValueChangeListener(event -> {
			mainLayout.removeAll();
			stampapdf.setEnabled(false);
			if (event.getSource().isEmpty()) {
				LOG.info("event.getSource().isEmpty()");
			} else if (event.getOldValue() == null) {
				LOG.info("event.getOldValue()");
				FcGiornataInfo fcGiornataInfo = event.getValue();
				LOG.info("gioranta " + "" + fcGiornataInfo.getCodiceGiornata());
				buildTabGiornata(mainLayout, "" + fcGiornataInfo.getCodiceGiornata());
				stampapdf.setEnabled(true);
			} else {
				FcGiornataInfo fcGiornataInfo = event.getValue();
				LOG.info("gioranta " + "" + fcGiornataInfo.getCodiceGiornata());
				buildTabGiornata(mainLayout, "" + fcGiornataInfo.getCodiceGiornata());
				stampapdf.setEnabled(true);
			}
		});
		comboGiornata.setWidthFull();

		add(comboGiornata);

		add(mainLayout);

		add(buildLegenda());

		comboGiornata.setValue(giornataInfo);

	}

	@SuppressWarnings("unchecked")
	private void buildTabGiornata(VerticalLayout layout, String giornata) {

		Integer currGG = Integer.valueOf(giornata);
		FcGiornataInfo giornataInfo = giornataInfoController.findByCodiceGiornata(currGG);

		Accordion accordion = new Accordion();
		accordion.setSizeFull();
		for (FcAttore a : squadre) {

			VerticalLayout vCasa = new VerticalLayout();
			HashMap<String, Object> mapCasa;
			try {
				mapCasa = buildData(a, giornataInfo);

				List<FcGiornataDett> itemsCasaTitolari = (List<FcGiornataDett>) mapCasa.get("itemsTitolari");
				List<FcGiornataDett> itemsCasaPanchina = (List<FcGiornataDett>) mapCasa.get("itemsPanchina");
				String schemaCasa = (String) mapCasa.get("schema");

				Grid<FcGiornataDett> tableSqCasaTitolari = buildResultSquadra(itemsCasaTitolari, "Titolari", schemaCasa);
				Grid<FcGiornataDett> tableSqCasaPanchina = buildResultSquadra(itemsCasaPanchina, "Panchina", "");

				vCasa.add(tableSqCasaTitolari);
				vCasa.add(tableSqCasaPanchina);

			} catch (Exception e) {
				LOG.info("NO DATA " + a.getDescAttore());
			}
			VerticalLayout layoutTotaliCasa = buildTotaliInfo(campionato, a, giornataInfo);
			vCasa.add(layoutTotaliCasa);
			vCasa.setSizeFull();

			accordion.add(a.getDescAttore(), vCasa);
		}

		layout.add(accordion);
		layout.setSizeFull();
	}

	private HashMap<String, Object> buildData(FcAttore attore,
			FcGiornataInfo giornataInfo) throws Exception {

		LOG.info("START buildData " + attore.getDescAttore());

		HashMap<String, Object> map = new HashMap<>();

		List<FcGiornataDett> all = giornataDettController.findByFcAttoreAndFcGiornataInfoOrderByOrdinamentoAsc(attore, giornataInfo);
		List<FcGiornataDett> items = new ArrayList<FcGiornataDett>();

		int countD = 0;
		int countC = 0;
		int countA = 0;
		for (FcGiornataDett gd : all) {
			items.add(gd);
			if (gd.getOrdinamento() < 12) {
				if (gd.getFcGiocatore().getFcRuolo().getIdRuolo().equals("D")) {
					countD++;
				} else if (gd.getFcGiocatore().getFcRuolo().getIdRuolo().equals("C")) {
					countC++;
				} else if (gd.getFcGiocatore().getFcRuolo().getIdRuolo().equals("A")) {
					countA++;
				}
			}
		}

		String schema = countD + "-" + countC + "-" + countA;

		List<FcGiornataDett> itemsTitolari = new ArrayList<FcGiornataDett>();
		List<FcGiornataDett> itemsPanchina = new ArrayList<FcGiornataDett>();
		for (FcGiornataDett gd2 : items) {
			if (gd2.getOrdinamento() < 12) {
				itemsTitolari.add(gd2);
			} else if (gd2.getOrdinamento() > 11) {
				itemsPanchina.add(gd2);
			}
		}

		map.put("items", items);
		map.put("itemsTitolari", itemsTitolari);
		map.put("itemsPanchina", itemsPanchina);
		map.put("schema", schema);

		LOG.info("END buildData " + attore.getDescAttore());

		return map;
	}

	private Grid<FcGiornataDett> buildResultSquadra(List<FcGiornataDett> items,
			String statoGiocatore, String schema) {

		Grid<FcGiornataDett> grid = new Grid<>();
		grid.setItems(items);
		grid.setAllRowsVisible(true);
		grid.setSelectionMode(Grid.SelectionMode.NONE);

		Column<FcGiornataDett> ruoloColumn = grid.addColumn(new ComponentRenderer<>(f -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.setAlignItems(Alignment.STRETCH);
			cellLayout.setSizeFull();
			if (f != null && f.getFcGiocatore() != null) {
				Image img = buildImage("classpath:images/", f.getFcGiocatore().getFcRuolo().getIdRuolo().toLowerCase() + ".png", f.getFcGiocatore().getFcRuolo().getDescRuolo());

				cellLayout.add(img);
			}
			return cellLayout;
		}));
		ruoloColumn.setSortable(false);
		ruoloColumn.setResizable(false);
		ruoloColumn.setHeader("");
		ruoloColumn.setAutoWidth(true);

		Column<FcGiornataDett> cognGiocatoreColumn = grid.addColumn(new ComponentRenderer<>(gd -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if ("S".equals(gd.getFlagAttivo())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			} else if ("N".equals(gd.getFlagAttivo())) {
				cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			}
			if (gd != null && gd.getFcGiocatore() != null) {
				Label lblGiocatore = new Label(gd.getFcGiocatore().getCognGiocatore());
				lblGiocatore.getStyle().set("fontSize", "smaller");
				cellLayout.add(lblGiocatore);

				ArrayList<Image> info = new ArrayList<Image>();
				if (gd.getOrdinamento() < 12 && StringUtils.isNotEmpty(gd.getFlagAttivo()) && "N".equals(gd.getFlagAttivo().toUpperCase())) {
					info.add(buildImage("classpath:images/", "uscito_s.png", "Uscito"));
				}

				if (gd.getOrdinamento() > 11 && StringUtils.isNotEmpty(gd.getFlagAttivo()) && "S".equals(gd.getFlagAttivo().toUpperCase())) {
					info.add(buildImage("classpath:images/", "entrato_s.png", "Entrato"));
				}

				if (info.size() > 0) {
					for (Image e : info) {
						cellLayout.add(e);
					}
				}

			}
			return cellLayout;
		}));
		cognGiocatoreColumn.setSortable(false);
		cognGiocatoreColumn.setResizable(false);
		cognGiocatoreColumn.setHeader("");
		cognGiocatoreColumn.setAutoWidth(true);

		Column<FcGiornataDett> nomeSquadraColumn = grid.addColumn(new ComponentRenderer<>(gd -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if ("S".equals(gd.getFlagAttivo())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			} else if ("N".equals(gd.getFlagAttivo())) {
				cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			}
			if (gd != null && gd.getFcGiocatore() != null) {
				Label lblSquadra = new Label(gd.getFcGiocatore().getFcSquadra().getNomeSquadra().substring(0, 3));
				lblSquadra.getStyle().set("fontSize", "smaller");
				FcSquadra sq = gd.getFcGiocatore().getFcSquadra();
				if (sq.getImg() != null) {
					try {
						Image img = Utils.getImage(sq.getNomeSquadra(), sq.getImg().getBinaryStream());
						cellLayout.add(img);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				// Image img = buildImage("classpath:/img/nazioni/",
				// gd.getFcGiocatore().getFcSquadra().getNomeSquadra() +
				// ".png");
				// cellLayout.add(img);
				cellLayout.add(lblSquadra);
			}
			return cellLayout;
		}));
		nomeSquadraColumn.setSortable(false);
		nomeSquadraColumn.setResizable(false);
		nomeSquadraColumn.setHeader("");
		nomeSquadraColumn.setAutoWidth(true);

		Column<FcGiornataDett> resultGiocatoreColumn = grid.addColumn(new ComponentRenderer<>(gd -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if ("S".equals(gd.getFlagAttivo())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			} else if ("N".equals(gd.getFlagAttivo())) {
				cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			}

			if (gd != null && gd.getFcGiocatore() != null) {

				ArrayList<Image> info = new ArrayList<Image>();

				for (int a = 0; a < gd.getFcPagelle().getAmmonizione(); a++) {
					info.add(buildImage("classpath:images/", "amm_s.png", "Ammonizione (-0,5)"));
				}

				for (int a = 0; a < gd.getFcPagelle().getEspulsione(); a++) {
					info.add(buildImage("classpath:images/", "esp_s.png", "Espulsione (-1)"));
				}

				for (int a = 0; a < gd.getFcPagelle().getGoalSubito(); a++) {
					info.add(buildImage("classpath:images/", "golsubito_s.png", "Gol subito (-1)"));
				}

				for (int a = 0; a < gd.getFcPagelle().getGoalRealizzato() - gd.getFcPagelle().getRigoreSegnato(); a++) {
					info.add(buildImage("classpath:images/", "golfatto_s.png", "Gol fatto (+3)"));
				}

				for (int a = 0; a < gd.getFcPagelle().getAutorete(); a++) {
					info.add(buildImage("classpath:images/", "autogol_s.png", "Autogol (-2)"));
				}

				for (int a = 0; a < gd.getFcPagelle().getRigoreFallito(); a++) {
					info.add(buildImage("classpath:images/", "rigoresbagliato_s.png", "Rigore sbagliato (-3)"));
				}

				for (int a = 0; a < gd.getFcPagelle().getRigoreSegnato(); a++) {
					info.add(buildImage("classpath:images/", "rigoresegnato_s.png", "Rigore segnato (+3)"));
				}

				for (int a = 0; a < gd.getFcPagelle().getRigoreParato(); a++) {
					info.add(buildImage("classpath:images/", "rigoreparato_s.png", "Rigore parato (+3)"));
				}

				for (int a = 0; a < gd.getFcPagelle().getAssist(); a++) {
					info.add(buildImage("classpath:images/", "assist_s.png", "Assist (+1)"));
				}

				for (int a = 0; a < gd.getFcPagelle().getGdv(); a++) {
					info.add(buildImage("classpath:images/", "golvittoria_s.png", "Bonus goal vittoria (+1)"));
				}

				if (info.size() > 0) {
					for (Image e : info) {
						cellLayout.add(e);
					}
				}
			}

			return cellLayout;

		}));
		resultGiocatoreColumn.setSortable(false);
		resultGiocatoreColumn.setResizable(false);
		resultGiocatoreColumn.setHeader("");
		resultGiocatoreColumn.setAutoWidth(true);

		Column<FcGiornataDett> votoColumn = grid.addColumn(new ComponentRenderer<>(gd -> {
			DecimalFormat myFormatter = new DecimalFormat("#0.00");
			Double d = Double.valueOf(0);
			if (gd.getVoto() != null) {
				d = gd.getVoto() / Costants.DIVISORE_10;
			}
			String sVoto = myFormatter.format(d);

			Label lbl = new Label(sVoto);
			lbl.getStyle().set("color", Costants.LIGHT_GRAY);
			if ("S".equals(gd.getFlagAttivo())) {
				lbl.getStyle().set("color", Costants.GRAY);
			} else if ("N".equals(gd.getFlagAttivo())) {
				lbl.getStyle().set("color", Costants.LIGHT_GRAY);
			}
			lbl.getStyle().set("fontSize", "smaller");
			return lbl;

		}));
		votoColumn.setSortable(false);
		votoColumn.setResizable(false);
		votoColumn.setHeader("FV");
		votoColumn.setAutoWidth(true);

		// Column<FcGiornataDett> cognGiocatoreColumn = grid.addColumn(new
		// ComponentRenderer<>(gd -> {
		//
		// HorizontalLayout cellLayout = new HorizontalLayout();
		// cellLayout.setMargin(false);
		// cellLayout.setPadding(false);
		// cellLayout.setSpacing(false);
		//
		// cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
		// if ("S".equals(gd.getFlagAttivo())) {
		// cellLayout.getStyle().set("color", Costants.GRAY);
		// } else if ("N".equals(gd.getFlagAttivo())) {
		// cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
		// }
		//
		// if (gd != null && gd.getFcGiocatore() != null) {
		//
		// Label lblGiocatore = new
		// Label(gd.getFcGiocatore().getCognGiocatore());
		// lblGiocatore.getStyle().set("fontSize", "smaller");
		// cellLayout.add(lblGiocatore);
		//
		// ArrayList<Image> info = new ArrayList<Image>();
		//
		// if (gd.getOrdinamento() < 12 &&
		// StringUtils.isNotEmpty(gd.getFlagAttivo()) &&
		// "N".equals(gd.getFlagAttivo().toUpperCase())) {
		// info.add(buildImage("classpath:images/", "uscito_s.png", "Uscito"));
		// }
		//
		// if (gd.getOrdinamento() > 11 &&
		// StringUtils.isNotEmpty(gd.getFlagAttivo()) &&
		// "S".equals(gd.getFlagAttivo().toUpperCase())) {
		// info.add(buildImage("classpath:images/", "entrato_s.png",
		// "Entrato"));
		// }
		//
		// for (int a = 0; a < gd.getFcPagelle().getAmmonizione(); a++) {
		// info.add(buildImage("classpath:images/", "amm_s.png", "Ammonizione
		// (-0,5)"));
		// }
		//
		// for (int a = 0; a < gd.getFcPagelle().getEspulsione(); a++) {
		// info.add(buildImage("classpath:images/", "esp_s.png", "Espulsione
		// (-1)"));
		// }
		//
		// for (int a = 0; a < gd.getFcPagelle().getGoalSubito(); a++) {
		// info.add(buildImage("classpath:images/", "golsubito_s.png", "Gol
		// subito (-1)"));
		// }
		//
		// for (int a = 0; a < gd.getFcPagelle().getGoalRealizzato() -
		// gd.getFcPagelle().getRigoreSegnato(); a++) {
		// info.add(buildImage("classpath:images/", "golfatto_s.png", "Gol fatto
		// (+3)"));
		// }
		//
		// for (int a = 0; a < gd.getFcPagelle().getAutorete(); a++) {
		// info.add(buildImage("classpath:images/", "autogol_s.png", "Autogol
		// (-2)"));
		// }
		//
		// for (int a = 0; a < gd.getFcPagelle().getRigoreFallito(); a++) {
		// info.add(buildImage("classpath:images/", "rigoresbagliato_s.png",
		// "Rigore sbagliato (-3)"));
		// }
		//
		// for (int a = 0; a < gd.getFcPagelle().getRigoreSegnato(); a++) {
		// info.add(buildImage("classpath:images/", "rigoresegnato_s.png",
		// "Rigore segnato (+3)"));
		// }
		//
		// for (int a = 0; a < gd.getFcPagelle().getRigoreParato(); a++) {
		// info.add(buildImage("classpath:images/", "rigoreparato_s.png",
		// "Rigore parato (+3)"));
		// }
		//
		// for (int a = 0; a < gd.getFcPagelle().getAssist(); a++) {
		// info.add(buildImage("classpath:images/", "assist_s.png", "Assist
		// (+1)"));
		// }
		//
		// for (int a = 0; a < gd.getFcPagelle().getGdv(); a++) {
		// info.add(buildImage("classpath:images/", "golvittoria_s.png", "Bonus
		// goal vittoria (+1)"));
		// }
		//
		// if (info.size() > 0) {
		// for (Image e : info) {
		// cellLayout.add(e);
		// }
		// }
		// }
		//
		// return cellLayout;
		//
		// }));
		// cognGiocatoreColumn.setSortable(false);
		// cognGiocatoreColumn.setResizable(false);
		// cognGiocatoreColumn.setHeader("Giocatore");
		// // cognGiocatoreColumn.setFlexGrow(0);
		// cognGiocatoreColumn.setWidth("240px");

		HeaderRow headerRow = grid.prependHeaderRow();

		HeaderCell headerCellStatoGiocatore = headerRow.join(ruoloColumn, cognGiocatoreColumn);
		headerCellStatoGiocatore.setText(statoGiocatore);

		HeaderCell headerCellModulo = headerRow.join(resultGiocatoreColumn, votoColumn);
		if (statoGiocatore.equals("Titolari")) {
			headerCellModulo.setText("Modulo: " + schema);
		}

		return grid;
	}

	private VerticalLayout buildTotaliInfo(FcCampionato campionato,
			FcAttore attore, FcGiornataInfo giornataInfo) {

		VerticalLayout layoutMain = new VerticalLayout();
		layoutMain.setWidth("80%");

		FcGiornataDettInfo info = giornataDettInfoController.findByFcAttoreAndFcGiornataInfo(attore, giornataInfo);
		FcClassificaTotPt totPunti = classificaTotalePuntiController.findByFcCampionatoAndFcAttoreAndFcGiornataInfo(campionato, attore, giornataInfo);

		NumberFormat formatter = new DecimalFormat("#0.00");
		String totG = "";
		if (totPunti != null && totPunti.getTotPt() != null) {
			totG = formatter.format(totPunti.getTotPt().doubleValue() / Costants.DIVISORE_10);
		}

		Label lblTotGiornata = new Label();
		lblTotGiornata.setText("Totale Giornata: " + totG);
		lblTotGiornata.getStyle().set("font-size", "24px");
		lblTotGiornata.getStyle().set("background", Costants.LIGHT_BLUE);
		lblTotGiornata.setSizeFull();

		Label lblInvio = new Label();
		lblInvio.setText("Inviata alle: " + (info == null ? "" : Utils.formatDate(info.getDataInvio(), "dd/MM/yyyy HH:mm:ss")));
		lblInvio.setSizeFull();

		layoutMain.add(lblTotGiornata);
		layoutMain.add(lblInvio);

		return layoutMain;
	}

	private VerticalLayout buildLegenda() {

		VerticalLayout layout = new VerticalLayout();
		layout.getStyle().set("border", Costants.BORDER_COLOR);
		// layout.setSizeFull();
		layout.setMargin(false);

		HorizontalLayout horizontalLayout1 = new HorizontalLayout();
		horizontalLayout1.setSpacing(true);

		horizontalLayout1.add(iconGolfatto_);
		Label lbl = new Label("Gol Fatto");
		horizontalLayout1.add(lbl);

		horizontalLayout1.add(iconGolsubito_);
		lbl = new Label("Gol Subito");
		horizontalLayout1.add(lbl);

		horizontalLayout1.add(iconAmm_);
		lbl = new Label("Ammonizione");
		horizontalLayout1.add(lbl);

		horizontalLayout1.add(iconEsp_);
		lbl = new Label("Espulsione");
		horizontalLayout1.add(lbl);

		horizontalLayout1.add(iconAssist_);
		lbl = new Label("Assist");
		horizontalLayout1.add(lbl);

		horizontalLayout1.add(iconEntrato_);
		lbl = new Label("Entrato");
		horizontalLayout1.add(lbl);

		HorizontalLayout horizontalLayout2 = new HorizontalLayout();
		horizontalLayout2.setSpacing(true);

		horizontalLayout2.add(iconUscito_);
		lbl = new Label("Uscito");
		horizontalLayout2.add(lbl);

		horizontalLayout2.add(iconAutogol_);
		lbl = new Label("Autogol");
		horizontalLayout2.add(lbl);

		horizontalLayout2.add(iconRigoreSegnato_);
		lbl = new Label("Rigore segnato");
		horizontalLayout2.add(lbl);

		horizontalLayout2.add(iconRigoreSbagliato_);
		lbl = new Label("Rigore sbagliato");
		horizontalLayout2.add(lbl);

		horizontalLayout2.add(iconRigoreParato_);
		lbl = new Label("Rigore parato");
		horizontalLayout2.add(lbl);

		horizontalLayout2.add(iconGolVittoria_);
		lbl = new Label("Gol Vittoria");
		horizontalLayout2.add(lbl);

		layout.add(horizontalLayout1);
		layout.add(horizontalLayout2);

		return layout;

	}

	private Image buildImage(String path, String nomeImg, String title) {
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
		img.setTitle(title);
		return img;
	}

}