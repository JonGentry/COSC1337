package com.wargames;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// This and SubClass are sample classes on how to switch scenes
public class Main extends Application
{
    BorderPane root = new BorderPane();
    Button btn=new Button("Click Here");
    Stage primaryStage;
    Scene scene ;
    public Main()
    {
        btn.setPrefWidth(75);
        btn.setPrefHeight(15);

        btn.setOnAction((e)->
        {
            SubClass s = new SubClass(primaryStage);
        });
    }

    @Override
    public void start(Stage primaryStage)
    {
        try {
            this.primaryStage=primaryStage;
            //          root.getChildren().add(btn);

            scene = initStage();

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private Scene initStage()
    {
        root.setCenter(btn);
        return new Scene(root,150,150);
    }


    public static void main(String[] args) {
        launch(args);
    }
}