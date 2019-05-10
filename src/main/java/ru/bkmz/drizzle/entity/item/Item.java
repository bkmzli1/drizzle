package ru.bkmz.drizzle.entity.item;

import ru.bkmz.drizzle.entity.Entity;
import ru.bkmz.drizzle.graphics.Sprite;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.util.Commons;

public abstract class Item extends Entity {

    private static final double DEFAULT_SPEED_X = 0;
    private static final double DEFAULT_SPEED_Y = 4;

    protected Item(double x, double y, double width, double height, Sprite sprite, double offsetX,
                   double offsetY, double dx, double dy, Level level) {
        super(x, y, width, height, sprite, offsetX, offsetY, level);

        this.dx = dx;
        this.dy = dy;
    }

    protected Item(double x, double y, double width, double height, Sprite sprite, double offsetX,
                   double offsetY, Level level) {
        this(x, y, width, height, sprite, offsetX, offsetY, DEFAULT_SPEED_X+ GameData.PLAYER_LEVEL.getValue()/2f, DEFAULT_SPEED_Y,
                level);
    }

    @Override
    public final void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.y + this.height > Commons.SCENE_GROUND) {
            kill();
        }

        if (this.level.isCollidingPlayerAABB(this)) {
            applyEffect();
            kill();
        }
    }

    public abstract void applyEffect();

}
