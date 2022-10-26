package fcweb.ui.views.em;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.vaadin.tabs.PagedTabs;

import com.flowingcode.vaadin.addons.simpletimer.SimpleTimer;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import common.util.Utils;
import fcweb.backend.data.entity.FcCalendarioTim;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.data.entity.FcSquadra;
import fcweb.backend.service.AccessoController;
import fcweb.backend.service.CalendarioTimController;
import fcweb.backend.service.GiornataInfoController;
import fcweb.backend.service.SquadraController;
import fcweb.ui.MainAppLayout;
import fcweb.utils.Costants;

@Route(value = "emhome", layout = MainAppLayout.class)
@PageTitle("Home")
public class EmHomeView extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment env;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private GiornataInfoController giornataInfoController;

	@Autowired
	private CalendarioTimController calendarioTimController;

	@Autowired
	private AccessoController accessoController;

	@Autowired
	private SquadraController squadraController;

	public EmHomeView() {
		LOG.info("EmHomeView()");
	}

	@PostConstruct
	void init() {
		try {
			LOG.info("init");
			if (!Utils.isValidVaadinSession()) {
				return;
			}
			accessoController.insertAccesso(this.getClass().getName());

			Image img = buildImage("classpath:images/", (String) env.getProperty("img.logo"));
			this.add(img);
			setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, img);

			this.add(builLayoutAvviso());

			buildGiornate();

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	private void buildGiornate() throws Exception {

		FcGiornataInfo giornataInfo = (FcGiornataInfo) VaadinSession.getCurrent().getAttribute("GIORNATA_INFO");
		FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
		Integer from = campionato.getStart();
		Integer to = campionato.getEnd();
		List<FcGiornataInfo> giornate = giornataInfoController.findByCodiceGiornataGreaterThanEqualAndCodiceGiornataLessThanEqual(from, to);

		VerticalLayout container = new VerticalLayout();
		PagedTabs tabs = new PagedTabs(container);
		for (FcGiornataInfo g : giornate) {
			List<FcCalendarioTim> listPartite = calendarioTimController.findByIdGiornataOrderByDataAsc(g.getCodiceGiornata());
			Grid<FcCalendarioTim> tablePartite = getTablePartite(listPartite);
			final VerticalLayout layout = new VerticalLayout();
			layout.setMargin(false);
			layout.setPadding(false);
			layout.setSpacing(false);
			layout.add(tablePartite);
			Tab tab = tabs.add(g.getDescGiornata(), layout, false);
			if (g.getDescGiornata().equals(giornataInfo.getDescGiornata())) {
				LOG.info(" selected tab " + giornataInfo.getDescGiornata());
				tabs.select(tab);
			}
		}

		this.add(tabs, container);
	}

	private Grid<FcCalendarioTim> getTablePartite(
			List<FcCalendarioTim> listPartite) throws Exception {

		Grid<FcCalendarioTim> grid = new Grid<>();
		grid.setItems(listPartite);
		grid.setSelectionMode(Grid.SelectionMode.NONE);
		grid.setAllRowsVisible(true);

		Column<FcCalendarioTim> dataColumn = grid.addColumn(
				//new LocalDateTimeRenderer<>(FcCalendarioTim::getData,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).withLocale(Locale.ITALY)))
				new LocalDateTimeRenderer<>(FcCalendarioTim::getData));
		dataColumn.setSortable(false);
		dataColumn.setAutoWidth(true);
		//dataColumn.setFlexGrow(2);

		Column<FcCalendarioTim> nomeSquadraCasaColumn = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			// cellLayout.setMargin(false);
			// cellLayout.setPadding(false);
			// cellLayout.setSpacing(false);
			// cellLayout.setAlignItems(Alignment.STRETCH);
			// cellLayout.setSizeFull();
			if (s != null && s.getSquadraCasa() != null) {
				// lblSquadra.getStyle().set("font-size", "11px");
//				Image img = buildImage("classpath:/img/nazioni/", s.getSquadraCasa() + ".png");
//				cellLayout.add(img);
				FcSquadra sq = squadraController.findByNomeSquadra(s.getSquadraCasa());
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
		nomeSquadraCasaColumn.setAutoWidth(true);

		Column<FcCalendarioTim> nomeSquadraFuoriColumn = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			// cellLayout.setMargin(false);
			// cellLayout.setPadding(false);
			// cellLayout.setSpacing(false);
			// cellLayout.setAlignItems(Alignment.STRETCH);
			// cellLayout.setSizeFull();
			if (s != null && s.getSquadraCasa() != null) {
				// lblSquadra.getStyle().set("font-size", "11px");
//				Image img = buildImage("classpath:/img/nazioni/", s.getSquadraFuori() + ".png");
//				cellLayout.add(img);
				FcSquadra sq = squadraController.findByNomeSquadra(s.getSquadraFuori());
				if (sq.getImg() != null) {
					try {
						Image img = Utils.getImage(sq.getNomeSquadra(), sq.getImg().getBinaryStream());
						cellLayout.add(img);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				Label lblSquadra = new Label(s.getSquadraFuori().substring(0, 3));
				cellLayout.add(lblSquadra);
			}
			return cellLayout;
		}));
		nomeSquadraFuoriColumn.setSortable(false);
		nomeSquadraFuoriColumn.setAutoWidth(true);

		Column<FcCalendarioTim> risultatoColumn = grid.addColumn(c -> c != null && c.getRisultato() != null ? c.getRisultato() : "");
		risultatoColumn.setSortable(false);
		risultatoColumn.setAutoWidth(true);
		//risultatoColumn.setFlexGrow(2);

		return grid;
	}

	private VerticalLayout builLayoutAvviso() throws Exception {

		FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
		FcGiornataInfo giornataInfo = (FcGiornataInfo) VaadinSession.getCurrent().getAttribute("GIORNATA_INFO");
		String nextDate = (String) VaadinSession.getCurrent().getAttribute("NEXTDATE");
		long millisDiff = (long) VaadinSession.getCurrent().getAttribute("MILLISDIFF");
		LOG.info("millisDiff " + millisDiff);

		final VerticalLayout layoutAvviso = new VerticalLayout();
		layoutAvviso.getStyle().set("border", Costants.BORDER_COLOR);
		layoutAvviso.getStyle().set("background", Costants.YELLOW);

		HorizontalLayout cssLayout = new HorizontalLayout();
		Label lblInfo = new Label("Prossima Giornata: " + Utils.buildInfoGiornataEm(giornataInfo, campionato));
		cssLayout.add(lblInfo);
		layoutAvviso.add(cssLayout);

		HorizontalLayout cssLayout2 = new HorizontalLayout();
		Label lblInfo2 = new Label("Consegna Formazione entro: " + nextDate);
		cssLayout2.add(lblInfo2);
		layoutAvviso.add(cssLayout2);

		SimpleTimer timer = new SimpleTimer(new BigDecimal(millisDiff / 1000));
		timer.setHours(true);
		timer.setMinutes(true);
		timer.setFractions(false);
		timer.start();
		timer.isRunning();
		timer.addTimerEndEvent(ev -> Notification.show("Timer ended"));
		layoutAvviso.add(timer);

		return layoutAvviso;
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