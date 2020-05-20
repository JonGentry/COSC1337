package com.wargames;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameBoard {
    BorderPane root = new BorderPane();
    Stage stage;

    // Indicate which player has a turn, initially it is the X player
    private char player = 'X';
    private char comp = 'O';

    Text taunt = new Text();

    private int playerScore = 0;
    private int compScore = 0;
    Text score = new Text("Score is: " + playerScore + " - " + compScore);

    Button clearBoard = new Button("Clear Board");

    // Create and initialize cell
    private final Cell[][] cell = new Cell[3][3];

    String play = "Your turn to play";
    // Create and initialize a status label
    Text PlayStatus = new Text(play);


    public GameBoard(Stage stage) {

        // Pane to hold cell
        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                pane.add(cell[i][j] = new Cell(), j, i);
        }

        VBox playerPane = new VBox();
        playerPane.getChildren().add(Player.getAvatarSelected());
        Player.getAvatarSelected().setFitHeight(100);
        Player.getAvatarSelected().setFitWidth(100);
        playerPane.getChildren().add(clearBoard);
        clearBoard.setOnAction(e-> {
            if (!play.equalsIgnoreCase(PlayStatus.getText())) {
                PlayStatus.setText(play);
                taunt.setText("");
                boardClear();
                player = 'X';
                comp = 'O';
            }
        });
        playerPane.setAlignment(Pos.CENTER);
        playerPane.setSpacing(10);
        playerPane.setPadding(new Insets(5,5,5,5));

        VBox compPane = new VBox();
        compPane.getChildren().add(Computer.getCompAvatar());
        compPane.getChildren().add(taunt);
        compPane.setAlignment(Pos.CENTER);
        compPane.setSpacing(10);
        compPane.setPadding(new Insets(5,5,5,5));

        HBox scorePane = new HBox();
        score.prefHeight(20);
        scorePane.getChildren().add(score);
        scorePane.setAlignment(Pos.CENTER);
        scorePane.setSpacing(10);
        scorePane.setPadding(new Insets(5,5,5,5));


        root.setCenter(pane);
        root.setBottom(PlayStatus);
        root.setRight(compPane);
        root.setLeft(playerPane);
        root.setTop(scorePane);

        // Create a gameScene and place it in the stage
        Scene gameScene = new Scene(root, 675, 375);
        stage.setTitle("War Games"); // Set the stage title
        stage.setScene(gameScene); // Place the gameScene in the stage
        stage.show(); // Display the stage
    }

    /**
     * Determine if the cell are all occupied
     */
    public boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (cell[i][j].getToken() == ' ')
                    return false;

        return true;
    }

    /**
     * Determine if the player with the specified token wins
     */
    public boolean isWon(char token) {
        for (int i = 0; i < 3; i++)
            if (cell[i][0].getToken() == token
                    && cell[i][1].getToken() == token
                    && cell[i][2].getToken() == token) {
                return true;
            }

        for (int j = 0; j < 3; j++)
            if (cell[0][j].getToken() == token
                    && cell[1][j].getToken() == token
                    && cell[2][j].getToken() == token) {
                return true;
            }

        if (cell[0][0].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][2].getToken() == token) {
            return true;
        } else if (cell[0][2].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][0].getToken() == token) {
            return true;
        }

        return false;
    }
    private void boardClear() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                cell[i][j].getChildren().clear();
                cell[i][j].setToken(' ');
            }
    }



    // An inner class for a cell
    public class Cell extends Pane {
        // Token used for this cell
        private char token = ' ';

        public Cell() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> playerTurn());
        }

        /**
         * Return token
         */
        public char getToken() {
            return token;
        }

        /**
         * Set a new token
         */
        public void setToken(char c) {
            token = c;


            if (token == 'X') {
                ImageView playerToken = new ImageView(new Image(Player.getPlayerToken()));

                //Fit playerToken height and width properties to the cells
                playerToken.fitHeightProperty().bind(this.heightProperty());
                playerToken.fitWidthProperty().bind(this.widthProperty());


                // Add the lines to the pane
                this.getChildren().addAll(playerToken);
            } else if (token == 'O') {
                ImageView computerToken = new ImageView(new Image(Computer.getComputerToken()));

                //Fit computerToken height and width properties to the cells
                computerToken.fitHeightProperty().bind(this.heightProperty());
                computerToken.fitWidthProperty().bind(this.widthProperty());

                //Add the avatar to the pane
                getChildren().addAll(computerToken); // Add the ellipse to the pane
            }
        }


        private void compTurn() {
            int min = 0;
            int max = 2;
            int range = max - min + 1;
            int see = 0;
            while (see == 0) {
                int i = (int) (Math.random() * range) + min;
                int j = (int) (Math.random() * range) + min;
                if (cell[i][j].getToken() == ' ') {
                    cell[i][j].setToken(comp);
                    see = 1;
                }
                if (isWon(comp)) {
                    PlayStatus.setText("Computer won! The game is over");
                    taunt.setText(Computer.taunt());
                    compScore++;
                    comp = ' '; // Game is over
                    score.setText("Score is: " + playerScore + " - " + compScore);
                } else if (isFull()) {
                    PlayStatus.setText("Draw! The game is over");
                    comp = ' '; // Game is over
                }
            }
        }

        /* Handle a mouse click event */
        private void playerTurn() {
            // If cell is empty and game is not over
            if (token == ' ' && player != ' ') {
                setToken(player); // Set token in the cell

                // Check game status
                if (isWon(player)) {
                    PlayStatus.setText("You won! The game is over");
                    playerScore++;
                    player = ' '; // Game is over
                    score.setText("Score is: " + playerScore + " - " + compScore);
                } else if (isFull()) {
                    PlayStatus.setText("Draw! The game is over");
                    player = ' '; // Game is over
                } else {
                    compTurn();
                }
            }
        }


    }
}
