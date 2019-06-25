package ru.bkmz.drizzle.entity.spawner;

import ru.bkmz.drizzle.Application;
import ru.bkmz.drizzle.entity.Entity;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;

import java.util.Objects;

public abstract class SpawnerOne extends Entity {

    protected final int rate;
    protected final int variation;
    protected final int count;

    protected int frameCount = 0;
    protected int frameLimit;

    protected SpawnerOne(double x, double y, double width, double height, Level level, int rate,
                         int variation, int count) {
        super(x, y, width, height, level);

        this.count = count;

        this.variation = variation;
        this.rate = rate;

        this.frameLimit = rate;
    }
    protected SpawnerOne(double x, double y, double width, double height, Level level, int rate,
                         int variation, GameData count, int power) {
        super(x, y, width, height, level);

        this.count = (count.getValue()/100)*power;

        this.variation = variation;
        this.rate = rate;

        this.frameLimit = rate;
    }
    @Override
    public final void tick() {
        if (this.frameCount++ >= this.frameLimit) {
            this.frameCount = 0;
            this.frameLimit = this.rate + (this.variation > 1 ? RANDOM.nextInt(this.variation) : 0);

            if (Objects.nonNull(this.level)) {
                for (int i = 0; i < this.count; i++) {

                    spawn();
                }
            }
        }
    }

    double getRandomX() {
        return Application.xMuse;
    }

    double getRandomY() {
        return Application.yMuse;
    }

    public abstract void spawn();


}
