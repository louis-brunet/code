package com.mygdx.game.userdata;

import com.mygdx.game.enums.UserDataType;

public class PlayerUserData extends UserData {

    public PlayerUserData(float width, float height) {
        super(width, height);

        this.setUserDataType(UserDataType.PLAYER);
    }
}
