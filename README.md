# Ultimate PK Smash
Tactical fighting game inspired by Pokemon games. 

## Details
Its based a client-server communication concept using Java `java.net.ServerSocket`.

Server side:
```java
public Server(int port) throws Exception {
    serverSocket = new ServerSocket(port); // 25800
    run(); // start the server
}
```

Client Side:
```java
new Thread(() -> {
  try {
      // Establish a socket connection
      socket = new Socket("localhost", 25800);
      
      // Create object output and input streams
      outputStream = new ObjectOutputStream(socket.getOutputStream());
      inputStream = new ObjectInputStream(socket.getInputStream());
      
      // Create a RegisterReq object and send it
      LogInReq logInReq = new LogInReq(username.getText(), password.getText());
      System.out.println("sending login request: " + logInReq);
      outputStream.writeObject(logInReq);
...
```

Each user has to register/log-in in order to play. Each logged user has its own game session 
and thus he has its own individual thread running on the server using `java.util.concurrent.FutureTask`.
```java
  User user = userService.getUser(logInReq.getUserName(), logInReq.getUserPassword());
  output.writeObject(new LogInResp(true, user));
  UserSession userSession = new UserSession(socket, user, output, input);
  FutureTask<SessionEndStatus> futureTask = new FutureTask<SessionEndStatus>(userSession);
  executorService.submit(futureTask);
```
When two players are in queue for a battle, the server matches them and they can start playing right away.

### Database
We are using PostgreSQL database and connecting via JDBC. <br>
Here we are storing information about each registered player, smashers, their abilities and also battle history.
```java
public static void connectToDatabase() throws SQLException {
    String dbName = "ultimatepksmashers";
    String url = "jdbc:postgresql://localhost:5432/";
    
    try {
        connection = DriverManager.getConnection(url, "postgres", PASSWORD);
        PreparedStatement statement = connection.prepareStatement("CREATE DATABASE " + dbName);
        statement.executeUpdate();
        System.out.println("created database " + dbName + " schema: " + connection.getSchema());
    }
    catch (Exception e) {
        // if already exists: make connection
        url = "jdbc:postgresql://localhost:5432/" + dbName;
        connection = DriverManager.getConnection(url, "postgres", PASSWORD);
        System.out.println("connected to database " + dbName + " schema: " + connection.getSchema());
    }
}
```

## Gameplay
Each player with one of "Smashers" form their collection can fight in a tour based fight in order to determine who is better.

- Each turn a player must select one of 3 attacks and one of 2 defence abilities and then click submit.
- When two players make their choices, the damage dealt is calculated and both of their HP is updated. <br>
  The HP update order is determined by a hidden star called 'ECTS' points: if your smasher have more of them, he attacks first (his damage dealt is calculated first).
- Battle ends when one of the "Smasher" gets below 0 HP.
- Winner gets new random "Smasher" to their collection.
 
![image](https://github.com/Rafa13io/UltimatePkSmash/assets/129612952/9647c342-5f08-44aa-9dcb-befbe96b5291)


## Dependencies
- [JavaFX](https://github.com/openjdk/jfx)
- [JDBC PostgreSQL Driver](https://jdbc.postgresql.org/)
