package fcweb.ui.views.user;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.wontlost.dicebear.Constants.Style;
import com.wontlost.dicebear.DicebearVaadin;
import com.wontlost.dicebear.Options;

import common.util.Utils;
import fcweb.backend.data.entity.FcAttore;
import fcweb.backend.service.AccessoService;
import fcweb.backend.service.AttoreService;
import fcweb.ui.MainAppLayout;
import fcweb.utils.CustomMessageDialog;

@Route(value = "settings", layout = MainAppLayout.class)
@PreserveOnRefresh
@PageTitle("Modifica Dati Utente")
public class SettingsView extends VerticalLayout
		implements ComponentEventListener<ClickEvent<Button>>{

	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AttoreService attoreController;

	private static final String[] styles = new String[] { Style.adventurer.toString(), Style.avataaars.toString(), Style.big_ears.toString(), Style.big_smile.toString(), Style.bottts.toString(), Style.croodles.toString(), Style.identicon.toString(), Style.micah.toString(), Style.miniavs.toString(), Style.open_peeps.toString(), Style.personas.toString(), Style.pixel_art.toString() };

	private TextField nome = null;
	private TextField cognome = null;
	private TextField email = null;
	private TextField username = null;
	private TextField descAttore = null;
	private TextField cellulare = null;
	private DicebearVaadin dicebearVaadin = null;
	private ComboBox<String> comboStyle = null;
	private ToggleButton settingNotifiche = null;

	private ToggleButton cambiaPassword = null;
	private VerticalLayout fieldsPwd = null;
	private PasswordField passwordOld = null;
	private PasswordField password1 = null;
	private PasswordField password2 = null;

	private Button saveButton;

	@Autowired
	private AccessoService accessoController;

	private FcAttore attore = null;

	@PostConstruct
	void init() {
		LOG.debug("init");
		if (!Utils.isValidVaadinSession()) {
			return;
		}
		accessoController.insertAccesso(this.getClass().getName());

		attore = (FcAttore) VaadinSession.getCurrent().getAttribute("ATTORE");
		
		initLayout();
	}

	private void initLayout() {

		try {
			nome = new TextField("Nome:");
			nome.setValue(attore.getNome());

			cognome = new TextField("Cognome:");
			cognome.setValue(attore.getCognome());

			email = new TextField("Email:");
			email.setValue(attore.getEmail());

			username = new TextField("Username:");
			username.setValue(attore.getUsername());
			username.setEnabled(false);

			descAttore = new TextField("Nome Squadra:");
			descAttore.setValue(attore.getDescAttore());
			descAttore.addValueChangeListener(event -> {
				String value = (String) event.getValue();
				if (value.equals("")) {
					CustomMessageDialog.showMessageError("Nome Squadra: obbligatoria!");
					return;
				}
			});

			cellulare = new TextField("Cellulare:");
			if (attore.getCellulare() != null) {
				cellulare.setValue(attore.getCellulare());	
			}

			dicebearVaadin = new DicebearVaadin();
			dicebearVaadin.setStyle(Style.avataaars);
			Options options = new Options();
			options.setRadius(100);
			options.setMargin(20);
			options.setWidth(100);
			options.setHeight(100);
			options.setBackground("transparent");
			dicebearVaadin.setOptions(options);

			comboStyle = new ComboBox<>();
			comboStyle.setLabel("Immagine Profilo:");
			comboStyle.setItems(styles);
			comboStyle.setPlaceholder("Immagine Profilo");
			comboStyle.setClearButtonVisible(false);
			comboStyle.addValueChangeListener(evt -> {

				if (evt.getValue().equals(Style.avataaars.toString())) {
					dicebearVaadin.setStyle(Style.avataaars);
				} else if (evt.getValue().equals(Style.adventurer.toString())) {
					dicebearVaadin.setStyle(Style.adventurer);
				} else if (evt.getValue().equals(Style.big_ears.toString())) {
					dicebearVaadin.setStyle(Style.big_ears);
				} else if (evt.getValue().equals(Style.big_smile.toString())) {
					dicebearVaadin.setStyle(Style.big_smile);
				} else if (evt.getValue().equals(Style.bottts.toString())) {
					dicebearVaadin.setStyle(Style.bottts);
				} else if (evt.getValue().equals(Style.croodles.toString())) {
					dicebearVaadin.setStyle(Style.croodles);
				} else if (evt.getValue().equals(Style.identicon.toString())) {
					dicebearVaadin.setStyle(Style.identicon);
				} else if (evt.getValue().equals(Style.micah.toString())) {
					dicebearVaadin.setStyle(Style.micah);
				} else if (evt.getValue().equals(Style.open_peeps.toString())) {
					dicebearVaadin.setStyle(Style.open_peeps);
				} else if (evt.getValue().equals(Style.personas.toString())) {
					dicebearVaadin.setStyle(Style.personas);
				} else if (evt.getValue().equals(Style.pixel_art.toString())) {
					dicebearVaadin.setStyle(Style.pixel_art);
				}
			});
			comboStyle.setValue(attore.getStyle());

			settingNotifiche = new ToggleButton();
			settingNotifiche.setLabel("Notifiche Email");
			settingNotifiche.setValue(attore.isNotifiche());

			cambiaPassword = new ToggleButton();
			cambiaPassword.setLabel("Cambia Password");
			cambiaPassword.setValue(false);
			cambiaPassword.addValueChangeListener(evt -> {
				fieldsPwd.setVisible(evt.getValue());
			});

			passwordOld = new PasswordField("Vecchia Password:");
			passwordOld.setValue("");

			password1 = new PasswordField("Nuova Password:");
			password1.setValue("");

			password2 = new PasswordField("Ripeti Password:");
			password2.setValue("");

			// Add both to a panel
			fieldsPwd = new VerticalLayout(passwordOld,password1,password2);
			fieldsPwd.setSpacing(true);
			fieldsPwd.setVisible(false);

			saveButton = new Button("Save");
			saveButton.addClickListener(this);

			FormLayout formLayout = new FormLayout();
			formLayout.add(nome, cognome, descAttore, cellulare, email, comboStyle, dicebearVaadin, settingNotifiche, username, cambiaPassword, fieldsPwd);
			formLayout.setResponsiveSteps(
					// Use one column by default
					new ResponsiveStep("0",1),
					// Use two columns, if layout's width exceeds 500px
					new ResponsiveStep("500px",2));
			formLayout.setColspan(email, 2);
			formLayout.setColspan(settingNotifiche, 2);
			formLayout.setColspan(cambiaPassword, 2);
			formLayout.setColspan(username, 2);
			formLayout.setColspan(fieldsPwd, 2);

			// add(descAttore);
			// add(comboStyle);
			// add(dicebearVaadin);
			// add(settingNotifiche);
			// add(cambiaPassword);
			// add(fieldsPwd);
			add(formLayout);
			add(saveButton);

		} catch (Exception ex2) {
			LOG.error(ex2.getMessage());
		}
	}

	@Override
	public void onComponentEvent(ClickEvent<Button> event) {
		if (event.getSource() == saveButton) {

			if (this.descAttore.getValue().equals("")) {
				CustomMessageDialog.showMessageError("Nome Squadra: obbligatoria!");
				return;
			}

			if (this.comboStyle.getValue().equals("")) {
				CustomMessageDialog.showMessageError("Style: obbligatoria!");
				return;
			}

			attore.setNome(nome.getValue());
			attore.setCognome(cognome.getValue());
			attore.setDescAttore(descAttore.getValue());
			attore.setCellulare(cellulare.getValue());
			attore.setStyle(comboStyle.getValue());
			attore.setNotifiche(settingNotifiche.getValue().booleanValue());
			if (this.cambiaPassword.getValue()) {
				if (!checkPwd()) {
					return;
				}
				attore.setPassword(this.password1.getValue());
			}
			attoreController.updateAttore(attore);

			CustomMessageDialog.showMessageInfo("Operazione eseguita con successo!");
		}
	}

	private boolean checkPwd() {

		if (passwordOld.getValue().equals("")) {
			CustomMessageDialog.showMessageError("Vecchia Password: obbligatoria!");
			return false;
		}

		if (!attore.getPassword().equals(passwordOld.getValue())) {
			CustomMessageDialog.showMessageError("Vecchia Password non valida!");
			return false;
		}

		if (password1.getValue().equals("")) {
			CustomMessageDialog.showMessageError("Nuova Password: obbligatoria!");
			return false;
		}

		if (password2.getValue().equals("")) {
			CustomMessageDialog.showMessageError("Ripeti Password: obbligatoria!");
			return false;
		}

		if (!password1.getValue().equals(password2.getValue())) {
			CustomMessageDialog.showMessageError("Nuova Password e Ripeti Password differenti!");
			return false;
		}

		return true;
	}

}