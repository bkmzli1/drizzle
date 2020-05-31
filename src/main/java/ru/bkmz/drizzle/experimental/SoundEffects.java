package ru.bkmz.drizzle.experimental;

import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.util.Sound;

import java.io.File;

public class SoundEffects {
    static boolean error = false;

    public static void playNew(String name) {
        if (!error) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Sound sound1 = new Sound(new File(GameData.appdata + "res/media/" + name), GameData.Settings_Effect_Volume.getValue());
                        sound1.play();
                    } catch (Exception e) {
                        error = true;
                    }
                }

            }).start();

        }
    }

    public static void playNewRandom(String name, GameData gd) {
        if (!error) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Sound sound1 = new Sound(new File(GameData.appdata + "res/media/" + name), GameData.Settings_Effect_Volume.getValue());
                        sound1.setVolume((int) (Math.random() * gd.getValue()));
                        sound1.play();
                    } catch (Exception e) {
                        error = true;
                    }
                }
            }).start();
        }

    }
}
