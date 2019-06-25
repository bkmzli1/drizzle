package ru.bkmz.drizzle.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.bkmz.drizzle.Application;
import ru.bkmz.drizzle.graphics.Drawable;
import ru.bkmz.drizzle.graphics.Sprite;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;

import java.util.Objects;
import java.util.Random;

public abstract class Entity implements Drawable {

    protected static final Random RANDOM = new Random();

    protected static Level level;
    protected final Sprite sprite;
    protected final double width;
    protected final double height;
    private final double spriteXOffset;
    private final double spriteYOffset;

    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    private boolean dead;

    protected Entity(double x, double y, double width, double height, Level level) {
        this(x, y, width, height, null, 0, 0, level);
    }
    protected Entity(double x, double y, double width, double height, Sprite sprite, double offsetX,
                     double offsetY, Level level) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.sprite = sprite;
        this.spriteXOffset = offsetX;
        this.spriteYOffset = offsetY;

        Entity.level = level;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (Objects.nonNull(this.sprite)) {
            this.sprite.draw(gc, this.x + this.spriteXOffset, this.y + this.spriteYOffset);
        }

        if (Application.DEBUG_MODE && GameData.BAG.getValue() != 1) {
            gc.setStroke(Color.WHITE);
            gc.strokeRect(this.x, this.y, this.width, this.height);

            gc.setFill(Color.WHITE);
            gc.fillText(this.getClass().getSimpleName() + "\n[" + this.x + " " + this.y + "]\n" +
                    this.dx + " " + this.dy, this.x + this.width, this.y);
        }
    }

    private double getCenterX() {
        return this.x + this.width / 2;
    }

    private double getCenterY() {
        return this.y + this.height / 2;
    }

    public final double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(x - getCenterX(), 2) + Math.pow(y - getCenterY(), 2));
    }

    public final double getHeight() {
        return this.height;
    }

    public final double getWidth() {
        return this.width;
    }

    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }

    public final boolean isCollidingAABB(Entity entity) {
        final boolean a = entity.x + entity.width > this.x;
        final boolean b = this.x + this.width > entity.x;
        final boolean c = entity.y + entity.height > this.y;
        final boolean d = this.y + this.height > entity.y;
        return a && b && c && d;
    }

    public final boolean isDead() {
        return this.dead;
    }

    public final void kill() {
        this.dead = true;
    }

    public abstract void tick();

}
