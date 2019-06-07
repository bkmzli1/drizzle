package ru.bkmz.drizzle.util;

import javafx.scene.media.MediaPlayer;
import ru.bkmz.drizzle.Application;

import java.io.File;

public class Media {
    private static MediaPlayer mediaPlayer;
    private boolean error = false;

    public Media(String f) {

        try {

            mediaPlayer = new MediaPlayer(
                    new javafx.scene.media.Media(new File(f).toURI().toString())

            );
        } catch (Exception e) {
            error = true;
            Application.addError(Media.class.getName() + ": " + e);
            System.err.println(e);
        }
    }

    public void indefinite() {
        if (!error) {
            try {

                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            } catch (Exception e) {
                error = true;
                Application.addError(Media.class.getName() + ": " + e);
            }
        }
    }

    public void play() {
        if (!error) {
            try {
                mediaPlayer.play();
            } catch (Exception e) {
                error = true;
                Application.addError(Media.class.getName() + ": " + e);
                System.err.println(e);
            }
        }
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void volume(int volume) {
        if (!error) {
            try {

                mediaPlayer.setVolume(volume / 10f);
            } catch (Exception e) {
                error = true;
                Application.addError(Media.class.getName() + ": " + e);
            }
        }
    }


}

