package fcweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.vaadin.artur.helpers.LaunchUtil;

import com.vaadin.flow.component.dependency.NpmPackage;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


/*
@SpringBootApplication
@EnableScheduling
public class FcltApplication{

	public static void main(String[] args)
			throws DatatypeConfigurationException {
		SpringApplication.run(FcltApplication.class, args);

//		LOGGER.info("Simple log statement with inputs {}, {} and {}", 1, 2, 3);
//		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//		LOGGER.info("fonts " + fonts.length);
//		for (String s : fonts) {
//			LOGGER.info(s);
//		}
	}

}

*/



@SpringBootApplication
@Theme(value = "myapp", variant = Lumo.LIGHT)
@PWA(name = "FcWeb Project", shortName = "FcWeb", offlineResources = {"images/logo.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@EnableScheduling
public class FcltApplication extends SpringBootServletInitializer implements AppShellConfigurator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(FcltApplication.class, args));
	}

}
