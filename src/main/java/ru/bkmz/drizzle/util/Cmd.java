package ru.bkmz.drizzle.util;

import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;

import java.util.Scanner;

public class Cmd implements Runnable {
    static Scanner sc = new Scanner(System.in);
    Level.LevelController levelController;
    Level level;


    public Cmd(Level.LevelController levelController) {
        this.levelController = levelController;
        level = levelController.getLevel();
    }

    private void comand() {
        String[] dComand = String.valueOf(sc.nextLine()).split("=");
        String comand = dComand[0];

        int value = 0;
        try {
            value = Integer.parseInt(dComand[1]);
        } catch (ArrayIndexOutOfBoundsException AIOODE) {
        }


        try {
            switch (comand) {
                case ("help"):
                    System.out.println("" +
                            "goodmod(int) -on/off invulnerability\n" +
                            "------------------------------------------\n" +
                            "set-\n" +
                            "    |-point(int) -sets the number of points \n" +
                            "    |-level(int) -sets player level\n" +
                            "    |-health(int) -sets the base count. hp\n" +
                            "------------------------------------------\n" +
                            "add-\n" +
                            "    |-point(int) - adds count points\n" +
                            "    |-level(int) - adds level\n" +
                            "    |-health(int) - adds the base count. hp\n" +
                            "    |-hp(int) - adds count hp\n" +
                            "    |-Experience(int) - adds experience\n" +
                            "    |-Shield(int) - adds shield\n" +
                            "    |-damage(int) - deals damage to a player\n"
                    );
                    break;


                case ("goodmod"):
                    levelController.getLevel().getPlayerProperties().setGodmod();
                    break;


                case ("set point"):
                    GDset(GameData.PLAYER_POINTS, value);
                    break;


                case "set level":
                    if (value <= 30 && value >= 0) {
                        GDset(GameData.PLAYER_LEVEL, value);
                    } else {
                        System.out.println("не поподает в диапозон 0-30");
                    }
                    break;


                case ("add point"):
                    GDadd(GameData.PLAYER_POINTS, value);
                    break;

                case ("set health"):
                    GDset(GameData.PLAYER_HEALTH, value);
                    break;

                case ("add health"):
                    GDadd(GameData.PLAYER_HEALTH, value);
                    break;


                case "add level":
                    if (value <= 30 && value >= 0) {
                        GDadd(GameData.PLAYER_LEVEL, value);
                    } else {
                        System.out.println("не поподает в диапозон 0-30");
                    }
                    break;
                case "add hp":
                    if (level.getPlayerProperties().getHealth() + value < 10) {
                        level.getPlayerProperties().addhil(value);
                    } else if (level.getPlayerProperties().getHealth() + value > 10) {
                        int difference = (int) (10 - level.getPlayerProperties().getHealth());
                        level.getPlayerProperties().addhil(difference);
                    }
                    break;


                case "add Experience":
                    for (int i = 0; i < value; i++) {
                        level.getPlayerProperties().addExperience(50);
                    }


                case "add Shield":
                    level.getPlayerProperties().addShield(value);
                    break;


                case "add damage":
                    level.getPlayerProperties().add_damage(value);
                    break;

                default:
                    System.out.println("команда:" + comand + " не опознана");
                    break;
            }
        } catch (Exception e) {
            System.out.println("ошибка: " + e);
            comand();
        }
        comand();
    }

    private void GDset(GameData gd, int value) {
        System.out.println("get point:" + gd.getValue());
        gd.setVolume(value);
        System.out.println("set point:" + gd.getValue());
        GameData.save();
    }

    private void GDadd(GameData gd, int value) {
        System.out.println("get point:" + gd.getValue());
        gd.setVolume(value + gd.getValue());
        System.out.println("set point:" + gd.getValue());
        GameData.save();
    }

    @Override
    public void run() {
        System.out.println("on cmd");
        try {

            comand();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
