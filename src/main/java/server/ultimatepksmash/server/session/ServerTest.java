package server.ultimatepksmash.server.session;

import server.ultimatepksmash.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerTest {


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
            output.writeObject(new LogInReq("rafalo", "123"));

            LogInResp logInResp = (LogInResp) input.readObject();
            System.out.println("Login status:" + logInResp.isSuccess() + " " + logInResp.getUser());
            output.writeObject(new BattleStart1v1Req(1L));
            BattleStartResponse battleStart1v1Response = (BattleStartResponse) input.readObject();
            System.out.println(battleStart1v1Response);
            //1
            output.writeObject(new StartRoundReq(1L, 1L));
            StartRoundResp startRoundResp = (StartRoundResp) input.readObject();
            //2
            output.writeObject(new StartRoundReq(1L, 1L));
             startRoundResp = (StartRoundResp) input.readObject();
            //3
            output.writeObject(new StartRoundReq(1L, 1L));
            startRoundResp = (StartRoundResp) input.readObject();
            //4
            output.writeObject(new StartRoundReq(1L, 1L));
            startRoundResp = (StartRoundResp) input.readObject();
            //5
            output.writeObject(new StartRoundReq(1L, 1L));
            startRoundResp = (StartRoundResp) input.readObject();
            //6
            output.writeObject(new StartRoundReq(1L, 1L));
            startRoundResp = (StartRoundResp) input.readObject();
            //7
            output.writeObject(new StartRoundReq(1L, 1L));
            startRoundResp = (StartRoundResp) input.readObject();
            //8
            output.writeObject(new StartRoundReq(1L, 1L));
            startRoundResp = (StartRoundResp) input.readObject();
            //9
            output.writeObject(new StartRoundReq(1L, 1L));
            startRoundResp = (StartRoundResp) input.readObject();

            Object result = (Object)  input.readObject();
            if(result instanceof BattleWonMessage)
            {
                BattleWonMessage battleWon = (BattleWonMessage) result;
                System.out.println("SUCCCCCCCCCCCCCCCCCCCCEEEEEEEEEEEEEEESSSSSSSSSSSSS");
            }else if(result instanceof  BattleLostMessage)
            {
                BattleLostMessage battleLostMessage = (BattleLostMessage) result;
                System.out.println("LOSTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            }
            output.writeObject(new LogOutReq());
            socket.close();//after successful login socket should be kept open
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
