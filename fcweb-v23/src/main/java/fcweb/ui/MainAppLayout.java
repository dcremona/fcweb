package fcweb.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

import common.util.Utils;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.ui.views.admin.FcAccessoView;
import fcweb.ui.views.admin.FcCalendarioTimView;
import fcweb.ui.views.admin.FcCampionatoView;
import fcweb.ui.views.admin.FcClassificaView;
import fcweb.ui.views.admin.FcExpStatView;
import fcweb.ui.views.admin.FcFormazioneView;
import fcweb.ui.views.admin.FcGiocatoreView;
import fcweb.ui.views.admin.FcGiornataDettView;
import fcweb.ui.views.admin.FcGiornataInfoView;
import fcweb.ui.views.admin.FcGiornataView;
import fcweb.ui.views.admin.FcMercatoDettView;
import fcweb.ui.views.admin.FcPagelleView;
import fcweb.ui.views.admin.FcPropertiesView;
import fcweb.ui.views.admin.FcSquadraView;
import fcweb.ui.views.admin.FcUserView;
import fcweb.ui.views.em.EmClassificaView;
import fcweb.ui.views.em.EmDownloadView;
import fcweb.ui.views.em.EmFormazioniView;
import fcweb.ui.views.em.EmHomeView;
import fcweb.ui.views.em.EmImpostazioniView;
import fcweb.ui.views.em.EmMercatoView;
import fcweb.ui.views.em.EmRegolamentoView;
import fcweb.ui.views.em.EmSquadreView;
import fcweb.ui.views.em.EmStatisticheView;
import fcweb.ui.views.em.EmTeamInsertView;
import fcweb.ui.views.seriea.AlboView;
import fcweb.ui.views.seriea.CalendarioView;
import fcweb.ui.views.seriea.ClassificaView;
import fcweb.ui.views.seriea.DownloadView;
import fcweb.ui.views.seriea.FormazioniView;
import fcweb.ui.views.seriea.FreePlayersView;
import fcweb.ui.views.seriea.HomeView;
import fcweb.ui.views.seriea.ImpostazioniView;
import fcweb.ui.views.seriea.MercatoView;
import fcweb.ui.views.seriea.RegolamentoView;
import fcweb.ui.views.seriea.SquadreAllView;
import fcweb.ui.views.seriea.SquadreView;
import fcweb.ui.views.seriea.StatisticheView;
import fcweb.ui.views.seriea.TeamInsertMobileView;
import fcweb.ui.views.seriea.TeamInsertView;
import fcweb.ui.views.user.SettingsView;

