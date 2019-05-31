package ru.bkmz.drizzle.entity.item;

import javafx.scene.image.Image;
import ru.bkmz.drizzle.experimental.SoundEffects;
import ru.bkmz.drizzle.graphics.Sprite;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.util.ImageLoader;

public class Energy extends Item {

    private static final double WIDTH = 7;
    private static final double HEIGHT = 7;
    private static final Image IMAGE = ImageLoader.INSTANCE.getImage("entity/energy");

    private static final int IMAGE_ROWS = 1;
    private static final int IMAGE_COLS = 1;
    private static final double SPRITE_X_OFFSET = -4;
    private static final double SPRITE_Y_OFFSET = -4;

    public Energy(double x, double y, Level level) {
        super(x, y, WIDTH, HEIGHT, new Sprite(IMAGE, IMAGE_ROWS, IMAGE_COLS), SPRITE_X_OFFSET,
                SPRITE_Y_OFFSET, level);
    }

    @Override
    public void applyEffect() {
        this.level.getPlayerProperties().addEnergy(1);
        SoundEffects.playNew("electric.wav");
    }
}
