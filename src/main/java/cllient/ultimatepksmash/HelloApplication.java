package cllient.ultimatepksmash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.ultimatepksmash.server.messages.InitReq;
import server.ultimatepksmash.server.messages.InitType;
import server.ultimatepksmash.server.messages.LogInResp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        TestServer();
        launch();
    }
    public static void TestServer() throws IOException {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 25800);

        //socket.setSoTimeout(5000);
        System.out.println("Message received");
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        System.out.println("Sending Init request");
        output.writeObject(new InitReq(InitType.logIn, "Rafal", "123", ""));

        LogInResp logInResp =  (LogInResp) input.readObject();
        System.out.println(logInResp.isSuccess() + " " + logInResp.getUser());
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {

        }
    }
}