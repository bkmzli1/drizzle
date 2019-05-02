/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package ru.bkmz.drizzle.entity.mob;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ru.bkmz.drizzle.entity.Entity;
import ru.bkmz.drizzle.entity.particle.AcidParticle;
import ru.bkmz.drizzle.experimental.SetingsPane;
import ru.bkmz.drizzle.graphics.animation.AnimatedSprite;
import ru.bkmz.drizzle.graphics.animation.Step;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.util.Commons;
import ru.bkmz.drizzle.util.ImageLoader;
import ru.bkmz.drizzle.util.MediaLoader;

import java.io.File;

import static ru.bkmz.drizzle.level.GameData.ACID_Volume;

public class Acid extends Mob {

    private static final double WIDTH = 10;
    private static final double HEIGHT = 10;
    private static final double SPEED_X_DEFAULT = 0;
    private static final double SPEED_Y_DEFAULT = 10;
    private static final Image IMAGE = ImageLoader.INSTANCE.getImage("entity/acid");
    private static final String MEDIA = MediaLoader.INSTANCE.getMedia("res/media/a_drop");
    private static final int IMAGE_ROWS = 1;
    private static final int IMAGE_COLS = 4;
    private static final double SPRITE_X_OFFSET = -1;
    private static final double SPRITE_Y_OFFSET = -14;
    private static final int ANIMATION_DELTA = 10;
    private static final Step[] ANIMATION_STEPS = {new Step(0, 0), new Step(0, 1), new Step(0, 2),
            new Step(0, 3), new Step(0, 0)};
    private static final int PARTICLE_COUNT = 5;


    private Acid(double x, double y, double dx, double dy, Level level) {

        super(x, y, WIDTH, HEIGHT,
                new AnimatedSprite(IMAGE, IMAGE_ROWS, IMAGE_COLS, ANIMATION_DELTA, ANIMATION_STEPS),
                SPRITE_X_OFFSET, SPRITE_Y_OFFSET, level);
        ((AnimatedSprite) this.sprite).play();
        oracleVid.setVolume(ACID_Volume.getValue());
        this.dx = dx;
        this.dy = dy;
    }

    public Acid(double x, double y, Level level) {
        this(x, y, SPEED_X_DEFAULT, SPEED_Y_DEFAULT, level);
    }

    @Override
    public final void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.y + this.height >= Commons.SCENE_GROUND) {
            this.y = Commons.SCENE_GROUND - this.height;

            oracleVid.play();
            kill();
            spawnParticles(0);
        }

        if (this.level.isCollidingPlayerAABB(this)) {
            this.level.getPlayerProperties().damage();

            this.y -= this.height;

            if (this.level.getPlayerProperties().getHealth() > 0) {
                spawnParticles(-1);
            }

            kill();
        }

        ((AnimatedSprite) this.sprite).tick();
    }
    private  double get_Volume(){
        return oracleVid.getVolume();
    }
    private void spawnParticles(double ySpeed) {
        Platform.runLater(() -> {
            for (int i = 0; i < Acid.PARTICLE_COUNT; i++) {
                double particleSize = Entity.RANDOM.nextInt(5) + 1;
                double particleXSpeed = Entity.RANDOM.nextInt(5) - 2.5;
                this.level.add(new AcidParticle(this.x, this.y + this.height - particleSize,
                        particleSize, particleSize, particleXSpeed, ySpeed,
                        this.level));
            }
        });
    }
    public static final MediaPlayer oracleVid = new MediaPlayer(
            new Media(new File(MEDIA).toURI().toString())
    );
}
