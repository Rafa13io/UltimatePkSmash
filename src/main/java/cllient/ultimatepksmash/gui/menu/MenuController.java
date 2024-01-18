package cllient.ultimatepksmash.gui.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.java.Log;
import server.ultimatepksmash.server.database.smasher.Smasher;
import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.messages.LogInResp;
import server.ultimatepksmash.server.messages.LogOutReq;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MenuController {
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    @FXML
    private ComboBox<Smasher> smashersDropBox;
    Parent root;
    User user;
    public MenuController(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream, User user)
    {
        this.socket = socket;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.user = user;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/welcome-page-view.fxml"));
        try {
            root = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    public void initialize() {
        smashersDropBox.setConverter(new StringConverter<Smasher>() {
            @Override
            public String toString(Smasher object) {
                return (object != null) ? object.getName() : "";
            }

            @Override
            public Smasher fromString(String string) {

                return user.getSmashers().stream().filter(s -> s.getName().equals(string)).findAny().orElse(null);
            }
        });
        smashersDropBox.getItems().addAll(user.getSmashers());
    }
    @FXML
    void on1vs1Clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/lobby1v1.fxml"));
        loader.setController(new Lobby1v1Controller(socket, outputStream, inputStream, user, smashersDropBox.getValue()));
        Parent destination = null;
        try {
            destination = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(destination);
        Stage stage = (Stage) smashersDropBox.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void on2vs2Clicked(MouseEvent event) {

    }

    @FXML
    void onShowResult(MouseEvent event) {

    }

    @FXML
    void onLogOut(MouseEvent event) {

        try {
            outputStream.writeObject(new LogOutReq());
            //socket.close();
            Scene scene = new Scene(root);
            Stage stage = (Stage) smashersDropBox.getScene().getWindow();
            stage.setScene(scene);
        }
        catch (Exception e)
        {
            System.out.println("Error while closing socket");
        }

    }


}
