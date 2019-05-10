package ru.bkmz.drizzle;

import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.input.Keyboard;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.level.Level.LevelController;
import ru.bkmz.drizzle.util.Commons;
import ru.bkmz.drizzle.util.FrameCounter;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


class Games {
    private final Canvas canvas = new Canvas(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private final FrameCounter frameCounter = new FrameCounter();

      private final LevelController levelController;

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
                if (Application.DEBUG_MODE) {
                    gc.setFill(Color.WHITE);
                    gc.fillText("getAverageFPS" + frameCounter.getAverageFPS(), 20, 680);
                    gc.fillText("getInstantFPS" + frameCounter.getInstantFPS(), 20, 660);
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
