package cllient.ultimatepksmash.gui.login;

import cllient.ultimatepksmash.gui.menu.MenuController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import server.ultimatepksmash.server.database.smasher.Smasher;
import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.messages.LogInReq;
import server.ultimatepksmash.server.messages.LogInResp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


@Getter
public class LoginController {

    @FXML
    private Button backButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label error;
    
    @FXML
    private TextField password;

    @FXML
    private TextField username;
    
    private Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    @FXML
    void goBack(MouseEvent event) throws IOException {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/welcome-page-view.fxml"));

            Parent root = null;
            try {
                root = loader.load();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            
            Scene nowaScena = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(nowaScena);
        });
    }
    public void goToMenuPage(LogInResp logInResp)
    {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/menu-view.fxml"));
            loader.setController(new MenuController(socket, outputStream, inputStream, logInResp.user));
            Parent destination = null;
            try {
                destination = loader.load();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(destination);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        });
    }
    
    @FXML
    void login(MouseEvent event) throws IOException {
        //w nowym watku otworzyc socket i wyslac login request
        new Thread(() -> {
            try {
                // Establish a socket connection
                socket = new Socket("localhost", 25800);
                
                // Create object output and input streams
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                
                // Create a RegisterReq object and send it
                LogInReq logInReq = new LogInReq(username.getText(), password.getText());
                System.out.println("sending login request: " + logInReq);
                outputStream.writeObject(logInReq);
                
                // Receive and handle the RegisterResp
                LogInResp logInResp = (LogInResp) inputStream.readObject();
                System.out.println(logInResp);
                
                if (logInResp.isSuccess()) {
                    goToMenuPage(logInResp);
                }
                else {
                    Platform.runLater(() -> {
                        error.setText("error while logging in");
                        error.setVisible(true);
                    });
                }
                
                // Close streams
                //outputStream.close();
                //inputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