/*
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PWA(name = "FcWeb Project", shortName = "FcWeb")
public class MainAppLayout
		extends AppLayoutRouterLayout<LeftLayouts.LeftResponsiveHybrid>{
	private static final long serialVersionUID = 1L;
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ResourceLoader resourceLoader;

	private Image buildImage(String nomeImg) {
		StreamResource resource = new StreamResource(nomeImg,() -> {
			Resource r = resourceLoader.getResource("classpath:images/" + nomeImg);
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

	private DefaultNotificationHolder notifications = new DefaultNotificationHolder();

	public MainAppLayout() {

		LOG.info("START MainAppLayout");

		try {

			if (Utils.isValidVaadinSession()) {

				initNotifications();

				FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
				FcAttore attore = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
				String title = campionato.getDescCampionato();
				Component appMenu = null;
				if ("1".equals(campionato.getType())) {
					appMenu = buildAppMenu(attore);
				} else {
					appMenu = buildAppMenuEm(attore);
				}

				HorizontalLayout layoutTitle = new HorizontalLayout();
				layoutTitle.setId("header");
				layoutTitle.setWidthFull();
				layoutTitle.setSpacing(false);
				layoutTitle.setAlignItems(FlexComponent.Alignment.CENTER);
				H6 viewTitle = new H6(title);
				layoutTitle.add(viewTitle);

				init(AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class).withTitle(layoutTitle).withAppBar(buildAppBar(attore.getDescAttore())).withIconComponent(buildImage("logo.png"))
						// .withSwipeOpen(false)
						.withAppMenu(appMenu).build());
			} else {

				LeftClickableItem menuLogin = new LeftClickableItem("Login",VaadinIcon.ENTER.create(),clickEvent -> {
					UI.getCurrent().getPage().executeJs("window.location.href='/fcWeb'");
				});

				init(AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class).withTitle("").withAppBar(menuLogin).withIconComponent(buildImage("header.jpg")).build());

			}

		} catch (Exception e) {
			e.printStackTrace();
			UI.getCurrent().getPage().executeJs("window.location.href='/fcWeb'");
		}
	}

	private Component buildAppMenu(FcAttore attore) {

		LeftNavigationItem menuHome = new LeftNavigationItem("Home",VaadinIcon.HOME.create(),HomeView.class);
		// badge.bind(menuHome.getBadge());
		LeftNavigationItem menuInsert = new LeftNavigationItem("Schiera Formazione",VaadinIcon.INSERT.create(),TeamInsertView.class);
		LeftNavigationItem menuInsertMobile = new LeftNavigationItem("Mobile",VaadinIcon.MOBILE_BROWSER.create(),TeamInsertMobileView.class);

		LeftSubMenuBuilder menuSquadreSub = LeftSubMenuBuilder.get("Squadre", VaadinIcon.DATABASE.create());
		LeftNavigationItem menuRose = new LeftNavigationItem("Rose",VaadinIcon.USER.create(),SquadreView.class);
		LeftNavigationItem menuRoseAll = new LeftNavigationItem("Tutte le Rose",VaadinIcon.USER_STAR.create(),SquadreAllView.class);
		menuSquadreSub.add(menuRose);
		menuSquadreSub.add(menuRoseAll);
		Component compSquadre = menuSquadreSub.build();

		LeftNavigationItem menuCalendario = new LeftNavigationItem("Calendario",VaadinIcon.CALENDAR.create(),CalendarioView.class);
		LeftNavigationItem menuClassifica = new LeftNavigationItem("Classifica",VaadinIcon.TABLE.create(),ClassificaView.class);
		LeftNavigationItem menuFormazioni = new LeftNavigationItem("Formazioni",VaadinIcon.CALENDAR_USER.create(),FormazioniView.class);
		LeftNavigationItem menuStatistiche = new LeftNavigationItem("Statistiche",VaadinIcon.CALC.create(),StatisticheView.class);
		LeftNavigationItem menuDownload = new LeftNavigationItem("Download",VaadinIcon.DOWNLOAD.create(),DownloadView.class);
		LeftNavigationItem menuAlbo = new LeftNavigationItem("Albo",VaadinIcon.ARCHIVES.create(),AlboView.class);
		LeftNavigationItem menuRegolamento = new LeftNavigationItem("Regolamento",VaadinIcon.CREDIT_CARD.create(),RegolamentoView.class);

		LeftSubMenuBuilder menuAdminSub = LeftSubMenuBuilder.get("Admin", VaadinIcon.TOOLS.create());
		LeftNavigationItem menuAdmin = new LeftNavigationItem("Impostazioni",VaadinIcon.ALARM.create(),ImpostazioniView.class);
		LeftNavigationItem menuMercato = new LeftNavigationItem("Mercato",VaadinIcon.INSERT.create(),MercatoView.class);
		LeftNavigationItem menuFreePlayers = new LeftNavigationItem("FreePlayers",VaadinIcon.VIMEO.create(),FreePlayersView.class);
		LeftNavigationItem menuCalenadrioTimDb = new LeftNavigationItem("CalendarioTim",VaadinIcon.CALENDAR_O.create(),FcCalendarioTimView.class);

		menuAdminSub.add(menuAdmin);
		menuAdminSub.add(menuMercato);
		menuAdminSub.add(menuFreePlayers);
		menuAdminSub.add(menuCalenadrioTimDb);

		Component compAdmin = menuAdminSub.build();
		compAdmin.setVisible(attore.isAdmin());

		LeftSubMenuBuilder menuAdminDb = LeftSubMenuBuilder.get("Database", VaadinIcon.ARCHIVES.create());
		LeftNavigationItem menuUtentiDb = new LeftNavigationItem("Utenti",VaadinIcon.USER_CHECK.create(),FcUserView.class);
		LeftNavigationItem menuProprietaDb = new LeftNavigationItem("Proprieta",VaadinIcon.BUILDING.create(),FcPropertiesView.class);
		LeftNavigationItem menuCampionatoDb = new LeftNavigationItem("Campionato",VaadinIcon.STOCK.create(),FcCampionatoView.class);
		LeftNavigationItem menuGiocatoreDb = new LeftNavigationItem("Giocatore",VaadinIcon.EDIT.create(),FcGiocatoreView.class);
		LeftNavigationItem menuGiornataInfoDb = new LeftNavigationItem("GiornataInfo",VaadinIcon.CALENDAR_BRIEFCASE.create(),FcGiornataInfoView.class);
		LeftNavigationItem menuGiornataDb = new LeftNavigationItem("Giornata",VaadinIcon.CALENDAR_CLOCK.create(),FcGiornataView.class);
		LeftNavigationItem menuGiornataDettDb = new LeftNavigationItem("GiornataDett",VaadinIcon.CALENDAR_ENVELOPE.create(),FcGiornataDettView.class);
		LeftNavigationItem menuFormazioneDb = new LeftNavigationItem("Formazione",VaadinIcon.USER_STAR.create(),FcFormazioneView.class);
		LeftNavigationItem menuClassificaDb = new LeftNavigationItem("Classifica",VaadinIcon.GRID.create(),FcClassificaView.class);
		LeftNavigationItem menuMercatoDettDb = new LeftNavigationItem("MercatoDett",VaadinIcon.DOCTOR.create(),FcMercatoDettView.class);
		LeftNavigationItem menuPagelleDb = new LeftNavigationItem("Pagelle",VaadinIcon.FACTORY.create(),FcPagelleView.class);
		LeftNavigationItem menuAccessoDb = new LeftNavigationItem("Accesso",VaadinIcon.ACCESSIBILITY.create(),FcAccessoView.class);
		LeftNavigationItem menuSquadraDb = new LeftNavigationItem("Squadra",VaadinIcon.ENVELOPES.create(),FcSquadraView.class);
		LeftNavigationItem menuExpStatDb = new LeftNavigationItem("ExpStat",VaadinIcon.GRID_BIG.create(),FcExpStatView.class);

		menuAdminDb.add(menuUtentiDb);
		menuAdminDb.add(menuProprietaDb);
		menuAdminDb.add(menuCampionatoDb);
		menuAdminDb.add(menuGiocatoreDb);
		menuAdminDb.add(menuGiornataInfoDb);
		menuAdminDb.add(menuGiornataDb);
		menuAdminDb.add(menuGiornataDettDb);
		menuAdminDb.add(menuFormazioneDb);
		menuAdminDb.add(menuClassificaDb);
		menuAdminDb.add(menuMercatoDettDb);
		menuAdminDb.add(menuPagelleDb);
		menuAdminDb.add(menuAccessoDb);
		menuAdminDb.add(menuSquadraDb);
		menuAdminDb.add(menuExpStatDb);

		Component compAdminDb = menuAdminDb.build();
		compAdminDb.setVisible(attore.isAdmin());

		Component appMenu = LeftAppMenuBuilder.get().add(menuHome).add(menuInsert).add(menuInsertMobile).add(compSquadre).add(menuCalendario).add(menuClassifica).add(menuFormazioni).add(menuStatistiche).add(menuDownload).add(menuAlbo).add(menuRegolamento).add(compAdmin).add(compAdminDb)
				// .addToSection(menuLogout, FOOTER)
				.build();

		return appMenu;
	}

	private FlexLayout buildAppBar(String descAttore) {

		FlexLayout layout = new FlexLayout();
		layout.setWidth("150px");
		layout.setHeight("50px");
		layout.getStyle().set("border", "1px solid #9E9E9E");

		Component menuNotificat = AppBarBuilder.get().add(new NotificationButton<>(VaadinIcon.BELL,notifications)).build();

		MenuBar menuBar = new MenuBar();
		menuBar.setOpenOnHover(true);

		Avatar avatar = new Avatar(descAttore);
		MenuItem account = menuBar.addItem(avatar);
		SubMenu subMenu = account.getSubMenu();
		MenuItem menuSettings = subMenu.addItem("Modifica dati utente");
		menuSettings.addComponentAsFirst(new Icon(VaadinIcon.USER));
		menuSettings.addClickListener(e -> {
			UI.getCurrent().navigate(SettingsView.class);
		});

		MenuItem menuLogout = subMenu.addItem("Logout");
		menuLogout.addComponentAsFirst(new Icon(VaadinIcon.EXIT));
		menuLogout.addClickListener(e -> {
			UI.getCurrent().getPage().executeJs("window.location.href='/fcWeb'");
		});

		layout.setFlexBasis("150px", menuNotificat, menuBar);
		layout.add(menuNotificat, menuBar);

		return layout;
	}

	private void initNotifications() {

		notifications.addClickListener(notification -> {
		});

		FcGiornataInfo giornataInfoCurr = (FcGiornataInfo) VaadinSession.getCurrent().getAttribute("GIORNATA_INFO");
		long millisDiff = (long) VaadinSession.getCurrent().getAttribute("MILLISDIFF");
		if (millisDiff > 0) {
			if (giornataInfoCurr.getDataAnticipo() != null) {
				notifications.add(new DefaultNotification("Data Anticipo",Utils.formatLocalDateTime(giornataInfoCurr.getDataAnticipo(), "dd/MM/yyyy HH:mm")));
			}

			if (giornataInfoCurr.getDataGiornata() != null) {
				notifications.add(new DefaultNotification("Data Giornata",Utils.formatLocalDateTime(giornataInfoCurr.getDataGiornata(), "dd/MM/yyyy HH:mm")));
			}

			if (giornataInfoCurr.getDataPosticipo() != null) {
				notifications.add(new DefaultNotification("Data Posticipo",Utils.formatLocalDateTime(giornataInfoCurr.getDataPosticipo(), "dd/MM/yyyy HH:mm")));
			}
		}
	}

	private Component buildAppMenuEm(FcAttore attore) {

		LeftNavigationItem menuHome = new LeftNavigationItem("Home",VaadinIcon.HOME.create(),EmHomeView.class);
		LeftNavigationItem menuMercato = new LeftNavigationItem("Mercato",VaadinIcon.CONTROLLER.create(),EmMercatoView.class);
		LeftNavigationItem menuInsert = new LeftNavigationItem("Schiera Formazione",VaadinIcon.INSERT.create(),EmTeamInsertView.class);
		LeftNavigationItem menuSquadre = new LeftNavigationItem("Rose",VaadinIcon.USER.create(),EmSquadreView.class);
		LeftNavigationItem menuClassifica = new LeftNavigationItem("Classifica",VaadinIcon.TABLE.create(),EmClassificaView.class);
		LeftNavigationItem menuFormazioni = new LeftNavigationItem("Formazioni",VaadinIcon.CALENDAR_USER.create(),EmFormazioniView.class);
		LeftNavigationItem menuStatistiche = new LeftNavigationItem("Statistiche",VaadinIcon.CALC.create(),EmStatisticheView.class);
		LeftNavigationItem menuDownload = new LeftNavigationItem("Download",VaadinIcon.DOWNLOAD.create(),EmDownloadView.class);
		LeftNavigationItem menuRegolamento = new LeftNavigationItem("Regolamento",VaadinIcon.CREDIT_CARD.create(),EmRegolamentoView.class);

		LeftSubMenuBuilder menuAdminSub = LeftSubMenuBuilder.get("Admin", VaadinIcon.TOOLS.create());
		LeftNavigationItem menuAdmin = new LeftNavigationItem("Impostazioni",VaadinIcon.ALARM.create(),EmImpostazioniView.class);
		LeftNavigationItem menuProprieta = new LeftNavigationItem("Proprieta",VaadinIcon.BUILDING.create(),FcPropertiesView.class);
		LeftNavigationItem menuUtenti = new LeftNavigationItem("Utenti",VaadinIcon.USER_CHECK.create(),FcUserView.class);
		LeftNavigationItem menuGiocatore = new LeftNavigationItem("Giocatore",VaadinIcon.EDIT.create(),FcGiocatoreView.class);
		LeftNavigationItem menuGiornataInfo = new LeftNavigationItem("GiornataInfo",VaadinIcon.LIST_OL.create(),FcGiornataInfoView.class);
		LeftNavigationItem menuFormazione = new LeftNavigationItem("Formazione",VaadinIcon.LIST_UL.create(),FcFormazioneView.class);
		LeftNavigationItem menuGiornataDett = new LeftNavigationItem("GiornataDett",VaadinIcon.CALENDAR_ENVELOPE.create(),FcGiornataDettView.class);
		LeftNavigationItem menuMercatoDett = new LeftNavigationItem("MercatoDett",VaadinIcon.DOCTOR.create(),FcMercatoDettView.class);
		LeftNavigationItem menuCampionato = new LeftNavigationItem("Campionato",VaadinIcon.STOCK.create(),FcCampionatoView.class);
		LeftNavigationItem menuPagelle = new LeftNavigationItem("Pagelle",VaadinIcon.STOCK.create(),FcPagelleView.class);
		LeftNavigationItem menuAccessoDb = new LeftNavigationItem("Accesso",VaadinIcon.ACCESSIBILITY.create(),FcAccessoView.class);
		LeftNavigationItem menuSquadraDb = new LeftNavigationItem("Squadra",VaadinIcon.ENVELOPES.create(),FcSquadraView.class);
		LeftNavigationItem menuCalenadrioTimDb = new LeftNavigationItem("CalendarioTim",VaadinIcon.CALENDAR_O.create(),FcCalendarioTimView.class);

		menuAdminSub.add(menuAdmin);
		menuAdminSub.add(menuProprieta);
		menuAdminSub.add(menuUtenti);
		menuAdminSub.add(menuGiocatore);
		menuAdminSub.add(menuGiornataInfo);
		menuAdminSub.add(menuFormazione);
		menuAdminSub.add(menuGiornataDett);
		menuAdminSub.add(menuMercatoDett);
		menuAdminSub.add(menuCampionato);
		menuAdminSub.add(menuPagelle);
		menuAdminSub.add(menuAccessoDb);
		menuAdminSub.add(menuSquadraDb);
		menuAdminSub.add(menuCalenadrioTimDb);

		Component compAdmin = menuAdminSub.build();
		compAdmin.setVisible(attore.isAdmin());

		Component appMenu = LeftAppMenuBuilder.get().add(menuHome).add(menuMercato).add(menuInsert).add(menuSquadre).add(menuClassifica).add(menuFormazioni).add(menuStatistiche).add(menuDownload).add(menuRegolamento).add(compAdmin)
				// .addToSection(menuLogout, FOOTER)
				.build();

		return appMenu;
	}

}
*/

