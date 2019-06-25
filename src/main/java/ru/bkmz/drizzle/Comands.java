package ru.bkmz.drizzle;

import ru.bkmz.drizzle.entity.Entity;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;

import java.util.Scanner;

class Comands extends Entity {

    Comands(double x, double y, double width, double height, Level level) {
        super(x, y, width, height, level);
    }
    static Thread thread ;
    static Scanner scanner = new Scanner(System.in);
    public static void comands() {
        String s = null;

        try {

            s = String.valueOf(scanner.next());
            if (s.equals("help")) {
                System.out.println("en=int - прибавить очков\n" +
                        "hp=int - кол. хп стартовое\n" +
                        "addEnergy=int - добавить энергию\n" +
                        "addShield=int - добовляет щит\n" +
                        "addExperience=int - добовляет ex\n" +
                        "addhil=int - добовляет hp\n" +
                        "add_damage=int - наносит урон игроку\n" +
                        "godmod - режим бога\n" +
                        "level=int - добовляет лэвел ");
                comands();
            } else if (s.equals("godmod")) {
                level.getPlayerProperties().setGodmod();
                comands();
            }

            String[] comand = s.split("=");
            String value = comand[1];
            switch (comand[0]) {
                case "en":
                    System.out.println("en:" + GameData.PLAYER_POINTS.getValue());
                    GameData.PLAYER_POINTS.setVolume(Integer.parseInt(value) + GameData.PLAYER_POINTS.getValue());
                    System.out.println("en:" + GameData.PLAYER_POINTS.getValue());
                    break;
                case "hp":
                    System.out.println("hp:" + GameData.PLAYER_HEALTH.getValue());
                    GameData.PLAYER_HEALTH.setVolume(Integer.parseInt(value));
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
                        int difference = (int) (10 - level.getPlayerProperties().getHealth());
                        level.getPlayerProperties().addhil(difference);
                    }
                    break;
                case "add_damage":
                    level.getPlayerProperties().add_damage(Integer.parseInt(value));
                    break;
                case "level":
                    GameData.PLAYER_LEVEL.setVolume(Integer.parseInt(value));
                    GameData.save();
                    break;
                default:
                    System.out.println("команда:" + comand[0] + " не опознана");
                    break;

            }
        } catch (Exception e) {
            System.out.println("при исполнении команды " + s + " произошла ошибка");
            System.err.println(e);
        }
        comands();
    }
    public static void run(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                comands();
            }
        });
        thread.start();

    }
    @Override
    public void tick() {

    }
}
