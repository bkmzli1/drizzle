package ru.bkmz.drizzle.level.player;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import ru.bkmz.drizzle.entity.Entity;
import ru.bkmz.drizzle.experimental.SoundEffects;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;



import java.util.Objects;

public class PlayerProperties {

    private static final int EXP_RATE = 1;
    private static final int EXP_POOL = 40 * 60;
    private static final double EXP_POOL_MOD = 1.2;

    private static boolean godmod = false;
    private final SimpleDoubleProperty healthProperty = new SimpleDoubleProperty(0.0001);
    private final SimpleDoubleProperty armorProperty = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty levelProperty = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty experienceProperty = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty energyProperty = new SimpleDoubleProperty(0);
    private final int energyRate;

    private boolean skillActive = false;
    private boolean boostActive = false;
    private int skillBurnout = 0;
    private int boostBurnout = 0;
    private int experience = 0;
    private int experienceMultiplier = EXP_RATE;
    private Skill selectedSkill = null;



    public void setGodmod() {
        godmod = !godmod;
        System.out.println(godmod);
    }

    public static boolean isGodmod() {
        return godmod;
    }

    public PlayerProperties() {
        healthProperty.set(GameData.PLAYER_HEALTH.getValue()+0.0000000000004565414654165665);

        levelProperty.set((int)GameData.PLAYER_LEVEL.getValue());

        int ord =(int) GameData.PLAYER_SELECTEDSKILL.getValue();
        selectedSkill = (ord > 0) ?
                ((ord - 1 < Skill.values().length) ? Skill.values()[ord - 1] : null) :
                null;

        energyRate =(int) (Objects.isNull(selectedSkill) ? 0 :
                selectedSkill.getRateMultiplier() * GameData.UPGRADE_POWERRATE.getValue());
    }

    public void activateSkill(Entity source, Level level) {
        if (!skillActive) {
            if (Objects.nonNull(selectedSkill)) {
                if (energyProperty.intValue() >= 100) {
                    energyProperty.set(100);

                    skillActive = true;
                    skillBurnout = selectedSkill.getDuration();
                    selectedSkill.applyEffect(source.getX() + source.getWidth() / 2,
                            source.getY() + source.getHeight() / 2, level, this);

                    GameData.STAT_COUNT_SKILLACTIVATION.increment();
                }
            }
        }
    }

    public void addEnergy(int multiplier) {

        GameData.STAT_COUNT_NODES.increment();
        if (!skillActive) {
            if (Objects.nonNull(selectedSkill)) {
                if (energyProperty.intValue() < 100) {
                    energyProperty.set(energyProperty.intValue() + energyRate * multiplier);
                    if (energyProperty.intValue() > 100) {
                        energyProperty.setValue(100);
                    }
                }
            }
        }
    }

    public void addExperience(int multiplier) {

        GameData.STAT_COUNT_EXPERIENCE.incrementBy(experienceMultiplier * multiplier);

        experience += experienceMultiplier * multiplier;
        experienceProperty.set((100 * experience /
                (EXP_POOL * Math.pow(EXP_POOL_MOD, levelProperty.intValue()))));
        if (experience >= EXP_POOL * Math.pow(EXP_POOL_MOD, levelProperty.intValue())) {
            experience = 0;

            levelProperty.set(levelProperty.intValue() + 1);
            GameData.PLAYER_POINTS.increment();
            GameData.PLAYER_LEVEL.setVolume(levelProperty.intValue());
        }
    }

    public void addExperience() {
        addExperience(1);
    }

    public void addShield() {
        SoundEffects.playNew("Shield.wav");
        GameData.STAT_COUNT_SHIELD.increment();
        if (armorProperty.intValue() < healthProperty.intValue()) {
            armorProperty.set(armorProperty.intValue() + 1);
        }
    }

    public void addShield(int multiplier) {
        GameData.STAT_COUNT_SHIELD.increment();
        if (armorProperty.intValue() < healthProperty.intValue()) {
            armorProperty.set(armorProperty.intValue() + multiplier);
        }
    }

    public void damage() {
        if (!godmod) {
            GameData.STAT_COUNT_DAMAGE.increment();

            if (armorProperty.intValue() > 0) {
                armorProperty.set(armorProperty.intValue() - 1);
            } else if (healthProperty.intValue() > 0) {
                healthProperty.set(healthProperty.doubleValue() - 1);
            }
        }
    }

    public void add_damage(int multiplier) {
        GameData.STAT_COUNT_DAMAGE.increment();

        if (armorProperty.intValue() > 0) {
            armorProperty.set(armorProperty.intValue() - multiplier);
        } else if (healthProperty.intValue() > 0) {
            healthProperty.set(healthProperty.intValue() - multiplier);
        }
    }

    public void addhil(int multiplier) {
        GameData.STAT_COUNT_DAMAGE.increment();

        if (armorProperty.intValue() > 0) {
            armorProperty.set(armorProperty.intValue() + multiplier);
        } else if (healthProperty.intValue() > 0) {
            healthProperty.set(healthProperty.intValue() + multiplier);
        }
    }

    public DoubleProperty getArmorProperty() {
        return armorProperty;
    }

    public DoubleProperty getEnergyProperty() {
        return energyProperty;
    }

    public DoubleProperty getExperienceProperty() {
        return experienceProperty;
    }

    public double getHealth() {
        return this.healthProperty.doubleValue();
    }

    public DoubleProperty getHealthProperty() {
        return healthProperty;
    }

    public DoubleProperty getLevelProperty() {
        return levelProperty;
    }

    public Skill getSelectedSkill() {
        return selectedSkill;
    }

    public boolean isSkillActive() {
        return skillActive;
    }

    public void setTemporaryExperienceBoost(int multiplier, int burnout) {
        boostBurnout = burnout;
        experienceMultiplier = multiplier;
        boostActive = true;
    }

    public void tick() {
        addExperience();

        if (boostActive) {
            if (--boostBurnout < 0) {
                experienceMultiplier = EXP_RATE;
                boostActive = false;
            }
        }

        if (skillActive) {
            energyProperty.set(100 * skillBurnout / selectedSkill.getDuration());
            if (--skillBurnout < 0) {
                energyProperty.set(0);
                skillActive = false;
            }
        }
    }

}
