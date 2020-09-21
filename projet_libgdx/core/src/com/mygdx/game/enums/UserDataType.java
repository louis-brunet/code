package com.mygdx.game.enums;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.userdata.UserData;

public enum UserDataType {
    PLAYER,
    GROUND;
    // TODO

    public static boolean bodyIsOfType(Body body, UserDataType type) {
        UserDataType bodyType = ((UserData) body.getUserData()).getUserDataType();
        if ( bodyType == null || type == null ) {
            return false;
        }
        return  bodyType.equals(type);
    }

    public static boolean bodyIsPlayer(Body body) {
        return UserDataType.bodyIsOfType(body, UserDataType.PLAYER);
    }

    public static boolean bodyIsGround(Body body) {
        return UserDataType.bodyIsOfType(body, UserDataType.GROUND);
    }
}
