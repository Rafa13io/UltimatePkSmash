package cllient.ultimatepksmash.gui.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    @FXML
    private Button backButton;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private Button registerButton;

    @FXML
    private TextField username;

    @FXML
    void registerUser(MouseEvent event) throws IOException {

    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/welcome-page-view.fxml"));
        Parent root = loader.load();

        WelcomePageController welcomePageController = loader.getController();

        Scene nowaScena = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(nowaScena);
    }
}
