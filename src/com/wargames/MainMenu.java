package com.wargames;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    //TODO Need to make a main menu to select avatars (Partial Complete), change backgrounds, and start game/ scene switch



    // Override the start method in the Application class
    @Override
    public void start(Stage primaryStage) {

        // Create a try block
        try {
            // Button start stop pane
            // Create a Vbox for the buttons
            VBox buttonPane = new VBox(2);

            buttonPane.setSpacing(15);
            buttonPane.setPadding(new Insets(5, 5, 5, 5));
            buttonPane.setAlignment(Pos.CENTER);

            Button playGame = new Button("Play Game");
            Button exit =  new Button("Exit");

            buttonPane.getChildren().addAll(playGame, exit);

            // Name entry pane

            HBox namePane = new HBox(2);

            namePane.setAlignment(Pos.TOP_LEFT);

            Label name = new Label("First Name:");
            TextField firstName = new TextField();

            namePane.getChildren().addAll(name, firstName);

            // Player Selection Pane Config

            GridPane playerSelectionPane = new GridPane();

            playerSelectionPane.setVgap(10);
            playerSelectionPane.setHgap(10);
            playerSelectionPane.setPadding(new Insets(15,5,5,5));


            ToggleButton avatar1 = new ToggleButton();
            ToggleButton avatar2 = new ToggleButton();
            ToggleButton avatar3 = new ToggleButton();
            ToggleButton avatar4 = new ToggleButton();
            ToggleButton avatar5 = new ToggleButton();
            ToggleButton avatar6 = new ToggleButton();

            //TODO having issues with image directory and IntelliJ, got it working through eclipse
            playerSelectionPane.add(avatar1, 0, 0);
            playerSelectionPane.add(Player.getAvatar1(), 1, 0);
            playerSelectionPane.add(avatar2, 2, 0);
            playerSelectionPane.add(Player.getAvatar2(), 3, 0);
            playerSelectionPane.add(avatar3, 4, 0);
            playerSelectionPane.add(Player.getAvatar3(), 5, 0);
            playerSelectionPane.add(avatar4, 0, 1);
            playerSelectionPane.add(Player.getAvatar4(), 1, 1);
            playerSelectionPane.add(avatar5, 2, 1);
            playerSelectionPane.add(Player.getAvatar5(), 3, 1);
            playerSelectionPane.add(avatar6, 4, 1);
            playerSelectionPane.add(Player.getAvatar6(), 5, 1);


            // Create a Border pane
            BorderPane borderPane = new BorderPane();
            borderPane.setRight(buttonPane);
            borderPane.setTop(namePane);
            borderPane.setLeft(playerSelectionPane);

            // Create a scene and set it to hold the border pane
            Scene scene = new Scene(borderPane, 675, 375);

            // Set the stage title
            primaryStage.setTitle("Main Menu");

            // Place the scene in the stage
            primaryStage.setScene(scene);

            // Display the stage
            primaryStage.show();

            // Catch exceptions
        } catch(Exception e) {

            // And print the stack
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
