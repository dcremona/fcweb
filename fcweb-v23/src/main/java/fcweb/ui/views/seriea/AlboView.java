package fcweb.ui.views.seriea;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import common.util.Utils;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.data.entity.FcExpStat;
import fcweb.backend.service.AccessoService;
import fcweb.backend.service.AlboService;
import fcweb.backend.service.AttoreService;
import fcweb.ui.MainAppLayout;
import fcweb.utils.Costants;;

@Route(value = "albo", layout = MainAppLayout.class)
@PageTitle("Albo")
@PreserveOnRefresh
public class AlboView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AlboService alboController;

	@Autowired
	private AttoreService attoreController;

	@Autowired
	private AccessoService accessoController;

	public AlboView() {
		LOG.info("AlboView()");
	}

	@PostConstruct
	void init() {
		LOG.debug("init");
		if (!Utils.isValidVaadinSession()) {
			return;
		}
		FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
		FcAttore attore = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
		accessoController.insertAccesso(campionato,attore,this.getClass().getName());

		initLayout();
	}

	private void initLayout() {

		List<FcExpStat> items = alboController.findAll();
		// items.sort((p1, p2) ->
		// p2.getAnno().compareToIgnoreCase(p1.getAnno()));
		this.add(getGrid(items));

		List<FcExpStat> modelCrosstab = getModelCrosstab(items);
		modelCrosstab.sort((p1, p2) -> p2.getScudetto().compareToIgnoreCase(p1.getScudetto()));
		this.add(getGrid2(modelCrosstab));

	}

	private Grid<FcExpStat> getGrid(List<FcExpStat> items) {

		Grid<FcExpStat> grid = new Grid<>();
		grid.setItems(items);
		// grid.setSizeFull();
		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
				GridVariant.LUMO_ROW_STRIPES);
		grid.setAllRowsVisible(true);

		// Column<FcExpStat> annoColumn = grid.addColumn(s -> s.getAnno());
		// annoColumn.setSortable(false);
		// annoColumn.setHeader("Anno");

		Column<FcExpStat> campionatoColumn = grid.addColumn(s -> s.getAnno() + " " + s.getCampionato());
		campionatoColumn.setSortable(false);
		campionatoColumn.setResizable(false);
		campionatoColumn.setHeader("Campionato");
		campionatoColumn.setWidth("150px");

		// Column<FcExpStat> scudettoColumn = grid.addColumn(s ->
		// s.getScudetto());
		// scudettoColumn.setSortable(false);
		Column<FcExpStat> scudettoColumn = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getScudetto())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getScudetto());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		scudettoColumn.setSortable(false);
		scudettoColumn.setResizable(false);
		scudettoColumn.setHeader("Scudetto");

		// Column<FcExpStat> p2Column = grid.addColumn(s -> s.getP2());
		// p2Column.setSortable(false);
		Column<FcExpStat> p2Column = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getP2())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getP2());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		p2Column.setSortable(false);
		p2Column.setResizable(false);
		p2Column.setHeader("Finalista");

		// Column<FcExpStat> p3Column = grid.addColumn(s -> s.getP3());
		// p3Column.setSortable(false);
		Column<FcExpStat> p3Column = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getP3())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getP3());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		p3Column.setSortable(false);
		p3Column.setResizable(false);
		p3Column.setHeader("3 Posto");

		// Column<FcExpStat> p4Column = grid.addColumn(s -> s.getP4());
		// p4Column.setSortable(false);
		Column<FcExpStat> p4Column = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getP4())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getP4());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		p4Column.setSortable(false);
		p4Column.setResizable(false);
		p4Column.setHeader("4 Posto");

		// Column<FcExpStat> p5Column = grid.addColumn(s -> s.getP5());
		// p5Column.setSortable(false);
		Column<FcExpStat> p5Column = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getP5())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getP5());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		p5Column.setSortable(false);
		p5Column.setResizable(false);
		p5Column.setHeader("5 Posto");

		// Column<FcExpStat> p6Column = grid.addColumn(s -> s.getP6());
		// p6Column.setSortable(false);
		Column<FcExpStat> p6Column = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getP6())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getP6());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		p6Column.setSortable(false);
		p6Column.setResizable(false);
		p6Column.setHeader("6 Posto");

		// Column<FcExpStat> p7Column = grid.addColumn(s -> s.getP7());
		// p7Column.setSortable(false);
		Column<FcExpStat> p7Column = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getP7())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getP7());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		p7Column.setSortable(false);
		p7Column.setResizable(false);
		p7Column.setHeader("7 Posto");

		// Column<FcExpStat> p8Column = grid.addColumn(s -> s.getP8());
		// p8Column.setSortable(false);
		Column<FcExpStat> p8Column = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getP8())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getP8());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		p8Column.setSortable(false);
		p8Column.setResizable(false);
		p8Column.setHeader("8 Posto");

		// Column<FcExpStat> winClasPtColumn = grid.addColumn(s ->
		// s.getWinClasPt());
		// winClasPtColumn.setSortable(false);
		Column<FcExpStat> winClasPtColumn = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getWinClasPt())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getWinClasPt());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		winClasPtColumn.setSortable(false);
		winClasPtColumn.setResizable(false);
		winClasPtColumn.setHeader("Clas Punti");

		// Column<FcExpStat> winClasRegColumn = grid.addColumn(s ->
		// s.getWinClasReg());
		// winClasRegColumn.setSortable(false);
		Column<FcExpStat> winClasRegColumn = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getWinClasReg())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getWinClasReg());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		winClasRegColumn.setSortable(false);
		winClasRegColumn.setResizable(false);
		winClasRegColumn.setHeader("Clas Regolare");

		Column<FcExpStat> winTvsTColumn = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			if (att.getDescAttore().equals(s.getWinClasTvsT())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}
			Label lblAttore = new Label(s.getWinClasTvsT());
			lblAttore.getStyle().set("fontSize", "smaller");
			cellLayout.add(lblAttore);
			return cellLayout;
		}));
		winTvsTColumn.setSortable(false);
		winTvsTColumn.setResizable(false);
		winTvsTColumn.setHeader("Clas TvsT");

		Column<FcExpStat> tripleteColumn = grid.addColumn(new ComponentRenderer<>(s -> {
			HorizontalLayout cellLayout = new HorizontalLayout();
			cellLayout.setMargin(false);
			cellLayout.setPadding(false);
			cellLayout.setSpacing(false);
			if (s.getScudetto().equals(s.getWinClasPt()) && s.getScudetto().equals(s.getWinClasReg())) {
				Label lblAttore = new Label(s.getScudetto());
				lblAttore.getStyle().set("fontSize", "smaller");
				cellLayout.add(lblAttore);
			}

			cellLayout.getStyle().set("color", Costants.LIGHT_GRAY);
			FcAttore att = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
			if (att.getDescAttore().equals(s.getScudetto()) && att.getDescAttore().equals(s.getWinClasPt())
					&& att.getDescAttore().equals(s.getWinClasReg())) {
				cellLayout.getStyle().set("color", Costants.GRAY);
			}

			return cellLayout;
		}));
		tripleteColumn.setSortable(false);
		tripleteColumn.setResizable(false);
		tripleteColumn.setHeader("Triplete");

		return grid;
	}

	private Grid<FcExpStat> getGrid2(List<FcExpStat> items) {

		Grid<FcExpStat> grid = new Grid<>();
		grid.setItems(items);
		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
				GridVariant.LUMO_ROW_STRIPES);
		grid.setAllRowsVisible(true);
		// grid.setSizeFull();

		Column<FcExpStat> annoColumn = grid.addColumn(s -> s.getAnno());
		annoColumn.setSortable(true);
		annoColumn.setHeader("Squadra");

		Column<FcExpStat> scudettoColumn = grid.addColumn(s -> s.getScudetto());
		scudettoColumn.setSortable(true);
		scudettoColumn.setHeader("Scudetto");

		Column<FcExpStat> p2Column = grid.addColumn(s -> s.getP2());
		p2Column.setSortable(true);
		p2Column.setHeader("Finalista");

		Column<FcExpStat> p3Column = grid.addColumn(s -> s.getP3());
		p3Column.setSortable(true);
		p3Column.setHeader("3 Posto");

		Column<FcExpStat> p4Column = grid.addColumn(s -> s.getP4());
		p4Column.setSortable(true);
		p4Column.setHeader("4 Posto");

		Column<FcExpStat> p5Column = grid.addColumn(s -> s.getP5());
		p5Column.setSortable(true);
		p5Column.setHeader("5 Posto");

		Column<FcExpStat> p6Column = grid.addColumn(s -> s.getP6());
		p6Column.setSortable(true);
		p6Column.setHeader("6 Posto");

		Column<FcExpStat> p7Column = grid.addColumn(s -> s.getP7());
		p7Column.setSortable(true);
		p7Column.setHeader("7 Posto");

		Column<FcExpStat> p8Column = grid.addColumn(s -> s.getP8());
		p8Column.setSortable(true);
		p8Column.setHeader("8 Posto");

		Column<FcExpStat> winClasPtColumn = grid.addColumn(s -> s.getWinClasPt());
		winClasPtColumn.setSortable(true);
		winClasPtColumn.setHeader("Clas Punti");

		Column<FcExpStat> winClasRegColumn = grid.addColumn(s -> s.getWinClasReg());
		winClasRegColumn.setSortable(true);
		winClasRegColumn.setHeader("Clas Regolare");

		Column<FcExpStat> winClasTvsTColumn = grid.addColumn(s -> s.getWinClasTvsT());
		winClasTvsTColumn.setSortable(true);
		winClasTvsTColumn.setHeader("Clas TvsT");

		return grid;

	}

	private List<FcExpStat> getModelCrosstab(List<FcExpStat> all) {

		List<FcExpStat> beans = new ArrayList<FcExpStat>();

		List<FcAttore> squadre = attoreController.findAll();

		for (FcAttore attore : squadre) {

			// if (attore.getIdAttore() > 0 && attore.getIdAttore() < 9) {

			String squadra = attore.getDescAttore();

			int count_scudetto = 0;
			int count_p2 = 0;
			int count_p3 = 0;
			int count_p4 = 0;
			int count_p5 = 0;
			int count_p6 = 0;
			int count_p7 = 0;
			int count_p8 = 0;
			int count_win_clas_pt = 0;
			int count_win_clas_reg = 0;
			int count_win_clas_tvst = 0;

			for (FcExpStat bean : all) {

				try {

					if (bean.getScudetto().equals(squadra)) {
						count_scudetto++;
					}
					if (bean.getP2().equals(squadra)) {
						count_p2++;
					}
					if (bean.getP3().equals(squadra)) {
						count_p3++;
					}
					if (bean.getP4().equals(squadra)) {
						count_p4++;
					}
					if (bean.getP5().equals(squadra)) {
						count_p5++;
					}
					if (bean.getP6().equals(squadra)) {
						count_p6++;
					}
					if (bean.getP7().equals(squadra)) {
						count_p7++;
					}
					if (bean.getP8().equals(squadra)) {
						count_p8++;
					}
					if (bean.getWinClasPt().equals(squadra)) {
						count_win_clas_pt++;
					}
					if (bean.getWinClasReg().equals(squadra)) {
						count_win_clas_reg++;
					}
					if (bean.getWinClasTvsT().equals(squadra)) {
						count_win_clas_tvst++;
					}

				} catch (Exception e) {
					continue;
				}
			}

			FcExpStat b = new FcExpStat();
			b.setAnno(squadra);
			b.setScudetto(count_scudetto < 10 ? "0" + count_scudetto : "" + count_scudetto);
			b.setP2(count_p2 < 10 ? "0" + count_p2 : "" + count_p2);
			b.setP3(count_p3 < 10 ? "0" + count_p3 : "" + count_p3);
			b.setP4(count_p4 < 10 ? "0" + count_p4 : "" + count_p4);
			b.setP5(count_p5 < 10 ? "0" + count_p5 : "" + count_p5);
			b.setP6(count_p6 < 10 ? "0" + count_p6 : "" + count_p6);
			b.setP7(count_p7 < 10 ? "0" + count_p7 : "" + count_p7);
			b.setP8(count_p8 < 10 ? "0" + count_p8 : "" + count_p8);

			b.setWinClasPt(count_win_clas_pt < 10 ? "0" + count_win_clas_pt : "" + count_win_clas_pt);
			b.setWinClasReg(count_win_clas_reg < 10 ? "0" + count_win_clas_reg : "" + count_win_clas_reg);
			b.setWinClasTvsT(count_win_clas_tvst < 10 ? "0" + count_win_clas_tvst : "" + count_win_clas_tvst);

			beans.add(b);
			// }
		}

		return beans;
	}

}