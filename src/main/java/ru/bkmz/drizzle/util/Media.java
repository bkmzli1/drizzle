package ru.bkmz.drizzle.util;

import javafx.scene.media.MediaPlayer;
import ru.bkmz.drizzle.Application;
import ru.bkmz.drizzle.level.GameData;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Media {
    private static MediaPlayer mediaPlayer;
    private boolean erorr = false;

    public Media(String f) {
        try {

            mediaPlayer = new MediaPlayer(
                    new javafx.scene.media.Media(new File(f).toURI().toString())

            );
        } catch (Exception e) {
            erorr = true;
            Application.error += e;
            System.err.println(e);
        }
    }

    public void indefinite() {
        try {

            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } catch (Exception e) {
            Application.error += e;
        }
    }

    public void play() {
        if (!erorr) {
            try {
                mediaPlayer.play();
            } catch (Exception e) {
                Application.error += e;
                System.err.println(e);
            }
        }
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void volume(int volume) {
        try {

            mediaPlayer.setVolume(volume / 10f);
        } catch (Exception e) {
            Application.error += e;
        }
    }


}

