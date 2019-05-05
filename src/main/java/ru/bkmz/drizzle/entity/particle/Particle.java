package ru.bkmz.drizzle.entity.particle;

import ru.bkmz.drizzle.entity.Entity;
import ru.bkmz.drizzle.level.Level;

public abstract class Particle extends Entity {

    Particle(double x, double y, double width, double height, Level level) {
        super(x, y, width, height, level);
    }

    @Override
    public abstract void tick();

}
