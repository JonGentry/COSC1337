package com.wargames;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class GameBoard extends Application {
    // Indicate which player has a turn, initially it is the X player
    private char player = 'X';
    private char comp = 'O';

    // Create and initialize cell
    private final Cell[][] cell =  new Cell[3][3];

    // Create and initialize a status label
    Label PlayStatus = new Label("X's turn to play");

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {

        // Pane to hold cell
        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(cell[i][j] = new Cell(), j, i);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(PlayStatus);

        // Create a gameScene and place it in the stage
        Scene gameScene = new Scene(borderPane, 675, 375);
        primaryStage.setTitle("War Games"); // Set the stage title
        primaryStage.setScene(gameScene); // Place the gameScene in the stage
        primaryStage.show(); // Display the stage
    }

    /** Determine if the cell are all occupied */
    public boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (cell[i][j].getToken() == ' ')
                    return false;

        return true;
    }

    /** Determine if the player with the specified token wins */
    public boolean isWon(char token) {
        for (int i = 0; i < 3; i++)
            if (cell[i][0].getToken() == token
                    && cell[i][1].getToken() == token
                    && cell[i][2].getToken() == token) {
                return true;
            }

        for (int j = 0; j < 3; j++)
            if (cell[0][j].getToken() ==  token
                    && cell[1][j].getToken() == token
                    && cell[2][j].getToken() == token) {
                return true;
            }

        if (cell[0][0].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][2].getToken() == token) {
            return true;
        }else if (cell[0][2].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][0].getToken() == token) {
            return true;
        }

        return false;
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

        /** Return token */
        public char getToken() {
            return token;
        }

        /** Set a new token */
        public void setToken(char c) {
            token = c;


            if (token == 'X') {
                ImageView playerToken = new ImageView(new Image(Player.getPlayerToken()));

                // TODO fix the x & y property values
                playerToken.xProperty().bind(this.layoutXProperty().divide(2));
                playerToken.yProperty().bind(this.layoutYProperty().divide(2));
                playerToken.fitHeightProperty().bind(this.heightProperty());
                playerToken.fitWidthProperty().bind(this.widthProperty());


                // Add the lines to the pane
                this.getChildren().addAll(playerToken);
            }
            else if (token == 'O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2,
                        this.getHeight() / 2, this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(
                        this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(
                        this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(
                        this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(
                        this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.GREEN);
                ellipse.setFill(Color.WHITE);

                getChildren().add(ellipse); // Add the ellipse to the pane
            }
        }



        private void compTurn() {
            int min = 0;
            int max = 2;
            int range = max - min + 1;
            int see = 0;
            while (see == 0) {
                int i = (int)(Math.random() * range) + min;
                int j = (int)(Math.random() * range) + min;
                if (cell[i][j].getToken() == ' ') {
                    cell[i][j].setToken(comp);
                    see = 1;
                }
                if (isWon(comp)) {
                    PlayStatus.setText(comp + " won! The game is over");
                    comp = ' '; // Game is over
                }
                else if (isFull()) {
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
                    PlayStatus.setText(player + " won! The game is over");
                    player = ' '; // Game is over
                }
                else if (isFull()) {
                    PlayStatus.setText("Draw! The game is over");
                    player = ' '; // Game is over
                }
                else {
                    compTurn();
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}