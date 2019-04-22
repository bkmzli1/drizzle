/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package ru.bkmz.drizzle.entity.particle;

import ru.bkmz.drizzle.entity.Entity;
import ru.bkmz.drizzle.entity.mob.Acid;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.util.Commons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class ShockParticle extends Particle {

    private static final double ARC_RADIUS_INCREMENT = 8;
    private static final double ARC_ANGLE = 180;

    private double radius = 0;

    public ShockParticle(double x, double y, double width, double height, Level level) {
        super(x, y, width, height, level);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.ALICEBLUE);
        gc.strokeArc(this.x - this.radius, this.y - this.radius, this.radius * 2, this.radius * 2,
                     0, ARC_ANGLE, ArcType.OPEN);
    }

    @Override
    public void tick() {
        this.radius += ARC_RADIUS_INCREMENT;
        if (this.radius > Commons.SCENE_WIDTH) {
            kill();
        }

        for (Entity m : this.level.getMobs()) {
            if (m instanceof Acid) {
                if (m.getDistance(this.x, this.y) <= this.radius) {
                    m.kill();
                }
            }
        }
    }

}
