package cllient.ultimatepksmash.gui.login;

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
import server.ultimatepksmash.server.messages.RegisterReq;
import server.ultimatepksmash.server.messages.RegisterResp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Getter
public class RegistrationController {

    @FXML
    private Button backButton;
    
    @FXML
    private Label error;

    @FXML
    private TextField email;

    @FXML
    private TextField password;
    
    @FXML
    private TextField username;
    
    @FXML
    private Button registerButton;
    
    private Socket socket;


    @FXML
    void registerUser(MouseEvent event) throws IOException {
        new Thread(() -> {
            try {
                // Establish a socket connection
                socket = new Socket("localhost", 25800);
                
                // Create object output and input streams
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                
                // Create a RegisterReq object and send it
                RegisterReq registerReq = new RegisterReq(username.getText(), password.getText(), email.getText());
                System.out.println("sending register request: " + registerReq);
                outputStream.writeObject(registerReq);
                
                // Receive and handle the RegisterResp
                RegisterResp registerResp = (RegisterResp) inputStream.readObject();
                System.out.println(registerResp);
                if (registerResp.isSuccessful()) {
                    goBack(null); //change this to menu
                }
                else {
                    Platform.runLater(() -> {
                        error.setText("error");
                        error.setVisible(true);
                    });
                }
                
                // Close the socket and streams
                socket.close();
                outputStream.close();
                inputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/welcome-page-view.fxml"));
                Parent root = loader.load();
                
                Scene nowaScena = new Scene(root);
                Stage stage = (Stage) backButton.getScene().getWindow();
                stage.setScene(nowaScena);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
}
