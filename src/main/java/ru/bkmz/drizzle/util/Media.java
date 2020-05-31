package ru.bkmz.drizzle.util;

import javafx.scene.media.MediaPlayer;
import ru.bkmz.drizzle.Application;

import java.io.File;

public class Media {
    private static MediaPlayer mediaPlayer;


    public Media(String f)  throws Exception{


        mediaPlayer = new MediaPlayer(
                new javafx.scene.media.Media(new File(f).toURI().toString())

        );

    }

    public void indefinite() {


        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);


    }

    public void play() {

        mediaPlayer.play();


    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void volume(int volume) {


        mediaPlayer.setVolume(volume / 10f);

    }
}



