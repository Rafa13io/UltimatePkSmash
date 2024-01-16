package server.ultimatepksmash.server;

import server.ultimatepksmash.server.database.DataBaseService;
import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.database.user.UserService;
import server.ultimatepksmash.server.messages.InitReq;
import server.ultimatepksmash.server.messages.InitType;
import server.ultimatepksmash.server.messages.LogInResp;
import server.ultimatepksmash.server.messages.RegisterResp;
import server.ultimatepksmash.server.session.SessionEndReason;
import server.ultimatepksmash.server.session.SessionEndStatus;
import server.ultimatepksmash.server.session.UserSession;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
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
    public Server(int port) throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(port);//port 25800
        run();
    }
    private void run() throws IOException, ClassNotFoundException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        while(true)
        {
            System.out.println("Awaiting for new users");
            Socket socket = serverSocket.accept();
            System.out.println("Message received");
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Waiting for Init request");
            InitReq initReq =  (InitReq) input.readObject();

            switch (initReq.getInitType())
            {
                case logIn:
                    UserService userService = new UserService();
                    User user;
                    try {
                        user = userService.getUser(initReq.getUserName(), initReq.getUserPassword());
                        output.writeObject(new LogInResp(true, user));
                        FutureTask<SessionEndStatus> futureTask =new FutureTask<SessionEndStatus>(new UserSession(socket));
                        executorService.execute(futureTask);
                        writeLock.lock();
                        sessionList.add(futureTask);
                        writeLock.unlock();
                    }
                    catch (Exception e)
                    {
                        output.writeObject(new LogInResp(false, null));
                        System.out.println("Problem with executing sql statement");
                        socket.close();
                    }

                    break;
                case register:
                    //TODO: implement this
                    output.writeObject(new RegisterResp(false, "Registration not implemented yet"));
                    socket.close();
            }
            executorService.shutdown();
        }

    }

}