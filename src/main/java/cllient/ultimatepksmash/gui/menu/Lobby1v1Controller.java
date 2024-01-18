package cllient.ultimatepksmash.gui.menu;


import cllient.ultimatepksmash.gui.arena.ArenaController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.ultimatepksmash.server.database.smasher.Smasher;
import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Lobby1v1Controller {
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    Smasher chosenSmasher;
    User user;
    @FXML
    private AnchorPane anchor;
    public  Lobby1v1Controller(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream, User user, Smasher chosenSmascher)
    {
        this.socket =socket;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.chosenSmasher = chosenSmascher;
        this.user = user;
    }
    public void goToArena(BattleStartResponse response)
    {
        Platform.runLater(() -> {
            System.out.println("go to arena");//TODO: implement this
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/arena-view.fxml"));
            loader.setController(new ArenaController(response,user,socket,inputStream,outputStream));
            Parent destination = null;
            try {
                destination = loader.load();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(destination);
            Stage stage = (Stage) anchor.getScene().getWindow();
            stage.setScene(scene);

        });

    }
    @FXML
    public void initialize() {
        new Thread(() -> {
            try {
                // Create a RegisterReq object and send it
                BattleStart1v1Req req = new BattleStart1v1Req(chosenSmasher.getId());
                System.out.println("sending login request: " + req);
                outputStream.writeObject(req);

                // Receive and handle the RegisterResp
                BattleStartResponse resp = (BattleStartResponse) inputStream.readObject();
                System.out.println(resp);
                goToArena(resp);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
