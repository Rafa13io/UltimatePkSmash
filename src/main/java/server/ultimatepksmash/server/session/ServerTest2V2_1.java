package server.ultimatepksmash.server.session;

import server.ultimatepksmash.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerTest2V2_1 {
    public static void main(String[] args) throws IOException {
        TestServer();
    }
    public static void TestServer() throws IOException {
        Socket socket = null;
        try {
        //Set atack 14L as 200 beofre test
            socket = new Socket("localhost", 25800);
            System.out.println("Connection opened");
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Sending Init request");
            output.writeObject(new LogInReq("wenflon", "123"));
            LogInResp logInResp = (LogInResp) input.readObject();
            System.out.println("Login status:" + logInResp.isSuccess() + " " + logInResp.getUser());
            output.writeObject(new BattleStart2v2Req(5L));
            BattleStartResponse battleStart2v2Response = (BattleStartResponse) input.readObject();
            //System.out.println(battleStart2v2Response);
            //1
            System.out.println("StartRoundReq 1 sending");
            output.writeObject(new StartRoundReq(14L, 9L));
            System.out.println("StartRoundResp 1 receiving");
            StartRoundResp startRoundResp = (StartRoundResp) input.readObject();
            //2
            System.out.println("StartRoundReq 2 sending");
            output.writeObject(new StartRoundReq(14L, 9L));
            System.out.println("StartRoundResp 2 receiving");
            startRoundResp = (StartRoundResp) input.readObject();
            System.out.println(" Object result receiving");
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
