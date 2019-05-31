package ru.bkmz.drizzle.util;

import javafx.scene.paint.*;
import ru.bkmz.drizzle.level.GameData;

public class Commons {

    public static double SCENE_WIDTH = GameData.SCENE_WIDTH.getValue();
    public static double SCENE_HEIGHT = GameData.SCENE_HEIGHT.getValue();
    public static double SCENE_GROUND = SCENE_HEIGHT / 40 * 36;

    public static void setScene() {
        SCENE_WIDTH = GameData.SCENE_WIDTH.getValue();
        SCENE_HEIGHT = GameData.SCENE_HEIGHT.getValue();
        SCENE_GROUND = SCENE_HEIGHT / 40 * 36;
    }


    public static Color
            colorGradient1 = Color.rgb(0, 255, 255),
            colorGradient2 = Color.rgb(0, 0, 255),
            colorTexOff = Color.rgb(0, 145, 225),
            colorTexOn = Color.rgb(0, 225, 225),
            canBuy = Color.rgb(0, 255, 255,0.8f),
            notBuy = Color.rgb(0, 255, 255,0.5f),
            buyFull = Color.rgb(0,255,200),
            numbers = Color.rgb(0,255,255,0.5f),
            skillDisabled = Color.rgb(0, 150, 150),
            skillOn = Color.rgb(0, 255, 145),
            skillOff = Color.rgb(0, 145, 255),
            colorNull = Color.rgb(0, 0, 0),
            colorTEST = Color.rgb(255, 0, 0);


    public static final LinearGradient GRADIENT = new LinearGradient(0, 0, 1, 1, true,
            CycleMethod.NO_CYCLE,
            new Stop(0,
                    colorGradient1),
            new Stop(1,
                    colorGradient2));

    public static final LinearGradient GRADIENT2 = new LinearGradient(0, 0, 0.5, 1, true,
            CycleMethod.NO_CYCLE,
            new Stop(0,
                    colorGradient2),
            new Stop(1,
                    colorGradient1));


}
