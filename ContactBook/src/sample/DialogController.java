package sample;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.datamodel.Contact;

public class DialogController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField notes;

    public Contact processResult() {
        String name = firstName.getText().trim();
        String last = lastName.getText().trim();
        String phone = phoneNumber.getText().trim();
        String note = notes.getText().trim();
        Contact contact = new Contact(name, last, phone, note);
        return contact;
    }
}
