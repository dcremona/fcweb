package fcweb.ui.views.login;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import common.util.Utils;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.data.entity.FcCampionato;
import fcweb.backend.data.entity.FcGiornataInfo;
import fcweb.backend.data.entity.FcPagelle;
import fcweb.backend.data.entity.FcProperties;
import fcweb.backend.service.AccessoService;
import fcweb.backend.service.AttoreService;
import fcweb.backend.service.CampionatoService;
import fcweb.backend.service.GiornataInfoRepository;
import fcweb.backend.service.PagelleService;
import fcweb.backend.service.ProprietaService;
import fcweb.ui.views.em.EmHomeView;
import fcweb.ui.views.seriea.HomeView;
import fcweb.utils.Costants;
import fcweb.utils.CustomMessageDialog;

@Route(value = "fcWeb")
@RouteAlias(value = "")
@PageTitle("Login")
public class LoginView extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment env;

	@Autowired
	private AttoreService attoreController;

	@Autowired
	private CampionatoService campionatoController;

	@Autowired
	private ProprietaService proprietaController;

	@Autowired
	private PagelleService pagelleController;

	@Autowired
	private GiornataInfoRepository giornataInfoRepository;

	@Autowired
	private AccessoService accessoController;

	@Autowired
	private ResourceLoader resourceLoader;

	@PostConstruct
	void init() {
		LOG.info("init");

		if (Utils.isValidVaadinSession()) {
			VaadinSession.getCurrent().getSession().invalidate();
			UI.getCurrent().getSession().close();
			return;
		}

		// WebBrowser browser = VaadinSession.getCurrent().getBrowser();
		// LOG.info("browser.isChrome() " + browser.isChrome());
		// LOG.info("browser.isChromeOS() " + browser.isChromeOS());
		// LOG.info("browser.isIPad() " + browser.isIPad());
		// LOG.info("browser.isIPhone() " + browser.isIPhone());
		// LOG.info("browser.isIOS() " + browser.isIOS());
		// LOG.info("browser.isSafari() " + browser.isSafari());
		// LOG.info("browser.isFirefox() " + browser.isFirefox());
		// LOG.info("browser.isLinux() " + browser.isLinux());
		// LOG.info("browser.isWindows() " + browser.isWindows());

		UI.getCurrent().getPage().retrieveExtendedClientDetails(event -> {
			int resX = event.getScreenWidth();
			int resY = event.getScreenHeight();
			LOG.info("resX " + resX);
			LOG.info("resY " + resY);
			LOG.info("Math.max " + Math.max(resX, resY));
			if (Math.max(resX, resY) < 900) {
				// small screen detected
				LOG.info("small screen detected ");
			}
		});
		// UI.getCurrent().getPage().retrieveExtendedClientDetails(extendedClientDetails
		// -> {
		// WebBrowser webBrowser = VaadinSession.getCurrent().getBrowser();
		// if (webBrowser.isIPad() || (webBrowser.isMacOSX() &&
		// extendedClientDetails.isTouchDevice())) {
		// add(new Paragraph("It's an iPad!"));
		// } else {
		// add(new Paragraph("It's not an iPad!"));
		// }
		// });

		// UI.getCurrent().getPage().addBrowserWindowResizeListener(e -> {
		// int winWidth = e.getWidth();
		// int winHeight = e.getHeight();
		// // Do something
		// LOG.info("winWidth " + winWidth);
		// LOG.info("winHeight " + winHeight);
		// });

		String imgLogo = (String) env.getProperty("img.logo");
		Image img = buildImage("classpath:images/", imgLogo);
		this.add(img);
		this.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, img);

		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setHeader(new LoginI18n.Header());
		i18n.getHeader().setTitle("Fc App");
		// i18n.getHeader().setDescription("Login using user/user or admin/admin");
		i18n.setAdditionalInformation(null);

		LoginI18n.Form i18nForm = i18n.getForm();
		i18nForm.setTitle("Fc App");
		i18nForm.setUsername("Email");
		i18nForm.setPassword("Password");
		i18nForm.setSubmit("Login");
		i18nForm.setForgotPassword("Password dimenticata?");
		i18n.setForm(i18nForm);

		LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
		i18nErrorMessage.setTitle("Email o password non valide");
		i18nErrorMessage.setMessage("Verifica di aver inserito l'email e la password corrette e riprova.");
		i18n.setErrorMessage(i18nErrorMessage);

		LoginForm loginForm = new LoginForm();
		loginForm.setI18n(i18n);

		loginForm.addLoginListener(e -> {

			boolean isAuthenticated = checkUser(e.getUsername(), e.getPassword());
			if (isAuthenticated) {
				FcCampionato campionato = (FcCampionato) VaadinSession.getCurrent().getAttribute("CAMPIONATO");
				FcAttore attore = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
				accessoController.insertAccesso(campionato,attore,this.getClass().getName());


				if ("logo.png".equals(imgLogo)) {
					UI.getCurrent().navigate(HomeView.class);
				} else {
					UI.getCurrent().navigate(EmHomeView.class);
				}
			} else {
				loginForm.setError(true);
			}
		});
		loginForm.setForgotPasswordButtonVisible(false);

		this.getStyle().set("border", Costants.BORDER_COLOR);

		this.add(loginForm);
		this.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, loginForm);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

	}

	private boolean checkUser(String username, String pwd) {

		LOG.debug("username: " + username);
		LOG.debug("pwd: " + pwd);

		if (StringUtils.isEmpty(username)) {
			CustomMessageDialog.showMessageError("Email non valida");
			return false;
		}

		FcAttore attore = null;
		if (StringUtils.isEmpty(pwd)) {
			attore = attoreController.findByUsername(username);
			if (attore == null) {
				CustomMessageDialog.showMessageError("Email non valida");
				return false;
			}

			if (!StringUtils.isEmpty(attore.getPassword())) {
				CustomMessageDialog.showMessageError("Password non valida");
				return false;
			}

		} else {
			attore = attoreController.findByUsernameAndPassword(username, pwd);
			if (attore == null) {
				CustomMessageDialog.showMessageError("Email o Password non valide");
				return false;
			}

			if (!attore.isActive()) {
				CustomMessageDialog.showMessageError("Utenza non attiva");
				return false;
			}
		}

		List<FcProperties> lProprieta = proprietaController.findAll();
		if (lProprieta.size() == 0) {
			CustomMessageDialog.showMessageError("Contattare amministratore! (loadProperties)");
			return false;
		}

		Properties properties = new Properties();
		for (FcProperties prop : lProprieta) {
			properties.setProperty(prop.getKey(), prop.getValue());
		}

		FcCampionato campionato = campionatoController.findByActive(true);
		if (campionato == null) {
			CustomMessageDialog.showMessageError("Contattare amministratore! (campionato=null)");
			return false;
		}
		LOG.info("Campionato: " + campionato.getIdCampionato());

		FcPagelle currentGG = pagelleController.findCurrentGiornata();
		FcGiornataInfo giornataInfo = null;
		if (currentGG == null) {
			giornataInfo = giornataInfoRepository.findByCodiceGiornata(Integer.valueOf(1));
		} else {
			giornataInfo = currentGG.getFcGiornataInfo();
			if (currentGG.getFcGiornataInfo().getCodiceGiornata() > campionato.getEnd()) {
				giornataInfo = giornataInfoRepository.findByCodiceGiornata(campionato.getEnd());
			}
		}
		LOG.info("CurrentGG: " + giornataInfo.getCodiceGiornata());

		String fusoOrario = (String) properties.getProperty("FUSO_ORARIO");
		String nextDate = Utils.getNextDate(giornataInfo);

		long millisDiff = 0;
		try {
			millisDiff = Utils.getMillisDiff(nextDate, fusoOrario);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		LOG.info("millisDiff : " + millisDiff);
		LOG.info("Login " + attore.getDescAttore() + " success");
		
		LOG.info("removeAttribute : " );
		VaadinSession.getCurrent().getSession().removeAttribute("GIORNATA_INFO");
		VaadinSession.getCurrent().getSession().removeAttribute("ATTORE");
		VaadinSession.getCurrent().getSession().removeAttribute("PROPERTIES");
		VaadinSession.getCurrent().getSession().removeAttribute("CAMPIONATO");
		VaadinSession.getCurrent().getSession().removeAttribute("NEXTDATE");
		VaadinSession.getCurrent().getSession().removeAttribute("MILLISDIFF");
		VaadinSession.getCurrent().getSession().removeAttribute("COUNTDOWNDATE");

		// Set a session attribute
		LOG.info("setAttribute : " );
		VaadinSession.getCurrent().setAttribute("GIORNATA_INFO", giornataInfo);
		VaadinSession.getCurrent().setAttribute("ATTORE", attore);
		VaadinSession.getCurrent().setAttribute("PROPERTIES", properties);
		VaadinSession.getCurrent().setAttribute("CAMPIONATO", campionato);
		VaadinSession.getCurrent().setAttribute("NEXTDATE", nextDate);
		VaadinSession.getCurrent().setAttribute("MILLISDIFF", millisDiff);
		VaadinSession.getCurrent().setAttribute("COUNTDOWNDATE", getCalendarCountDown(nextDate, fusoOrario));

		return true;
	}

	private Date getCalendarCountDown(String currentDataGiornata,
			String FUSO_ORARIO) {

		Calendar c = Calendar.getInstance();
		int dd = 0;
		int mm = 0;
		int yy = 0;
		int h = 0;
		int m = 0;
		try {
			int fuso = Integer.parseInt(FUSO_ORARIO);
			dd = Integer.parseInt(currentDataGiornata.substring(0, 2));
			mm = Integer.parseInt(currentDataGiornata.substring(3, 5)) - 1;
			yy = Integer.parseInt(currentDataGiornata.substring(6, 10));
			h = Integer.parseInt(currentDataGiornata.substring(11, 13)) - fuso;
			m = Integer.parseInt(currentDataGiornata.substring(14, 16));
		} catch (Exception e) {
			LOG.error("getCalendarCountDown ");
		}
		c.set(yy, mm, dd, h, m, 0);

		return c.getTime();
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
