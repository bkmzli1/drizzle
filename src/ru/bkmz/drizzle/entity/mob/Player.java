package ru.bkmz.drizzle.entity.mob;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import ru.bkmz.drizzle.graphics.animation.AnimatedSprite;
import ru.bkmz.drizzle.graphics.animation.Step;
import ru.bkmz.drizzle.input.Keyboard;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.level.player.PlayerProperties;
import ru.bkmz.drizzle.util.Commons;
import ru.bkmz.drizzle.util.ImageLoader;

import java.util.Objects;

public class Player extends Mob {

    private static final double WIDTH = 47;
    private static final double HEIGHT = 65;
    private static final double SPEED_X_LIMIT = 10;
    private static final double SPEED_Y_LIMIT = -10;
    private static final double SPEED_X_INCREMENT = 0.5;
    private static final double SPEED_X_INCREMENT2 = 0.6;
    private static final double SPEED_Y_INCREMENT = 0.5;
    private static final Image IMAGE = ImageLoader.INSTANCE.getImage("entity/player3");
    private static final int IMAGE_ROWS = 2;
    private static final int IMAGE_COLS = 4;
    private static final double SPRITE_X_OFFSET = -4;
    private static final double SPRITE_Y_OFFSET = -1;
    private static final int ANIMATION_DELTA = 8;

    public static double getWIDTH() {
        return WIDTH;
    }

    private static final Step ANIMATION_STEPS[] = { new Step(0, 0), new Step(1, 0), new Step(1, 1),
            new Step(1, 2), new Step(1, 3) };

    private final Keyboard keyboard;
    private final double speed;

    private boolean jump = true;

    public Player(double x, double y, Level level, Keyboard keyboard, PlayerProperties properties) {
        super(x, y, WIDTH, HEIGHT,
                new AnimatedSprite(IMAGE, IMAGE_ROWS, IMAGE_COLS, ANIMATION_DELTA, ANIMATION_STEPS),
                SPRITE_X_OFFSET, SPRITE_Y_OFFSET, level);

        this.keyboard = Objects.requireNonNull(keyboard);
        this.speed = (GameData.UPGRADE_MOVEMENT.getValue() > 0) ? SPEED_X_INCREMENT2 :
                SPEED_X_INCREMENT;
    }

    @Override
    public void tick() {

        if ((this.keyboard.isHeld(KeyCode.D) && !this.keyboard.isHeld(KeyCode.A))) {
            this.dx = Math.min(this.dx + this.speed, SPEED_X_LIMIT);
        }

        if (this.keyboard.isHeld(KeyCode.A) && !this.keyboard.isHeld(KeyCode.D)) {
            this.dx = Math.max(this.dx - this.speed, -SPEED_X_LIMIT);
        }

        if (!this.keyboard.isHeld(KeyCode.A) && !this.keyboard.isHeld(KeyCode.D)) {
            if (this.dx > 0) {
                this.dx = Math.max(this.dx - this.speed, 0);
            } else if (this.dx < 0) {
                this.dx = Math.min(this.dx + this.speed, 0);
            }
        }

        if (this.keyboard.isHeld(KeyCode.SPACE)||this.keyboard.isHeld(KeyCode.W)) {
            if (!this.jump) {
                this.jump = true;
                this.dy = SPEED_Y_LIMIT;

                GameData.STAT_COUNT_JUMP.increment();
            }
        }

        if (this.keyboard.isPressed(KeyCode.F)) {
            level.getPlayerProperties().activateSkill(this, level);
        }

        this.x += this.dx;
        this.y += this.dy;

        if (this.jump) {
            this.dy += SPEED_Y_INCREMENT;
        }

        if (this.x < 0) {
            this.x = 0;
            this.dx = 0;
        } else if (this.x + this.width > Commons.SCENE_WIDTH) {
            this.x = Commons.SCENE_WIDTH - this.width;
            this.dx = 0;
        }

        if (this.y < 0) {
            this.y = 0;
            this.dy = 0;
        } else if (this.y + this.height > Commons.SCENE_GROUND) {
            this.y = Commons.SCENE_GROUND - this.height;
            this.dy = 0;

            this.jump = false;
        }

        if (this.dx != 0) {
            this.sprite.setScale((this.dx > 0 ? 1 : -1), 1);
            ((AnimatedSprite) this.sprite).play();
        } else {
            ((AnimatedSprite) this.sprite).stop();
        }

        ((AnimatedSprite) this.sprite).tick();
    }

}
