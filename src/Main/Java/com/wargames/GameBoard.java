package com.wargames;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Math.abs;

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
    Text playStatus = new Text(play);

    // FadeTransitions for the score animations
    FadeTransition scoreAnimation;

    Text animationText;



    public GameBoard(Stage stage) {

        playStatus.setFont(Font.font("Verdana", FontWeight.BOLD, 70));



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
        clearBoard.setOnAction(e -> {
            if (!play.equalsIgnoreCase(playStatus.getText())) {
                playStatus.setText(play);
                taunt.setText("");
                boardClear();
                root.setBottom(playStatus);
                player = 'X';
                comp = 'O';
            }
        });
        playerPane.setAlignment(Pos.CENTER);
        playerPane.setSpacing(10);
        playerPane.setPadding(new Insets(5, 5, 5, 5));

        VBox compPane = new VBox();
        compPane.getChildren().add(Computer.getCompAvatar());
        compPane.getChildren().add(taunt);
        compPane.setAlignment(Pos.CENTER);
        compPane.setSpacing(10);
        compPane.setPadding(new Insets(5, 5, 5, 5));

        HBox scorePane = new HBox();
        score.prefHeight(20);
        scorePane.getChildren().add(score);
        scorePane.setAlignment(Pos.CENTER);
        scorePane.setSpacing(10);
        scorePane.setPadding(new Insets(5, 5, 5, 5));


        root.setCenter(pane);
        root.setBottom(playStatus);
        root.setRight(compPane);
        root.setLeft(playerPane);
        root.setTop(scorePane);
        root.setStyle(WarGamesApp.backgroundColor);

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
                if (cell[i][j].getToken() == '_')
                    return false;

        return true;
    }

    public char[][] cellToCharArrayBoard() {
        char[][] temp = {{'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                temp[i][j] = cell[i][j].getToken();
        }
        return temp;
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
                cell[i][j].setToken('_');
            }
    }


    // An inner class for a cell
    public class Cell extends Pane {
        // Token used for this cell
        private char token = '_';

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
            Move bestMove = findBestMove(cellToCharArrayBoard());
            cell[bestMove.row][bestMove.col].setToken(comp);
            if (isWon(comp)) {
                playStatus.setText("Computer won!");
                taunt.setText(Computer.taunt());
                compScore++;
                comp = '_'; // Game is over
                score.setText("Score is: " + playerScore + " - " + compScore);

                playScoreAnimation();
            } else if (isFull()) {
                playStatus.setText("Draw!");
                comp = '_'; // Game is over

                playScoreAnimation();
            }
        }



        /* Handle a mouse click event */
        private void playerTurn() {
            // If cell is empty and game is not over
            if (token == '_' && player != '_' && !isWon(comp)) {
                setToken(player); // Set token in the cell

                // Check game status
                if (isWon(player)) {
                    playStatus.setText("You won!");
                    playerScore++;
                    player = '_'; // Game is over
                    score.setText("Score is: " + playerScore + " - " + compScore);

                    playScoreAnimation();
                } else if (isFull()) {
                    playStatus.setText("Draw!");
                    player = '_'; // Game is over

                    playScoreAnimation();
                } else {
                    compTurn();
                }
            }
        }

        private void playScoreAnimation() {
            animationText = new Text(100, 100, playStatus.getText());

            animationText.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
            animationText.setFill(Color.DARKRED);

            root.setBottom(animationText);

            animationText.setTextAlignment(TextAlignment.CENTER);

            scoreAnimation = new FadeTransition(Duration.millis(1500), animationText);

            scoreAnimation.setFromValue(0.0);
            scoreAnimation.setToValue(1.0);

            scoreAnimation.setAutoReverse(true);
            scoreAnimation.setCycleCount(2);

            scoreAnimation.play();
        }
    }

    static class Move {
        int row, col;
    }

    Boolean isBoardNotFull(char[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }

    int evaluate(char[][] b) {

        // Check Rows for victory condition
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] &&
                    b[row][1] == b[row][2]) {
                if (b[row][0] == player) {
                    return +10;
                } else if (b[row][0] == comp) {
                    return -10;
                }
            }
        }

        // Check Columns for victory condition
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] &&
                    b[1][col] == b[2][col]) {
                if (b[0][col] == player) {
                    return +10;
                } else if (b[0][col] == comp)
                    return -10;
            }
        }

        // Check Diagonals for victory condition
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == player) {
                return +10;
            } else if (b[0][0] == comp)
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == player) {
                return +10;
            } else if (b[0][2] == comp)
                return -10;
        }

        // Return 0 if there's no win condition on this move
        return 0;
    }

    // Considers all possible moves on the board, and assign values to each move to determine which is the most optimal
    int minimax(char[][] board,
                int depth, Boolean isMaximizer) {
        int score = evaluate(board);
        // If maximizer has a win condition, return it but subtract depth so that the score prioritizes close moves
        if (score == 10)
            return score - depth;

        // If minimizer has a win condition, return it but add depth so that the score prioritizes close moves
        if (score == -10)
            return score + depth;

        // If board is full and the end result is a Tie
        if (!isBoardNotFull(board))
            return 0;

        // Maximizer's turn
        int best;
        if (isMaximizer) {
            best = -1000;

            // Iterate through all cells on the board
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == '_') {

                        board[i][j] = player;
                        // Iterate moves recursively to find the best end results for the Maximizer
                        best = Math.max(best, minimax(board,
                                depth + 1, false));

                        // Undo the move to return the board to the current state
                        board[i][j] = '_';
                    }
                }
            }
        }

        // Minimizer turn
        else {
            best = 1000;

            // Iterate through all cells on the board
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == '_') {
                        board[i][j] = comp;
                        // Iterate moves recursively to find the best end results for the Minimizer
                        best = Math.min(best, minimax(board,
                                depth + 1, true));

                        // Undo the move to return the board to the current state
                        board[i][j] = '_';
                    }
                }
            }
        }
        return best;
    }

    // Finds the best possible move for the computer and player, and uses that knowledge for offense and defense
    Move findBestMove(char[][] board) {
        int bestVal = 1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Iterate through all cells and calculate a minimax score for each empty cell
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    // Look at the best moves for the computer to make offensively
                    board[i][j] = comp;
                    int bestMoveVal = minimax(board, 0, false);

                    // Look at the best moves for the player, so the computer can use this information for defense
                    board[i][j] = player;
                    int worstMoveVal = minimax(board, 0, true);

                    // Undo the move to return the board to the current state
                    board[i][j] = '_';

                    int offenseOrDefense;

                    if (abs(bestMoveVal) < abs(worstMoveVal)) {
                        // Defensive move
                        offenseOrDefense = -worstMoveVal;
                    } else {
                        // Offensive move
                        offenseOrDefense = bestMoveVal;
                    }

                    // Set the best Move to be the optimal move we picked above
                    if ( offenseOrDefense < bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = offenseOrDefense;
                    }
                }
            }
        }

        System.out.println("Value of the best move : " + bestVal);

        return bestMove;
    }
}