package ru.bkmz.drizzle.entity.particle;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.util.Commons;

public class RainParticle extends Particle {

    private static final Color COLOR_1 = Color.rgb(100, 149, 237, 0.2);
    private static final Color COLOR_2 = Color.rgb(173, 216, 230, 0.2);

    private final Color color;

    public RainParticle(double x, double y, double width, double height, double dx, double dy,
                        Level level) {
        super(x, y, width, height, level);

        this.dx = dx;
        this.dy = dy;

        this.color = RANDOM.nextBoolean() ? COLOR_1 : COLOR_2;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillRect(this.x, this.y, this.width,
                this.y + this.height > Commons.SCENE_GROUND ? this.y - Commons.SCENE_GROUND :
                        this.height);
    }

    @Override
    public void tick() {
        this.x += this.dx + GameData.PLAYER_LEVEL.getValue() / 10f;
        this.y += this.dy;

        if (Objects.nonNull(this.level.getPlayer())) {
            if (this.level.getPlayer().isCollidingAABB(this)) {
                kill();
            }
        }

        if (this.y > Commons.SCENE_GROUND) {
            kill();
        }
    }

}
