package ru.bkmz.drizzle.util;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import ru.bkmz.drizzle.level.GameData;

public class Commons {

    public static double SCENE_WIDTH = GameData.SCENE_WIDTH.getValue();
    public static double SCENE_HEIGHT = GameData.SCENE_HEIGHT.getValue();

    public static double SCENE_GROUND = SCENE_HEIGHT / 40 * 36;


    public static final LinearGradient GRADIENT = new LinearGradient(0, 0, 1, 1, true,
            CycleMethod.NO_CYCLE,
            new Stop(0,
                    Color.rgb(0, 50, 225)),
            new Stop(1,
                    Color.rgb(0, 225, 225)));

    public static final LinearGradient GRADIENT2 = new LinearGradient(0, 0, 0.5, 1, true,
            CycleMethod.NO_CYCLE,
            new Stop(0,
                    Color.rgb(0, 50, 225)),
            new Stop(1,
                    Color.rgb(0, 225, 225)));


}
