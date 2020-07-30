package com.wargames;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class WarGamesApp extends Application {
    BorderPane root = new BorderPane();
    Stage primaryStage;
    Scene scene;

    // Create a Vbox for the buttons
    VBox buttonPane = new VBox(2);

    // Create two buttons to play game or exit app
    Button playGame = new Button ("Play Game");
    Button exit = new Button("Exit");

    // Create a Vbox for Text and Grid Pane
    VBox selectionPane = new VBox(2);

    // Create string and Text for selection pane header
    String text = "Please select an Avatar:";
    Text avatarText = new Text(text);

    // Create a ToggleGroup for the avatar toggle buttons
    ToggleGroup avatarGroup = new ToggleGroup();

    // Create toggle buttons to correlate with the avatar selection
    ToggleButton avatar1 = new ToggleButton();
    ToggleButton avatar2 = new ToggleButton();
    ToggleButton avatar3 = new ToggleButton();
    ToggleButton avatar4 = new ToggleButton();
    ToggleButton avatar5 = new ToggleButton();
    ToggleButton avatar6 = new ToggleButton();


    ChoiceBox background  = new ChoiceBox();
    public static Color backgoundColor = Color.WHITE;


    public WarGamesApp(){

        //TODO Backgound select not working, not sure why setFill or just a Color variable is not working for the scene
        background.getItems().addAll("White", "Red", "Blue", "Yellow", "Green", "Pink");

        background.setOnAction(e-> {
            if (background.getValue().toString().equalsIgnoreCase("white"))
                backgoundColor = Color.WHITE;
            else if (background.getValue().toString().equalsIgnoreCase("red"))
                backgoundColor = Color.RED;
            else if (background.getValue().toString().equalsIgnoreCase("blue"))
                scene.setFill(Color.BLUE);
            else if (background.getValue().toString().equalsIgnoreCase("yellow"))
                scene.setFill(Color.YELLOW);
            else if (background.getValue().toString().equalsIgnoreCase("green"))
                scene.setFill(Color.GREEN);
            else if (background.getValue().toString().equalsIgnoreCase("pink"))
                scene.setFill(Paint.valueOf("white"));
            else
                scene.setFill(Color.WHITE);
        });

        // Set play game button to switch stages and start game only if avatar selected
        playGame.setOnAction(e-> {
            if (!Player.getAvatarSelected().equals(null)) {
                GameBoard game = new GameBoard(primaryStage);
            }
            else
                System.out.println("Select an avatar");
        });

        // Set exit button to close on mouse click
        exit.setOnMouseClicked(e -> primaryStage.close());
    }

    // Override the start method in the Application class
    @Override
    public void start(Stage primaryStage) {

        // Create a try block
        try {

            this.primaryStage = primaryStage;

            /*-------------------------------------Button start stop pane--------------------------------------------*/

            // Set the button panes spacing padding and alignment
            buttonPane.setSpacing(100);
            buttonPane.setPadding(new Insets(5, 5, 5, 5));
            buttonPane.setAlignment(Pos.CENTER);

            // Add the buttons to the button pane
            buttonPane.getChildren().addAll(playGame, exit);


            /*--------------------------------- Player Selection Pane Config-----------------------------------------*/



            // Set the selection pane padding and alignment
            selectionPane.setPadding(new Insets(5,5,5,5));
            selectionPane.setAlignment(Pos.CENTER);

            // Add the avatarText to the selection pane
            selectionPane.getChildren().add(avatarText);

            // Create a grid plane for player avatar selection
            GridPane avatarSelectionPane = new GridPane();

            // Set Grid pane gaps, padding and alignment
            avatarSelectionPane.setVgap(10);
            avatarSelectionPane.setHgap(10);
            avatarSelectionPane.setPadding(new Insets(5,5,5,5));
            avatarSelectionPane.setAlignment(Pos.CENTER);

            // Set the Avatar toggle buttons to toggle group
            avatar1.setToggleGroup(avatarGroup);
            avatar2.setToggleGroup(avatarGroup);
            avatar3.setToggleGroup(avatarGroup);
            avatar4.setToggleGroup(avatarGroup);
            avatar5.setToggleGroup(avatarGroup);
            avatar6.setToggleGroup(avatarGroup);

            // Set action on mouse click to update the avatar selected
            avatar1.setOnMouseClicked(e -> Player.setAvatarSelected(Player.getAvatar1()));
            avatar2.setOnMouseClicked(e -> Player.setAvatarSelected(Player.getAvatar2()));
            avatar3.setOnMouseClicked(e -> Player.setAvatarSelected(Player.getAvatar3()));
            avatar4.setOnMouseClicked(e -> Player.setAvatarSelected(Player.getAvatar4()));
            avatar5.setOnMouseClicked(e -> Player.setAvatarSelected(Player.getAvatar5()));
            avatar6.setOnMouseClicked(e -> Player.setAvatarSelected(Player.getAvatar6()));


            // Tie the Toggle Buttons and Player Avatars to the grid pane
            avatarSelectionPane.add(avatar1, 0, 0);
            avatarSelectionPane.add(Player.getAvatar1(), 1, 0);
            avatarSelectionPane.add(avatar2, 2, 0);
            avatarSelectionPane.add(Player.getAvatar2(), 3, 0);
            avatarSelectionPane.add(avatar3, 4, 0);
            avatarSelectionPane.add(Player.getAvatar3(), 5, 0);
            avatarSelectionPane.add(avatar4, 0, 1);
            avatarSelectionPane.add(Player.getAvatar4(), 1, 1);
            avatarSelectionPane.add(avatar5, 2, 1);
            avatarSelectionPane.add(Player.getAvatar5(), 3, 1);
            avatarSelectionPane.add(avatar6, 4, 1);
            avatarSelectionPane.add(Player.getAvatar6(), 5, 1);

            // Add the avatarSelectionPane to the Vbox selection pane
            selectionPane.getChildren().add(avatarSelectionPane);

            /*------------------------------------Root pane and Scene config------------------------------------------*/



            scene = initStage();

            // Set the stage Title and Scene then play
            primaryStage.setTitle("Main Menu");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Catch exceptions
        } catch(Exception e) {

            // And print the stack
            e.printStackTrace();
        }
    }

    private Scene initStage() {
        root.setTop(background );
        root.setRight(buttonPane);
        root.setCenter(selectionPane);

        // Create a menuScene and set it to hold the border pane
        return new Scene(root, 675, 375, backgoundColor);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
