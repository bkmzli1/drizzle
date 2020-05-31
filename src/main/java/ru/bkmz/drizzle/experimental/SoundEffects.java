package ru.bkmz.drizzle.experimental;

import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.util.Sound;

import java.io.File;

public class SoundEffects {

    public static void playNew(String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Sound sound1 = new Sound(new File(GameData.appdata + "res/media/" + name), GameData.Settings_Effect_Volume.getValue());
                sound1.play();
            }
        }).start();


    }
    public static void playNewRandom(String name,GameData gd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Sound sound1 = new Sound(new File(GameData.appdata + "res/media/" + name), GameData.Settings_Effect_Volume.getValue());
                sound1.setVolume((int) (Math.random() * gd.getValue()));
                sound1.play();
            }
        }).start();


    }
}
