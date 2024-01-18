package cllient.ultimatepksmash.gui.arena;

import cllient.ultimatepksmash.gui.menu.MenuController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.ultimatepksmash.server.database.smasher.Smasher;
import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.messages.*;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ArenaController {
    @FXML
    private Label smasher1Name;
    @FXML
    private Label hp1;
    @FXML
    private ProgressBar progressBar1;

    @FXML
    private Button attackButton1;
    @FXML
    private Button attackButton2;
    @FXML
    private Button attackButton3;

    @FXML
    private Button defenceButton1;

    @FXML
    private Button defenceButton2;

    @FXML
    private Label smasher2Name;
    @FXML
    private Label hp2;
    @FXML
    private ProgressBar progressBar2;
    @FXML
    private TextArea arenaConsole;
    private Smasher smasher1;
    private Smasher smasher2;

    private double currentHP1;
    private double currentHP2;

    private boolean attackActive1 = false;
    private boolean attackActive2 = false;
    private boolean attackActive3 = false;
    private boolean defenceActive1 = false;
    private boolean defenceActive2 = false;

    @FXML
    private ImageView smasher1ImageView;
    @FXML
    private ImageView smasher2ImageView;

    @FXML
    private TextArea attack1Description;
    @FXML
    private TextArea attack2Description;
    @FXML
    private TextArea attack3Description;
    @FXML
    private TextArea defence1Description;
    @FXML
    private TextArea defence2Description;
    @FXML
    private Button submitButton;
    private Long chosenAttackId;
    private Long chosenDefenceId;
    private Smasher userSmasher;
    private boolean teamA;
    private boolean decisionSubmitted = false;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    @FXML
    private Label userName1;
    private String userName1text;
    private String userName2text;
    @FXML
    private Label userName2 ;
    
    private User user;

    public ArenaController(BattleStartResponse battleStartResponse, User user, Socket socket, ObjectInputStream in, ObjectOutputStream out){
        this.output = out;
        this.input = in;
        this.socket = socket;
        this.user = user;

        if(Objects.equals(user.getUsername(), battleStartResponse.getPlayersNames().get(0))){
            teamA =true;
        }else{
            teamA = false;
        }

        if(teamA){
            userSmasher = battleStartResponse.getSmashers().get(0);
            this.smasher1 = battleStartResponse.getSmashers().get(0);
            this.smasher2 = battleStartResponse.getSmashers().get(1);
            this.userName1text = battleStartResponse.getPlayersNames().get(0);
            this.userName2text = battleStartResponse.getPlayersNames().get(1);

        }else{
            userSmasher = battleStartResponse.getSmashers().get(1);
            this.smasher1 = battleStartResponse.getSmashers().get(0);
            this.smasher2 = battleStartResponse.getSmashers().get(1);
            this.userName1text = battleStartResponse.getPlayersNames().get(0);
            this.userName2text = battleStartResponse.getPlayersNames().get(1);

        }

    }
    @FXML
    public void initialize() {

        userName1.setText(userName1text);
        userName2.setText(userName2text);
        // Inicjalizacja ProgressBar i Label dla smasher1

        currentHP1 = smasher1.getHealthPoints();
        progressBar1.setProgress(smasher1.getHealthPoints());
        progressBar1.setStyle("-fx-accent: #00ff00");
        smasher1Name.setText(smasher1.getName());
        currentHP1 = smasher1.getHealthPoints();


        currentHP2 = smasher2.getHealthPoints();
        progressBar2.setProgress(smasher2.getHealthPoints());
        progressBar2.setStyle("-fx-accent: #00ff00");
        smasher2Name.setText(smasher2.getName());

        updateHP(progressBar1,hp1, currentHP1,smasher1.getHealthPoints());
        updateHP(progressBar2,hp2, currentHP2,smasher2.getHealthPoints());


        loadSmasherImage(smasher1ImageView, "C:\\Users\\kubad\\Desktop\\programowanie\\UltimatePkSmash\\src\\main\\photos\\avat ig.png");

        flipImageHorizontally(smasher2ImageView);
        loadSmasherImage(smasher2ImageView, "C:\\Users\\kubad\\Desktop\\programowanie\\UltimatePkSmash\\src\\main\\photos\\avat ig.png");


        attack1Description.appendText("Opis: " + userSmasher.getAttacks().get(0).getDescription() + "\n");
        attack1Description.appendText("Dmg: " + userSmasher.getAttacks().get(0).getAttackPoints().toString() + "\n");
        attack1Description.appendText("Typ " + userSmasher.getAttacks().get(0).getType().toString());
        attackButton1.setText(userSmasher.getAttacks().get(0).getName());

        attack2Description.appendText("Opis: " + userSmasher.getAttacks().get(1).getDescription() + "\n");
        attack2Description.appendText("Dmg: " + userSmasher.getAttacks().get(1).getAttackPoints().toString() + "\n");
        attack2Description.appendText("Typ " + userSmasher.getAttacks().get(1).getType().toString());
        attackButton2.setText(userSmasher.getAttacks().get(1).getName());

        attack3Description.appendText("Opis: " + userSmasher.getAttacks().get(2).getDescription() + "\n");
        attack3Description.appendText("Dmg: " + userSmasher.getAttacks().get(2).getAttackPoints().toString() + "\n");
        attack3Description.appendText("Typ " + userSmasher.getAttacks().get(2).getType().toString());
        attackButton3.setText(userSmasher.getAttacks().get(2).getName());

        defence1Description.appendText("Opis: " + userSmasher.getDefences().get(0).getDescription() + "\n");
        defence1Description.appendText("Def: " + userSmasher.getDefences().get(0).getDefencePoints().toString() + "\n");
        defence1Description.appendText("Typ " + userSmasher.getDefences().get(0).getType().toString());
        defenceButton1.setText(userSmasher.getDefences().get(0).getName());

        defence2Description.appendText("Opis: " + userSmasher.getDefences().get(1).getDescription() + "\n");
        defence2Description.appendText("Def: " + userSmasher.getDefences().get(1).getDefencePoints().toString() + "\n");
        defence2Description.appendText("Typ " + userSmasher.getDefences().get(1).getType().toString());
        defenceButton2.setText(userSmasher.getDefences().get(1).getName());

        submitButton.setOnAction(event -> submitDecision(chosenAttackId, chosenDefenceId));
        arenaConsole.setEditable(false);
    }


    @FXML
    public void handleAttackButton1() {

        //TODO zczytać dane z attaku ale smasher jeszcze nie ma ataków

        attackActive1 = !attackActive1;

        if (attackActive1) {
            attackButton1.setStyle("-fx-background-color: #99ccff; -fx-text-fill: #000000; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 2px"); // Zmiana koloru tła i tekstu na wciśnięty
            attackButton2.setStyle("");
            attackActive2 = false;
            attackButton3.setStyle("");
            attackActive3 = false;
        } else {
            attackButton1.setStyle(""); // Resetowanie stylu do domyślnego
        }
        chosenAttackId = userSmasher.getAttacks().get(0).getId();

    }

    @FXML
    public void handleAttackButton2() {

        //TODO zczytać dane z attaku ale smasher jeszcze nie ma ataków
        attackActive2 = !attackActive2;


        if (attackActive2) {
            attackButton2.setStyle("-fx-background-color: #99ccff; -fx-text-fill: #000000; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 2px"); // Zmiana koloru tła i tekstu na wciśnięty
            attackButton1.setStyle("");
            attackActive1 = false;
            attackButton3.setStyle("");
            attackActive3 = false;
        } else {
            attackButton2.setStyle(""); // Resetowanie stylu do domyślnego
        }

        chosenAttackId = userSmasher.getAttacks().get(1).getId();
        String smasherName = userSmasher.getName();

    }

    @FXML
    public void handleAttackButton3() {
        attackActive3 = !attackActive3;
        attackActive2 = false;
        attackActive1 = false;

        if (attackActive3) {
            attackButton3.setStyle("-fx-background-color: #99ccff; -fx-text-fill: #000000; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 2px"); // Zmiana koloru tła i tekstu na wciśnięty
            attackButton1.setStyle("");
            attackActive1 = false;
            attackButton2.setStyle("");
            attackActive2 = false;
        } else {
            attackButton3.setStyle(""); // Resetowanie stylu do domyślnego
        }
        chosenAttackId = userSmasher.getAttacks().get(2).getId();
        //TODO zczytać dane z attaku ale smasher jeszcze nie ma ataków

        String smasherName = userSmasher.getName();


    }
    @FXML
    public void handleButtonDefence1(){
        defenceActive1 = !defenceActive1;

        if (defenceActive1) {
            defenceButton1.setStyle("-fx-background-color: #99ccff; -fx-text-fill: #000000; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 2px"); // Zmiana koloru tła i tekstu na wciśnięty
            defenceButton2.setStyle("");
            defenceActive2 = false;
        } else {
            defenceButton1.setStyle(""); // Resetowanie stylu do domyślnego
        }

        chosenDefenceId = userSmasher.getDefences().get(0).getId();

        String smasherName = userSmasher.getName();


    }
    @FXML
    public void handleButtonDefence2(){
        defenceActive2 = !defenceActive2;

        if (defenceActive2) {
            defenceButton2.setStyle("-fx-background-color: #99ccff; -fx-text-fill: #000000; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 2px"); // Zmiana koloru tła i tekstu na wciśnięty
            defenceButton1.setStyle("");
            defenceActive1 = false;
        } else {
            defenceButton2.setStyle(""); // Resetowanie stylu do domyślnego
        }

        //TODO zczytać dane z attaku ale smasher jeszcze nie ma ataków
        chosenDefenceId = userSmasher.getDefences().get(1).getId();
        String smasherName = userSmasher.getName();

//
    }

    public void updateHP(ProgressBar progressBar,Label hpLabel, double currentHP, double maxHP){
        //update hp
        double healthPercentage = currentHP / maxHP;
        progressBar.setProgress(healthPercentage);
        progressBar.setStyle("-fx-accent: " + getColorFromHealth(healthPercentage));

        //update label
        String hpText = String.format("%.0f/%.0f", currentHP, maxHP);
        hpLabel.setText(hpText);
    }

    public void submitDecision(Long attack, Long defence) {
        if (!decisionSubmitted) {
            System.out.println(attack + " " + defence);

            setButtonsDisabled();
            decisionSubmitted = true;


            new Thread(() -> {
                try {
                    output.writeObject(new StartRoundReq(attack,defence));
                    StartRoundResp startRoundResp = (StartRoundResp) input.readObject();

                    Platform.runLater(() -> {
                        handleServerResponse(startRoundResp);
                    });

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();


        } else {
            // Możesz obsłużyć sytuację, gdy decyzja została już wcześniej wysłana
            System.out.println("Decyzja już wysłana.");
            arenaConsole.appendText("Decyzja już wysłana.");

        }
    }
    public void handleServerResponse(StartRoundResp startRoundResp){
        char firstTeam = startRoundResp.getFirstTeam();

        if( firstTeam == 'A'){
            updateTeam2(startRoundResp);
            if(startRoundResp.isNextRoundPossible()){
                updateTeam1(startRoundResp);
            }else{
                String player1 = userName1text;
                String player2 = userName2text;
                if (startRoundResp.isWasAttackFatalForTeamA() || currentHP1 <= 0){
                    String text = userName1text + " " + smasher1.getName() + " zostaje pokonany" + "\n";
                    arenaConsole.appendText(text);

                    if(userSmasher == smasher1){
                        try {
                            input.readObject();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        updateHP(progressBar1,hp1,0,smasher1.getHealthPoints());
                        showGameOverDialog("Przegrana gracza " + player1 ,"Wygrywa gracz:" + player2 + "\n"  + smasher1.getName() + " zostaje pokonany" );
                    }
                }else{
                    if(startRoundResp.isWasAttackFatalForTeamB() || currentHP2 <= 0){
                        String text = userName1text + " " + smasher1.getName() + " zostaje pokonany" + "\n";
                        arenaConsole.appendText(text);
                        updateHP(progressBar2,hp2,0,smasher2.getHealthPoints());

                        if(userSmasher == smasher1){
                            BattleWonMessage battleWonMessage;
                            try {
                                battleWonMessage = (BattleWonMessage) input.readObject();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            updateHP(progressBar2,hp2,0,smasher2.getHealthPoints());
                            showGameOverDialog(" TEAM A gracz pierwszy Wygrywa gracza " + player1 ,"Przegrywa gracz:" + player2 + "\n Wygrałeś: " + battleWonMessage.getWonSmasher().getName()  );
                        }
                    }
                }
            }
        }else{
            updateTeam1(startRoundResp);
            if(startRoundResp.isNextRoundPossible()){
                updateTeam2(startRoundResp);
            }else{
                String player1 = userName1text;
                String player2 = userName2text;

                if (startRoundResp.isWasAttackFatalForTeamB() || currentHP2 <= 0){
                    String text = userName2text + " " + smasher2.getName() + " zostaje pokonany " + "\n" ;
                    arenaConsole.appendText(text);
                    updateHP(progressBar2,hp2,0,smasher1.getHealthPoints());

                    if(userSmasher == smasher2){
                        try {
                            input.readObject();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        updateHP(progressBar2,hp2,0,smasher1.getHealthPoints());
                        showGameOverDialog("Przegrana gracza " + player2 ,"Wygrywa gracz: " + player1 + "\n"  + smasher2.getName() + " zostaje pokonany" );
                    }

                }else{
                    if(startRoundResp.isWasAttackFatalForTeamA() || currentHP1 <= 0){
                        String text = userName1text + " " + smasher1.getName() + " zostaje pokonany" + "\n";
                        arenaConsole.appendText(text);
                        updateHP(progressBar1,hp1,0,smasher1.getHealthPoints());
                        
                        if(userSmasher == smasher2){
                            BattleWonMessage battleWonMessage;
                            try {
                                battleWonMessage = (BattleWonMessage) input.readObject();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
//                            updateHP(progressBar1,hp1,0,smasher1.getHealthPoints());
                            showGameOverDialog("Wygrywa gracza " + player2 ,"Przegrywa gracz:" + player1 + "\n Wygrałeś: " + battleWonMessage.getWonSmasher().getName()  );
                        }
                    }
                }
            }
        }

        resetButtonStyles();
        decisionSubmitted =false;
    }
    public void updateTeam1(StartRoundResp startRoundResp){
        double damageTaken = startRoundResp.getDamageTeamsA();
        currentHP1 = currentHP1 - damageTaken;
        String enemyAtack = userName2text + " " + smasher2.getName() + " Zadaje " + damageTaken + " Obrażeń";

        arenaConsole.appendText(enemyAtack + "\n");

        updateHP(progressBar1,hp1,currentHP1,smasher1.getHealthPoints());

        String player1 = userName1text;
        String player2 = userName2text;

        if (startRoundResp.isWasAttackFatalForTeamA() || currentHP1 <= 0){
            String text = userName1text + " " + smasher1.getName() + " zostaje pokonany" + "\n";
            arenaConsole.appendText(text);

            if(userSmasher == smasher1){
                showGameOverDialog("Przegrana gracza " + player1 ,"Wygrywa gracz:" + player2 + "\n"  + userSmasher.getName() + " zostaje pokonany" );
            }
        }else{
            if(startRoundResp.isWasAttackFatalForTeamB() || currentHP2 <= 0){
                String text = userName1text + " " + smasher1.getName() + " zostaje pokonany" + "\n";
                arenaConsole.appendText(text);

                if(userSmasher == smasher1){
                    showGameOverDialog("Wygyrwa gracza " + player1 ,"Przegrywa gracz:" + player2 + "\n"  + userSmasher.getName() + " zostaje pokonany" + "\n Wygrałeś: ");
                }
            }
        }
    }

    public void updateTeam2(StartRoundResp startRoundResp){
        double damageTaken = startRoundResp.getDamageTeamsB();
        currentHP2 = currentHP2 - damageTaken;
        String enemyAtack = userName1text + " " + smasher1.getName() + "Zadaje " + damageTaken + " Obrażeń";

        arenaConsole.appendText(enemyAtack + "\n" );

        updateHP(progressBar2,hp2,currentHP2,smasher2.getHealthPoints());

        String player1 = userName1text;
        String player2 = userName2text;

        if (startRoundResp.isWasAttackFatalForTeamB() || currentHP2 <= 0){
            String text = userName2text + " " + smasher2.getName() + " zostaje pokonany " + "\n" ;
            arenaConsole.appendText(text);

            if(userSmasher == smasher2){
                showGameOverDialog("Przegrana gracza " + player2 ,"Wygrywa gracz: " + player1 + "\n"  + userSmasher.getName() + " zostaje pokonany" );
            }

        }else{
            if(startRoundResp.isWasAttackFatalForTeamA() || currentHP1 <= 0){
                String text = userName1text + " " + smasher1.getName() + " zostaje pokonany" + "\n";
                arenaConsole.appendText(text);

                if(userSmasher == smasher2){
                    showGameOverDialog("Wygyrwa gracza " + player2 ,"Przegrywa gracz:" + player1 + "\n"  + userSmasher.getName() + " zostaje pokonany" + "\n Wygrałeś: " );
                }
            }
        }
    }
    private void showGameOverDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Tutaj możesz dodać kod obsługujący zamknięcie gry lub inne akcje
//                System.exit(0); // Przykładowe zamknięcie aplikacji po wciśnięciu OK
                goToMenuPage();
            }
        });
    }
    
    public void goToMenuPage()
    {
//        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cllient/ultimatepksmash/menu-view.fxml"));
            loader.setController(new MenuController(socket, output, input, this.user));
            Parent destination = null;
            try {
                destination = loader.load();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(destination);
            Stage stage = (Stage) attackButton1.getScene().getWindow();
            stage.setScene(scene);
//        });
    }
    
    private void setButtonsDisabled(){
        setButtonDisabledStyle(attackButton1);
        setButtonDisabledStyle(attackButton2);
        setButtonDisabledStyle(attackButton3);
        setButtonDisabledStyle(defenceButton1);
        setButtonDisabledStyle(defenceButton2);
    }
    private void resetButtonStyles() {
        setButtonDefaultStyle(attackButton1);
        setButtonDefaultStyle(attackButton2);
        setButtonDefaultStyle(attackButton3);
        setButtonDefaultStyle(defenceButton1);
        setButtonDefaultStyle(defenceButton2);
    }
    private void setButtonDefaultStyle(Button button) {
        button.setDisable(false);
        button.setStyle("");
    }

    private void setButtonSuccessStyle(Button button) {
        button.setStyle("-fx-background-color: #00ff00; -fx-text-fill: #000000; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 2px");
    }
    private void setButtonDisabledStyle(Button button) {
        button.setDisable(true);
        button.setStyle("-fx-opacity: 0.5;"); // Możesz dostosować styl do swoich preferencji
    }


    private void loadSmasherImage(ImageView imageView, String imagePath) {
        Image image = new Image(imagePath);
        imageView.setImage(image);
    }
    private String getColorFromHealth(double healthPercentage) {
        // Tutaj możesz dostosować kolor w zależności od healthPercentage
        if (healthPercentage > 0.7) {
            return "green";
        } else if (healthPercentage > 0.3) {
            return "yellow";
        } else {
            return "red";
        }
    }

    private void flipImageHorizontally(ImageView imageView) {
        double imageWidth = imageView.getBoundsInLocal().getWidth();

        Scale flipScale = new Scale(-1, 1, imageWidth / 2, 0);
        imageView.getTransforms().add(flipScale);
    }
}
