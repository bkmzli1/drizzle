package ru.bkmz.drizzle;

import ru.bkmz.drizzle.entity.Entity;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;

import java.util.Scanner;

class Comands extends Entity {
    Comands(double x, double y, double width, double height, Level level) {
        super(x, y, width, height, level);
    }

    void comands() {
        try {
            Scanner scanner = new Scanner(System.in);
            String s = String.valueOf(scanner.next());
            if (s.equals("help")) {
                System.out.println("en=int - прибавить очков\n" +
                        "hp=int - кол. хп стартовое\n" +
                        "addEnergy=int - добавить энергию\n" +
                        "addShield=int - добовляет щит" +
                        "addExperience=int - добовляет ex\n" +
                        "addhil=int - добовляет hp\n" +
                        "add_damage=int - наносит урон игроку\n" +
                        "godmod=boolean - режим бога\n");
                comands();
            }
            String[] comand = s.split("=");
            String value = comand[1];
            switch (comand[0]) {
                case "en":
                    System.out.println("en:" + GameData.PLAYER_POINTS.getValue());
                    GameData.PLAYER_POINTS.setValue(Integer.parseInt(value + GameData.PLAYER_POINTS.getValue()));
                    System.out.println("en:" + GameData.PLAYER_POINTS.getValue());
                    break;
                case "hp":
                    System.out.println("hp:" + GameData.PLAYER_HEALTH.getValue());
                    GameData.PLAYER_HEALTH.setValue(Integer.parseInt(value));
                    System.out.println("hp:" + GameData.PLAYER_HEALTH.getValue());
                    break;
                case "kill":

                    break;
                case "close":
                    System.exit(0);
                    break;
                case "addEnergy":
                    level.getPlayerProperties().addEnergy(Integer.parseInt(value));
                    break;
                case "addShield":
                    level.getPlayerProperties().addShield(Integer.parseInt(value));
                    break;
                case "addExperience":
                    for (int i = 0; i < Integer.parseInt(value); i++) {
                        level.getPlayerProperties().addExperience(50);
                    }
                case "addhil":
                    if (level.getPlayerProperties().getHealth() + Integer.parseInt(value) < 10) {
                        level.getPlayerProperties().addhil(Integer.parseInt(value));
                    } else if (level.getPlayerProperties().getHealth() + Integer.parseInt(value) > 10) {
                        int difference = 10 - level.getPlayerProperties().getHealth();
                        level.getPlayerProperties().addhil(difference);
                    }
                    break;
                case "add_damage":
                    level.getPlayerProperties().add_damage(Integer.parseInt(value));
                    break;
                case "godmod":
                    level.getPlayerProperties().setGodmod(Boolean.parseBoolean(value));
                    break;
                default:
                    System.out.println("команда:" + comand[0] + " не опознана");
                    break;

            }
        } catch (Exception e) {
            System.out.println("при исполнении команды произошла ошибка");
            System.out.println(e);
        }
        comands();
    }

    @Override
    public void tick() {

    }
}

class ThendlerConsol extends Thread {
    @Override
    public void run() {
        Comands comands = new Comands(1, 1, 1, 1, null);
        comands.comands();
    }
}