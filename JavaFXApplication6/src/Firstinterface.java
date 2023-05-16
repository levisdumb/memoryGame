import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Firstinterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button playButton = new Button("PLAY!");
        playButton.setOnAction(event -> startMemoryGame(primaryStage));
        playButton.setStyle("-fx-background-color:#22343e; -fx-border-radius: 13px; -fx-cursor: hand;-fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 20px; -fx-pref-width: 100px; -fx-pref-height: 30px; -fx-text-fill: white; ");
        playButton.setOnMouseEntered(event -> playButton.setStyle("-fx-background-color: #4d758a; -fx-border-radius: 13px; -fx-cursor: hand; -fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 20px; -fx-pref-width: 100px; -fx-pref-height: 30px; -fx-text-fill: white;"));
        playButton.setOnMouseExited(event -> playButton.setStyle("-fx-background-color: #22343e; -fx-border-radius: 13px; -fx-cursor: hand; -fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 20px; -fx-pref-width: 100px; -fx-pref-height: 30px; -fx-text-fill: white;"));
       
        Image image = new Image(getClass().getResourceAsStream("images/reboutLogo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250); // Set the desired width
        imageView.setFitHeight(190); // Set the desired height
        StackPane stackPane = new StackPane(imageView);
        stackPane.setPadding(new Insets(30)); // Adjust the padding as needed

        Label label = new Label("REBOUT GAME");
        label.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 30px; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox root = new VBox(label, stackPane, playButton);
        root.setSpacing(10);
        root.setPadding(new Insets(80));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rebout Game");
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #22343e, #4d758a, #1ffa9f)");
        primaryStage.show();
    }
    
    

    private void startMemoryGame(Stage primaryStage) {
        MemoryGame memoryGame = new MemoryGame();
        memoryGame.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
