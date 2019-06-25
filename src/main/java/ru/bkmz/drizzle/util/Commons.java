package ru.bkmz.drizzle.util;

import javafx.scene.paint.*;
import javafx.scene.text.Text;
import ru.bkmz.drizzle.level.GameData;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static ru.bkmz.drizzle.util.Language.getLanguageMap;

public class Commons {

    public static double SCENE_WIDTH = GameData.SCENE_WIDTH.getValue();
    public static double SCENE_HEIGHT = GameData.SCENE_HEIGHT.getValue();
    public static double SCENE_GROUND = SCENE_HEIGHT / 40 * 36;
    public static String[] colorName = new String[]{"colorGradient1", "colorGradient2",
            "colorTexOff", "colorTexOn",
            "canBuy", "notBuy", "buyFull"
            , "numbers",
            "skillDisabled", "skillOn", "skillOff"};

    private static String file = GameData.appdata + "color.ser";

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
        if (GameData.THEME.getValue() == 1) {
            nullColor();
        } else if (GameData.THEME.getValue() == 2) {
            if (!new File(file).exists()) {
                nullColor();
            } else {
                try {
                    read();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        gradent();
    }

    private static void gradent() {
        GRADIENT = new LinearGradient(0, 0, 0, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0,
                        getColor("colorGradient1")),
                new Stop(1,
                        getColor("colorGradient2"))
        );
        GRADIENT2 = new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0,
                        getColor("colorGradient1")),
                new Stop(1,
                        getColor("colorGradient2")));

    }

    public static void nullColor() {
        colorMap.put("colorGradient1", Color.rgb(0, 255, 255,1.0f));
        colorMap.put("colorGradient2", Color.rgb(0, 0, 255,1.0f));
        colorMap.put("colorTexOff", Color.rgb(0, 145, 225,1.0f));
        colorMap.put("colorTexOn", Color.rgb(0, 225, 225,1.0f));
        colorMap.put("canBuy", Color.rgb(0, 255, 255, 0.8f));
        colorMap.put("notBuy", Color.rgb(0, 255, 255, 0.5f));
        colorMap.put("buyFull", Color.rgb(0, 255, 200,1.0f));
        colorMap.put("numbers", Color.rgb(0, 255, 255, 0.5f));
        colorMap.put("skillDisabled", Color.rgb(0, 150, 150,1f));
        colorMap.put("skillOn", Color.rgb(0, 255, 145,1.0f));
        colorMap.put("skillOff", Color.rgb(0, 145, 255,1.0f));
        gradent();
        try {
            write();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Color getColor(String name) {
        Color color;

        color = colorMap.get(name);

        return color;
    }

    public static void setColor(String name, int red, int green, int blue) {


        colorMap.put(name, Color.rgb(red, green, blue));
        gradent();

    }

    public static void setColorRed(String name, int red) {
        try {
            colorMap.put(name, Color.rgb(red, (int) (255 * getColor(name).getGreen()), (int) (255 * getColor(name).getBlue())));
            gradent();
        } catch (Exception ignored) {

        }

    }

    public static void setColorGreen(String name, int green) {
        try {

            colorMap.put(name, Color.rgb((int) (255 * getColor(name).getRed()), green, (int) (255 * getColor(name).getBlue())));
            gradent();
        } catch (Exception ignored) {
        }

    }

    public static void setColorBlue(String name, int blue) {
        try {

            colorMap.put(name, Color.rgb((int) (255 * getColor(name).getRed()), (int) (255 * getColor(name).getGreen()), blue));

            gradent();
        } catch (Exception ignored) {
        }
    }

    public static void setColorOpacity(String name, int opacity) {
        try {
            colorMap.put(name, Color.rgb((int) (255 * getColor(name).getRed()), (int) (255 * getColor(name).getGreen()), (int) (255 * getColor(name).getBlue()), opacity/100f));
            gradent();
        } catch (Exception ignored) {

        }

    }

    public static void read() throws Exception {
        FileInputStream fiStream = new FileInputStream(new File(file));
        ObjectInputStream oiStream = new ObjectInputStream(fiStream);
        for (String s : colorName) {

            colorMap.put(s, Color.rgb(oiStream.readInt(), oiStream.readInt(), oiStream.readInt()));


        }
        oiStream.close();
        fiStream.close();

    }

    public static void write() throws Exception {
        FileOutputStream foStream = new FileOutputStream(new File(file), false);
        ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
        ooStream.reset();
        for (String s : colorName) {
            ooStream.writeInt((int) (getColor(s).getRed() * 255));
            ooStream.writeInt((int) (getColor(s).getGreen() * 255));
            ooStream.writeInt((int) (getColor(s).getBlue() * 255));
        }


        ooStream.close();
        foStream.close();
    }

    public static Color getColor(int r, int g, int b, float o) {
        o = o / 100f;
        return Color.rgb(r, g, b, o);
    }
    public static void getCollorText(Text text, String name, int colorValve) {
        if (name.equals(getLanguageMap("red"))) {
            text.setFill(getColor(colorValve, 0, 0, 100));
        } else if (name.equals(getLanguageMap("green"))) {
            text.setFill(getColor(0, colorValve, 0, 100));
        } else if (name.equals(getLanguageMap("blue"))) {
            text.setFill(getColor(0, 0, colorValve, 100));
        }else if (name.equals(getLanguageMap("opacity"))) {
            text.setFill(getColor((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255), 100));
        }
    }


}
