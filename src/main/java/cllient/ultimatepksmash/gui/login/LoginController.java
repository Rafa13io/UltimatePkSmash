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

public class LoginController {

    @FXML
    private Button backButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/welcome-page-view.fxml"));
        Parent root = loader.load();

        WelcomePageController welcomePageController = loader.getController();

        Scene nowaScena = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(nowaScena);
    }
    @FXML
    void login(MouseEvent event) {

    }

}
