package cllient.ultimatepksmash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.ultimatepksmash.server.messages.RegisterReq;
import server.ultimatepksmash.server.messages.LogInResp;
import server.ultimatepksmash.server.messages.RegisterResp;
import server.ultimatepksmash.server.messages.LogInReq;
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
            System.out.println("Connection opened");
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Sending Init request");
            output.writeObject(new RegisterReq("rafalo", "123", "rafalo@gmail.com"));

            RegisterResp registerResp =  (RegisterResp) input.readObject();
            System.out.println("Register status:" + registerResp.isSuccessful() + " " + registerResp.getMessage());
            socket.close();

            socket = new Socket("localhost", 25800);
            System.out.println("Connection opened");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Sending Init request");
            output.writeObject(new LogInReq("rafalo", "123"));

            LogInResp logInResp =  (LogInResp) input.readObject();
            System.out.println("Login status:" + logInResp.isSuccess() + " " + logInResp.getUser());
            socket.close();//after successful login socket should be kept open


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {

        }
    }
}