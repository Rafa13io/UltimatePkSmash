package server.ultimatepksmash.server.session;

import server.ultimatepksmash.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerTestResults {


    public static void main(String[] args) throws IOException {
        TestServer();
    }
    public static void TestServer() throws IOException {
        Socket socket = null;
        try {

            socket = new Socket("localhost", 25800);
            System.out.println("Connection opened");
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Sending Init request");
            output.writeObject(new LogInReq("wenflon", "123"));

            LogInResp logInResp = (LogInResp) input.readObject();
            System.out.println("Login status:" + logInResp.isSuccess() + " " + logInResp.getUser());
            output.writeObject(new Get1v1ResultsReq());
            Get1v1ResultsResp get1v1ResultsResponse = (Get1v1ResultsResp) input.readObject();
            System.out.println(get1v1ResultsResponse);
            output.writeObject(new LogOutReq());
            socket.close();//after successful login socket should be kept open
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