/* 1
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PWA(name = "FcWeb Project", shortName = "FcWeb")
public class MainAppLayout extends AppLayout implements BeforeEnterObserver{
	private static final long serialVersionUID = 1L;
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	private Tabs tabs = new Tabs();
	private Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();

	@Autowired
	private ResourceLoader resourceLoader;

	private Image buildImage(String nomeImg) {
		StreamResource resource = new StreamResource(nomeImg,() -> {
			Resource r = resourceLoader.getResource("classpath:images/" + nomeImg);
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

	public MainAppLayout() {
		LOG.info("START MainAppLayout");

		if (Utils.isValidVaadinSession()) {

			FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
			FcAttore attore = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");

			String title = campionato.getDescCampionato();
			if ("1".equals(campionato.getType())) {
				buildAppMenu(attore);
			} else {
				buildAppMenuEm(attore);
			}

			final HorizontalLayout menuBar = new HorizontalLayout();
			// menuBar.setAlignItems(Alignment.END);
			menuBar.setWidth("100%");

			Image img = buildImage("logo.png");
			img.setHeight("44px");
			menuBar.add(new DrawerToggle());
			menuBar.add(img);
			menuBar.add(new H4(title));

			final FlexLayout menuBarEnd = new FlexLayout();
			menuBarEnd.getStyle().set("margin-left", "auto");

			Button editProfile = new Button(attore.getDescAttore(),VaadinIcon.EDIT.create());
			editProfile.addClickListener(e -> {
				UI.getCurrent().navigate(SettingsView.class);
			});
			Button logout = new Button("Log out",VaadinIcon.EXIT.create());
			logout.addClickListener(e -> {
				UI.getCurrent().getPage().executeJs("window.location.href='/fcWeb'");
			});
			menuBarEnd.add(editProfile);
			menuBarEnd.add(logout);

			menuBar.add(menuBarEnd);

			addToNavbar(menuBar);

			tabs.setOrientation(Tabs.Orientation.VERTICAL);
			addToDrawer(tabs);
			
		} else {

			final HorizontalLayout menuBar = new HorizontalLayout();
			menuBar.setWidth("100%");

			Image img = buildImage("header.jpg");
			img.setHeight("44px");
			menuBar.add(new DrawerToggle());
			menuBar.add(img);

			final FlexLayout menuBarEnd = new FlexLayout();
			menuBarEnd.getStyle().set("margin-left", "auto");

			Button login = new Button("Login",VaadinIcon.EXIT.create());
			login.addClickListener(e -> {
				UI.getCurrent().getPage().executeJs("window.location.href='/fcWeb'");
			});
			menuBarEnd.add(login);

			menuBar.add(menuBarEnd);

			addToNavbar(menuBar);

			tabs.setOrientation(Tabs.Orientation.VERTICAL);
			addToDrawer(tabs);

			tabs.setOrientation(Tabs.Orientation.VERTICAL);
			addToDrawer(tabs);
		}
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
	}

	private void buildAppMenu(FcAttore attore) {

		addMenuTab("Home", VaadinIcon.HOME.create(), HomeView.class);
		addMenuTab("Schiera Formazione", VaadinIcon.INSERT.create(), TeamInsertView.class);
		addMenuTab("Mobile", VaadinIcon.MOBILE_BROWSER.create(), TeamInsertMobileView.class);
		addMenuTab("Rose", VaadinIcon.USER.create(), SquadreView.class);
		addMenuTab("Tutte le Rose", VaadinIcon.USER_STAR.create(), SquadreAllView.class);
		addMenuTab("Calendario", VaadinIcon.CALENDAR.create(), CalendarioView.class);
		addMenuTab("Classifica", VaadinIcon.TABLE.create(), ClassificaView.class);
		addMenuTab("Formazioni", VaadinIcon.CALENDAR_USER.create(), FormazioniView.class);
		addMenuTab("Statistiche", VaadinIcon.CALC.create(), StatisticheView.class);
		addMenuTab("Download", VaadinIcon.DOWNLOAD.create(), DownloadView.class);
		addMenuTab("Albo", VaadinIcon.ARCHIVES.create(), AlboView.class);
		addMenuTab("Regolamento", VaadinIcon.CREDIT_CARD.create(), RegolamentoView.class);

		if (attore.isAdmin()) {
			addMenuTab("Impostazioni", VaadinIcon.ALARM.create(), ImpostazioniView.class);
			addMenuTab("Mercato", VaadinIcon.INSERT.create(), MercatoView.class);
			addMenuTab("FreePlayers", VaadinIcon.VIMEO.create(), FreePlayersView.class);
			addMenuTab("Utenti", VaadinIcon.USER_CHECK.create(), FcUserView.class);
			addMenuTab("Campionato", VaadinIcon.STOCK.create(), FcCampionatoView.class);
			addMenuTab("Proprieta", VaadinIcon.BUILDING.create(), FcPropertiesView.class);
			addMenuTab("Giocatore", VaadinIcon.EDIT.create(), FcGiocatoreView.class);
			addMenuTab("GiornataInfo", VaadinIcon.CALENDAR_BRIEFCASE.create(), FcGiornataInfoView.class);
			addMenuTab("Giornata", VaadinIcon.CALENDAR_CLOCK.create(), FcGiornataView.class);
			addMenuTab("GiornataDett", VaadinIcon.CALENDAR_ENVELOPE.create(), FcGiornataDettView.class);
			addMenuTab("Formazione", VaadinIcon.USER_STAR.create(), FcFormazioneView.class);
			addMenuTab("Classifica", VaadinIcon.GRID.create(), FcClassificaView.class);
			addMenuTab("MercatoDett", VaadinIcon.DOCTOR.create(), FcMercatoDettView.class);
			addMenuTab("Pagelle", VaadinIcon.FACTORY.create(), FcPagelleView.class);
			addMenuTab("CalendarioTim", VaadinIcon.CALENDAR_O.create(), FcCalendarioTimView.class);
			addMenuTab("Accesso", VaadinIcon.ELASTIC.create(), FcAccessoView.class);
			addMenuTab("Squadra", VaadinIcon.ENVELOPES.create(), FcSquadraView.class);
			addMenuTab("ExpStat", VaadinIcon.GRID_BIG.create(), FcExpStatView.class);
		}
	}

	private void buildAppMenuEm(FcAttore attore) {

		addMenuTab("Home", VaadinIcon.HOME.create(), EmHomeView.class);
		addMenuTab("Mercato", VaadinIcon.INSERT.create(), EmMercatoView.class);
		addMenuTab("Schiera Formazione", VaadinIcon.INSERT.create(), EmTeamInsertView.class);
		addMenuTab("Rose", VaadinIcon.USER.create(), EmSquadreView.class);
		addMenuTab("Classifica", VaadinIcon.TABLE.create(), EmClassificaView.class);
		addMenuTab("Formazioni", VaadinIcon.CALENDAR_USER.create(), EmFormazioniView.class);
		addMenuTab("Statistiche", VaadinIcon.CALC.create(), EmStatisticheView.class);
		addMenuTab("Download", VaadinIcon.DOWNLOAD.create(), EmDownloadView.class);
		addMenuTab("Regolamento", VaadinIcon.CREDIT_CARD.create(), EmRegolamentoView.class);

		if (attore.isAdmin()) {
			addMenuTab("Impostazioni", VaadinIcon.ALARM.create(), EmImpostazioniView.class);
			addMenuTab("Proprieta", VaadinIcon.BUILDING.create(), FcPropertiesView.class);
			addMenuTab("Utenti", VaadinIcon.USER_CHECK.create(), FcUserView.class);
			addMenuTab("Giocatore", VaadinIcon.EDIT.create(), FcGiocatoreView.class);
			addMenuTab("GiornataInfo", VaadinIcon.LIST_OL.create(), FcGiornataInfoView.class);
			addMenuTab("Formazione", VaadinIcon.LIST_UL.create(), FcFormazioneView.class);
			addMenuTab("GiornataDett", VaadinIcon.CALENDAR_ENVELOPE.create(), FcGiornataDettView.class);
			addMenuTab("MercatoDett", VaadinIcon.STOCK.create(), FcMercatoDettView.class);
			addMenuTab("Campionato", VaadinIcon.STOCK.create(), FcCampionatoView.class);
			addMenuTab("Pagelle", VaadinIcon.STOCK.create(), FcPagelleView.class);
		}
	}

	private void addMenuTab(String label, Icon icon,
			Class<? extends Component> target) {
		Tab tab = new Tab(icon,new RouterLink(label,target));
		navigationTargetToTab.put(target, tab);
		tabs.add(tab);
	}
}

*/

