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

// Declare gameboard class
public class GameBoard {

    // Create a base boarder pane
    BorderPane root = new BorderPane();

    // Indicate which player has a turn, player plays first
    private char player = 'X';
    private char comp = 'O';

    // Create a text to hold the computer taunt
    Text taunt = new Text();

    // Declare and initialize the scores, and score text
    private int playerScore = 0;
    private int compScore = 0;
    Text score = new Text("Score is: " + playerScore + " - " + compScore);

    // Create a button to clear the board
    Button clearBoard = new Button("Clear Board");

    // Create and initialize cell
    private final Cell[][] cell = new Cell[3][3];

    // Create a string to tell the player to play
    String play = "Your turn to play";

    // Create and initialize a status label with the play text
    Text playStatus = new Text(play);

    // FadeTransitions for the score animations
    FadeTransition scoreAnimation;

    // Creat a text to hold the win lose draw animation text
    Text animationText;

    // Method to start the gameboard stage
    public GameBoard(Stage stage) {

        // Set the font of the playstatus
        playStatus.setFont(Font.font("Verdana", FontWeight.BOLD, 70));

        /*------------------------------------Tic-Tac-Toe grid pane--------------------------------------------------*/

        // Pane to hold cell
        GridPane pane = new GridPane();

        // Loop to add cells in the grid pane
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                pane.add(cell[i][j] = new Cell(), j, i);
        }

        /*-----------------------------------------Player info pane--------------------------------------------------*/

        // Create a vbox to hold the player info... Avatar and clear board
        VBox playerPane = new VBox();

        // Get player avatar and set dimensions
        playerPane.getChildren().add(Player.getAvatarSelected());
        Player.getAvatarSelected().setFitHeight(100);
        Player.getAvatarSelected().setFitWidth(100);

        // Add the clear board button to the player plane
        playerPane.getChildren().add(clearBoard);

        // Set the clear board button to remove the imageviews/ tokens from the cells
        clearBoard.setOnAction(e -> {
            // Check if play string does not equal the playstatus text
            if (!play.equalsIgnoreCase(playStatus.getText())) {

                // If true reset playstatus and taunt
                playStatus.setText(play);
                taunt.setText("");

                // Call method to clear board
                boardClear();

                // Reset playstatus to the bottom border and remove score animation
                root.setBottom(playStatus);

                // Reset player tokens
                player = 'X';
                comp = 'O';
            }
        });

        // Set player pane styling
        playerPane.setAlignment(Pos.CENTER);
        playerPane.setSpacing(10);
        playerPane.setPadding(new Insets(5, 5, 5, 5));

        /*-------------------------------------Computer info pane-----------------------------------------------------*/

        // Create a Vbox for the computer info... Avatar and taunt
        VBox compPane = new VBox();

        // Add the avatar imageview and taunt text
        compPane.getChildren().add(Computer.getCompAvatar());
        compPane.getChildren().add(taunt);

        // Set comp pane styling
        compPane.setAlignment(Pos.CENTER);
        compPane.setSpacing(10);
        compPane.setPadding(new Insets(5, 5, 5, 5));

        /*-----------------------------------------Score info pane---------------------------------------------------*/

        // Create a Hbox for the score info
        HBox scorePane = new HBox();

        // Set the score text height and add to the hbox
        score.prefHeight(20);
        scorePane.getChildren().add(score);

        // Set the score pane styling
        scorePane.setAlignment(Pos.CENTER);
        scorePane.setSpacing(10);
        scorePane.setPadding(new Insets(5, 5, 5, 5));

        /*-----------------------------------------Root pane config--------------------------------------------------*/

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


    // Create method to determine if the cells are all occupied
    public boolean isFull() {

        // Loop through the grid
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (cell[i][j].getToken() == '_') // Check if there are any blanks
                    return false;
        return true; // else return true
    }

