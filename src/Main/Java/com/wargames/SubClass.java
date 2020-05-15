package com.wargames;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// This and Main are sample classes on how to switch scenes
public class SubClass
{

    StackPane root = new StackPane();
    Stage stage;

    public SubClass(Stage stage)
    {
        Scene scene=new Scene(root,300,300);
        stage.setScene(scene);
        stage.show();
    }
}