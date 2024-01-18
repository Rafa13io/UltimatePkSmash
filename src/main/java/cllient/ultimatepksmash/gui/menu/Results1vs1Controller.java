package cllient.ultimatepksmash.gui.menu;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import server.ultimatepksmash.server.database.results.Result1v1Read;
import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.messages.Get1v1ResultsReq;
import server.ultimatepksmash.server.messages.Get1v1ResultsResp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Results1vs1Controller {

    @FXML
    private TableView<Result1v1Read> table;
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    User user;
    public Results1vs1Controller(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream, User user)
    {
        this.socket =socket;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.user = user;
    }
    @FXML
    void onBackClicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/menu-view.fxml"));
        loader.setController(new MenuController(socket, outputStream, inputStream, user));
        Parent destination = null;
        try {
            destination = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(destination);
        Stage stage = (Stage) table.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    public void initialize()
    {

        try {
            // Create a RegisterReq object and send it
            Get1v1ResultsReq req = new Get1v1ResultsReq();
            System.out.println("sending login request: " + req);
            outputStream.writeObject(req);

            // Receive and handle the RegisterResp
            Get1v1ResultsResp resp = (Get1v1ResultsResp) inputStream.readObject();
            System.out.println(resp);
            ObservableList<Result1v1Read> result1vs1s = FXCollections.observableArrayList(resp.getResult1vs1List());

            TableColumn<Result1v1Read, String> firstNameCol = new TableColumn<>("End date");
            firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));

            TableColumn<Result1v1Read, String> secondNameCol = new TableColumn<>("Winner");
            secondNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWinner()));

            TableColumn<Result1v1Read, String> thirdNameCol = new TableColumn<>("Loser");
            secondNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoser()));
            table.getColumns().addAll(firstNameCol, secondNameCol, thirdNameCol);
            table.setItems(result1vs1s);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

}
