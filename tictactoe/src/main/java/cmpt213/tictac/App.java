package cmpt213.tictac;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    private Stage stage;
    private GridPane gridPane;
    private Label messageLabel;
    private int currentPlayer;
    private int[][] gameBoard;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        Label titleLabel = new Label("Tic-Tac-Toe");
        titleLabel.setFont(Font.font(40));
        Button startButton = new Button("Start New Game");
        startButton.setOnAction(e -> startNewGame());
        VBox startVBox = new VBox(10, titleLabel, startButton);
        startVBox.setAlignment(Pos.TOP_CENTER);
        Scene startScene = new Scene(startVBox, 300, 300);

        stage.setScene(startScene);
        stage.show();
    }
        public static void main(String[] args) {
        launch();
    }


    
    public void startNewGame() {
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 0, 10, 0));

        gridPane.setBorder(new Border(new BorderStroke(
            Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(2),
            new Insets(0, 10, 0, 10)
    )));
        messageLabel = new Label("Player X's Turn");
        messageLabel.setFont(Font.font(20));
        currentPlayer = 1;
        gameBoard = new int[4][4];
    
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                final int currentRow = row;
                final int currentCol = col;
    
                Rectangle rectangle = new Rectangle(80, 80, Color.rgb(197,180,227)); // Change color or size as needed
                rectangle.setOnMouseEntered(e -> rectangle.setFill(Color.rgb(177, 160, 207))); // Darker color on hover
                rectangle.setOnMouseExited(e -> rectangle.setFill(Color.rgb(197, 180, 227)));
                // Add event handling for clicks on each cell
            
                // Set the border properties
                rectangle.setStroke(Color.MAGENTA);
                rectangle.setStrokeWidth(1);
                rectangle.setStrokeType(StrokeType.INSIDE);
                //rectangle.setStroke(Color.TRANSPARENT);


                rectangle.setOnMouseClicked(event -> handleMove(currentRow, currentCol));
    
                gridPane.add(rectangle, col, row);
            }
        }
    
        VBox gameVBox = new VBox(10, messageLabel);
        gameVBox.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER); // Center align the GridPane
        gameVBox.getChildren().add(gridPane);
    
        Scene gameScene = new Scene(gameVBox, 400, 450);
        stage.setScene(gameScene);

        Button restartButton = new Button("Start New Game");
        restartButton.setOnAction(e -> startNewGame());

    }

   

    public void handleMove(int row, int col) {
        if (gameBoard[row][col] == 0) { // Check if the cell is empty
            gameBoard[row][col] = currentPlayer; // Update game board with player's move
            
            Image playerImage = currentPlayer == 1 ? new Image("file:img/X.png") : new Image("file:img/O.png");
            ImageView clickedCell = createImageView(playerImage);
            
            gridPane.add(clickedCell, col, row); // Add the image to the grid
            
            if (checkForWin(row, col)) {
                messageLabel.setText("Player " + (currentPlayer == 1 ? "X" : "O") + " wins!");
                handleGameEnd();
                // Handle game end
            } else if (checkForDraw()) {
                messageLabel.setText("It's a draw!");
                handleGameEnd();
                // Handle game end
            } else {
                currentPlayer = currentPlayer == 1 ? 2 : 1; // Switch player
                messageLabel.setText("Player " + (currentPlayer == 1 ? "X" : "O") + "'s Turn");
            }
        }
    }
    
    public ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        return imageView;
    }
    
    
    public boolean checkForWin(int row, int col) {
        int player = gameBoard[row][col];
    
        // Check for a horizontal win
        for (int i = 0; i < 4; i++) {
            if (gameBoard[row][i] != player) {
                break;
            }
            if (i == 3) {
                return true;
            }
        }
    
        // Check for a vertical win
        for (int i = 0; i < 4; i++) {
            if (gameBoard[i][col] != player) {
                break;
            }
            if (i == 3) {
                return true;
            }
        }
    
        // Check for diagonals
        if (row == col || row + col == 3) {
            boolean diagonal1 = true, diagonal2 = true;
            for (int i = 0; i < 4; i++) {
                if (gameBoard[i][i] != player) {
                    diagonal1 = false;
                }
                if (gameBoard[i][3 - i] != player) {
                    diagonal2 = false;
                }
            }
            if (diagonal1 || diagonal2) {
                return true;
            }
        }
    
        return false;
    }
    
    public boolean checkForDraw() {
        for (int[] row : gameBoard) {
            for (int cell : row) {
                if (cell == 0) {
                    return false; // Empty cell found, game is not a draw
                }
            }
        }
        return true; // All cells are filled, game is a draw
    }


    public void handleGameEnd() {
        Button restartButton = new Button("Start New Game");
        restartButton.setOnAction(e -> startNewGame());
    
        VBox gameVBox = new VBox(10, messageLabel, gridPane, restartButton);
        gameVBox.setAlignment(Pos.CENTER);
    
        Scene gameScene = new Scene(gameVBox, 400, 500); // Increased the height for the button
        stage.setScene(gameScene);
    }
}






