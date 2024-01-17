package server.ultimatepksmash.server.gamesmanager;

import server.ultimatepksmash.server.database.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GameSession {
    private List<User> players = new ArrayList<>();
    private ReentrantReadWriteLock onGoingRoundReceiving = new ReentrantReadWriteLock();
    private Lock onGoingRoundAllowReceiving = onGoingRoundReceiving.readLock();
    private Lock onGoingRoundBlockReceiving = onGoingRoundReceiving.writeLock();

    private ReentrantReadWriteLock onGoingRoundSending = new ReentrantReadWriteLock();
    private Lock onGoingRoundAllowSending = onGoingRoundSending.readLock();
    private Lock onGoingRoundBlockSending = onGoingRoundSending.writeLock();


    private ReentrantReadWriteLock numberOfPlayersReadyLock = new ReentrantReadWriteLock();
    private Lock numberOfPlayersReadyRaadLock = numberOfPlayersReadyLock.readLock();
    private Lock numberOfPlayersReadyWriteLock = numberOfPlayersReadyLock.writeLock();

    int numberOfPlayersReady = 0;
    public GameSession()
    {
        onGoingRoundBlockReceiving.lock();
    }
    public void addPlayer(User user)
    {
        synchronized (players)
        {
            players.add(user);
            numberOfPlayersReadyWriteLock.lock();
            numberOfPlayersReadyWriteLock.lock();
            numberOfPlayersReady++;
            if(numberOfPlayersReady == players.size())
            {
                onGoingRoundBlockReceiving.unlock();
            }
            numberOfPlayersReadyWriteLock.unlock();
            numberOfPlayersReadyWriteLock.unlock();
            onGoingRoundAllowReceiving.lock();
        }
    }
    public int getNumberOfPlayers()
    {
        synchronized (players)
        {
            return  players.size();
        }
    }
    public void waitForReceivingPart()
    {
//        if(onGoingRoundBlockReceiving.tryLock())
//        {
//            onGoingRoundBlockReceiving.lock();
//        }
//        onGoingRoundAllowSending.lock();
    }

    public void waitForSendingPart()
    {
//        if(onGoingRoundBlockSending.tryLock())
//        {
//            onGoingRoundBlockSending.lock();
//        }
//        onGoingRoundAllowReceiving.lock();
    }

    public void notifyMessageReceived()
    {
//TODO: implement this.

    }
}
