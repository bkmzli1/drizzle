package ru.bkmz.drizzle.entity.item;

import ru.bkmz.drizzle.graphics.Sprite;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.util.ImageLoader;

import javafx.scene.image.Image;

public class Shield extends Item {

    private static final double WIDTH = 20;
    private static final double HEIGHT = 30;
    private static final Image IMAGE = ImageLoader.INSTANCE.getImage("entity/armor");
    private static final int IMAGE_ROWS = 1;
    private static final int IMAGE_COLS = 1;
    private static final double SPRITE_X_OFFSET = 0;
    private static final double SPRITE_Y_OFFSET = 0;

    public Shield(double x, double y, Level level) {
        super(x, y, WIDTH, HEIGHT, new Sprite(IMAGE, IMAGE_ROWS, IMAGE_COLS), SPRITE_X_OFFSET,
                SPRITE_Y_OFFSET, level);
    }

    @Override
    public void applyEffect() {
        this.level.getPlayerProperties().addShield();
    }
}
