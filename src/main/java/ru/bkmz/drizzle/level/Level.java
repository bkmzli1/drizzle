package ru.bkmz.drizzle.level;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ru.bkmz.drizzle.Application;
import ru.bkmz.drizzle.entity.Entity;
import ru.bkmz.drizzle.entity.item.Item;
import ru.bkmz.drizzle.entity.mob.Mob;
import ru.bkmz.drizzle.entity.mob.Player;
import ru.bkmz.drizzle.entity.particle.Particle;
import ru.bkmz.drizzle.entity.particle.RainParticle;
import ru.bkmz.drizzle.entity.spawner.*;
import ru.bkmz.drizzle.graphics.Overlay;
import ru.bkmz.drizzle.input.Keyboard;
import ru.bkmz.drizzle.level.player.PlayerProperties;
import ru.bkmz.drizzle.util.Commons;
import ru.bkmz.drizzle.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.bkmz.drizzle.level.GameData.*;

public class Level {

    private final List<Entity> mobs = new ArrayList<>();
    private final List<Entity> particles = new ArrayList<>();
    private final List<Spawner> spawners = new ArrayList<>();
    private final Image background = ImageLoader.INSTANCE.getImage("background/background");
    private final Keyboard keyboard;
    private final LevelController levelController = new LevelController(this);

    private PlayerProperties properties;
    private Overlay overlay;
    private boolean paused = false;
    private boolean played = false;

    public class LevelController {

        private final Level level;

        private boolean inScope = false;
        private boolean hasClosedFlag = false;

        private LevelController(Level level) {
            this.level = level;
        }

        public void endGame() {
            if (this.inScope) {
                this.inScope = false;
                this.level.stop();
            }
        }

        public boolean inScope() {
            return this.inScope;
        }

        public boolean isClosed() {
            if (this.hasClosedFlag) {
                this.inScope = false;
                this.hasClosedFlag = false;
                return true;
            } else {
                return false;
            }
        }

        public boolean isPaused() {
            return this.inScope && this.level.paused;
        }

        public boolean isRunning() {
            return this.inScope && this.level.played;
        }

        public void pauseGame() {
            if (this.inScope) {
                this.level.paused = true;
            }
        }

        public void startGame() {
            if (!this.inScope) {
                this.inScope = true;
                this.level.start();
            }
        }

        public void unpauseGame() {
            if (this.inScope) {
                this.level.paused = false;
            }
        }

        public void update(GraphicsContext gc) {
            this.level.tick();
            this.level.draw(gc);
        }


    }

    public Level(Keyboard keyboard) {
        this.keyboard = keyboard;

        this.spawners.add(new RainSpawner(0, -20, Commons.SCENE_WIDTH, 0, this, 0, 0, 5));
        this.spawners.add(new RainSpawner(-1000, -20, Commons.SCENE_WIDTH, 0, this, 0, 0, 5));
        this.spawners.add(new RainSpawner(-2000, -20, Commons.SCENE_WIDTH, 0, this, 0, 0, 5));
    }

    public void add(Entity e) {
        if (e instanceof Mob || e instanceof Item) {
            this.mobs.add(e);
        } else if (e instanceof Particle) {
            this.particles.add(e);
        } else if (e instanceof Spawner) {
            this.spawners.add((Spawner) e);
        }
    }

    private void draw(GraphicsContext gc) {
        gc.drawImage(this.background, 0, 0, Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);

        for (Entity p : this.particles) {
            p.draw(gc);
        }

        for (Entity m : this.mobs) {
            m.draw(gc);
        }

        if (Objects.nonNull(this.overlay)) {
            this.overlay.draw(gc);
        }

        if (Application.DEBUG_MODE) {
            gc.setFill(Color.WHITE);
            gc.fillText("Spawners\t\t: " + this.spawners.size(), 20, 150);
            gc.fillText("Mobs\t\t: " + this.mobs.size(), 20, 165);
            gc.fillText("Particles\t\t: " + this.particles.size(), 20, 180);

            gc.fillText("isPlayed\t\t: " + this.played, 20, 210);
            gc.fillText("isPaused\t\t: " + this.paused, 20, 225);

            gc.fillText("isPlayer\t\t: " + Objects.nonNull(getPlayer()), 20, 255);
            if (Objects.nonNull(this.properties)) {
                gc.fillText("Health\t\t: " + this.properties.getHealth(), 20, 270);
                gc.fillText("Shield\t\t: " + this.properties.getArmorProperty().intValue(), 20,
                            285);
                gc.fillText("Energy\t\t: " + this.properties.getEnergyProperty().intValue(), 20,
                            300);
                gc.fillText("Level\t\t: " + this.properties.getLevelProperty().intValue(), 20, 315);
                gc.fillText("Experience\t: " + this.properties.getExperienceProperty().intValue(),
                            20, 330);
            }
        }
    }

