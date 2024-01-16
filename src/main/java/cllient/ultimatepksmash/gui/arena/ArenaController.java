package cllient.ultimatepksmash.gui.arena;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import org.w3c.dom.Text;
import server.ultimatepksmash.server.database.samsher.Smasher;

import java.util.function.ToDoubleBiFunction;

public class ArenaController {
    @FXML
    private Label smasher1Name;
    @FXML
    private Label hp1;
    @FXML
    private ProgressBar progressBar1;

    @FXML
    private Button attackTest;

    @FXML
    private Button DEFENCE1;

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


    @FXML
    private ImageView smasher1ImageView;
    @FXML
    private ImageView smasher2ImageView;

    public ArenaController() {
        // Konstruktor bezargumentowy
        this.smasher1 = new Smasher(1L,"Rigby ","Brązowy szop",100D,"C:\\Users\\Vecza\\IntelliJ_Projects\\UltimateSmash\\src\\main\\photos\\avat ig.png");
        this.smasher2 = new Smasher(2L,"rIGBY","Brązowy szop",100D,"C:\\Users\\Vecza\\IntelliJ_Projects\\UltimateSmash\\src\\main\\photos\\avat ig.png");

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
        updateHP(progressBar1, currentHP1,smasher1.getHealthPoints());

        currentHP2 = smasher2.getHealthPoints();
        progressBar2.setProgress(smasher2.getHealthPoints());
        //ustawienie kolorku na zielony
        progressBar2.setStyle("-fx-accent: #00ff00");
        smasher2Name.setText(smasher2.getName());


        updateHpLabel(hp1, currentHP1, smasher1.getHealthPoints());
        updateHpLabel(hp2, currentHP2, smasher2.getHealthPoints());

        loadSmasherImage(smasher1ImageView, smasher1.getPhotoPath());

        flipImageHorizontally(smasher2ImageView);
        loadSmasherImage(smasher2ImageView, smasher2.getPhotoPath());
    }

    @FXML
    public void handleAttackButton() {

        //TODO zczytać dane z attaku ale smasher jeszcze nie ma ataków

        String smasherName = smasher1.getName();

        String logMessage = "Smasher " + smasherName + " użył ataku: " + ", zadając " + " obrażeń.\n";
        // Tu umieść kod obsługujący naciśnięcie guzika
        arenaConsole.appendText(logMessage);

        System.out.println(logMessage);
    }

    public void updateHP(ProgressBar progressBar, double currentHP, double maxHP){
        double healthPercentage = currentHP / maxHP;
        progressBar.setProgress(healthPercentage);
        progressBar.setStyle("-fx-accent: " + getColorFromHealth(healthPercentage));
    }

    private void updateHpLabel(Label hpLabel, double currentHP, double maxHP) {
        // Formatowanie tekstu i ustawienie w etykiecie
        String hpText = String.format("%.0f/%.0f", currentHP, maxHP);
        hpLabel.setText(hpText);
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
