import javafx.application.Application;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;

public class MemoryGame extends Application {

    private static final int NUM_CARDS = 16;

    private StackPane card1;
    private StackPane card2;
    private Image backImage;
    private int matchesFound = 0;
    private boolean gameStarted = false;
    private Timeline timer;
    private Label timerLabel;
    private int secondsElapsed = 0;
    private List<StackPane> cardPanes;

   
     
        @Override
    public void start(Stage gameStage) {
        List<Image> frontImages = loadFrontImages();
        backImage = new Image(getClass().getResourceAsStream("image/back.png"));
        cardPanes = createCardPanes(frontImages, backImage);
        GridPane gridPane = createGridPane(cardPanes);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        
        Button restartButton = new Button("Restart");
        restartButton.setOnAction(event -> restartGame());
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            Firstinterface firstInterface = new Firstinterface();
              firstInterface.start(new Stage());
            gameStage.close();
        });
        
      setButtonStyle(restartButton);
        setButtonStyle(backButton);

        timerLabel = new Label("Time: 0s");
        timerLabel.setStyle("-fx-font-family: Arial Black; -fx-font-size: 40px; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox buttonBox = new HBox(restartButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        VBox root = new VBox(gridPane, timerLabel, buttonBox);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #22343e, #4d758a, #1ffa9f)");

        Scene scene = new Scene(root);
        gameStage.setScene(scene);
        gameStage.setTitle("Rebout Game");
        gameStage.setMaximized(true);
        gameStage.show();
    }

    private void setButtonStyle(Button button) {
        button.setStyle("-fx-background-color: #22343e; -fx-border-radius: 8px; -fx-cursor: hand; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 15px; -fx-pref-width: 80px; -fx-pref-height: 30px;");
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #4d758a; -fx-border-radius: 8px; -fx-cursor: hand; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 15px; -fx-pref-width: 80px; -fx-pref-height:30px;")); 
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #22343e; -fx-border-radius: 8px; -fx-cursor: hand; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 15px; -fx-pref-width: 80px; -fx-pref-height: 30px;"));       
    }
  
    private List<Image> loadFrontImages() {
        List<Image> frontImages = new ArrayList<>();
        frontImages.add(new Image(getClass().getResourceAsStream("image/html.png")));
        frontImages.add(new Image(getClass().getResourceAsStream("image/csharp.png")));
        frontImages.add(new Image(getClass().getResourceAsStream("image/java.png")));
        frontImages.add(new Image(getClass().getResourceAsStream("image/kotlin.png")));
        frontImages.add(new Image(getClass().getResourceAsStream("image/phyton.png")));
        frontImages.add(new Image(getClass().getResourceAsStream("image/go_2.png")));
        frontImages.add(new Image(getClass().getResourceAsStream("image/jscript.png")));
        frontImages.add(new Image(getClass().getResourceAsStream("image/css.png")));

        return frontImages;
    }

    private List<StackPane> createCardPanes(List<Image> frontImages, Image backImage) {
        List<StackPane> cardPanes = new ArrayList<>();
        
        for (int i = 0; i < NUM_CARDS / 2; i++) {
            StackPane cardPane1 = createCardPane(frontImages.get(i), backImage);
            StackPane cardPane2 = createCardPane(frontImages.get(i), backImage);
            cardPanes.add(cardPane1);
            cardPanes.add(cardPane2);
}
    // Shuffle the card panes
    Collections.shuffle(cardPanes);

    return cardPanes;
}

private StackPane createCardPane(Image frontImage, Image backImage) {
    StackPane cardPane = new StackPane();
    cardPane.getStyleClass().add("card");

    Rectangle cardShape = new Rectangle(100, 100);
    cardShape.setArcWidth(20);
    cardShape.setArcHeight(20);
    cardShape.setFill(Color.WHITE);

    ImageView imageView = new ImageView(backImage);
    imageView.setFitWidth(60);
    imageView.setFitHeight(100);
    imageView.setPreserveRatio(true);
    imageView.setSmooth(true);
    imageView.setCache(true);

    cardPane.getChildren().addAll(cardShape, imageView);

    cardPane.setOnMouseClicked(event -> handleCardClick(cardPane, imageView, frontImage, backImage));
    return cardPane;
}

private void handleCardClick(StackPane cardPane, ImageView imageView, Image frontImage, Image backImage) {
    if (!gameStarted) {
        startTimer();
        gameStarted = true;
    }

    if (card1 == null) {
        // First card clicked
        card1 = cardPane;
        imageView.setImage(frontImage);
    } else if (card2 == null) {
        // Second card clicked
        card2 = cardPane;
        imageView.setImage(frontImage);

        if (card1.getChildren().get(1) instanceof ImageView && card2.getChildren().get(1) instanceof ImageView) {
            ImageView imageView1 = (ImageView) card1.getChildren().get(1);
            ImageView imageView2 = (ImageView) card2.getChildren().get(1);

            if (imageView1.getImage().equals(imageView2.getImage())) {
                // Match found
                card1.setDisable(true);
                card2.setDisable(true);
                card1 = null;
                card2 = null;
                matchesFound++;

                if (matchesFound == NUM_CARDS / 2) {
                    // All matches found
                    stopTimer();
                    System.out.println("Congratulations! You've won the game.");
                    showCongratulationsAlert();
                }
            } else {
                // No match found
                card1.setDisable(false);
                card2.setDisable(false);

                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(event -> {
                    imageView1.setImage(backImage);
                    imageView2.setImage(backImage);
                    card1 = null;
                    card2 = null;
                });
                pause.play();
            }
        }
    }
}

private void startTimer() {
    timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        secondsElapsed++;
        timerLabel.setText("Time: " + secondsElapsed + "s");
    }));
    timer.setCycleCount(Timeline.INDEFINITE);
    timer.play();
}

private void stopTimer() {
    timer.stop();
}

private void showCongratulationsAlert() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Congratulations!");
    alert.setHeaderText("You matched all the pairs!");
    alert.setContentText("Time taken: " + secondsElapsed + " seconds\n\nDo you want to play again?");

    ButtonType restartButtonType = new ButtonType("Restart");
    ButtonType exitButtonType = new ButtonType("Exit");

    alert.getButtonTypes().setAll(restartButtonType, exitButtonType);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == restartButtonType) {
        restartGame();
    } else {
        // Exit the game
        System.exit(0);
    }
}

private void restartGame() {
    matchesFound =0;
card1 = null;
card2 = null;
gameStarted = false;
secondsElapsed = 0;
timerLabel.setText("Time: 0s");
    // Reset all card panes
    for (StackPane cardPane : cardPanes) {
        ImageView imageView = (ImageView) cardPane.getChildren().get(1);
        imageView.setImage(backImage);
        cardPane.setDisable(false);
    }

    // Shuffle the card panes
    Collections.shuffle(cardPanes);
}

private GridPane createGridPane(List<StackPane> cardPanes) {
    GridPane gridPane = new GridPane();

    int columnCount = 0;
    int rowCount = 0;

    for (StackPane cardPane : cardPanes) {
        gridPane.add(cardPane, columnCount, rowCount);

        columnCount++;
        if (columnCount == 4) {
            columnCount = 0;
            rowCount++;
        }
    }

    return gridPane;
}

public static void main(String[] args) {
    launch(args);
}
}