    // Method to tie the grid to a temporary char array
    public char[][] cellToCharArrayBoard() {
        char[][] temp = {{'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}};

        // Loop through the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                temp[i][j] = cell[i][j].getToken(); // Set array index to the grid token
        }
        return temp;
    }

    // Method to check if a specified token wins
    public boolean isWon(char token) {

        // Loop three times
        for (int i = 0; i < 3; i++)

            // If statement to check horizontal win
            if (cell[i][0].getToken() == token
                    && cell[i][1].getToken() == token
                    && cell[i][2].getToken() == token) {
                return true;
            }

        // Loop three times
        for (int j = 0; j < 3; j++)

            // If statement to check for vertical win
            if (cell[0][j].getToken() == token
                    && cell[1][j].getToken() == token
                    && cell[2][j].getToken() == token) {
                return true;
            }

        // If statement to check for diagonal wins
        if (cell[0][0].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][2].getToken() == token) {
            return true;
        } else return cell[0][2].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][0].getToken() == token;
    }

    // Method to clear the board
    private void boardClear() {
        // loop through the grid
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                cell[i][j].getChildren().clear(); // Then clear the imageview
                cell[i][j].setToken('_'); // and reset token to blank
            }
    }
    
    // An inner class for a cell
    public class Cell extends Pane {
        // Token used for this cell
        private char token = '_';

        // Method to create cell
        public Cell() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);

            // Action to indicate player's selection
            this.setOnMouseClicked(e -> {
                if (!isWon(comp) && !isWon(player)) // Check to make sure that the game isn't over
                playerTurn();
            });
        }

        // Method to get token
        public char getToken() {
            return token;
        }

        // Method to set token
        public void setToken(char c) {
            token = c;

            // If statement to check for player
            if (token == 'X') {

                // If player, create a new imageview of player token image
                ImageView playerToken = new ImageView(new Image(Player.getPlayerToken()));

                //Fit playerToken height and width properties to the cells
                playerToken.fitHeightProperty().bind(this.heightProperty());
                playerToken.fitWidthProperty().bind(this.widthProperty());

                // Add the imageview to the pane
                this.getChildren().addAll(playerToken);

            // If statement to check for comp
            } else if (token == 'O') {

                // If comp, create a new imageview of comp token image
                ImageView computerToken = new ImageView(new Image(Computer.getComputerToken()));

                //Fit computerToken height and width properties to the cells
                computerToken.fitHeightProperty().bind(this.heightProperty());
                computerToken.fitWidthProperty().bind(this.widthProperty());

                //Add the imageview to the pane
                getChildren().addAll(computerToken);
            }
        }

        // Method for the computer's turn
        private void compTurn() {

            // Create a move using the minimax algorithm for best move and set the token
            Move bestMove = findBestMove(cellToCharArrayBoard());
            cell[bestMove.row][bestMove.col].setToken(comp);

            // Check win condition
            if (isWon(comp)) {

                // Update playstatus, increase score, and taunt the player
                playStatus.setText("Computer won!");
                taunt.setText(Computer.taunt());
                compScore++;
                score.setText("Score is: " + playerScore + " - " + compScore);

                // Set comp's token to blank for game over scenario
                comp = '_';

                // Play the score animation
                playScoreAnimation();

            // Check draw condition
            } else if (isFull()) {

                // Update playstatus
                playStatus.setText("Draw!");

                // Set comp's token to blank for game over scenario
                comp = '_';

                // Play the score animation
                playScoreAnimation();
            }
        }

        // Method for the players turn, this will handle the mouse click event
        private void playerTurn() {

            // If cell is empty and game is not over
            if (token == '_' && player != '_') {
                setToken(player); // Set token in the cell

                // Check for win condition
                if (isWon(player)) {

                    // Update the playstatus, score, and player token
                    playStatus.setText("You won!");
                    playerScore++;
                    player = '_'; // Game is over
                    score.setText("Score is: " + playerScore + " - " + compScore);

                    // Play the win animation
                    playScoreAnimation();

                // Check for draw condition
                } else if (isFull()) {

                    // Update playstatus and token
                    playStatus.setText("Draw!");
                    player = '_'; // Game is over

                    // Play the score animation
                    playScoreAnimation();
                // If no win or draw then it's the computers turn
                } else {
                    compTurn();
                }
            }
        }

        // Method to call the score animation
        private void playScoreAnimation() {

            // Update animation text to the update playstatus from win or draw condition
            animationText = new Text(100, 100, playStatus.getText());

            // Set the text styling
            animationText.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
            animationText.setFill(Color.DARKRED);

            // set the text in the border pane
            root.setBottom(animationText);

            // Set the alignment for the text
            animationText.setTextAlignment(TextAlignment.CENTER);

            // Create a new fade transition animation using the animation text
            scoreAnimation = new FadeTransition(Duration.millis(1500), animationText);

            // Start from nothing and move to full
            scoreAnimation.setFromValue(0.0);
            scoreAnimation.setToValue(1.0);

            // Set auto reverse and cycle count
            scoreAnimation.setAutoReverse(true);
            scoreAnimation.setCycleCount(2);

            // Start animation
            scoreAnimation.play();
        }
    }

    // Create move class
    static class Move {
        int row, col;
    }

    // Method to make sure the board is not full
    Boolean isBoardNotFull(char[][] board) {

        // Loop through the grid
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_') // check for a blank and return true
                    return true;
        return false; // no blanks found
    }

    // Method to evaluate win conditions
    int evaluate(char[][] b) {

        // Check Rows for victory condition
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] &&
                    b[row][1] == b[row][2]) {
                if (b[row][0] == player) { // If the player won, then return +10 in the minimax evaluation
                    return +10;
                } else if (b[row][0] == comp) { // If the computer won, then return -10 in the minimax evaluation
                    return -10;
                }
            }
        }

        // Check Columns for victory condition
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] &&
                    b[1][col] == b[2][col]) {
                if (b[0][col] == player) { // If the player won, then return +10 in the minimax evaluation
                    return +10;
                } else if (b[0][col] == comp) // If the computer won, then return -10 in the minimax evaluation
                    return -10;
            }
        }

        // Check Diagonals for victory condition
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == player) { // If the player won, then return +10 in the minimax evaluation
                return +10;
            } else if (b[0][0] == comp) // If the computer won, then return -10 in the minimax evaluation
                return -10;
        }

        // Check other diagonal for victory condition
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == player) { // If the player won, then return +10 in the minimax evaluation
                return +10;
            } else if (b[0][2] == comp) // If the computer won, then return -10 in the minimax evaluation
                return -10;
        }

        // Return 0 if there's no win condition on this move
        return 0;
    }

    // Method that considers all possible moves on the board, and assign values to each move to determine which is the most optimal
    int minimax(char[][] board, int depth, Boolean isMaximizer) {

        // Update the score to evaluate the board
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
                    if (board[i][j] == '_') { // Check for blanks

                        // Set player token
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

                    if (board[i][j] == '_') { // Check for blanks

                        // Set the computer token
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

    // Method that finds the best possible move for the computer and player, and uses that knowledge for offense and defense
    Move findBestMove(char[][] board) {

        // Declare and initialize the best value
        int bestVal = 1000;

        // Create new move
        Move bestMove = new Move();

        // Declare row and col starting ints
        bestMove.row = -1;
        bestMove.col = -1;

        // Iterate through all cells and calculate a minimax score for each empty cell
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') { // Check for open moves

                    // Look at the best moves for the computer to make offensively
                    board[i][j] = comp;
                    int bestMoveVal = minimax(board, 0, false);

                    // Look at the best moves for the player, so the computer can use this information for defense
                    board[i][j] = player;
                    int worstMoveVal = minimax(board, 0, true);

                    // Undo the move to return the board to the current state
                    board[i][j] = '_';

                    // Initialize int for checking offense or defense
                    int offenseOrDefense;

                    // If statement to check if the best move absolute value is less than the worst move absolute value
                    if (abs(bestMoveVal) < abs(worstMoveVal)) {
                        // Defensive move
                        offenseOrDefense = -worstMoveVal;
                    } else {
                        // Offensive move
                        offenseOrDefense = bestMoveVal;
                    }

                    // Set the best Move to be the optimal move we picked above and update the best value
                    if ( offenseOrDefense < bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = offenseOrDefense;
                    }
                }
            }
        }

        // Use to check values in console
        // System.out.println("Value of the best move : " + bestVal);

        return bestMove;
    }
}