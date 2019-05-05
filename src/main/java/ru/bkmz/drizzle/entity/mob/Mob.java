package ru.bkmz.drizzle.entity.mob;

import java.util.Objects;

import ru.bkmz.drizzle.entity.Entity;
import ru.bkmz.drizzle.graphics.Sprite;
import ru.bkmz.drizzle.level.Level;

public abstract class Mob extends Entity {

    Mob(double x, double y, double width, double height, Sprite sprite, double offsetX,
        double offsetY, Level level) {
        super(x, y, width, height, Objects.requireNonNull(sprite), offsetX, offsetY, level);
    }

}
