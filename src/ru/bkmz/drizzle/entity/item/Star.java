/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package ru.bkmz.drizzle.entity.item;

import ru.bkmz.drizzle.graphics.Sprite;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.util.ImageLoader;

import javafx.scene.image.Image;

public class Star extends Item {

    private static final double WIDTH = 7;
    private static final double HEIGHT = 7;
    private static final Image IMAGE = ImageLoader.INSTANCE.getImage("entity/star");
    private static final int IMAGE_ROWS = 1;
    private static final int IMAGE_COLS = 1;
    private static final double SPRITE_X_OFFSET = -4;
    private static final double SPRITE_Y_OFFSET = -4;

    public Star(double x, double y, Level level) {
        super(x, y, WIDTH, HEIGHT, new Sprite(IMAGE, IMAGE_ROWS, IMAGE_COLS), SPRITE_X_OFFSET,
                SPRITE_Y_OFFSET, level);
    }

    @Override
    public void applyEffect() {
        int selector = RANDOM.nextInt(3);

        switch (selector) {
            case 0:
                this.level.getPlayerProperties().addEnergy(5);
                break;
            case 1:
                this.level.getPlayerProperties().addShield();
                break;
            case 2:
                this.level.getPlayerProperties().addExperience(10 * 60);
                break;
        }

    }
}
