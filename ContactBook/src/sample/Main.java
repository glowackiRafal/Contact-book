package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.datamodel.ContactData;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("My Contacts");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        ContactData.getInstance().loadContacts1();
    }

    @Override
    public void stop() throws Exception {
        ContactData.getInstance().storeContacts();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
