package com.wargames;

import javafx.scene.image.ImageView;

public class Computer {

    //TODO add dog image to the imageview
    private static ImageView computerToken;

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
