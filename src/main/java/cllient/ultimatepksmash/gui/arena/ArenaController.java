package cllient.ultimatepksmash.gui.arena;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import server.ultimatepksmash.server.database.skills.Type;
import server.ultimatepksmash.server.database.skills.attack.Attack;
import server.ultimatepksmash.server.database.skills.defence.Defence;
import server.ultimatepksmash.server.database.smasher.Smasher;

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
    private Attack chosenAttack;
    private Defence chosenDefence;

    @FXML
    private Label timerLabel;  // Add a Label for the timer

    private Timeline timerTimeline;
    private int countdownSeconds = 60;

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
        currentHP1 = smasher1.getHealthPoints() - 80;


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
        submitButton.setOnAction(event -> submitDecision(chosenAttack,chosenDefence));

        initializeTimer();
        startTimer();
    }

    private void initializeTimer() {
        timerTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), this::updateTimer)
        );
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
    }
    private void startTimer() {
        timerTimeline.play();
    }

    private void stopTimer() {
        timerTimeline.stop();
    }

    private void updateTimer(ActionEvent event) {
        if (countdownSeconds > 0) {
            countdownSeconds--;
            updateTimerLabel();
        } else {
            // Timer reached zero, stop the timer and perform actions accordingly
            stopTimer();
            handleTimerEnd();  // Add your logic for timer expiration
        }
    }

    private void updateTimerLabel() {
        timerLabel.setText(String.valueOf(countdownSeconds));
    }

    // ... (existing methods)

    // Add your logic for timer expiration here
    private void handleTimerEnd() {
        // Example: Display a message or perform an action when the timer reaches zero
        arenaConsole.appendText("Time's up! Round ended.\n");
        // Add any additional actions you want to perform at the end of the timer
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
        chosenAttack = smasher1.getAttacks().get(0);
        String smasherName = smasher1.getName();

        String logMessage = "Smasher " + smasherName + " użył ataku : " + smasher1.getAttacks().get(0).getName() +", zadając " + smasher1.getAttacks().get(0).getAttackPoints().toString() + " obrażeń.\n";
        // Tu umieść kod obsługujący naciśnięcie guzika
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


        String smasherName = smasher1.getName();

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

        chosenDefence = smasher1.getDefences().get(0);

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

    public String submitDecision(Attack attack, Defence defence){
        if (!decisionSubmitted) {
            String atk = attack.getAttackPoints().toString();
            String def = defence.getDefencePoints().toString();
            System.out.println(atk + " " + def);

            stopTimer();  // Stop the timer when a decision is submitted

            decisionSubmitted = true;

            // Dezaktywuj przyciski
            setButtonDisabledStyle(attackButton1);
            setButtonDisabledStyle(attackButton2);
            setButtonDisabledStyle(attackButton3);
            setButtonDisabledStyle(defenceButton1);
            setButtonDisabledStyle(defenceButton2);

            return (atk + " " + def);
        } else {
            // Możesz obsłużyć sytuację, gdy decyzja została już wcześniej wysłana
            System.out.println("Decyzja już wysłana.");
            return null;
        }
    }
    private void setButtonDisabledStyle(Button button) {
        button.setDisable(true);
        button.setStyle("-fx-opacity: 0.5;"); // Możesz dostosować styl do swoich preferencji
    }
    public void executeRound(int hp){
        currentHP2 = currentHP2 - hp;
        updateHP(progressBar2,hp2,currentHP2,smasher2.getHealthPoints());
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
