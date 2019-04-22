/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package ru.bkmz.drizzle.level.player;

import ru.bkmz.drizzle.entity.particle.ShockParticle;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.level.Timescale;
import ru.bkmz.drizzle.util.Commons;
import ru.bkmz.drizzle.util.QuadConsumer;

public enum Skill {

    SHOCKWAVE((X, Y, L, PP) -> L.add(new ShockParticle(X, Commons.SCENE_GROUND, 0, 0, L)), 2 *
            Timescale.TICKS_PER_SECOND, 5),

    SHIELD_SPAWN((X, Y, L, PP) -> PP.addShield(), Timescale.TICKS_PER_SECOND >> 1, 3),

    EXPERIENCE_BOOST((X, Y, L, PP) -> PP
            .setTemporaryExperienceBoost(2,
                                         Timescale.TICKS_PER_MINUTE), Timescale.TICKS_PER_MINUTE, 1);

    private final QuadConsumer<Double, Double, Level, PlayerProperties> effect;
    private final int duration;
    private final int rateMultiplier;

    private Skill(QuadConsumer<Double, Double, Level, PlayerProperties> consumer, int duration,
                  int rateMultiplier) {
        this.effect = consumer;
        this.duration = duration;
        this.rateMultiplier = rateMultiplier;
    }

    public void applyEffect(double x, double y, Level level, PlayerProperties properties) {
        effect.accept(x, y, level, properties);
    }

    public int getDuration() {
        return duration;
    }

    public int getRateMultiplier() {
        return rateMultiplier;
    }

}
