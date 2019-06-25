package ru.bkmz.drizzle.graphics;

import java.util.Objects;

import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.player.PlayerProperties;
import ru.bkmz.drizzle.level.player.Skill;
import ru.bkmz.drizzle.util.ImageLoader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Overlay implements Drawable {

    private static Sprite HLT_BAR = new Sprite(ImageLoader.IMAGE_LOADER
            .getImage("gui/bars/health10"), 1, 1);
    private static Sprite ARM_BAR = new Sprite(ImageLoader.IMAGE_LOADER
            .getImage("gui/bars/armor10"), 1, 1);
    private static final Sprite EXP_BAR = new Sprite(ImageLoader.IMAGE_LOADER
            .getImage("gui/bars/experience"), 1, 100);
    private static final Sprite PWR_BAR = new Sprite(ImageLoader.IMAGE_LOADER
            .getImage("gui/bars/energy"), 2, 100);

    private static final Sprite ABL_ICO = new Sprite(ImageLoader.IMAGE_LOADER
            .getImage("gui/icons/ability"), 1, 4);
    private static final Sprite HLC_ICO = new Sprite(ImageLoader.IMAGE_LOADER
            .getImage("gui/icons/health"), 1, 1);
    private static final Sprite EXP_ICO = new Sprite(ImageLoader.IMAGE_LOADER
            .getImage("gui/icons/experience"), 1, 1);
    private static final Sprite PWR_ICO = new Sprite(ImageLoader.IMAGE_LOADER
            .getImage("gui/icons/energy"), 1, 1);

    private static final Sprite SQ_FRAME = new Sprite(ImageLoader.IMAGE_LOADER
            .getImage("gui/icons/frame"), 1, 1);
    private static final Sprite RC_FRAME = new Sprite(ImageLoader.IMAGE_LOADER
            .getImage("gui/bars/frame"), 1, 1);

    private final double x;
    private final double y;

    private Skill skill;
    private int level;
    private Double health;

    public Overlay(double x, double y, PlayerProperties properties) {
        this.x = x;
        this.y = y;
        this.skill = properties.getSelectedSkill();

        level = properties.getLevelProperty().intValue();
        health = properties.getHealth();
        HLT_BAR.setTileSpan(1, GameData.PLAYER_HEALTH.getValue());
        ARM_BAR.setTileSpan(1, 0);
        EXP_BAR.setTileSpan(1, 0);
        PWR_BAR.setTileSpan(1, 0);
        HLT_BAR = new Sprite(ImageLoader.IMAGE_LOADER
                .getImage("gui/bars/health"+GameData.PLAYER_HEALTH.getValue() ), 1, 1);
        if (Objects.nonNull(skill)) {
            switch (skill) {
                case SHORTWAVE:
                    ABL_ICO.selectTile(0, 0);
                    break;
                case SHIELD_SPAWN:
                    ABL_ICO.selectTile(0, 1);
                    break;
                case EXPERIENCE_BOOST:
                    ABL_ICO.selectTile(0, 2);
                    break;
            }
        }

        properties.getLevelProperty().addListener((Observable, OldValue, NewValue) -> {
            level = NewValue.intValue();

        });

        properties.getHealthProperty().addListener((Observable, OldValue, NewValue) -> {
            if (NewValue.intValue()!=0) {
                HLT_BAR = new Sprite(ImageLoader.IMAGE_LOADER
                        .getImage("gui/bars/health" + NewValue.intValue()), 1, 1);
            }else {
                HLT_BAR.setTileSpan(1, 0);
            }
            health = NewValue.doubleValue();
            //HLT_BAR.setTileSpan(1, NewValue.intValue());
        });

        properties.getArmorProperty().addListener((Observable, OldValue, NewValue) -> {

            if (NewValue.intValue()!=0) {
                ARM_BAR = new Sprite(ImageLoader.IMAGE_LOADER
                        .getImage("gui/bars/armor" + NewValue.intValue()), 1, 1);
            }else {
                ARM_BAR.setTileSpan(1, 0);
            }

        });

        properties.getExperienceProperty().addListener((Observable, OldValue, NewValue) -> {
            EXP_BAR.setTileSpan(1, NewValue.doubleValue());
        });

        properties.getEnergyProperty().addListener((Observable, OldValue, NewValue) -> {
            PWR_BAR.setTileSpan(1, NewValue.intValue());

            if (properties.isSkillActive() || NewValue.intValue() >= 100) {
                PWR_BAR.selectTile(1, 0);
            } else {
                PWR_BAR.selectTile(0, 0);
            }
        });
    }

    @Override
    public void draw(GraphicsContext gc) {
        drawStatic(gc);

        HLT_BAR.draw(gc, x + 20, y + 10);
        ARM_BAR.draw(gc, x + 20, y + 10);
        EXP_BAR.draw(gc, x + 20, y + 30);
        PWR_BAR.draw(gc, x + 20, y + 50);

        gc.setFill(Color.YELLOW);
        gc.fillText("" + level, x + 230, y + 42);
        gc.setFill(Color.RED);
        gc.fillText("" + (int)health.doubleValue(), x + 230, y + 21);
    }

    private void drawStatic(GraphicsContext gc) {
        HLC_ICO.draw(gc, x + 2, y + 10);
        EXP_ICO.draw(gc, x + 2, y + 30);

        RC_FRAME.draw(gc, x + 20, y + 10);
        RC_FRAME.draw(gc, x + 20, y + 30);

        if (Objects.nonNull(skill)) {
            PWR_ICO.draw(gc, x + 2, y + 50);
            RC_FRAME.draw(gc, x + 20, y + 50);

            SQ_FRAME.draw(gc, x + 20, y + 70);
            ABL_ICO.draw(gc, x + 22, y + 72);
        }
    }

}
