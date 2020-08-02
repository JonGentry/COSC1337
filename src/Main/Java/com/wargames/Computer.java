package com.wargames;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Declare computer class
public class Computer {

    // Declare and initialize ImageView for computer avatar
    private static final ImageView COMP_AVATAR = new ImageView(new Image("/image/compAvatar.png", 100, 100, false, false));

    // Declare and initialize ImageView for computer playing piece
    private static final String COMPUTER_TOKEN = "/image/dogPiece.png";

    // Getter for computer avatar
    public static ImageView getCompAvatar() {
        return COMP_AVATAR;
    }

    // Getter for computer playing piece
    public static String getComputerToken() {
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
            return "You're a Loser!";
        else
            return "I can do this in my sleep!";
    }
}
