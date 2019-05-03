package ru.bkmz.drizzle.entity.spawner;

import ru.bkmz.drizzle.entity.particle.RainParticle;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.entity.Entity;

public final class RainSpawner extends Spawner {

    private static final int WIDTH = 1;
    private static final int HEIGHT_MIN = 10;
    private static final int HEIGHT_MAX = 40;
    private static final int SPEED_X = 0;
    private static final int SPEED_Y_MIN = 10;
    private static final int SPEED_Y_MAX = 40;

    public RainSpawner(double x, double y, double width, double height, Level level, int rate,
                       int variation, int count) {
        super(x, y, width, height, level, rate, variation, count);
    }

    @Override
    public void spawn() {
        this.level.add(new RainParticle(getRandomX(), getRandomY(), WIDTH,
                                        HEIGHT_MIN + Entity.RANDOM.nextInt(HEIGHT_MAX - HEIGHT_MIN + 1),
                                        SPEED_X,
                                        SPEED_Y_MIN + Entity.RANDOM.nextInt(SPEED_Y_MAX - SPEED_Y_MIN + 1),
                                        this.level));
    }
}