/* 2
@PageTitle("Main")
public class MainAppLayout extends AppLayout {

	private static final long serialVersionUID = 1L;
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static class MenuItemInfo {

        private String text;
        private Icon icon;
        private Class<? extends Component> view;
        private boolean visible = false;

        public MenuItemInfo(String text, Icon  icon, Class<? extends Component> view, boolean visible) {
            this.text = text;
            this.icon = icon;
            this.view = view;
            this.visible = visible;
        }

        public String getText() {
            return text;
        }

        public Icon  getIcon() {
            return icon;
        }

        public Class<? extends Component> getView() {
            return view;
        }

        public boolean isVisible() {
            return visible;
        }

    }

    private final Tabs menu;
    private H1 viewTitle;

    public MainAppLayout() {
    	
    	LOG.info("START MainAppLayout");

		FcAttore attore = null;
		FcCampionato campionato = null;

    	if (Utils.isValidVaadinSession()) {
    		campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
    		attore = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
    	}
    	setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent(attore));
        menu = createMenu(campionato,attore);
        addToDrawer(createDrawerContent(menu));
    }


    private Component createHeaderContent(FcAttore attore) {
    
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("sidemenu-header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);

    	if (attore != null) {

    		final FlexLayout menuBarEnd = new FlexLayout();
    		menuBarEnd.getStyle().set("margin-left", "auto");

    		Button editProfile = new Button(attore.getDescAttore(),VaadinIcon.EDIT.create());
    		editProfile.addClickListener(e -> {
    			UI.getCurrent().navigate(SettingsView.class);
    		});
    		Button logout = new Button("Log out",VaadinIcon.EXIT.create());
    		logout.addClickListener(e -> {
    			UI.getCurrent().getPage().executeJs("window.location.href='/fcWeb'");
    		});
    		menuBarEnd.add(editProfile);
    		menuBarEnd.add(logout);
    		
    		layout.add(menuBarEnd);

//    		Avatar avatar = new Avatar(attore.getDescAttore());
//    		MenuBar menuBar = new MenuBar();
//    		menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
//    		MenuItem menuItem = menuBar.addItem(avatar);
//    		SubMenu subMenu = menuItem.getSubMenu();
//    		MenuItem menuSettings = subMenu.addItem("Modifica dati utente");
//    		menuSettings.addClickListener(e -> {
//    			UI.getCurrent().navigate(SettingsView.class);
//    		});
//
//    		MenuItem menuLogout = subMenu.addItem("Logout"); 		
//    		menuLogout.addClickListener(e -> {
//    			UI.getCurrent().getPage().executeJs("window.location.href='/fcWeb'");
//    		});
//
//    		menuBarEnd.add(menuBar);
//    		
//    		layout.add(menuBarEnd);

    	} else {
    		final FlexLayout menuBarEnd = new FlexLayout();
			Button login = new Button("Login",VaadinIcon.EXIT.create());
			login.addClickListener(e -> {
				UI.getCurrent().getPage().executeJs("window.location.href='/fcWeb'");
			});
			menuBarEnd.add(login);
			
			layout.add(menuBarEnd);
    		
    	}

        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("sidemenu-menu");
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "Fc App logo"));
        logoLayout.add(new H1("Fc App"));
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu(FcCampionato campionato,FcAttore attore) {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        
        if (campionato != null && attore != null) {
            for (Tab menuTab : createMenuItems(campionato,attore)) {
                tabs.add(menuTab);
            }
        }
        return tabs;
    }

    private List<Tab> createMenuItems(FcCampionato campionato,FcAttore attore) {
    	
		//String title = campionato.getDescCampionato();
		MenuItemInfo[] menuItems = null;
		if ("1".equals(campionato.getType())) {
        	menuItems = new MenuItemInfo[]{ //
                new MenuItemInfo("Home", VaadinIcon.HOME.create(), HomeView.class, true), //
                new MenuItemInfo("Schiera Formazione", VaadinIcon.INSERT.create(), TeamInsertView.class, true), //
                new MenuItemInfo("Mobile", VaadinIcon.MOBILE_BROWSER.create(), TeamInsertMobileView.class, true), //
                new MenuItemInfo("Rose", VaadinIcon.USER.create(), SquadreView.class, true), //
                new MenuItemInfo("Tutte le Rose", VaadinIcon.USER_STAR.create(), SquadreAllView.class, true), //
                new MenuItemInfo("Calendario", VaadinIcon.CALENDAR.create(), CalendarioView.class, true), //
                new MenuItemInfo("Classifica", VaadinIcon.TABLE.create(), ClassificaView.class, true), //
                new MenuItemInfo("Formazioni", VaadinIcon.CALENDAR_USER.create(), FormazioniView.class, true), //
                new MenuItemInfo("Statistiche", VaadinIcon.CALC.create(), StatisticheView.class, true), //
                new MenuItemInfo("Download", VaadinIcon.DOWNLOAD.create(), DownloadView.class, true), //
                new MenuItemInfo("Albo", VaadinIcon.ARCHIVES.create(), AlboView.class, true), //
                new MenuItemInfo("Regolamento", VaadinIcon.CREDIT_CARD.create(), RegolamentoView.class, true), //
                
                new MenuItemInfo("Impostazioni", VaadinIcon.ALARM.create(), ImpostazioniView.class, attore.isAdmin()), //
                new MenuItemInfo("Mercato", VaadinIcon.INSERT.create(), MercatoView.class, attore.isAdmin()), //
                new MenuItemInfo("Free Players", VaadinIcon.VIMEO.create(), FreePlayersView.class, attore.isAdmin()), //
                new MenuItemInfo("CalendarioTim", VaadinIcon.CALENDAR_O.create(), FcCalendarioTimView.class, attore.isAdmin()), //
                new MenuItemInfo("Utenti", VaadinIcon.USER_CHECK.create(), FcUserView.class, attore.isAdmin()), //
                new MenuItemInfo("Proprieta", VaadinIcon.BUILDING.create(), FcPropertiesView.class, attore.isAdmin()), //
                new MenuItemInfo("Campionato", VaadinIcon.STOCK.create(), FcCampionatoView.class, attore.isAdmin()), //
                new MenuItemInfo("Giocatore", VaadinIcon.EDIT.create(), FcGiocatoreView.class, attore.isAdmin()), //
                new MenuItemInfo("GiornataInfo", VaadinIcon.CALENDAR_BRIEFCASE.create(), FcGiornataInfoView.class, attore.isAdmin()), //
                new MenuItemInfo("Giornata", VaadinIcon.CALENDAR_CLOCK.create(), FcGiornataView.class, attore.isAdmin()), //
                new MenuItemInfo("GiornataDett", VaadinIcon.CALENDAR_ENVELOPE.create(), FcGiornataDettView.class, attore.isAdmin()), //
                new MenuItemInfo("Formazione", VaadinIcon.USER_STAR.create(), FcFormazioneView.class, attore.isAdmin()), //
                new MenuItemInfo("Classifica", VaadinIcon.GRID.create(), FcClassificaView.class, attore.isAdmin()), //
                new MenuItemInfo("MercatoDett", VaadinIcon.DOCTOR.create(), FcMercatoDettView.class, attore.isAdmin()), //
                new MenuItemInfo("Pagelle", VaadinIcon.FACTORY.create(), FcPagelleView.class, attore.isAdmin()), //
                new MenuItemInfo("Accesso", VaadinIcon.ACCESSIBILITY.create(), FcAccessoView.class, attore.isAdmin()), //
                new MenuItemInfo("Squadre Tim", VaadinIcon.ENVELOPES.create(), FcSquadraView.class, attore.isAdmin()), //
                new MenuItemInfo("ExpStat", VaadinIcon.GRID_BIG.create(), FcExpStatView.class, attore.isAdmin()), //
            };

		} else {

			menuItems = new MenuItemInfo[]{ //
                new MenuItemInfo("Home", VaadinIcon.HOME.create(), EmHomeView.class, true), //
                new MenuItemInfo("Mercato", VaadinIcon.INSERT.create(), EmMercatoView.class, true), //
                new MenuItemInfo("Schiera Formazione", VaadinIcon.INSERT.create(), EmTeamInsertView.class, true), //
                new MenuItemInfo("Rose", VaadinIcon.USER.create(), EmSquadreView.class, true), //

                new MenuItemInfo("Classifica", VaadinIcon.TABLE.create(), EmClassificaView.class, true), //
                new MenuItemInfo("Formazioni", VaadinIcon.CALENDAR_USER.create(), EmFormazioniView.class, true), //
                new MenuItemInfo("Statistiche", VaadinIcon.CALC.create(), EmStatisticheView.class, true), //
                new MenuItemInfo("Download", VaadinIcon.DOWNLOAD.create(), EmDownloadView.class, true), //
                new MenuItemInfo("Regolamento", VaadinIcon.CREDIT_CARD.create(), EmRegolamentoView.class, true), //
                new MenuItemInfo("Impostazioni", VaadinIcon.ALARM.create(), EmImpostazioniView.class, attore.isAdmin()), //
                new MenuItemInfo("Proprieta", VaadinIcon.BUILDING.create(), FcPropertiesView.class, attore.isAdmin()), //
                new MenuItemInfo("Utenti", VaadinIcon.USER_CHECK.create(), FcUserView.class, attore.isAdmin()), //
                new MenuItemInfo("Giocatore", VaadinIcon.EDIT.create(), FcGiocatoreView.class, attore.isAdmin()), //
                new MenuItemInfo("GiornataInfo", VaadinIcon.CALENDAR_BRIEFCASE.create(), FcGiornataInfoView.class, attore.isAdmin()), //
                new MenuItemInfo("GiornataDett", VaadinIcon.CALENDAR_ENVELOPE.create(), FcGiornataDettView.class, attore.isAdmin()), //
                new MenuItemInfo("Formazione", VaadinIcon.USER_STAR.create(), FcFormazioneView.class, attore.isAdmin()), //
                new MenuItemInfo("MercatoDett", VaadinIcon.DOCTOR.create(), FcMercatoDettView.class, attore.isAdmin()), //
                new MenuItemInfo("Pagelle", VaadinIcon.FACTORY.create(), FcPagelleView.class, attore.isAdmin()), //
                new MenuItemInfo("Accesso", VaadinIcon.ACCESSIBILITY.create(), FcAccessoView.class, attore.isAdmin()), //
                new MenuItemInfo("Campionato", VaadinIcon.STOCK.create(), FcCampionatoView.class, attore.isAdmin()), //
            };
		}

        List<Tab> tabs = new ArrayList<>();
        for (MenuItemInfo menuItemInfo : menuItems) {
        	if (menuItemInfo.isVisible()) {
        		tabs.add(createTab(menuItemInfo));	
        	}
        }
        return tabs;
    }

    private static Tab createTab(MenuItemInfo menuItemInfo) {
        Tab tab = new Tab();
        RouterLink link = new RouterLink();
        link.setRoute(menuItemInfo.getView());
        Span iconElement = new Span();
        iconElement.addClassNames("text-l", "pr-s");
        if (menuItemInfo.getIcon() != null) {
            iconElement.add(menuItemInfo.getIcon());
        }
        link.add(iconElement, new Text(menuItemInfo.getText()));
        tab.add(link);
        ComponentUtil.setData(tab, Class.class, menuItemInfo.getView());
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
} */




