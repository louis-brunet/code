package com.mygdx.game.userdata;

import com.mygdx.game.enums.UserDataType;

public class GroundUserData extends UserData {
    public GroundUserData(float width, float height) {
        super(width, height);

        this.setUserDataType(UserDataType.GROUND);
    }
}
