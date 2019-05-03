package ru.bkmz.drizzle.entity.spawner;

import ru.bkmz.drizzle.entity.item.Energy;
import ru.bkmz.drizzle.level.Level;

public class EnergySpawner extends Spawner {

    public EnergySpawner(double x, double y, double width, double height, Level level, int rate,
                         int variation, int count) {
        super(x, y, width, height, level, rate, variation, count);
    }

    @Override
    public void spawn() {
        this.level.add(new Energy(getRandomX(), getRandomY(), this.level));
    }

}
