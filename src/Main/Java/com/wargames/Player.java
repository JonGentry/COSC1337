package com.wargames;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

    // Declare and initialize ImageView for the player piece
    private static final ImageView PLAYER_TOKEN = new ImageView(new Image("/image/catPiece.png",75,75,false,false));

    // Declare and initialize ImageViews for avatar selection
    private static final ImageView AVATAR_1 = new ImageView(new Image("/image/avatar1.png", 50, 50, false, false));
    private static final ImageView AVATAR_2 = new ImageView(new Image("/image/avatar2.png", 50, 50, false, false));
    private static final ImageView AVATAR_3 = new ImageView(new Image("/image/avatar3.png", 50, 50, false, false));
    private static final ImageView AVATAR_4 = new ImageView(new Image("/image/avatar4.png", 50, 50, false, false));
    private static final ImageView AVATAR_5 = new ImageView(new Image("/image/avatar5.png", 50, 50, false, false));
    private static final ImageView AVATAR_6 = new ImageView(new Image("/image/avatar6.png", 50, 50, false, false));

    // Create a variable that can take player avatar selection
    private static ImageView avatarSelected;


    /*-------------------------------------------Getters for all final variables-----------------------------------*/
    public static ImageView getPlayerToken() {
        return PLAYER_TOKEN;
    }

    public static ImageView getAvatar1() {
        return AVATAR_1;
    }

    public static ImageView getAvatar2() {
        return AVATAR_2;
    }

    public static ImageView getAvatar3() {
        return AVATAR_3;
    }

    public static ImageView getAvatar4() {
        return AVATAR_4;
    }

    public static ImageView getAvatar5() {
        return AVATAR_5;
    }

    public static ImageView getAvatar6() {
        return AVATAR_6;
    }

    /*-------------------------------------------Getter and Setter for avatar selection------------------------------*/

    public static ImageView getAvatarSelected() {
        return avatarSelected;
    }

    public static void setAvatarSelected(ImageView avatarSelected) {
        Player.avatarSelected = avatarSelected;
    }
}
