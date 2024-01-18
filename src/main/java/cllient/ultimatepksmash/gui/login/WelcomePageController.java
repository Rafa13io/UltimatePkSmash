package cllient.ultimatepksmash.gui.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePageController {

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    void goToLoginController(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/login-view.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();

        Scene nowaScena = new Scene(root);
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(nowaScena);
    }

    @FXML
    void goToRegisterController(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/registration-view.fxml"));
        Parent root = loader.load();

        RegistrationController registrationController = loader.getController();

        Scene nowaScena = new Scene(root);
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(nowaScena);
    }

}
