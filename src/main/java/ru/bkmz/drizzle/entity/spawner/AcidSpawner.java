package ru.bkmz.drizzle.entity.spawner;

import ru.bkmz.drizzle.entity.mob.Acid;
import ru.bkmz.drizzle.level.Level;

public class AcidSpawner extends Spawner {

    public AcidSpawner(double x, double y, double width, double height, Level level, int rate,
                       int variation, int count) {
        super(x, y, width, height, level, rate, variation, count);
    }

    @Override
    public void spawn() {
        this.level.add(new Acid(getRandomX(), getRandomY(), this.level));
    }

}