/**
 * The main view is a top-level placeholder for other views.
 */
public class MainAppLayout extends AppLayout {

	private static final long serialVersionUID = 1L;
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	/*
	
	@Autowired
	private ResourceLoader resourceLoader;

	private Image buildImage(String nomeImg) {
		StreamResource resource = new StreamResource(nomeImg,() -> {
			Resource r = resourceLoader.getResource("classpath:images/" + nomeImg);
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
	*/

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames("font-medium", "text-s");

            link.add(new LineAwesomeIcon(iconClass), text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

        /**
         * Simple wrapper to create icons using LineAwesome iconset. See
         * https://icons8.com/line-awesome
         */
        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public LineAwesomeIcon(String lineawesomeClassnames) {
                // Use Lumo classnames for suitable font size and margin
                addClassNames("me-s", "text-l");
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }
    }

    private H1 viewTitle;

    public MainAppLayout() {
    	
    	LOG.info("START MainAppLayout");

		FcAttore attore = null;
		FcCampionato campionato = null;

		setPrimarySection(Section.DRAWER);
    	if (Utils.isValidVaadinSession()) {
    		campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
    		attore = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
            
            addToNavbar(true, createHeaderContent(attore,campionato));
            addToDrawer(createDrawerContent(attore,campionato));
    	} else {
            addToNavbar(false, createHeaderContentLogin());
    	}
    }

