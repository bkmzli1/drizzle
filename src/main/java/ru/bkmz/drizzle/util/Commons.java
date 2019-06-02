package ru.bkmz.drizzle.util;

import javafx.scene.paint.*;
import ru.bkmz.drizzle.level.GameData;

import java.util.HashMap;
import java.util.Map;

public class Commons {

    public static double SCENE_WIDTH = GameData.SCENE_WIDTH.getValue();
    public static double SCENE_HEIGHT = GameData.SCENE_HEIGHT.getValue();
    public static double SCENE_GROUND = SCENE_HEIGHT / 40 * 36;

    public static void setScene() {
        SCENE_WIDTH = GameData.SCENE_WIDTH.getValue();
        SCENE_HEIGHT = GameData.SCENE_HEIGHT.getValue();
        SCENE_GROUND = SCENE_HEIGHT / 40 * 36;
    }


    private static Color
            colorNull = Color.rgb(0, 0, 0),
            colorTEST = Color.rgb(255, 0, 0);
    private static Map<String, Color> colorMap = new HashMap<String, Color>();



    public static LinearGradient GRADIENT = null;
    public static LinearGradient GRADIENT2 = null;

    public static void colorLoader() {
        if (GameData.Settings_BACKGROUND.getValue() == 1) {
            colorMap.put("colorGradient1", Color.rgb(0, 255, 255));
            colorMap.put("colorGradient2", Color.rgb(0, 0, 255));
            colorMap.put("colorTexOff", Color.rgb(0, 145, 225));
            colorMap.put("colorTexOn", Color.rgb(0, 225, 225));
            colorMap.put("canBuy", Color.rgb(0, 255, 255, 0.8f));
            colorMap.put("notBuy", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("buyFull", Color.rgb(0, 255, 200));
            colorMap.put("numbers", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("skillDisabled", Color.rgb(0, 150, 150));
            colorMap.put("skillOn", Color.rgb(0, 255, 145));
            colorMap.put("skillOff", Color.rgb(0, 145, 255));
        } else if (GameData.Settings_BACKGROUND.getValue() == 2) {
            colorMap.put("colorGradient1", Color.rgb(0, 255, 255));
            colorMap.put("colorGradient2", Color.rgb(0, 0, 255));
            colorMap.put("colorTexOff", Color.rgb(255, 145, 225));
            colorMap.put("colorTexOn", Color.rgb(0, 225, 225));
            colorMap.put("canBuy", Color.rgb(0, 255, 255, 0.8f));
            colorMap.put("notBuy", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("buyFull", Color.rgb(0, 255, 200));
            colorMap.put("numbers", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("skillDisabled", Color.rgb(0, 150, 150));
            colorMap.put("skillOn", Color.rgb(0, 255, 145));
            colorMap.put("skillOff", Color.rgb(0, 145, 255));
        } else if (GameData.Settings_BACKGROUND.getValue() == 3) {
            colorMap.put("colorGradient1", Color.rgb(0, 255, 255));
            colorMap.put("colorGradient2", Color.rgb(0, 0, 255));
            colorMap.put("colorTexOff", Color.rgb(0, 145, 225));
            colorMap.put("colorTexOn", Color.rgb(0, 225, 225));
            colorMap.put("canBuy", Color.rgb(0, 255, 255, 0.8f));
            colorMap.put("notBuy", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("buyFull", Color.rgb(0, 255, 200));
            colorMap.put("numbers", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("skillDisabled", Color.rgb(0, 150, 150));
            colorMap.put("skillOn", Color.rgb(0, 255, 145));
            colorMap.put("skillOff", Color.rgb(0, 145, 255));
        } else if (GameData.Settings_BACKGROUND.getValue() == 4) {
            colorMap.put("colorGradient1", Color.rgb(0, 255, 255));
            colorMap.put("colorGradient2", Color.rgb(0, 0, 255));
            colorMap.put("colorTexOff", Color.rgb(0, 145, 225));
            colorMap.put("colorTexOn", Color.rgb(0, 225, 225));
            colorMap.put("canBuy", Color.rgb(0, 255, 255, 0.8f));
            colorMap.put("notBuy", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("buyFull", Color.rgb(0, 255, 200));
            colorMap.put("numbers", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("skillDisabled", Color.rgb(0, 150, 150));
            colorMap.put("skillOn", Color.rgb(0, 255, 145));
            colorMap.put("skillOff", Color.rgb(0, 145, 255));
        } else if (GameData.Settings_BACKGROUND.getValue() == 5) {
            colorMap.put("colorGradient1", Color.rgb(0, 255, 255));
            colorMap.put("colorGradient2", Color.rgb(0, 0, 255));
            colorMap.put("colorTexOff", Color.rgb(0, 145, 225));
            colorMap.put("colorTexOn", Color.rgb(0, 225, 225));
            colorMap.put("canBuy", Color.rgb(0, 255, 255, 0.8f));
            colorMap.put("notBuy", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("buyFull", Color.rgb(0, 255, 200));
            colorMap.put("numbers", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("skillDisabled", Color.rgb(0, 150, 150));
            colorMap.put("skillOn", Color.rgb(0, 255, 145));
            colorMap.put("skillOff", Color.rgb(0, 145, 255));
        } else if (GameData.Settings_BACKGROUND.getValue() == 6) {
            colorMap.put("colorGradient1", Color.rgb(0, 255, 255));
            colorMap.put("colorGradient2", Color.rgb(0, 0, 255));
            colorMap.put("colorTexOff", Color.rgb(0, 145, 225));
            colorMap.put("colorTexOn", Color.rgb(0, 225, 225));
            colorMap.put("canBuy", Color.rgb(0, 255, 255, 0.8f));
            colorMap.put("notBuy", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("buyFull", Color.rgb(0, 255, 200));
            colorMap.put("numbers", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("skillDisabled", Color.rgb(0, 150, 150));
            colorMap.put("skillOn", Color.rgb(0, 255, 145));
            colorMap.put("skillOff", Color.rgb(0, 145, 255));
        } else if (GameData.Settings_BACKGROUND.getValue() == 7) {
            colorMap.put("colorGradient1", Color.rgb(0, 255, 255));
            colorMap.put("colorGradient2", Color.rgb(0, 0, 255));
            colorMap.put("colorTexOff", Color.rgb(0, 145, 225));
            colorMap.put("colorTexOn", Color.rgb(0, 225, 225));
            colorMap.put("canBuy", Color.rgb(0, 255, 255, 0.8f));
            colorMap.put("notBuy", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("buyFull", Color.rgb(0, 255, 200));
            colorMap.put("numbers", Color.rgb(0, 255, 255, 0.5f));
            colorMap.put("skillDisabled", Color.rgb(0, 150, 150));
            colorMap.put("skillOn", Color.rgb(0, 255, 145));
            colorMap.put("skillOff", Color.rgb(0, 145, 255));
        }
        GRADIENT = new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0,
                        getColor("colorGradient1")),
                new Stop(1,
                        getColor("colorGradient2"))
        );
        GRADIENT2 = new LinearGradient(0, 0, 0.5, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0,
                        getColor("colorGradient2")),
                new Stop(1,
                        getColor("colorGradient1")));

    }

    public static Color getColor(String name) {
        Color color;

        color = colorMap.get(name);

        return color;
    }


}
