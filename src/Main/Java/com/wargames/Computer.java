package com.wargames;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Computer {

    // Declare and initialize ImageView for computer playing piece
    private static final ImageView COMPUTER_TOKEN = new ImageView(new Image("/image/dogPiece.png",75,75,false,false));;

    // Getter for computer playing piece
    public static ImageView getComputerToken() {
        return COMPUTER_TOKEN;
    }

    // Create method to taunt the player
    public static String taunt() {

        // Create a randomizer
        int randomizer = (int)(Math.random() * 4);

        // If statement to check the randomizer and select a taunt
        if (randomizer == 0)
            return "Are you even trying?";
        else if (randomizer == 1)
            return "Do you even know how to play?";
        else if (randomizer == 2)
            return "You're going to have to do better than that!";
        else
            return "I can do this in my sleep";



    }


}
