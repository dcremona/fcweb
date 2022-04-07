package fcweb.utils;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;

import de.codecamp.vaadin.components.messagedialog.MessageDialog;

public class CustomMessageDialog{

	public static String LABEL_CHIUDI = "Chiudi";
	public static String LABEL_ANNULLA = "Annulla";
	public static String LABEL_SALVA = "Salva";

	public static String MSG_OK = "Operazione eseguita con successo!";

	public static String MSG_MAIL_KO = "Errore invio mail";
	public static String MSG_ADMIN_MERCATO_KO = "L'amministratore non ha ancora abilitato il mercato.";
	public static String MSG_ERROR_GENERIC = "Errore nel sistema, contattare amministratore";

	public static String MSG_ERROR_INSERT_GIOCATORI = "Attenzione, impostare tutti i giocatori";

	public static String TITLE_MSG_CONFIRM = "Per favore conferma:";
	public static String TITLE_MSG_ERROR = "Messaggio di errore";
	public static String TITLE_MSG_INFO = "Messaggio di conferma";

	public static void showMessageError(String msg) {
		MessageDialog messageDialog = new MessageDialog().setTitle(TITLE_MSG_ERROR, VaadinIcon.WARNING.create()).setMessage(msg == null ? "ND" : msg);
		// messageDialog.addButtonToMiddle().text("Annulla").tertiary().onClick(ev
		// -> Notification.show("Annulla")).closeOnClick();
		messageDialog.addButtonToMiddle().text(LABEL_CHIUDI).primary().onClick(ev -> Notification.show(LABEL_CHIUDI)).closeOnClick();
		messageDialog.open();
	}

	public static void showMessageInfo(String msg) {
		MessageDialog messageDialog = new MessageDialog().setTitle(TITLE_MSG_INFO, VaadinIcon.EXCLAMATION.create()).setMessage(msg == null ? "ND" : msg);
		// messageDialog.addButtonToMiddle().text("Annulla").tertiary().onClick(ev
		// -> Notification.show("Annulla")).closeOnClick();
		messageDialog.addButtonToMiddle().text(LABEL_CHIUDI).primary().onClick(ev -> Notification.show(LABEL_CHIUDI)).closeOnClick();
		messageDialog.open();
	}

	public static void showMessageErrorDetails(String msg, String details) {

		MessageDialog dialog = new MessageDialog();
		dialog.setTitle(TITLE_MSG_ERROR, VaadinIcon.WARNING.create());
		dialog.setMessage(msg);

		dialog.addButtonToLeft().text("Details").title("Tooltip").icon(VaadinIcon.ARROW_DOWN).toggleDetails();
		dialog.addButtonToLeft().text("Abort").tertiary().closeOnClick();
		dialog.addButton().text("Ignore").error().closeOnClick();
		dialog.addButton().text("Retry").icon(VaadinIcon.ROTATE_LEFT).primary().closeOnClick();

		TextArea detailsText = new TextArea();
		detailsText.setWidthFull();
		detailsText.setMaxHeight("15em");
		detailsText.setReadOnly(true);
		detailsText.setValue(details);
		dialog.getDetails().add(detailsText);

		dialog.open();

	}

}
