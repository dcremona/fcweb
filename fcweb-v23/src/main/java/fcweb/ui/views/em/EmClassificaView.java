package fcweb.ui.views.em;

import java.io.InputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.vaadin.olli.FileDownloadWrapper;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.GridVariant;
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
import fcweb.backend.data.ClassificaBean;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.service.AccessoService;
import fcweb.backend.service.ClassificaTotalePuntiService;
import fcweb.ui.MainAppLayout;
import fcweb.utils.Costants;
import fcweb.utils.JasperReporUtils;

@Route(value = "emclassifica", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("Classifica")
public class EmClassificaView extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClassificaTotalePuntiService classificaTotalePuntiController;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private AccessoService accessoController;

	@Autowired
	private Environment env;

	private List<ClassificaBean> items = null;
	private FcGiornataInfo giornataInfo = null;
	private Properties p = null;

	@PostConstruct
	void init() throws Exception {
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

	private void initData() throws Exception {

		p = (Properties) VaadinSession.getCurrent().getAttribute("PROPERTIES");
		giornataInfo = (FcGiornataInfo) VaadinSession.getCurrent().getAttribute("GIORNATA_INFO");

		items = classificaTotalePuntiController.getModelClassifica(giornataInfo.getIdGiornataFc());
	}

	private void initLayout() throws Exception {

		LOG.info("initLayout");

		HorizontalLayout layoutGrid = new HorizontalLayout();
		layoutGrid.setMargin(false);
		layoutGrid.setPadding(false);
		layoutGrid.setSpacing(false);
		layoutGrid.setSizeFull();

		Grid<ClassificaBean> grid;
		try {
			grid = buildTableClassifica(items, giornataInfo);
			layoutGrid.add(grid);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}

		try {
			this.add(buildButtonPdf(p));
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		this.add(layoutGrid);
		try {
			this.add(buildGrafico(items));
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Component buildGrafico(List<ClassificaBean> items) throws Exception {

		String[] att = new String[items.size()];
		String[] data = new String[items.size()];

		int i = 0;
		Series series = new Series("Tot Pt");
		for (ClassificaBean cl : items) {
			String sq = cl.getSquadra();
			double puntiRosa = (cl.getTotPunti() / Costants.DIVISORE_10);
			att[i] = sq;
			data[i] = new String("" + puntiRosa);
			i++;
		}
		series.setData(data);

		ApexCharts barChart = ApexChartsBuilder.get().withChart(ChartBuilder.get().withType(Type.bar).build())

				.withTitle(TitleSubtitleBuilder.get().withText("Totale Punti").withAlign(Align.left).build()).withPlotOptions(PlotOptionsBuilder.get().withBar(BarBuilder.get().withHorizontal(false).build()).build())

				.withDataLabels(DataLabelsBuilder.get().withEnabled(false).build())

				.withSeries(series)

				.withXaxis(XAxisBuilder.get().withCategories(att).build()).build();

		barChart.setWidth("800px");
		barChart.setHeight("600px");

		return barChart;
	}

	private HorizontalLayout buildButtonPdf(Properties p) throws Exception {

		Button stampapdf = new Button("Classifica pdf");
		stampapdf.setIcon(VaadinIcon.DOWNLOAD.create());
		FileDownloadWrapper button1Wrapper = new FileDownloadWrapper(new StreamResource("Classifica.pdf",() -> {
			try {
				String imgLog = (String) env.getProperty("img.logo");
				Map<String, Object> hm = new HashMap<String, Object>();
				hm.put("DIVISORE", "" + Costants.DIVISORE_10);
				hm.put("PATH_IMG", "images/" + imgLog);
				Resource resource = resourceLoader.getResource("classpath:reports/em/classifica.jasper");
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

	private Grid<ClassificaBean> buildTableClassifica(
			List<ClassificaBean> items, FcGiornataInfo giornataInfo)
			throws Exception {

		Grid<ClassificaBean> grid = new Grid<>();
		grid.setItems(items);
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
		grid.setAllRowsVisible(true);
		grid.setSelectionMode(Grid.SelectionMode.NONE);
		grid.setMultiSort(true);

		Column<ClassificaBean> posizioneColumn = grid.addColumn(new ComponentRenderer<>(classifica -> {
			int x = items.indexOf(classifica) + 1;
			Label lblPosizione = new Label("" + x);
			return lblPosizione;
		})).setHeader("");
		posizioneColumn.setSortable(false);

		Column<ClassificaBean> squadraColumn = grid.addColumn(classifica -> classifica.getSquadra());
		squadraColumn.setSortable(false);
		squadraColumn.setHeader("Squadra");

		Column<ClassificaBean> totPuntiColumn = grid.addColumn(new ComponentRenderer<>(classifica -> {
			DecimalFormat myFormatter = new DecimalFormat("#0.00");
			Double dTotPunti = classifica.getTotPunti() != null ? classifica.getTotPunti() / Costants.DIVISORE_10 : 0;
			String sTotPunti = myFormatter.format(dTotPunti);

			Label lblTotPunti = new Label(sTotPunti);

			lblTotPunti.getStyle().set("font-size", "14px");
			lblTotPunti.getStyle().set("color", Costants.BLUE);
			lblTotPunti.getElement().getStyle().set("-webkit-text-fill-color", Costants.BLUE);
			return lblTotPunti;

		})).setHeader("Totale Punti");
		totPuntiColumn.setSortable(true);
		totPuntiColumn.setComparator((p1,
				p2) -> p1.getTotPunti().compareTo(p2.getTotPunti()));

		Column<ClassificaBean> parzialePuntiColumn = grid.addColumn(new ComponentRenderer<>(classifica -> {
			DecimalFormat myFormatter = new DecimalFormat("#0.00");
			Double dTotPunti = classifica.getTotPuntiParziale() != null ? classifica.getTotPuntiParziale() / Costants.DIVISORE_10 : 0;
			String sTotPunti = myFormatter.format(dTotPunti);
			return new Label(sTotPunti);
		})).setHeader("Parziale Punti");
		parzialePuntiColumn.setSortable(true);
		parzialePuntiColumn.setComparator((p1,
				p2) -> p1.getTotPuntiParziale().compareTo(p2.getTotPuntiParziale()));

		if (giornataInfo.getIdGiornataFc() >= 1) {
			Column<ClassificaBean> punti1Column = grid.addColumn(new ComponentRenderer<>(classifica -> {
				DecimalFormat myFormatter = new DecimalFormat("#0.00");
				Double dTotPunti = classifica.getPuntiGiornata1() != null ? classifica.getPuntiGiornata1() / Costants.DIVISORE_10 : 0;
				String sTotPunti = myFormatter.format(dTotPunti);
				return new Label(sTotPunti);
			})).setHeader("Punti_1");
			punti1Column.setSortable(true);
			punti1Column.setComparator((p1,
					p2) -> p1.getPuntiGiornata1().compareTo(p2.getPuntiGiornata1()));
		}

		if (giornataInfo.getIdGiornataFc() >= 2) {
			Column<ClassificaBean> punti2Column = grid.addColumn(new ComponentRenderer<>(classifica -> {
				DecimalFormat myFormatter = new DecimalFormat("#0.00");
				Double dTotPunti = classifica.getPuntiGiornata2() != null ? classifica.getPuntiGiornata2() / Costants.DIVISORE_10 : 0;
				String sTotPunti = myFormatter.format(dTotPunti);
				return new Label(sTotPunti);
			})).setHeader("Punti_2");
			punti2Column.setSortable(true);
			punti2Column.setComparator((p1,
					p2) -> p1.getPuntiGiornata2().compareTo(p2.getPuntiGiornata2()));
		}

		if (giornataInfo.getIdGiornataFc() >= 3) {
			Column<ClassificaBean> punti3Column = grid.addColumn(new ComponentRenderer<>(classifica -> {
				DecimalFormat myFormatter = new DecimalFormat("#0.00");
				Double dTotPunti = classifica.getPuntiGiornata3() != null ? classifica.getPuntiGiornata3() / Costants.DIVISORE_10 : 0;
				String sTotPunti = myFormatter.format(dTotPunti);
				return new Label(sTotPunti);
			})).setHeader("Punti_3");
			punti3Column.setSortable(true);
			punti3Column.setComparator((p1,
					p2) -> p1.getPuntiGiornata3().compareTo(p2.getPuntiGiornata3()));
		}

		if (giornataInfo.getIdGiornataFc() >= 4) {
			Column<ClassificaBean> punti4Column = grid.addColumn(new ComponentRenderer<>(classifica -> {
				DecimalFormat myFormatter = new DecimalFormat("#0.00");
				Double dTotPunti = classifica.getPuntiGiornata4() != null ? classifica.getPuntiGiornata4() / Costants.DIVISORE_10 : 0;
				String sTotPunti = myFormatter.format(dTotPunti);
				return new Label(sTotPunti);
			})).setHeader("Punti_4");
			punti4Column.setSortable(true);
			punti4Column.setComparator((p1,
					p2) -> p1.getPuntiGiornata4().compareTo(p2.getPuntiGiornata4()));
		}

		if (giornataInfo.getIdGiornataFc() >= 5) {
			Column<ClassificaBean> punti5Column = grid.addColumn(new ComponentRenderer<>(classifica -> {
				DecimalFormat myFormatter = new DecimalFormat("#0.00");
				Double dTotPunti = classifica.getPuntiGiornata5() != null ? classifica.getPuntiGiornata5() / Costants.DIVISORE_10 : 0;
				String sTotPunti = myFormatter.format(dTotPunti);
				return new Label(sTotPunti);
			})).setHeader("Punti_5");
			punti5Column.setSortable(true);
			punti5Column.setComparator((p1,
					p2) -> p1.getPuntiGiornata5().compareTo(p2.getPuntiGiornata5()));
		}

		if (giornataInfo.getIdGiornataFc() >= 6) {
			Column<ClassificaBean> punti6Column = grid.addColumn(new ComponentRenderer<>(classifica -> {
				DecimalFormat myFormatter = new DecimalFormat("#0.00");
				Double dTotPunti = classifica.getPuntiGiornata6() != null ? classifica.getPuntiGiornata6() / Costants.DIVISORE_10 : 0;
				String sTotPunti = myFormatter.format(dTotPunti);
				return new Label(sTotPunti);
			})).setHeader("Punti_6");
			punti6Column.setSortable(true);
			punti6Column.setComparator((p1,
					p2) -> p1.getPuntiGiornata6().compareTo(p2.getPuntiGiornata6()));
		}

		if (giornataInfo.getIdGiornataFc() >= 7) {
			Column<ClassificaBean> punti7Column = grid.addColumn(new ComponentRenderer<>(classifica -> {
				DecimalFormat myFormatter = new DecimalFormat("#0.00");
				Double dTotPunti = classifica.getPuntiGiornata7() != null ? classifica.getPuntiGiornata7() / Costants.DIVISORE_10 : 0;
				String sTotPunti = myFormatter.format(dTotPunti);
				return new Label(sTotPunti);
			})).setHeader("Punti_7");
			punti7Column.setSortable(true);
			punti7Column.setComparator((p1,
					p2) -> p1.getPuntiGiornata7().compareTo(p2.getPuntiGiornata7()));
		}

		return grid;
	}
}