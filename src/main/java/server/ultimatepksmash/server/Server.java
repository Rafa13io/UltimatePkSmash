package server.ultimatepksmash.server;

import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.database.user.UserService;
import server.ultimatepksmash.server.messages.LogInReq;
import server.ultimatepksmash.server.messages.RegisterReq;
import server.ultimatepksmash.server.messages.LogInResp;
import server.ultimatepksmash.server.messages.RegisterResp;
import server.ultimatepksmash.server.session.SessionEndStatus;
import server.ultimatepksmash.server.session.UserSession;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final List<FutureTask<SessionEndStatus>> sessionList = new ArrayList<>();
    private final ServerSocket serverSocket;
    public FutureTask<UserSession> getSesionFutureTask(int id)
    {
        throw new  UnsupportedOperationException("Not implemented yet. Future task session is not being filled");
//        FutureTask<UserSession> futureTask;
//        readLock.lock();
//        futureTask = sessionList.get(id);
//        readLock.unlock();
//        return futureTask;
    }
    private ServerSocket mainSocket;
    public Server(int port) throws Exception {
        serverSocket = new ServerSocket(port);//port 25800
        run();
    }
    private void run() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        while(true)
        {
            System.out.println("Awaiting for new users");
            Socket socket = serverSocket.accept();
            System.out.println("Message received");
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Waiting for Init request");
            Object obj =  (Object) input.readObject();
            UserService userService = new UserService();
            if(obj instanceof LogInReq)
            {
                LogInReq logInReq  = (LogInReq) obj;
                try {
                    User user;
                    user = userService.getUser(logInReq.getUserName(), logInReq.getUserPassword());
                    output.writeObject(new LogInResp(true, user));
                    //output.flush();
                    FutureTask<SessionEndStatus> futureTask =new FutureTask<SessionEndStatus>(new UserSession(socket, user, output, input));
                    executorService.submit(futureTask);
                    writeLock.lock();
                    sessionList.add(futureTask);
                    writeLock.unlock();
                }
                catch (Exception e)
                {
                    output.writeObject(new LogInResp(false, null));
                    System.out.println("Problem with executing sql statement");
                    socket.close();
                    throw new Exception(e);
                }
            }
            else if(obj instanceof RegisterReq)
            {
                RegisterReq registerReq = (RegisterReq) obj;
                try {
                    User user = new User(registerReq.getUserName(), registerReq.getUserEmail(), registerReq.getUserPassword());
                    userService.addUser(user);
                    output.writeObject(new RegisterResp(true, "Register successful"));
                    socket.close();
                }
                catch (Exception e)
                {
                    output.writeObject(new RegisterResp(false, "Registration failed"));
                    System.out.println("Problem with executing sql statement while trying to register new user");
                    socket.close();
                }

            }

        }
        //executorService.shutdown();
    }

}