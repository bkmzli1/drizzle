package ru.bkmz.drizzle;

import com.sun.scenario.animation.AbstractMasterTimer;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.input.Keyboard;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.level.Level.LevelController;
import ru.bkmz.drizzle.util.Commons;
import ru.bkmz.drizzle.util.FrameCounter;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


public class Games {
    private static Canvas canvas = new Canvas(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);//размеры окна
    private final GraphicsContext gc = canvas.getGraphicsContext2D();//2d

    private final FrameCounter frameCounter = new FrameCounter();//fps

    private final LevelController levelController;
    private final AbstractMasterTimer timer = null;

    Games(Keyboard keyboard) {

        this.levelController = new Level(keyboard).getLevelController();
        new AnimationTimer() {

            @Override
            public void handle(long now) {

                levelController.update(gc);
                if (levelController.isClosed()) {
                    canvas.fireEvent(new StateEvent(StateEvent.MENU));
                }

                if (keyboard.isPressed(KeyCode.ESCAPE)) {
                    if (levelController.isRunning()) {
                        if (levelController.isPaused()) {
                            canvas.fireEvent(new StateEvent(StateEvent.UNPAUSE));
                        } else {
                            canvas.fireEvent(new StateEvent(StateEvent.PAUSE));
                        }
                    } else {
                        canvas.fireEvent(new StateEvent(StateEvent.MENU));
                    }
                }

                keyboard.update();

                frameCounter.sample(now);
                if ((Application.DEBUG_MODE || GameData.Settings_FPS.getValue() == 1) && GameData.Settings_FPS.getValue() != 2) {
                    gc.setFill(Color.WHITE);
                    gc.fillText("Average Settings_FPS: " + (int) frameCounter.getAverageFPS(), 0, Commons.SCENE_HEIGHT);
                } else if (GameData.Settings_FPS.getValue() == 2) {
                    gc.setFill(Color.WHITE);
                    gc.fillText("Instant Settings_FPS: " + (int) frameCounter.getInstantFPS(), 0, Commons.SCENE_HEIGHT);
                }

            }
        }.start();

    }

    Canvas getCanvas() {
        return canvas;
    }

    void close() {
        this.levelController.endGame();
    }

    void pause() {
        this.levelController.pauseGame();
    }

    void play() {
        this.levelController.startGame();
    }

    void unpause() {
        this.levelController.unpauseGame();
    }

}