    public LevelController getLevelController() {
        return this.levelController;
    }

    public List<Entity> getMobs() {
        return mobs.subList(1, mobs.size());
    }

    public Player getPlayer() {
        return (this.mobs.size() > 0) ? (Player) this.mobs.get(0) : null;
    }

    public PlayerProperties getPlayerProperties() {
        return this.properties;
    }

    public boolean isCollidingPlayerAABB(Entity entity) {
        return Objects.nonNull(getPlayer()) && getPlayer().isCollidingAABB(entity);
    }

    private void tick() {
        if (!this.paused) {
            for (Spawner s : this.spawners) {
                s.tick();
            }

            for (Entity m : this.mobs) {
                m.tick();
            }

            for (Entity p : this.particles) {
                p.tick();
            }

            if (Objects.nonNull(this.properties)) {
                this.properties.tick();
            }


            this.mobs.removeIf(Entity::isDead);
            this.particles.removeIf(Entity::isDead);
            this.spawners.removeIf(Entity::isDead);
        }

    }

    private void start() {
        this.paused = false;
        this.played = true;

        this.properties = new PlayerProperties();
        this.overlay = new Overlay(0, 20, this.properties);

        this.mobs.add(new Player((Commons.SCENE_WIDTH - Player.getWIDTH()) / 2, Commons.SCENE_GROUND,
                                 this, this.keyboard, this.properties));

        this.spawners.add(new AcidSpawner(0, -50, Commons.SCENE_WIDTH, 0, this, AcidSpawner_rate.getValue(), AcidSpawner_variation.getValue(), AcidSpawner_count.getValue()));
        this.spawners.add(new AcidSpawner(-1000, -50, Commons.SCENE_WIDTH, 0, this, AcidSpawner_rate.getValue(), AcidSpawner_variation.getValue(), AcidSpawner_count.getValue()));
        this.spawners.add(new AcidSpawner(-2000, -50, Commons.SCENE_WIDTH, 0, this, AcidSpawner_rate.getValue(), AcidSpawner_variation.getValue(), AcidSpawner_count.getValue()));
        this.spawners.add(new ArmorSpawner(0, -50, Commons.SCENE_WIDTH, 0, this,
                                           Timescale.TICKS_PER_MINUTE >> 1,
                                           10 * Timescale.TICKS_PER_SECOND, 1));
        this.spawners
                .add(new EnergySpawner(0, -50, Commons.SCENE_WIDTH, 0, this,
                                       Timescale.TICKS_PER_SECOND, Timescale.TICKS_PER_SECOND, 1));
        this.spawners.add(new StarSpawner(0, -50, Commons.SCENE_WIDTH, 0, this,
                                          20 * Timescale.TICKS_PER_SECOND, 0, 1));

        this.properties.getHealthProperty().addListener((Observable, OldValue, NewValue) -> {
            if (NewValue.intValue() <= 0) {
                Platform.runLater(() -> {
                    GameData.save();
                    stop();
                });
            }
        });
    }

    private void stop() {
        this.paused = false;
        this.played = false;

        this.levelController.hasClosedFlag = true;

        this.properties = null;
        this.overlay = null;

        this.mobs.clear();
        this.spawners.subList(1, spawners.size()).clear();
        this.particles.removeIf(E -> !(E instanceof RainParticle));
    }
}
