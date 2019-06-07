package ru.bkmz.drizzle.util;

import ru.bkmz.drizzle.Application;
import ru.bkmz.drizzle.level.GameData;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound implements AutoCloseable {

    private boolean released = false;
    private AudioInputStream stream = null;
    private Clip clip = null;
    private int i = 0;
    private FloatControl volumeControl = null;
    private boolean playing = false;
    private boolean error = false;

    public Sound(File f,int volume) {
        try {
            stream = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            released = true;
            setVolume(volume);
        } catch (Exception e) {
            Application.addError(Sound.class.getName()+": "+e);
            e.printStackTrace();
            released = false;
            error = true;
            close();
        }
    }

    // true если звук успешно загружен, false если произошла ошибка
    public boolean isReleased() {
        return released;
    }

    // проигрывается ли звук в данный момент
    public boolean isPlaying() {
        return playing;
    }

    // Запуск
	/*
	  breakOld определяет поведение, если звук уже играется
	  Если breakOld==true, о звук будет прерван и запущен заново
	  Иначе ничего не произойдёт
	*/
    public void play(boolean breakOld) {
        try {


            if (!error) {
                if (breakOld) {
                    clip.setFramePosition(i);
                    i++;
                    clip.start();

                }
            }
        }catch (Exception e){
            error = true;
            Application.addError(Media.class.getName()+": "+e);
            System.err.println(e);
        }
    }

    // То же самое, что и playNew(true)
    public void play() {
        if (!error) {
            play(true);
        }
    }

    // Останавливает воспроизведение
    public void stop() {
        if (playing) {
            clip.stop();
        }
    }

    public void close() {
        if (clip != null)
            clip.close();

        if (stream != null)
            try {
                stream.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
    }

    // Установка громкости
	/*
	  x долже быть в пределах от 0 до 1 (от самого тихого к самому громкому)
	*/
    public void setVolume(float x) {
        x = x / 10f;
        if (x < 0) x = 0;
        if (x > 1) x = 1;
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        volumeControl.setValue((max - min) * x + min);
    }

    // Возвращает текущую громкость (число от 0 до 1)
    public float getVolume() {
        float v = volumeControl.getValue();
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        return (v - min) / (max - min);
    }

    // Дожидается окончания проигрывания звука
    public void join() {
        if (!released) return;
        synchronized (clip) {
            try {
                while (playing)
                    clip.wait();
            } catch (InterruptedException exc) {
            }
        }
    }

    private class Listener implements LineListener {
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                playing = false;
                synchronized (clip) {
                    clip.notify();
                }
            }
        }
    }
}