    private Component createHeaderContent(FcAttore attore,FcCampionato campionato) {

        HorizontalLayout layout = new HorizontalLayout();
        //layout.setClassName("sidemenu-header");
        //layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

    	DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        layout.add(toggle);
        
        viewTitle = new H1();
        viewTitle.addClassNames("m-0", "text-l");
        
        layout.add(viewTitle);
        
		Avatar avatar = new Avatar(attore.getNome() + " " +attore.getCognome());
		MenuBar menuBar = new MenuBar();
		menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
		MenuItem menuItem = menuBar.addItem(avatar);
		SubMenu subMenu = menuItem.getSubMenu();
		MenuItem menuSettings = subMenu.addItem("Modifica dati utente");
		menuSettings.addClickListener(e -> {
			UI.getCurrent().navigate(SettingsView.class);
		});
		MenuItem menuLogout = subMenu.addItem("Logout"); 		
		menuLogout.addClickListener(e -> {
			UI.getCurrent().getPage().executeJs("window.location.href='/fcWeb'");
		});

		final FlexLayout menuBarEnd = new FlexLayout();
		menuBarEnd.getStyle().set("margin-left", "auto");
		menuBarEnd.add(menuBar);

		layout.add(menuBarEnd);
        
        return layout;
        
    }
    
