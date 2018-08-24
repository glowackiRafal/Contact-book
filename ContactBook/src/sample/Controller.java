package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TableView<Contact> tableView;

    public void initialize() {
        tableView.setItems(ContactData.getInstance().getContacts());

    }

    @FXML
    public void handleRemoveMenuItem() {
        Contact contact = (Contact) tableView.getSelectionModel().getSelectedItem();
        deleteItem(contact);
    }

    @FXML
    public void handleEditMenuItem() {
        Contact contact = tableView.getSelectionModel().getSelectedItem();


        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Editing: "+contact.getFirstName());
        dialog.setHeaderText("Use this to edit selected contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        if (dialogWindow(dialog, fxmlLoader)) return;
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            Contact tmpContact = controller.processResult();
            contact.setFirstName(tmpContact.getFirstName());
            contact.setLastName(tmpContact.getLastName());
            contact.setPhoneNumber(tmpContact.getPhoneNumber());
            contact.setNotes(tmpContact.getNotes());
        }
    }

    private boolean dialogWindow(Dialog<ButtonType> dialog, FXMLLoader fxmlLoader) {
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return true;
        }
        return false;
    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new Contact");
        dialog.setHeaderText("Use this to create new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        if (dialogWindow(dialog, fxmlLoader)) return;
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            Contact contact = controller.processResult();
            ContactData.getInstance().addContact(contact);
        }
    }

    public void deleteItem(Contact contact) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Todo Item");
        alert.setHeaderText("Delete contact: " + contact.getFirstName());
        alert.setContentText("Are you sure? Press ok to confirm, or cancel to back out");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactData.getInstance().removeContact(contact);
        }
    }
}
