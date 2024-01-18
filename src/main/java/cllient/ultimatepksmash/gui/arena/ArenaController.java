package cllient.ultimatepksmash.gui.arena;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import server.ultimatepksmash.server.database.skills.Type;
import server.ultimatepksmash.server.database.skills.attack.Attack;
import server.ultimatepksmash.server.database.skills.defence.Defence;
import server.ultimatepksmash.server.database.smasher.Smasher;
import server.ultimatepksmash.server.messages.StartRoundReq;
import server.ultimatepksmash.server.messages.StartRoundResp;

import java.util.ArrayList;
import java.util.List;

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
    private Attack smasherAttack;
    private Defence smasherDefence;

    private List<Attack> attackList;
    private List<Defence> defenceList;
    @FXML
    private ImageView smasher1ImageView;
    @FXML
    private ImageView smasher2ImageView;

    @FXML
    private TextArea attack1Description;
    @FXML
    private Label attack1Dmg;
    @FXML
    private Label attack1Type;
    @FXML
    private Button submitButton;
    private Long chosenAttackId;
    private Long chosenDefenceId;
    @FXML
    private Button test;

    private boolean teamA = false;
    private boolean decisionSubmitted = false;
    public ArenaController() {
        // Konstruktor bezargumentowy
        this.smasherAttack = new Attack(1L,"Podcinka","Podcina przeciwnika", Type.fizyczny,15,1L);
        this.smasherDefence = new Defence(1L, "Twardy jak skałą","Bardzo twardy",Type.fizyczny,10,1l);
        this.attackList = new ArrayList<>();
        this.defenceList = new ArrayList<>();
        this.attackList.add(smasherAttack);
        this.defenceList.add(smasherDefence);

        this.smasher1 = new Smasher(1L,"Rigby ","Brązowy szop",10,100D,"C:\\Users\\Vecza\\IntelliJ_Projects\\UltimateSmash\\src\\main\\photos\\avat ig.png",attackList,defenceList);
        this.smasher2 = new Smasher(2L,"rIGBY","Brązowy szop",100D,10,"C:\\Users\\Vecza\\IntelliJ_Projects\\UltimateSmash\\src\\main\\photos\\avat ig.png");

    }

    @FXML
    public void initialize() {
        // Inicjalizacja ProgressBar i Label dla smasher1
        currentHP1 = smasher1.getHealthPoints();
        progressBar1.setProgress(smasher1.getHealthPoints());
        progressBar1.setStyle("-fx-accent: #00ff00");
        smasher1Name.setText(smasher1.getName());
        // statycczna utrata hp
        currentHP1 = smasher1.getHealthPoints();


        currentHP2 = smasher2.getHealthPoints();
        progressBar2.setProgress(smasher2.getHealthPoints());
        //ustawienie kolorku na zielony
        progressBar2.setStyle("-fx-accent: #00ff00");
        smasher2Name.setText(smasher2.getName());

        updateHP(progressBar1,hp1, currentHP1,smasher1.getHealthPoints());
        updateHP(progressBar2,hp2, currentHP2,smasher2.getHealthPoints());


        loadSmasherImage(smasher1ImageView, smasher1.getPhotoPath());

        flipImageHorizontally(smasher2ImageView);
        loadSmasherImage(smasher2ImageView, smasher2.getPhotoPath());


        attack1Description.appendText("Opis: " +smasher1.getAttacks().get(0).getDescription());
        attack1Description.setEditable(false);
        attack1Dmg.setText("Obrażenia: " + smasher1.getAttacks().get(0).getAttackPoints().toString());
        attack1Type.setText("Typ: " + smasher1.getAttacks().get(0).getType().toString());

        attackButton1.setText(smasher1.getAttacks().get(0).getName());
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
        chosenAttackId = smasher1.getAttacks().get(0).getId();

        String smasherName = smasher1.getName();
        String logMessage = "Smasher " + smasherName + " użył ataku : " + smasher1.getAttacks().get(0).getName() +", zadając " + smasher1.getAttacks().get(0).getAttackPoints().toString() + " obrażeń.\n";
        arenaConsole.appendText(logMessage);
        System.out.println(logMessage);
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

        chosenAttackId = smasher2.getAttacks().get(1).getId();
        String smasherName = smasher2.getName();

        String logMessage = "Smasher " + smasherName + " użył ataku : "  +", zadając " + " obrażeń.\n";
        // Tu umieść kod obsługujący naciśnięcie guzika
        arenaConsole.appendText(logMessage);

        System.out.println(logMessage);
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
        chosenAttackId = smasher1.getAttacks().get(2).getId();
        //TODO zczytać dane z attaku ale smasher jeszcze nie ma ataków

        String smasherName = smasher1.getName();

        String logMessage = "Smasher " + smasherName + " użył ataku 3 : " + ", zadając " + " obrażeń.\n";
        // Tu umieść kod obsługujący naciśnięcie guzika
        arenaConsole.appendText(logMessage);

        System.out.println(logMessage);
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

        chosenDefenceId = smasher1.getDefences().get(0).getId();

        String smasherName = smasher1.getName();

        String logMessage = "Smasher " + smasherName + " użył obrony 1 : " + ", broniąc " + " obrażeń.\n";
        // Tu umieść kod obsługujący naciśnięcie guzika
        arenaConsole.appendText(logMessage);

        System.out.println(logMessage);
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
        chosenDefenceId = smasher1.getDefences().get(1).getId();
        String smasherName = smasher1.getName();

        String logMessage = "Smasher " + smasherName + " użył obrony 1 : " + ", broniąc " + " obrażeń.\n";
        // Tu umieść kod obsługujący naciśnięcie guzika
        arenaConsole.appendText(logMessage);

        System.out.println(logMessage);
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

    public StartRoundReq submitDecision(Long attack, Long defence){
        if (!decisionSubmitted) {
            StartRoundReq startRoundReq = new StartRoundReq(attack,defence);
            System.out.println(attack + " " + defence);

            setButtonsDisabled();
            decisionSubmitted = true;

            return startRoundReq;
        } else {
            // Możesz obsłużyć sytuację, gdy decyzja została już wcześniej wysłana
            System.out.println("Decyzja już wysłana.");
            arenaConsole.appendText("Decyzja już wysłana.");
            return null;
        }
    }
    public void handleServerResponse(){
        StartRoundResp startRoundResp = new StartRoundResp(10D,10D,false,false,1L,1L,1L,1L,true,'A');
        char firstTeam = startRoundResp.getFirstTeam();

        if( firstTeam == 'A'){
            updateTeam2(startRoundResp);
            if(startRoundResp.isNextRoundPossible()){
                updateTeam1(startRoundResp);
            }
        }else{
            updateTeam1(startRoundResp);
            if(startRoundResp.isNextRoundPossible()){
                updateTeam2(startRoundResp);
            }
        }

        resetButtonStyles();
        decisionSubmitted =false;
    }
    public void updateTeam1(StartRoundResp startRoundResp){
        double damageTaken = startRoundResp.getDamageTeamsA();
        currentHP1 = currentHP1 - damageTaken;
        String enemyAtack ="Nazwa Usera Smasher " + smasher2.getName() + "Zadaje " + damageTaken + " Obrażeń";

        arenaConsole.appendText(enemyAtack + "\n");

        updateHP(progressBar1,hp1,currentHP1,smasher1.getHealthPoints());

        if (startRoundResp.isWasAttackFatalForTeamA() || currentHP1 <= 0){
            String text = " Nazwa Usera: Smasher " + smasher1.getName() + " zostaje pokonany" + "\n";
            arenaConsole.appendText(text);
            showGameOverDialog("Przegrana ","Nazwa Usera: Smasher " + smasher1.getName() + " zostaje pokonany" + "\n" );
        };
    }

    public void updateTeam2(StartRoundResp startRoundResp){
        double damageTaken = startRoundResp.getDamageTeamsB();
        currentHP2 = currentHP2 - damageTaken;
        String enemyAtack ="Nazwa Usera Smasher " + smasher1.getName() + "Zadaje " + damageTaken + " Obrażeń";

        arenaConsole.appendText(enemyAtack + "\n" );

        updateHP(progressBar2,hp2,currentHP2,smasher2.getHealthPoints());

        if (startRoundResp.isWasAttackFatalForTeamA() || currentHP2 <= 0){
            String text = " Nazwa Usera: Smasher " + smasher2.getName() + " zostaje pokonany " + "\n" ;
            arenaConsole.appendText(text);
            showGameOverDialog("Przegrana ","Nazwa Usera: Smasher " + smasher2.getName() + " zostaje pokonany" + "\n");
        };
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
                System.exit(0); // Przykładowe zamknięcie aplikacji po wciśnięciu OK
            }
        });
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
