package fcweb.ui.views.em;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.vaadin.ronny.AbsoluteLayout;

import com.flowingcode.vaadin.addons.simpletimer.SimpleTimer;
import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import common.mail.ContentIdGenerator;
import common.mail.MailClient;
import common.util.Utils;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcCalendarioCompetizione;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.data.entity.FcFormazione;
import fcweb.backend.data.entity.FcGiocatore;
import fcweb.backend.data.entity.FcGiornataDett;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.data.entity.FcSquadra;
import fcweb.backend.data.entity.FcStatistiche;
import fcweb.backend.service.AccessoService;
import fcweb.backend.service.AttoreService;
import fcweb.backend.service.CalendarioCompetizioneService;
import fcweb.backend.service.FormazioneService;
import fcweb.backend.service.GiornataDettService;
import fcweb.backend.service.SquadraService;
import fcweb.ui.MainAppLayout;
import fcweb.utils.Costants;
import fcweb.utils.CustomMessageDialog;

@Route(value = "eminsert", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("Schiera Formazione")
public class EmTeamInsertView extends VerticalLayout
		implements ComponentEventListener<ClickEvent<Button>>{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment env;

	@Autowired
	private JavaMailSenderImpl javaMailSender;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String width = "100px";
	private static final String height = "120px";

	private static final int _P = 210;
	private static final int _D = 360;
	private static final int _C = 510;
	private static final int _A = 660;

	private static final int _350px = 350;
	private static final int _400px = 400;
	private static final int _450px = 450;
	private static final int _500px = 500;
	private static final int _550px = 550;
	private static final int _600px = 600;
	private static final int _650px = 650;
	private static final int _700px = 700;
	private static final int _750px = 750;
	private static final int _860px = 860;
	private static final int _960px = 960;
	private static final int _1060px = 1060;
	private static final int _1200px = 1200;

	private FcAttore attore = null;
	private FcGiornataInfo giornataInfo = null;
	private FcCampionato campionato = null;
	private String nextDate = null;
	private long millisDiff = 0;
	private String idAttore = "";
	private String descAttore = "";
	private Properties p = null;

	private Button save;

	private ToggleButton checkMail;
	private ComboBox<String> comboModulo;

	private Grid<FcGiocatore> tableFormazione;
	private Grid<FcGiocatore> tablePlayer1;
	private Grid<FcGiocatore> tablePlayer2;
	private Grid<FcGiocatore> tablePlayer3;
	private Grid<FcGiocatore> tablePlayer4;
	private Grid<FcGiocatore> tablePlayer5;
	private Grid<FcGiocatore> tablePlayer6;
	private Grid<FcGiocatore> tablePlayer7;
	private Grid<FcGiocatore> tablePlayer8;
	private Grid<FcGiocatore> tablePlayer9;
	private Grid<FcGiocatore> tablePlayer10;
	private Grid<FcGiocatore> tablePlayer11;
	private Grid<FcGiocatore> tablePlayer12;
	private Grid<FcGiocatore> tablePlayer13;
	private Grid<FcGiocatore> tablePlayer14;
	private Grid<FcGiocatore> tablePlayer15;
	private Grid<FcGiocatore> tablePlayer16;
	private Grid<FcGiocatore> tablePlayer17;
	private Grid<FcGiocatore> tablePlayer18;
	private Grid<FcGiocatore> tablePlayer19;
	private Grid<FcGiocatore> tablePlayer20;
	private Grid<FcGiocatore> tablePlayer21;
	private Grid<FcGiocatore> tablePlayer22;
	private Grid<FcGiocatore> tablePlayer23;

	// DATA
	private static final String[] schemi = new String[] { "5-4-1", "5-3-2", "4-5-1", "4-4-2", "4-3-3", "3-5-2", "3-4-3" };
	private List<FcGiocatore> modelFormazione = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer1 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer2 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer3 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer4 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer5 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer6 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer7 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer8 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer9 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer10 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer11 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer12 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer13 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer14 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer15 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer16 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer17 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer18 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer19 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer20 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer21 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer22 = new ArrayList<FcGiocatore>();
	private List<FcGiocatore> modelPlayer23 = new ArrayList<FcGiocatore>();

	@Autowired
	private AttoreService attoreController;

	@Autowired
	private FormazioneService formazioneController;

	@Autowired
	private GiornataDettService giornataDettController;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private CalendarioCompetizioneService calendarioTimController;
	private Grid<FcCalendarioCompetizione> tablePartite;

	private AbsoluteLayout absLayout;

	@Autowired
	private AccessoService accessoController;

	@Autowired
	private SquadraService squadraController;

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

	private void initData() throws Exception {

		p = (Properties) VaadinSession.getCurrent().getAttribute("PROPERTIES");
		attore = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
		giornataInfo = (FcGiornataInfo) VaadinSession.getCurrent().getAttribute("GIORNATA_INFO");
		campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
		nextDate = (String) VaadinSession.getCurrent().getAttribute("NEXTDATE");
		millisDiff = (long) VaadinSession.getCurrent().getAttribute("MILLISDIFF");

		idAttore = "" + attore.getIdAttore();
		descAttore = attore.getDescAttore();

		modelFormazione = getModelFormazione(attore, campionato);
	}

	private void initLayout() throws Exception {

		absLayout = new AbsoluteLayout(1600,1200);
		absLayout.getElement().getStyle().set("border", Costants.BORDER_COLOR);
		absLayout.getElement().getStyle().set("background", Costants.LOWER_GRAY);

		save = new Button("Save");
		save.setIcon(VaadinIcon.DATABASE.create());
		save.addClickListener(this);

		checkMail = new ToggleButton();
		checkMail.setLabel("Invia Email");
		checkMail.setValue(true);

		comboModulo = new ComboBox<>();
		comboModulo.setItems(schemi);
		comboModulo.setClearButtonVisible(true);
		comboModulo.setPlaceholder("Modulo");
		comboModulo.addValueChangeListener(evt -> {

			LOG.info(" addValueChangeListener " + evt.getValue());
			removeAllElementsList();

			if (evt.getValue() != null) {
				String modulo = (String) evt.getValue();

				absLayout.add(tablePlayer1, _550px, _P);

				absLayout.add(tablePlayer12, _860px, _P);
				absLayout.add(tablePlayer13, _960px, _P);
				absLayout.add(tablePlayer14, _1060px, _P);

				absLayout.add(tablePlayer15, _860px, _D);
				absLayout.add(tablePlayer16, _960px, _D);
				absLayout.add(tablePlayer17, _1060px, _D);

				absLayout.add(tablePlayer18, _860px, _C);
				absLayout.add(tablePlayer19, _960px, _C);
				absLayout.add(tablePlayer20, _1060px, _C);

				absLayout.add(tablePlayer21, _860px, _A);
				absLayout.add(tablePlayer22, _960px, _A);
				absLayout.add(tablePlayer23, _1060px, _A);

				// 5-4-1 5-3-2 4-5-1 4-4-2 4-3-3 3-5-2 3-4-3
				if (modulo.equals("5-4-1")) {

					absLayout.add(tablePlayer2, _350px, _D);
					absLayout.add(tablePlayer3, _450px, _D);
					absLayout.add(tablePlayer4, _550px, _D);
					absLayout.add(tablePlayer5, _650px, _D);
					absLayout.add(tablePlayer6, _750px, _D);

					absLayout.add(tablePlayer7, _400px, _C);
					absLayout.add(tablePlayer8, _500px, _C);
					absLayout.add(tablePlayer9, _600px, _C);
					absLayout.add(tablePlayer10, _700px, _C);

					absLayout.add(tablePlayer11, _550px, _A);

				} else if (modulo.equals("5-3-2")) {

					absLayout.add(tablePlayer2, _350px, _D);
					absLayout.add(tablePlayer3, _450px, _D);
					absLayout.add(tablePlayer4, _550px, _D);
					absLayout.add(tablePlayer5, _650px, _D);
					absLayout.add(tablePlayer6, _750px, _D);

					absLayout.add(tablePlayer7, _450px, _C);
					absLayout.add(tablePlayer8, _550px, _C);
					absLayout.add(tablePlayer9, _650px, _C);

					absLayout.add(tablePlayer10, _500px, _A);
					absLayout.add(tablePlayer11, _600px, _A);

				} else if (modulo.equals("4-5-1")) {

					absLayout.add(tablePlayer2, _400px, _D);
					absLayout.add(tablePlayer3, _500px, _D);
					absLayout.add(tablePlayer4, _600px, _D);
					absLayout.add(tablePlayer5, _700px, _D);

					absLayout.add(tablePlayer6, _350px, _C);
					absLayout.add(tablePlayer7, _450px, _C);
					absLayout.add(tablePlayer8, _550px, _C);
					absLayout.add(tablePlayer9, _650px, _C);
					absLayout.add(tablePlayer10, _750px, _C);

					absLayout.add(tablePlayer11, _550px, _A);

				} else if (modulo.equals("4-4-2")) {

					absLayout.add(tablePlayer2, _400px, _D);
					absLayout.add(tablePlayer3, _500px, _D);
					absLayout.add(tablePlayer4, _600px, _D);
					absLayout.add(tablePlayer5, _700px, _D);

					absLayout.add(tablePlayer6, _400px, _C);
					absLayout.add(tablePlayer7, _500px, _C);
					absLayout.add(tablePlayer8, _600px, _C);
					absLayout.add(tablePlayer9, _700px, _C);

					absLayout.add(tablePlayer10, _500px, _A);
					absLayout.add(tablePlayer11, _600px, _A);

				} else if (modulo.equals("4-3-3")) {

					absLayout.add(tablePlayer2, _400px, _D);
					absLayout.add(tablePlayer3, _500px, _D);
					absLayout.add(tablePlayer4, _600px, _D);
					absLayout.add(tablePlayer5, _700px, _D);

					absLayout.add(tablePlayer6, _450px, _C);
					absLayout.add(tablePlayer7, _550px, _C);
					absLayout.add(tablePlayer8, _650px, _C);

					absLayout.add(tablePlayer9, _450px, _A);
					absLayout.add(tablePlayer10, _550px, _A);
					absLayout.add(tablePlayer11, _650px, _A);

				} else if (modulo.equals("3-5-2")) {

					absLayout.add(tablePlayer2, _450px, _D);
					absLayout.add(tablePlayer3, _550px, _D);
					absLayout.add(tablePlayer4, _650px, _D);

					absLayout.add(tablePlayer5, _350px, _C);
					absLayout.add(tablePlayer6, _450px, _C);
					absLayout.add(tablePlayer7, _550px, _C);
					absLayout.add(tablePlayer8, _650px, _C);
					absLayout.add(tablePlayer9, _750px, _C);

					absLayout.add(tablePlayer10, _500px, _A);
					absLayout.add(tablePlayer11, _600px, _A);

				} else if (modulo.equals("3-4-3")) {

					absLayout.add(tablePlayer2, _450px, _D);
					absLayout.add(tablePlayer3, _550px, _D);
					absLayout.add(tablePlayer4, _650px, _D);

					absLayout.add(tablePlayer5, _400px, _C);
					absLayout.add(tablePlayer6, _500px, _C);
					absLayout.add(tablePlayer7, _600px, _C);
					absLayout.add(tablePlayer8, _700px, _C);

					absLayout.add(tablePlayer9, _450px, _A);
					absLayout.add(tablePlayer10, _550px, _A);
					absLayout.add(tablePlayer11, _650px, _A);
				}
			}
		});

		tableFormazione = getTableFormazione(modelFormazione);

		tablePlayer1 = getTableGiocatore(modelPlayer1);
		tablePlayer2 = getTableGiocatore(modelPlayer2);
		tablePlayer3 = getTableGiocatore(modelPlayer3);
		tablePlayer4 = getTableGiocatore(modelPlayer4);
		tablePlayer5 = getTableGiocatore(modelPlayer5);
		tablePlayer6 = getTableGiocatore(modelPlayer6);
		tablePlayer7 = getTableGiocatore(modelPlayer7);
		tablePlayer8 = getTableGiocatore(modelPlayer8);
		tablePlayer9 = getTableGiocatore(modelPlayer9);
		tablePlayer10 = getTableGiocatore(modelPlayer10);
		tablePlayer11 = getTableGiocatore(modelPlayer11);
		tablePlayer12 = getTableGiocatore(modelPlayer12);
		tablePlayer13 = getTableGiocatore(modelPlayer13);
		tablePlayer14 = getTableGiocatore(modelPlayer14);
		tablePlayer15 = getTableGiocatore(modelPlayer15);
		tablePlayer16 = getTableGiocatore(modelPlayer16);
		tablePlayer17 = getTableGiocatore(modelPlayer17);
		tablePlayer18 = getTableGiocatore(modelPlayer18);
		tablePlayer19 = getTableGiocatore(modelPlayer19);
		tablePlayer20 = getTableGiocatore(modelPlayer20);
		tablePlayer21 = getTableGiocatore(modelPlayer21);
		tablePlayer22 = getTableGiocatore(modelPlayer22);
		tablePlayer23 = getTableGiocatore(modelPlayer23);

		List<FcCalendarioCompetizione> listPartite = calendarioTimController.findByIdGiornataOrderByDataAsc(giornataInfo.getCodiceGiornata());
		tablePartite = getTablePartite(listPartite);

		Image panchina = buildImage("classpath:images/", "panchina.jpg");

		final VerticalLayout layoutAvviso = new VerticalLayout();
		layoutAvviso.getStyle().set("border", Costants.BORDER_COLOR);
		layoutAvviso.getStyle().set("background", Costants.YELLOW);
		layoutAvviso.setWidth("500px");

		HorizontalLayout cssLayout = new HorizontalLayout();
		Label lblInfo = new Label("Prossima Giornata: " + Utils.buildInfoGiornataEm(giornataInfo, campionato));
		cssLayout.add(lblInfo);
		layoutAvviso.add(cssLayout);

		HorizontalLayout cssLayout2 = new HorizontalLayout();
		Label lblInfo2 = new Label("Consegna Formazione entro: " + nextDate);
		cssLayout2.add(lblInfo2);
		layoutAvviso.add(cssLayout2);

		int top = 5;
		absLayout.add(save, 20, top);
		absLayout.add(checkMail, 110, top + 5);
		absLayout.add(layoutAvviso, _350px, top);
		absLayout.add(panchina, _860px, top);
		absLayout.add(tablePartite, _1200px, top);

		absLayout.add(comboModulo, 20, 50);

		absLayout.add(tableFormazione, 10, 150);
		Image campo = buildImage("classpath:images/", "campo.jpg");
		absLayout.add(campo, _350px, 150);

		this.add(absLayout);

		try {
			loadFcGiornatadett(attore, giornataInfo);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		if (millisDiff == 0) {
			showMessageStopInsert();
		} else {
			SimpleTimer timer = new SimpleTimer(new BigDecimal(millisDiff / 1000));
			timer.setHours(true);
			timer.setMinutes(true);
			timer.setFractions(false);
			timer.start();
			timer.isRunning();
			timer.addTimerEndEvent(ev -> showMessageStopInsert());
			layoutAvviso.add(timer);
		}

	}

	private void showMessageStopInsert() {
		setEnabled(false);
		CustomMessageDialog.showMessageError("Impossibile inserire la formazione, tempo scaduto!");
	}

	private String getInfoPlayer(FcGiocatore bean) {
		String info = "N.D.";
		if (bean != null && bean.getFcStatistiche() != null && bean.getFcStatistiche().getMediaVoto() != 0) {
			NumberFormat formatter = new DecimalFormat("#0.00");
			String mv = formatter.format(bean.getFcStatistiche().getMediaVoto() / Costants.DIVISORE_10);
			String fv = formatter.format(bean.getFcStatistiche().getFantaMedia() / Costants.DIVISORE_10);

			info = bean.getCognGiocatore() + "\n";
			info += "Squadra: " + bean.getFcSquadra().getNomeSquadra() + "\n";
			info += "Giocate: " + bean.getFcStatistiche().getGiocate() + "\n";
			info += "MV: " + mv + "\n";
			info += "FV: " + fv + "\n";
			info += "Goal: " + bean.getFcStatistiche().getGoalFatto() + "\n";
			info += "Assist: " + bean.getFcStatistiche().getAssist() + "\n";
			info += "Ammonizione: " + bean.getFcStatistiche().getAmmonizione() + "\n";
			info += "Espulsione: " + bean.getFcStatistiche().getEspulsione() + "\n";
			if ("P".equals(bean.getFcRuolo().getIdRuolo().toUpperCase())) {
				info += "Goal Subito: " + bean.getFcStatistiche().getGoalSubito() + "\n";
			}
		}

		return info;
	}

	private void refreshAndSortGridFormazione() {
		LOG.info("refreshAndSortGridFormazione");
		modelFormazione.sort((p1,
				p2) -> p2.getFcRuolo().getIdRuolo().compareToIgnoreCase(p1.getFcRuolo().getIdRuolo()));
		tableFormazione.getDataProvider().refreshAll();
	}

	private void removeAllElementsList() {

		LOG.info("removeAllElementsList");
		if (modelPlayer1.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer1.get(0);
			modelFormazione.add(bean);
			modelPlayer1.clear();
			tablePlayer1.getDataProvider().refreshAll();
		}
		if (modelPlayer2.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer2.get(0);
			modelFormazione.add(bean);
			modelPlayer2.clear();
			tablePlayer2.getDataProvider().refreshAll();
		}
		if (modelPlayer3.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer3.get(0);
			modelFormazione.add(bean);
			modelPlayer3.clear();
			tablePlayer3.getDataProvider().refreshAll();
		}
		if (modelPlayer4.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer4.get(0);
			modelFormazione.add(bean);
			modelPlayer4.clear();
			tablePlayer4.getDataProvider().refreshAll();
		}
		if (modelPlayer5.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer5.get(0);
			modelFormazione.add(bean);
			modelPlayer5.clear();
			tablePlayer5.getDataProvider().refreshAll();
		}
		if (modelPlayer6.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer6.get(0);
			modelFormazione.add(bean);
			modelPlayer6.clear();
			tablePlayer6.getDataProvider().refreshAll();
		}
		if (modelPlayer7.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer7.get(0);
			modelFormazione.add(bean);
			modelPlayer7.clear();
			tablePlayer7.getDataProvider().refreshAll();
		}
		if (modelPlayer8.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer8.get(0);
			modelFormazione.add(bean);
			modelPlayer8.clear();
			tablePlayer8.getDataProvider().refreshAll();
		}
		if (modelPlayer9.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer9.get(0);
			modelFormazione.add(bean);
			modelPlayer9.clear();
			tablePlayer9.getDataProvider().refreshAll();
		}
		if (modelPlayer10.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer10.get(0);
			modelFormazione.add(bean);
			modelPlayer10.clear();
			tablePlayer10.getDataProvider().refreshAll();
		}
		if (modelPlayer11.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer11.get(0);
			modelFormazione.add(bean);
			modelPlayer11.clear();
			tablePlayer11.getDataProvider().refreshAll();
		}
		if (modelPlayer12.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer12.get(0);
			modelFormazione.add(bean);
			modelPlayer12.clear();
			tablePlayer12.getDataProvider().refreshAll();
		}
		if (modelPlayer13.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer13.get(0);
			modelFormazione.add(bean);
			modelPlayer13.clear();
			tablePlayer13.getDataProvider().refreshAll();
		}
		if (modelPlayer14.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer14.get(0);
			modelFormazione.add(bean);
			modelPlayer14.clear();
			tablePlayer14.getDataProvider().refreshAll();
		}
		if (modelPlayer15.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer15.get(0);
			modelFormazione.add(bean);
			modelPlayer15.clear();
			tablePlayer15.getDataProvider().refreshAll();
		}
		if (modelPlayer16.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer16.get(0);
			modelFormazione.add(bean);
			modelPlayer16.clear();
			tablePlayer16.getDataProvider().refreshAll();
		}
		if (modelPlayer17.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer17.get(0);
			modelFormazione.add(bean);
			modelPlayer17.clear();
			tablePlayer17.getDataProvider().refreshAll();
		}
		if (modelPlayer18.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer18.get(0);
			modelFormazione.add(bean);
			modelPlayer18.clear();
			tablePlayer18.getDataProvider().refreshAll();
		}
		if (modelPlayer19.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer19.get(0);
			modelFormazione.add(bean);
			modelPlayer19.clear();
			tablePlayer19.getDataProvider().refreshAll();
		}
		if (modelPlayer20.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer20.get(0);
			modelFormazione.add(bean);
			modelPlayer20.clear();
			tablePlayer20.getDataProvider().refreshAll();
		}
		if (modelPlayer21.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer21.get(0);
			modelFormazione.add(bean);
			modelPlayer21.clear();
			tablePlayer21.getDataProvider().refreshAll();
		}
		if (modelPlayer22.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer22.get(0);
			modelFormazione.add(bean);
			modelPlayer22.clear();
			tablePlayer22.getDataProvider().refreshAll();
		}
		if (modelPlayer23.size() != 0) {
			FcGiocatore bean = (FcGiocatore) modelPlayer23.get(0);
			modelFormazione.add(bean);
			modelPlayer23.clear();
			tablePlayer23.getDataProvider().refreshAll();
		}

		refreshAndSortGridFormazione();

		absLayout.remove(tablePlayer1);
		absLayout.remove(tablePlayer2);
		absLayout.remove(tablePlayer3);
		absLayout.remove(tablePlayer4);
		absLayout.remove(tablePlayer5);
		absLayout.remove(tablePlayer6);
		absLayout.remove(tablePlayer7);
		absLayout.remove(tablePlayer8);
		absLayout.remove(tablePlayer9);
		absLayout.remove(tablePlayer10);
		absLayout.remove(tablePlayer11);
		absLayout.remove(tablePlayer12);
		absLayout.remove(tablePlayer13);
		absLayout.remove(tablePlayer14);
		absLayout.remove(tablePlayer15);
		absLayout.remove(tablePlayer16);
		absLayout.remove(tablePlayer17);
		absLayout.remove(tablePlayer18);
		absLayout.remove(tablePlayer19);
		absLayout.remove(tablePlayer20);
		absLayout.remove(tablePlayer21);
		absLayout.remove(tablePlayer22);
		absLayout.remove(tablePlayer23);
	}

	private ArrayList<FcGiocatore> getModelFormazione(FcAttore attore,
			FcCampionato campionato) {

		List<FcFormazione> listFormazione = formazioneController.findByFcCampionatoAndFcAttoreOrderByFcGiocatoreFcRuoloDescTotPagatoDesc(campionato, attore, false);

		ArrayList<FcGiocatore> beans = new ArrayList<FcGiocatore>();
		for (FcFormazione f : listFormazione) {
			if (f.getFcGiocatore() != null) {
				beans.add(f.getFcGiocatore());
			}
		}

		return beans;
	}

	private Grid<FcGiocatore> getTableGiocatore(List<FcGiocatore> items) {

		Grid<FcGiocatore> grid = new Grid<>();
		grid.setItems(items);
		grid.setSelectionMode(Grid.SelectionMode.NONE);
		grid.getStyle().set("--_lumo-grid-border-width", "0px");
		// grid.getStyle().set("border", Costants.BORDER_COLOR);
		grid.setWidth(width);
		grid.setHeight(height);

		Column<FcGiocatore> giocatoreColumn = grid.addColumn(new ComponentRenderer<>(g -> {
			VerticalLayout cellLayout = new VerticalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.setSizeUndefined();
			cellLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
			if (g != null) {

				String title = getInfoPlayer(g);

				String ruolo = g.getFcRuolo().getIdRuolo();
				if ("P".equals(ruolo)) {
					cellLayout.getElement().getStyle().set("border", Costants.BORDER_COLOR_P);
				} else if ("D".equals(ruolo)) {
					cellLayout.getElement().getStyle().set("border", Costants.BORDER_COLOR_D);
				} else if ("C".equals(ruolo)) {
					cellLayout.getElement().getStyle().set("border", Costants.BORDER_COLOR_C);
				} else if ("A".equals(ruolo)) {
					cellLayout.getElement().getStyle().set("border", Costants.BORDER_COLOR_A);
				}

				if (!g.isFlagAttivo()) {
					cellLayout.getElement().getStyle().set("background", Costants.LOWER_GRAY);
					cellLayout.getElement().getStyle().set("-webkit-text-fill-color", Costants.RED);
				}

				Span lblOrdinamento = new Span();
				lblOrdinamento.setText("" + getOrdinamento(g));
				lblOrdinamento.getStyle().set("font-size", "14px");
				lblOrdinamento.setTitle(title);
				cellLayout.add(lblOrdinamento);
				cellLayout.setAlignSelf(Alignment.CENTER, lblOrdinamento);

				Image imgR = buildImage("classpath:images/", ruolo.toLowerCase() + ".png");
				imgR.setTitle(title);
				cellLayout.add(imgR);

				// Label lblGiocatore = new Label(g.getCognGiocatore());
				Span lblGiocatore = new Span();
				lblGiocatore.setText(g.getCognGiocatore());
				lblGiocatore.getStyle().set("font-size", "11px");
				lblGiocatore.setTitle(title);
				cellLayout.add(lblGiocatore);
				cellLayout.setAlignSelf(Alignment.STRETCH, lblGiocatore);

				if (g.getFcSquadra() != null) {

					FcSquadra sq = g.getFcSquadra();
					if (sq.getImg40() != null) {
						try {
							Image img = Utils.getImage(sq.getNomeSquadra(), sq.getImg40().getBinaryStream());
							cellLayout.add(img);
							cellLayout.setAlignSelf(Alignment.START, img);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					// Label lblInfoNomeSquadra = new
					// Label(sq.getNomeSquadra());
					Span lblInfoNomeSquadra = new Span();
					lblInfoNomeSquadra.setText(sq.getNomeSquadra());
					lblInfoNomeSquadra.getStyle().set("font-size", "11px");
					lblInfoNomeSquadra.setTitle(title);
					cellLayout.add(lblInfoNomeSquadra);
					cellLayout.setAlignSelf(Alignment.STRETCH, lblInfoNomeSquadra);
				}

				Element element = cellLayout.getElement(); // DOM element
				element.addEventListener("click", e -> {

					FcGiocatore bean = (FcGiocatore) g;
					LOG.info("click " + bean.getCognGiocatore());
					modelFormazione.add(bean);
					refreshAndSortGridFormazione();

					if (grid == tablePlayer1) {
						modelPlayer1.remove(bean);
						tablePlayer1.getDataProvider().refreshAll();
					} else if (grid == tablePlayer2) {
						modelPlayer2.remove(bean);
						tablePlayer2.getDataProvider().refreshAll();
					} else if (grid == tablePlayer3) {
						modelPlayer3.remove(bean);
						tablePlayer3.getDataProvider().refreshAll();
					} else if (grid == tablePlayer4) {
						modelPlayer4.remove(bean);
						tablePlayer4.getDataProvider().refreshAll();
					} else if (grid == tablePlayer5) {
						modelPlayer5.remove(bean);
						tablePlayer5.getDataProvider().refreshAll();
					} else if (grid == tablePlayer6) {
						modelPlayer6.remove(bean);
						tablePlayer6.getDataProvider().refreshAll();
					} else if (grid == tablePlayer7) {
						modelPlayer7.remove(bean);
						tablePlayer7.getDataProvider().refreshAll();
					} else if (grid == tablePlayer8) {
						modelPlayer8.remove(bean);
						tablePlayer8.getDataProvider().refreshAll();
					} else if (grid == tablePlayer9) {
						modelPlayer9.remove(bean);
						tablePlayer9.getDataProvider().refreshAll();
					} else if (grid == tablePlayer10) {
						modelPlayer10.remove(bean);
						tablePlayer10.getDataProvider().refreshAll();
					} else if (grid == tablePlayer11) {
						modelPlayer11.remove(bean);
						tablePlayer11.getDataProvider().refreshAll();
					} else if (grid == tablePlayer12) {
						modelPlayer12.remove(bean);
						tablePlayer12.getDataProvider().refreshAll();
					} else if (grid == tablePlayer13) {
						modelPlayer13.remove(bean);
						tablePlayer13.getDataProvider().refreshAll();
					} else if (grid == tablePlayer14) {
						modelPlayer14.remove(bean);
						tablePlayer14.getDataProvider().refreshAll();
					} else if (grid == tablePlayer15) {
						modelPlayer15.remove(bean);
						tablePlayer15.getDataProvider().refreshAll();
					} else if (grid == tablePlayer16) {
						modelPlayer16.remove(bean);
						tablePlayer16.getDataProvider().refreshAll();
					} else if (grid == tablePlayer17) {
						modelPlayer17.remove(bean);
						tablePlayer17.getDataProvider().refreshAll();
					} else if (grid == tablePlayer18) {
						modelPlayer18.remove(bean);
						tablePlayer18.getDataProvider().refreshAll();
					} else if (grid == tablePlayer19) {
						modelPlayer19.remove(bean);
						tablePlayer19.getDataProvider().refreshAll();
					} else if (grid == tablePlayer20) {
						modelPlayer20.remove(bean);
						tablePlayer20.getDataProvider().refreshAll();
					} else if (grid == tablePlayer21) {
						modelPlayer21.remove(bean);
						tablePlayer21.getDataProvider().refreshAll();
					} else if (grid == tablePlayer22) {
						modelPlayer22.remove(bean);
						tablePlayer22.getDataProvider().refreshAll();
					} else if (grid == tablePlayer23) {
						modelPlayer23.remove(bean);
						tablePlayer23.getDataProvider().refreshAll();
					}

				});
			}
			return cellLayout;

		}));
		giocatoreColumn.setSortable(false);
		giocatoreColumn.setResizable(false);

		return grid;

	}

	private Grid<FcGiocatore> getTableFormazione(List<FcGiocatore> items) {

		Grid<FcGiocatore> grid = new Grid<>();
		grid.setItems(items);
		grid.setSelectionMode(Grid.SelectionMode.NONE);
		grid.setAllRowsVisible(true);
		grid.setWidth("330px");

		Column<FcGiocatore> ruoloColumn = grid.addColumn(new ComponentRenderer<>(g -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.setAlignItems(Alignment.STRETCH);
			// cellLayout.setSizeFull();
			if (g != null) {
				String title = getInfoPlayer(g);
				if (g.getFcRuolo() != null) {
					Image img = buildImage("classpath:images/", g.getFcRuolo().getIdRuolo().toLowerCase() + ".png");
					img.setTitle(title);
					cellLayout.add(img);
				}
			}
			return cellLayout;
		}));
		ruoloColumn.setSortable(true);
		ruoloColumn.setHeader("R");
		ruoloColumn.setWidth("35px");
		ruoloColumn.setComparator((p1,
				p2) -> p1.getFcRuolo().getIdRuolo().compareTo(p2.getFcRuolo().getIdRuolo()));
		// ruoloColumn.setAutoWidth(true);

		// Column<FcGiocatore> cognGiocatoreColumn = grid.addColumn(g -> g !=
		// null ? g.getCognGiocatore() : "");
		Column<FcGiocatore> cognGiocatoreColumn = grid.addColumn(new ComponentRenderer<>(g -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.setAlignItems(Alignment.STRETCH);
			if (g != null) {
				String title = getInfoPlayer(g);
				if (!g.isFlagAttivo()) {
					cellLayout.getElement().getStyle().set("background", Costants.LOWER_GRAY);
					cellLayout.getElement().getStyle().set("-webkit-text-fill-color", Costants.RED);
				}
				if (g.getCognGiocatore() != null) {
					// Label lblGiocatore = new Label(g.getCognGiocatore());
					Span lblGiocatore = new Span();
					lblGiocatore.setText(g.getCognGiocatore());
					lblGiocatore.setTitle(title);
					cellLayout.add(lblGiocatore);
				}
			}
			return cellLayout;
		}));
		cognGiocatoreColumn.setSortable(false);
		cognGiocatoreColumn.setHeader("Giocatore");
		cognGiocatoreColumn.setWidth("145px");
		// cognGiocatoreColumn.setAutoWidth(true);
		
		Column<FcGiocatore> nomeSquadraColumn = grid.addColumn(new ComponentRenderer<>(g -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.setAlignItems(Alignment.STRETCH);
			if (g != null) {
				String title = getInfoPlayer(g);
				if (!g.isFlagAttivo()) {
					cellLayout.getElement().getStyle().set("background", Costants.LOWER_GRAY);
					cellLayout.getElement().getStyle().set("-webkit-text-fill-color", Costants.RED);
				}
				if (g.getFcSquadra() != null) {
					FcSquadra sq = g.getFcSquadra();
					if (sq.getImg() != null) {
						try {
							Image img = Utils.getImage(sq.getNomeSquadra(), sq.getImg().getBinaryStream());
							img.setTitle(title);
							cellLayout.add(img);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					Span lblSquadra = new Span();
					lblSquadra.setText(sq.getNomeSquadra().substring(0, 3));
					lblSquadra.setTitle(title);
					cellLayout.add(lblSquadra);
				}
			}
			return cellLayout;
		}));
		nomeSquadraColumn.setSortable(true);
		nomeSquadraColumn.setComparator((p1,
				p2) -> p1.getFcSquadra().getNomeSquadra().compareTo(p2.getFcSquadra().getNomeSquadra()));
		nomeSquadraColumn.setHeader("Naz");
		nomeSquadraColumn.setWidth("70px");
		// nomeSquadraColumn.setAutoWidth(true);
		
		Column<FcGiocatore> mediaVotoColumn = grid.addColumn(new ComponentRenderer<>(g -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			if (g != null) {
				String title = getInfoPlayer(g);
				if (!g.isFlagAttivo()) {
					cellLayout.getElement().getStyle().set("background", Costants.LOWER_GRAY);
					cellLayout.getElement().getStyle().set("-webkit-text-fill-color", Costants.RED);
				}
				FcStatistiche s = g.getFcStatistiche();
				String imgThink = "2.png";
				if (s != null && s.getMediaVoto() != 0) {
					if (s.getMediaVoto() > Costants.EM_RANGE_MAX_MV) {
						imgThink = "1.png";
					} else if (s.getMediaVoto() < Costants.EM_RANGE_MIN_MV) {
						imgThink = "3.png";
					}
				}
				Image img = buildImage("classpath:images/", imgThink);
				img.setTitle(title);
				cellLayout.add(img);

				DecimalFormat myFormatter = new DecimalFormat("#0.00");
				Double d = Double.valueOf(0);
				if (s != null) {
					d = s.getMediaVoto() / Costants.DIVISORE_10;
				}
				String sTotPunti = myFormatter.format(d);
				// Label lbl = new Label(sTotPunti);
				Span lbl = new Span();
				lbl.setText(sTotPunti);
				lbl.setTitle(title);
				cellLayout.add(lbl);
			}
			return cellLayout;
		}));
		mediaVotoColumn.setSortable(true);
		mediaVotoColumn.setComparator((p1,
				p2) -> p1.getFcStatistiche().getMediaVoto().compareTo(p2.getFcStatistiche().getMediaVoto()));
		mediaVotoColumn.setHeader("Mv");
		mediaVotoColumn.setWidth("70px");
		// mediaVotoColumn.setAutoWidth(true);

		grid.addItemClickListener(event -> {
			String valModulo = (String) comboModulo.getValue();
			if (valModulo == null) {
				return;
			}
			FcGiocatore bean = event.getItem();
			LOG.info("click " + bean.getCognGiocatore());

			if (bean != null) {

				if (existGiocatore(bean)) {
					LOG.info("existGiocatore true");
					return;
				}

				boolean bDel = false;
				if (bean.getFcRuolo().getIdRuolo().equals("P")) {
					if (modelPlayer1.size() == 0) {
						modelPlayer1.add(bean);
						tablePlayer1.getDataProvider().refreshAll();
						bDel = true;
					}
				} else if (bean.getFcRuolo().getIdRuolo().equals("D")) {

					if (valModulo.equals("5-4-1") || valModulo.equals("5-3-2")) {

						if (modelPlayer2.size() == 0) {
							modelPlayer2.add(bean);
							tablePlayer2.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer3.size() == 0) {
							modelPlayer3.add(bean);
							tablePlayer3.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer4.size() == 0) {
							modelPlayer4.add(bean);
							tablePlayer4.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer5.size() == 0) {
							modelPlayer5.add(bean);
							tablePlayer5.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer6.size() == 0) {
							modelPlayer6.add(bean);
							tablePlayer6.getDataProvider().refreshAll();
							bDel = true;
						}

					} else if (valModulo.equals("4-5-1") || valModulo.equals("4-4-2") || valModulo.equals("4-3-3")) {

						if (modelPlayer2.size() == 0) {
							modelPlayer2.add(bean);
							tablePlayer2.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer3.size() == 0) {
							modelPlayer3.add(bean);
							tablePlayer3.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer4.size() == 0) {
							modelPlayer4.add(bean);
							tablePlayer4.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer5.size() == 0) {
							modelPlayer5.add(bean);
							tablePlayer5.getDataProvider().refreshAll();
							bDel = true;
						}

					} else if (valModulo.equals("3-5-2") || valModulo.equals("3-4-3")) {

						if (modelPlayer2.size() == 0) {
							modelPlayer2.add(bean);
							tablePlayer2.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer3.size() == 0) {
							modelPlayer3.add(bean);
							tablePlayer3.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer4.size() == 0) {
							modelPlayer4.add(bean);
							tablePlayer4.getDataProvider().refreshAll();
							bDel = true;
						}
					}

				} else if (bean.getFcRuolo().getIdRuolo().equals("C")) {

					if (valModulo.equals("4-5-1")) {

						if (modelPlayer6.size() == 0) {
							modelPlayer6.add(bean);
							tablePlayer6.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer7.size() == 0) {
							modelPlayer7.add(bean);
							tablePlayer7.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer8.size() == 0) {
							modelPlayer8.add(bean);
							tablePlayer8.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer9.size() == 0) {
							modelPlayer9.add(bean);
							tablePlayer9.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer10.size() == 0) {
							modelPlayer10.add(bean);
							tablePlayer10.getDataProvider().refreshAll();
							bDel = true;
						}
					} else if (valModulo.equals("3-5-2")) {

						if (modelPlayer5.size() == 0) {
							modelPlayer5.add(bean);
							tablePlayer5.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer6.size() == 0) {
							modelPlayer6.add(bean);
							tablePlayer6.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer7.size() == 0) {
							modelPlayer7.add(bean);
							tablePlayer7.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer8.size() == 0) {
							modelPlayer8.add(bean);
							tablePlayer8.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer9.size() == 0) {
							modelPlayer9.add(bean);
							tablePlayer9.getDataProvider().refreshAll();
							bDel = true;
						}

					} else if (valModulo.equals("5-4-1")) {

						if (modelPlayer7.size() == 0) {
							modelPlayer7.add(bean);
							tablePlayer7.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer8.size() == 0) {
							modelPlayer8.add(bean);
							tablePlayer8.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer9.size() == 0) {
							modelPlayer9.add(bean);
							tablePlayer9.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer10.size() == 0) {
							modelPlayer10.add(bean);
							tablePlayer10.getDataProvider().refreshAll();
							bDel = true;
						}

					} else if (valModulo.equals("4-4-2")) {

						if (modelPlayer6.size() == 0) {
							modelPlayer6.add(bean);
							tablePlayer6.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer7.size() == 0) {
							modelPlayer7.add(bean);
							tablePlayer7.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer8.size() == 0) {
							modelPlayer8.add(bean);
							tablePlayer8.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer9.size() == 0) {
							modelPlayer9.add(bean);
							tablePlayer9.getDataProvider().refreshAll();
							bDel = true;
						}

					} else if (valModulo.equals("3-4-3")) {

						if (modelPlayer5.size() == 0) {
							modelPlayer5.add(bean);
							tablePlayer5.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer6.size() == 0) {
							modelPlayer6.add(bean);
							tablePlayer6.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer7.size() == 0) {
							modelPlayer7.add(bean);
							tablePlayer7.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer8.size() == 0) {
							modelPlayer8.add(bean);
							tablePlayer8.getDataProvider().refreshAll();
							bDel = true;
						}

					} else if (valModulo.equals("5-3-2")) {

						if (modelPlayer7.size() == 0) {
							modelPlayer7.add(bean);
							tablePlayer7.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer8.size() == 0) {
							modelPlayer8.add(bean);
							tablePlayer8.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer9.size() == 0) {
							modelPlayer9.add(bean);
							tablePlayer9.getDataProvider().refreshAll();
							bDel = true;
						}

					} else if (valModulo.equals("4-3-3")) {
						if (modelPlayer6.size() == 0) {
							modelPlayer6.add(bean);
							tablePlayer6.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer7.size() == 0) {
							modelPlayer7.add(bean);
							tablePlayer7.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer8.size() == 0) {
							modelPlayer8.add(bean);
							tablePlayer8.getDataProvider().refreshAll();
							bDel = true;
						}
					}

				} else if (bean.getFcRuolo().getIdRuolo().equals("A")) {

					if (valModulo.equals("4-5-1") || valModulo.equals("5-4-1")) {

						if (modelPlayer11.size() == 0) {
							modelPlayer11.add(bean);
							tablePlayer11.getDataProvider().refreshAll();
							bDel = true;
						}

					} else if (valModulo.equals("3-5-2") || valModulo.equals("4-4-2") || valModulo.equals("5-3-2")) {

						if (modelPlayer10.size() == 0) {
							modelPlayer10.add(bean);
							tablePlayer10.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer11.size() == 0) {
							modelPlayer11.add(bean);
							tablePlayer11.getDataProvider().refreshAll();
							bDel = true;
						}

					} else if (valModulo.equals("3-4-3") || valModulo.equals("4-3-3")) {

						if (modelPlayer9.size() == 0) {
							modelPlayer9.add(bean);
							tablePlayer9.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer10.size() == 0) {
							modelPlayer10.add(bean);
							tablePlayer10.getDataProvider().refreshAll();
							bDel = true;
						} else if (modelPlayer11.size() == 0) {
							modelPlayer11.add(bean);
							tablePlayer11.getDataProvider().refreshAll();
							bDel = true;
						}
					}
				}

				if (!bDel) {

					if (modelPlayer12.size() == 0) {
						modelPlayer12.add(bean);
						tablePlayer12.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer13.size() == 0) {
						modelPlayer13.add(bean);
						tablePlayer13.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer14.size() == 0) {
						modelPlayer14.add(bean);
						tablePlayer14.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer15.size() == 0) {
						modelPlayer15.add(bean);
						tablePlayer15.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer16.size() == 0) {
						modelPlayer16.add(bean);
						tablePlayer16.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer17.size() == 0) {
						modelPlayer17.add(bean);
						tablePlayer17.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer18.size() == 0) {
						modelPlayer18.add(bean);
						tablePlayer18.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer19.size() == 0) {
						modelPlayer19.add(bean);
						tablePlayer19.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer20.size() == 0) {
						modelPlayer20.add(bean);
						tablePlayer20.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer21.size() == 0) {
						modelPlayer21.add(bean);
						tablePlayer21.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer22.size() == 0) {
						modelPlayer22.add(bean);
						tablePlayer22.getDataProvider().refreshAll();
						bDel = true;
					} else if (modelPlayer23.size() == 0) {
						modelPlayer23.add(bean);
						tablePlayer23.getDataProvider().refreshAll();
						bDel = true;
					}
				}

				if (bDel) {
					modelFormazione.remove(bean);
					refreshAndSortGridFormazione();
				}

			}

		});

		return grid;
	}

	private boolean existGiocatore(FcGiocatore g) {

		if (modelPlayer1.size() != 0) {
			if (modelPlayer1.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer2.size() != 0) {
			if (modelPlayer2.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer3.size() != 0) {
			if (modelPlayer3.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer4.size() != 0) {
			if (modelPlayer4.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer5.size() != 0) {
			if (modelPlayer5.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer6.size() != 0) {
			if (modelPlayer6.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer7.size() != 0) {
			if (modelPlayer7.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer8.size() != 0) {
			if (modelPlayer8.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer9.size() != 0) {
			if (modelPlayer9.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer10.size() != 0) {
			if (modelPlayer10.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer11.size() != 0) {
			if (modelPlayer11.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer12.size() != 0) {
			if (modelPlayer12.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer13.size() != 0) {
			if (modelPlayer13.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer14.size() != 0) {
			if (modelPlayer14.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer15.size() != 0) {
			if (modelPlayer15.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer16.size() != 0) {
			if (modelPlayer16.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer17.size() != 0) {
			if (modelPlayer17.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer18.size() != 0) {
			if (modelPlayer18.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer19.size() != 0) {
			if (modelPlayer19.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer20.size() != 0) {
			if (modelPlayer20.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer21.size() != 0) {
			if (modelPlayer21.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer22.size() != 0) {
			if (modelPlayer22.indexOf(g) != -1) {
				return true;
			}
		}
		if (modelPlayer23.size() != 0) {
			if (modelPlayer23.indexOf(g) != -1) {
				return true;
			}
		}

		return false;
	}

	private int getOrdinamento(FcGiocatore g) {

		if (modelPlayer1.size() != 0) {
			if (modelPlayer1.indexOf(g) != -1) {
				return 1;
			}
		}
		if (modelPlayer2.size() != 0) {
			if (modelPlayer2.indexOf(g) != -1) {
				return 2;
			}
		}
		if (modelPlayer3.size() != 0) {
			if (modelPlayer3.indexOf(g) != -1) {
				return 3;
			}
		}
		if (modelPlayer4.size() != 0) {
			if (modelPlayer4.indexOf(g) != -1) {
				return 4;
			}
		}
		if (modelPlayer5.size() != 0) {
			if (modelPlayer5.indexOf(g) != -1) {
				return 5;
			}
		}
		if (modelPlayer6.size() != 0) {
			if (modelPlayer6.indexOf(g) != -1) {
				return 6;
			}
		}
		if (modelPlayer7.size() != 0) {
			if (modelPlayer7.indexOf(g) != -1) {
				return 7;
			}
		}
		if (modelPlayer8.size() != 0) {
			if (modelPlayer8.indexOf(g) != -1) {
				return 8;
			}
		}
		if (modelPlayer9.size() != 0) {
			if (modelPlayer9.indexOf(g) != -1) {
				return 9;
			}
		}
		if (modelPlayer10.size() != 0) {
			if (modelPlayer10.indexOf(g) != -1) {
				return 10;
			}
		}
		if (modelPlayer11.size() != 0) {
			if (modelPlayer11.indexOf(g) != -1) {
				return 11;
			}
		}
		if (modelPlayer12.size() != 0) {
			if (modelPlayer12.indexOf(g) != -1) {
				return 12;
			}
		}
		if (modelPlayer13.size() != 0) {
			if (modelPlayer13.indexOf(g) != -1) {
				return 13;
			}
		}
		if (modelPlayer14.size() != 0) {
			if (modelPlayer14.indexOf(g) != -1) {
				return 14;
			}
		}
		if (modelPlayer15.size() != 0) {
			if (modelPlayer15.indexOf(g) != -1) {
				return 15;
			}
		}
		if (modelPlayer16.size() != 0) {
			if (modelPlayer16.indexOf(g) != -1) {
				return 16;
			}
		}
		if (modelPlayer17.size() != 0) {
			if (modelPlayer17.indexOf(g) != -1) {
				return 17;
			}
		}
		if (modelPlayer18.size() != 0) {
			if (modelPlayer18.indexOf(g) != -1) {
				return 18;
			}
		}
		if (modelPlayer19.size() != 0) {
			if (modelPlayer19.indexOf(g) != -1) {
				return 19;
			}
		}
		if (modelPlayer20.size() != 0) {
			if (modelPlayer20.indexOf(g) != -1) {
				return 20;
			}
		}
		if (modelPlayer21.size() != 0) {
			if (modelPlayer21.indexOf(g) != -1) {
				return 21;
			}
		}
		if (modelPlayer22.size() != 0) {
			if (modelPlayer22.indexOf(g) != -1) {
				return 22;
			}
		}
		if (modelPlayer23.size() != 0) {
			if (modelPlayer23.indexOf(g) != -1) {
				return 23;
			}
		}

		return 0;
	}

	private void loadFcGiornatadett(FcAttore attore,
			FcGiornataInfo giornataInfo) throws Exception {

		LOG.info("loadFcGiornatadett");

		List<FcGiornataDett> lGiocatori = giornataDettController.findByFcAttoreAndFcGiornataInfoOrderByOrdinamentoAsc(attore, giornataInfo);

		if (lGiocatori.size() == 0) {
			this.comboModulo.setValue(null);
			removeAllElementsList();
			return;
		}

		int countD = 0;
		int countC = 0;
		int countA = 0;

		for (FcGiornataDett gd : lGiocatori) {

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

		this.comboModulo.setValue(schema);

		modelFormazione.clear();
		refreshAndSortGridFormazione();

		for (FcGiornataDett gd : lGiocatori) {

			FcGiocatore bean = gd.getFcGiocatore();
			if (gd.getOrdinamento() == 1) {
				modelPlayer1.clear();
				modelPlayer1.add(bean);
				tablePlayer1.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 2) {
				modelPlayer2.clear();
				modelPlayer2.add(bean);
				tablePlayer2.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 3) {
				modelPlayer3.clear();
				modelPlayer3.add(bean);
				tablePlayer3.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 4) {
				modelPlayer4.clear();
				modelPlayer4.add(bean);
				tablePlayer4.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 5) {
				modelPlayer5.clear();
				modelPlayer5.add(bean);
				tablePlayer5.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 6) {
				modelPlayer6.clear();
				modelPlayer6.add(bean);
				tablePlayer6.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 7) {
				modelPlayer7.clear();
				modelPlayer7.add(bean);
				tablePlayer7.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 8) {
				modelPlayer8.clear();
				modelPlayer8.add(bean);
				tablePlayer8.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 9) {
				modelPlayer9.clear();
				modelPlayer9.add(bean);
				tablePlayer9.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 10) {
				modelPlayer10.clear();
				modelPlayer10.add(bean);
				tablePlayer10.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 11) {
				modelPlayer11.clear();
				modelPlayer11.add(bean);
				tablePlayer11.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 12) {
				modelPlayer12.clear();
				modelPlayer12.add(bean);
				tablePlayer12.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 13) {
				modelPlayer13.clear();
				modelPlayer13.add(bean);
				tablePlayer13.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 14) {
				modelPlayer14.clear();
				modelPlayer14.add(bean);
				tablePlayer14.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 15) {
				modelPlayer15.clear();
				modelPlayer15.add(bean);
				tablePlayer15.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 16) {
				modelPlayer16.clear();
				modelPlayer16.add(bean);
				tablePlayer16.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 17) {
				modelPlayer17.clear();
				modelPlayer17.add(bean);
				tablePlayer17.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 18) {
				modelPlayer18.clear();
				modelPlayer18.add(bean);
				tablePlayer18.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 19) {
				modelPlayer19.clear();
				modelPlayer19.add(bean);
				tablePlayer19.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 20) {
				modelPlayer20.clear();
				modelPlayer20.add(bean);
				tablePlayer20.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 21) {
				modelPlayer21.clear();
				modelPlayer21.add(bean);
				tablePlayer21.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 22) {
				modelPlayer22.clear();
				modelPlayer22.add(bean);
				tablePlayer22.getDataProvider().refreshAll();
			} else if (gd.getOrdinamento() == 23) {
				modelPlayer23.clear();
				modelPlayer23.add(bean);
				tablePlayer23.getDataProvider().refreshAll();
			} else {
				modelFormazione.add(bean);
				refreshAndSortGridFormazione();
			}
		}
	}

	@Override
	public void onComponentEvent(ClickEvent<Button> event) {

		if (check()) {

			try {
				insert(giornataInfo.getCodiceGiornata());
			} catch (Exception exi) {
				CustomMessageDialog.showMessageErrorDetails(CustomMessageDialog.MSG_ERROR_GENERIC, exi.getMessage());
				return;
			}

			if (checkMail.getValue()) {
				try {
					String dataora = getSysdate();

					sendNewMail(giornataInfo.getDescGiornataFc());

					LOG.info("send_mail OK");

					try {
						insert_dett_info(giornataInfo.getCodiceGiornata(), dataora);
						LOG.info("insert_dett_info OK");
					} catch (Exception exd) {
						LOG.error(exd.getMessage());
						CustomMessageDialog.showMessageErrorDetails(CustomMessageDialog.MSG_ERROR_GENERIC, exd.getMessage());
					}

					CustomMessageDialog.showMessageInfo("Formazione inserita, email inviata con successo!");

				} catch (Exception excpt) {
					LOG.error(excpt.getMessage());
					CustomMessageDialog.showMessageErrorDetails(CustomMessageDialog.MSG_MAIL_KO, excpt.getMessage());
					return;
				}

			} else {
				CustomMessageDialog.showMessageInfo("Formazione inserita con successo! \nPer rendere effettiva la formazione abilitare invio email.");
			}
		}
	}

	private String getSysdate() {

		String sql = "select sysdate() from dual";
		return jdbcTemplate.query(sql, new ResultSetExtractor<String>(){
			@Override
			public String extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				if (rs.next()) {

					String dataora = rs.getString(1);
					return dataora;
				}
				return null;
			}
		});
	}

	private boolean check() {

		if (modelPlayer1.size() == 0 || modelPlayer2.size() == 0 || modelPlayer3.size() == 0 || modelPlayer4.size() == 0 || modelPlayer5.size() == 0 || modelPlayer6.size() == 0 || modelPlayer7.size() == 0 || modelPlayer8.size() == 0 || modelPlayer9.size() == 0 || modelPlayer10.size() == 0 || modelPlayer11.size() == 0 || modelPlayer12.size() == 0 || modelPlayer13.size() == 0 || modelPlayer14.size() == 0 || modelPlayer15.size() == 0 || modelPlayer16.size() == 0 || modelPlayer17.size() == 0 || modelPlayer18.size() == 0 || modelPlayer19.size() == 0 || modelPlayer20.size() == 0 || modelPlayer21.size() == 0 || modelPlayer22.size() == 0 || modelPlayer23.size() == 0) {
			CustomMessageDialog.showMessageError(CustomMessageDialog.MSG_ERROR_INSERT_GIOCATORI);
			return false;
		}
		return true;
	}

	private void insert(int giornata) throws Exception {

		String query = "";
		try {
			query = " DELETE FROM fc_giornata_dett WHERE ID_GIORNATA=" + giornata + " AND ID_ATTORE=" + idAttore;
			jdbcTemplate.update(query);

			String ID_GIORNATA = "" + giornata;
			String ID_GIOCATORE = "";
			String ID_STATO_GIOCATORE = "";
			String ORDINAMENTO = "";
			int ord = 1;
			for (int i = 0; i < 23; i++) {

				ORDINAMENTO = "" + ord;
				ord++;
				if (i == 0) {
					FcGiocatore bean = (FcGiocatore) modelPlayer1.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 1) {
					FcGiocatore bean = (FcGiocatore) modelPlayer2.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 2) {
					FcGiocatore bean = (FcGiocatore) modelPlayer3.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 3) {
					FcGiocatore bean = (FcGiocatore) modelPlayer4.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 4) {
					FcGiocatore bean = (FcGiocatore) modelPlayer5.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 5) {
					FcGiocatore bean = (FcGiocatore) modelPlayer6.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 6) {
					FcGiocatore bean = (FcGiocatore) modelPlayer7.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 7) {
					FcGiocatore bean = (FcGiocatore) modelPlayer8.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 8) {
					FcGiocatore bean = (FcGiocatore) modelPlayer9.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 9) {
					FcGiocatore bean = (FcGiocatore) modelPlayer10.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 10) {
					FcGiocatore bean = (FcGiocatore) modelPlayer11.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "T";
				} else if (i == 11) {
					FcGiocatore bean = (FcGiocatore) modelPlayer12.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 12) {
					FcGiocatore bean = (FcGiocatore) modelPlayer13.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 13) {
					FcGiocatore bean = (FcGiocatore) modelPlayer14.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 14) {
					FcGiocatore bean = (FcGiocatore) modelPlayer15.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 15) {
					FcGiocatore bean = (FcGiocatore) modelPlayer16.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 16) {
					FcGiocatore bean = (FcGiocatore) modelPlayer17.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 17) {
					FcGiocatore bean = (FcGiocatore) modelPlayer18.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 18) {
					FcGiocatore bean = (FcGiocatore) modelPlayer19.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 19) {
					FcGiocatore bean = (FcGiocatore) modelPlayer20.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 20) {
					FcGiocatore bean = (FcGiocatore) modelPlayer21.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 21) {
					FcGiocatore bean = (FcGiocatore) modelPlayer22.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				} else if (i == 22) {
					FcGiocatore bean = (FcGiocatore) modelPlayer23.get(0);
					ID_GIOCATORE = "" + bean.getIdGiocatore();
					ID_STATO_GIOCATORE = "R";
				}

				query = " INSERT INTO fc_giornata_dett (ID_GIORNATA,ID_ATTORE, ID_GIOCATORE,ID_STATO_GIOCATORE,ORDINAMENTO,VOTO) VALUES (" + ID_GIORNATA + ",";
				query += idAttore + "," + ID_GIOCATORE + ",'" + ID_STATO_GIOCATORE + "'," + ORDINAMENTO + ",0)";

				jdbcTemplate.update(query);
			}

		} catch (Exception e) {
			CustomMessageDialog.showMessageErrorDetails(CustomMessageDialog.MSG_ERROR_GENERIC, e.getMessage());
		}
	}

	private void insert_dett_info(int giornata, String dataora)
			throws Exception {

		String query = "";
		try {
			query = " DELETE FROM fc_giornata_dett_info WHERE ID_GIORNATA=" + giornata + " AND ID_ATTORE=" + idAttore;
			jdbcTemplate.update(query);

			String ID_GIORNATA = "" + giornata;
			query = " INSERT INTO fc_giornata_dett_info (ID_GIORNATA,ID_ATTORE, FLAG_INVIO,DATA_INVIO) VALUES (" + ID_GIORNATA + ",";
			query += idAttore + ",1,'" + dataora + "')";

			jdbcTemplate.update(query);
		} catch (Exception e) {
			CustomMessageDialog.showMessageErrorDetails(CustomMessageDialog.MSG_ERROR_GENERIC, e.getMessage());
		}
	}

	private void sendNewMail(String desc_giornata) throws AddressException,
			IOException, MessagingException, NamingException {

		String subject = "Formazione " + descAttore + " - " + desc_giornata;

		String modulo = this.comboModulo.getValue().toString();

		String formazioneHtml = "";
		formazioneHtml += "<html><head><title>FC</title></head>\n";
		formazioneHtml += "<body>\n";
		formazioneHtml += "<p>" + desc_giornata + "</p>\n";
		formazioneHtml += "<br>\n";
		formazioneHtml += "<p>" + modulo + "</p>\n";
		formazioneHtml += "<br>\n";

		formazioneHtml += "<table>";

		String NOME_GIOCATORE = "";
		String RUOLO = "";
		String STATO = "";
		String ORDINAMENTO = "";
		String SQUADRA = "";
		int ord = 1;
		Map<String, InputStream> listImg = new HashMap<String, InputStream>();
		for (int i = 0; i < 23; i++) {

			ORDINAMENTO = "" + ord;
			FcGiocatore bean = null;

			if (i == 0) {
				bean = (FcGiocatore) modelPlayer1.get(0);
			} else if (i == 1) {
				bean = (FcGiocatore) modelPlayer2.get(0);
			} else if (i == 2) {
				bean = (FcGiocatore) modelPlayer3.get(0);
			} else if (i == 3) {
				bean = (FcGiocatore) modelPlayer4.get(0);
			} else if (i == 4) {
				bean = (FcGiocatore) modelPlayer5.get(0);
			} else if (i == 5) {
				bean = (FcGiocatore) modelPlayer6.get(0);
			} else if (i == 6) {
				bean = (FcGiocatore) modelPlayer7.get(0);
			} else if (i == 7) {
				bean = (FcGiocatore) modelPlayer8.get(0);
			} else if (i == 8) {
				bean = (FcGiocatore) modelPlayer9.get(0);
			} else if (i == 9) {
				bean = (FcGiocatore) modelPlayer10.get(0);
			} else if (i == 10) {
				bean = (FcGiocatore) modelPlayer11.get(0);
			} else if (i == 11) {
				bean = (FcGiocatore) modelPlayer12.get(0);
			} else if (i == 12) {
				bean = (FcGiocatore) modelPlayer13.get(0);
			} else if (i == 13) {
				bean = (FcGiocatore) modelPlayer14.get(0);
			} else if (i == 14) {
				bean = (FcGiocatore) modelPlayer15.get(0);
			} else if (i == 15) {
				bean = (FcGiocatore) modelPlayer16.get(0);
			} else if (i == 16) {
				bean = (FcGiocatore) modelPlayer17.get(0);
			} else if (i == 17) {
				bean = (FcGiocatore) modelPlayer18.get(0);
			} else if (i == 18) {
				bean = (FcGiocatore) modelPlayer19.get(0);
			} else if (i == 19) {
				bean = (FcGiocatore) modelPlayer20.get(0);
			} else if (i == 20) {
				bean = (FcGiocatore) modelPlayer21.get(0);
			} else if (i == 21) {
				bean = (FcGiocatore) modelPlayer22.get(0);
			} else if (i == 22) {
				bean = (FcGiocatore) modelPlayer23.get(0);
			}

			NOME_GIOCATORE = bean.getCognGiocatore();
			RUOLO = bean.getFcRuolo().getDescRuolo();
			SQUADRA = bean.getFcSquadra().getNomeSquadra();

			if (i < 11) {
				STATO = "Titolare";
			} else {
				STATO = "Riserva";
			}
			String color = "BGCOLOR=\"#FF9331\"";
			if (Integer.parseInt(ORDINAMENTO) >= 1 && Integer.parseInt(ORDINAMENTO) <= 11) {
				color = "BGCOLOR=\"#ABFF73\"";
			} else if (Integer.parseInt(ORDINAMENTO) >= 12 && Integer.parseInt(ORDINAMENTO) <= 23) {
				color = "BGCOLOR=\"#FFFF84\"";
			}

			String cidNomeSq = ContentIdGenerator.getContentId();
			FcSquadra sq = bean.getFcSquadra();
			if (sq.getImg() != null) {
				try {
					listImg.put(cidNomeSq, sq.getImg().getBinaryStream());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			formazioneHtml += "<tr " + color + ">";

			formazioneHtml += "<td>";
			formazioneHtml += ORDINAMENTO;
			formazioneHtml += "</td>";

			formazioneHtml += "<td>";
			formazioneHtml += RUOLO;
			formazioneHtml += "</td>";

			formazioneHtml += "<td>";
			formazioneHtml += NOME_GIOCATORE;
			formazioneHtml += "</td>";

			formazioneHtml += "<td><img src=\"cid:" + cidNomeSq + "\" />";
			formazioneHtml += SQUADRA;
			formazioneHtml += "</td>";

			formazioneHtml += "<td>";
			formazioneHtml += STATO;
			formazioneHtml += "</td>";

			formazioneHtml += "</tr>";

			ord++;
		}

		formazioneHtml += "</table>\n";

		formazioneHtml += "<br>\n";
		formazioneHtml += "<br>\n";
		formazioneHtml += "<p>Ciao " + descAttore + "</p>\n";
		formazioneHtml += "</body>\n";
		formazioneHtml += "<html>";

		MailClient client = new MailClient(javaMailSender);

		String email_destinatario = "";
		String ACTIVE_MAIL = (String) p.getProperty("ACTIVE_MAIL");
		if ("true".equals(ACTIVE_MAIL)) {
			List<FcAttore> attori = attoreController.findByActive(true);
			for (FcAttore a : attori) {
				if (a.isNotifiche()) {
					email_destinatario += a.getEmail() + ";";
				}
			}
		} else {
			email_destinatario = (String) p.getProperty("to");
		}

		String[] to = null;
		if (email_destinatario != null && !email_destinatario.equals("")) {
			to = Utils.tornaArrayString(email_destinatario, ";");
		}

		String[] cc = null;
		String[] bcc = null;

		String from = (String) env.getProperty("spring.mail.username");

		client.sendMail2(from, to, cc, bcc, subject, formazioneHtml, "text/html", "3", listImg);

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

	private Grid<FcCalendarioCompetizione> getTablePartite(
			List<FcCalendarioCompetizione> listPartite) {

		Grid<FcCalendarioCompetizione> grid = new Grid<>();
		grid.setItems(listPartite);
		grid.setSelectionMode(Grid.SelectionMode.NONE);
		grid.setAllRowsVisible(true);
		grid.setWidth("300px");

		Column<FcCalendarioCompetizione> nomeSquadraCasaColumn = grid.addColumn(new ComponentRenderer<>(s -> {

			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.setAlignItems(Alignment.STRETCH);
			// cellLayout.setSizeFull();
			if (s != null && s.getSquadraCasa() != null) {
				FcSquadra sq = squadraController.findByIdSquadra(s.getIdSquadraCasa());
				if (sq.getImg() != null) {
					try {
						Image img = Utils.getImage(sq.getNomeSquadra(), sq.getImg().getBinaryStream());
						cellLayout.add(img);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				Label lblSquadra = new Label(s.getSquadraCasa().substring(0, 3));
				cellLayout.add(lblSquadra);
			}

			return cellLayout;

		}));
		nomeSquadraCasaColumn.setSortable(false);
		// nomeSquadraCasaColumn.setHeader("Casa");
		nomeSquadraCasaColumn.setAutoWidth(true);

		Column<FcCalendarioCompetizione> nomeSquadraFuoriColumn = grid.addColumn(new ComponentRenderer<>(s -> {

			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			cellLayout.setAlignItems(Alignment.STRETCH);
			// cellLayout.setSizeFull();

			if (s != null && s.getSquadraCasa() != null) {
				Label lblSquadra = new Label(s.getSquadraFuori().substring(0, 3));
				FcSquadra sq = squadraController.findByIdSquadra(s.getIdSquadraFuori());
				if (sq.getImg() != null) {
					try {
						Image img = Utils.getImage(sq.getNomeSquadra(), sq.getImg().getBinaryStream());
						cellLayout.add(img);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				cellLayout.add(lblSquadra);
			}

			return cellLayout;

		}));
		nomeSquadraFuoriColumn.setSortable(false);
		// nomeSquadraFuoriColumn.setHeader("Fuori");
		nomeSquadraFuoriColumn.setAutoWidth(true);

		Column<FcCalendarioCompetizione> dataColumn = grid.addColumn(new LocalDateTimeRenderer<>(FcCalendarioCompetizione::getData,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).withLocale(Locale.ITALY)));
		dataColumn.setSortable(false);
		dataColumn.setAutoWidth(true);
		dataColumn.setFlexGrow(2);

		return grid;
	}

}