    private Component createHeaderContentLogin() {
        
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("sidemenu-header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
		layout.setJustifyContentMode(FlexLayout.JustifyContentMode.START);
        
        layout.add(new DrawerToggle());

        viewTitle = new H1();
        viewTitle.addClassNames("m-0", "text-l");
        
        layout.add(viewTitle);

		//final FlexLayout menuBarEnd = new FlexLayout();
		Button login = new Button("Login",VaadinIcon.EXIT.create());
		login.addClickListener(e -> {
			UI.getCurrent().getPage().executeJs("window.location.href='/fcWeb'");
		});
		//menuBarEnd.add(login);
		layout.add(login);

        return layout;
    }

    private Component createDrawerContent(FcAttore attore,FcCampionato campionato) {
        H2 appName = new H2("Fc App");
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");
        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(
        		//appName,
                createNavigation(attore,campionato), 
                createFooter()
        );
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
//        return section;
    	
        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("sidemenu-menu");
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "Fc App logo"));
        logoLayout.add(appName);
        
        layout.add(logoLayout, section);
        
        return layout;
    	
    }

    private Nav createNavigation(FcAttore attore,FcCampionato campionato) {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems(attore,campionato)) {
            list.add(menuItem);

        }
        return nav;
    }

    private MenuItemInfo[] createMenuItems(FcAttore attore,FcCampionato campionato) {

    	if ("1".equals(campionato.getType())) {
	
    		if (attore.isAdmin()) {
            	return new MenuItemInfo[]{ //

                        new MenuItemInfo("Home", "la la-home", HomeView.class), //
                        new MenuItemInfo("Schera Formazione", "la la-futbol", TeamInsertView.class), //
                        new MenuItemInfo("Mobile", "la la-mobile", TeamInsertMobileView.class), //
                        new MenuItemInfo("Rose", "la la-user", SquadreView.class), //
                        new MenuItemInfo("Tutte le Rose", "la-user-friends", SquadreAllView.class), //
                        new MenuItemInfo("Calendario", "la la-calendar", CalendarioView.class), //
                        new MenuItemInfo("Classifica", "la la-table", ClassificaView.class), //
                        new MenuItemInfo("Formazioni", "la la-calendar-check", FormazioniView.class), //
                        new MenuItemInfo("Statistiche", "la la-chart-line", StatisticheView.class), //
                        new MenuItemInfo("Download", "la la-download", DownloadView.class), //
                        new MenuItemInfo("Albo", "la la-history", AlboView.class), //
                        new MenuItemInfo("Regolamento", "la la-comment", RegolamentoView.class), //
                        
                        new MenuItemInfo("Impostazioni", "la la-tools", ImpostazioniView.class), //
                        new MenuItemInfo("Mercato", "la la-search-dollar", MercatoView.class), //
                        new MenuItemInfo("Free Players", "la la-free-code-camp", FreePlayersView.class), //
                        new MenuItemInfo("Calendario Serie A", "la la-calendar-alt", FcCalendarioTimView.class), //
                        new MenuItemInfo("Utenti", "la la-user-edit", FcUserView.class), //
                        new MenuItemInfo("Proprieta", "la la-toolbox", FcPropertiesView.class), //
                        new MenuItemInfo("Campionato", "la la-brush", FcCampionatoView.class), //
                        new MenuItemInfo("Giocatore", "la la-users", FcGiocatoreView.class), //
                        new MenuItemInfo("GiornataInfo", "la la-calendar-week", FcGiornataInfoView.class), //
                        new MenuItemInfo("Giornata", "la la-calendar-day", FcGiornataView.class), //
                        new MenuItemInfo("GiornataDett", "la la-calendar-check", FcGiornataDettView.class), //
                        new MenuItemInfo("Formazione", "la la-users-cog", FcFormazioneView.class), //
                        new MenuItemInfo("Classifica", "la la-table", FcClassificaView.class), //
                        new MenuItemInfo("MercatoDett", "la la-border-all", FcMercatoDettView.class), //
                        new MenuItemInfo("Pagelle", "la la-poll", FcPagelleView.class), //
                        new MenuItemInfo("Accesso", "la la-universal-access", FcAccessoView.class), //
                        new MenuItemInfo("Squadre Serie A", "la la-users-cog", FcSquadraView.class), //
                        new MenuItemInfo("ExpStat", "la la-keyboard", FcExpStatView.class), //
            	};
    			
    		} else {
            	return new MenuItemInfo[]{ //
                        new MenuItemInfo("Home", "la la-home", HomeView.class), //
                        new MenuItemInfo("Schera Formazione", "la la-futbol", TeamInsertView.class), //
                        new MenuItemInfo("Mobile", "la la-mobile", TeamInsertMobileView.class), //
                        new MenuItemInfo("Rose", "la la-user", SquadreView.class), //
                        new MenuItemInfo("Tutte le Rose", "la-user-friends", SquadreAllView.class), //
                        new MenuItemInfo("Calendario", "la la-calendar", CalendarioView.class), //
                        new MenuItemInfo("Classifica", "la la-table", ClassificaView.class), //
                        new MenuItemInfo("Formazioni", "la la-calendar-check", FormazioniView.class), //
                        new MenuItemInfo("Statistiche", "la la-chart-line", StatisticheView.class), //
                        new MenuItemInfo("Download", "la la-download", DownloadView.class), //
                        new MenuItemInfo("Albo", "la la-history", AlboView.class), //
                        new MenuItemInfo("Regolamento", "la la-comment", RegolamentoView.class), //
                };
    		}
        	
    	} else {
    		
    		if (attore.isAdmin()) {

            	return new MenuItemInfo[]{ //
                        new MenuItemInfo("Home", "la la-home", EmHomeView.class), //
                        new MenuItemInfo("Mercato", "la la-search-dollar", EmMercatoView.class), //
                        new MenuItemInfo("Schera Formazione", "la la-futbol", EmTeamInsertView.class), //
                        new MenuItemInfo("Rose", "la la-user", EmSquadreView.class), //
                        new MenuItemInfo("Classifica", "la la-table", EmClassificaView.class), //
                        new MenuItemInfo("Formazioni", "la la-calendar-check", EmFormazioniView.class), //
                        new MenuItemInfo("Statistiche", "la la-chart-line", EmStatisticheView.class), //
                        new MenuItemInfo("Download", "la la-download", EmDownloadView.class), //
                        new MenuItemInfo("Regolamento", "la la-comment", EmRegolamentoView.class), //
                        
                        new MenuItemInfo("Impostazioni", "la la-tools", EmImpostazioniView.class), //
                        new MenuItemInfo("Proprieta", "la la-toolbox", FcPropertiesView.class), //
                        new MenuItemInfo("Utenti", "la la-user-edit", FcUserView.class), //
                        new MenuItemInfo("Giocatore", "la la-users", FcGiocatoreView.class), //
                        new MenuItemInfo("GiornataInfo", "la la-calendar-week", FcGiornataInfoView.class), //
                        new MenuItemInfo("GiornataDett", "la la-calendar-check", FcGiornataDettView.class), //
                        new MenuItemInfo("Formazione", "la la-users-cog", FcFormazioneView.class), //
                        new MenuItemInfo("MercatoDett", "la la-border-all", FcMercatoDettView.class), //
                        new MenuItemInfo("Pagelle", "la la-poll", FcPagelleView.class), //
                        new MenuItemInfo("Accesso", "la la-universal-access", FcAccessoView.class), //
                        new MenuItemInfo("Campionato", "la la-brush", FcCampionatoView.class), //
            	};

    		} else {
            	return new MenuItemInfo[]{ //
                        new MenuItemInfo("Home", "la la-home", EmHomeView.class), //
                        new MenuItemInfo("Mercato", "la la-search-dollar", EmMercatoView.class), //
                        new MenuItemInfo("Schera Formazione", "la la-futbol", EmTeamInsertView.class), //
                        new MenuItemInfo("Rose", "la la-user", EmSquadreView.class), //
                        new MenuItemInfo("Classifica", "la la-table", EmClassificaView.class), //
                        new MenuItemInfo("Formazioni", "la la-calendar-check", EmFormazioniView.class), //
                        new MenuItemInfo("Statistiche", "la la-chart-line", EmStatisticheView.class), //
                        new MenuItemInfo("Download", "la la-download", EmDownloadView.class), //
                        new MenuItemInfo("Regolamento", "la la-comment", EmRegolamentoView.class), //
            	};
    		}
    	}
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}








