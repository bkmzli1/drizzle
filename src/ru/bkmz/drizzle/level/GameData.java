package ru.bkmz.drizzle.level;

import java.io.*;
import java.util.Properties;

public enum GameData {

    STAT_COUNT_EXPERIENCE(0, Integer.MAX_VALUE, "опыт"),
    STAT_COUNT_DAMAGE(0, Integer.MAX_VALUE, "урон"),
    STAT_COUNT_SHIELD(0, Integer.MAX_VALUE, "собрано щитов"),
    STAT_COUNT_NODES(0, Integer.MAX_VALUE, "Собранные узлы"),
    STAT_COUNT_STARS(0, Integer.MAX_VALUE, "Звезды собираются"),
    STAT_COUNT_JUMP(0, Integer.MAX_VALUE, "кол прыжков"),
    STAT_COUNT_SKILLACTIVATION(0, Integer.MAX_VALUE, "кол навыков актевиравоно"),

    PLAYER_LEVEL(1, Integer.MAX_VALUE, "уровень"),
    PLAYER_POINTS(0, Integer.MAX_VALUE, "Точки обновления"),
    PLAYER_SELECTEDSKILL(0, 3, "Выбранный Навык"),
    PLAYER_HEALTH(3, 10, "Здоровье"),

    UPGRADE_MOVEMENT(0, 1, "Обновление движения"),
    UPGRADE_POWERRATE(1, 2, "Тариф на электроэнергию"),
    UPGRADE_SHOCKWAVE(0, 1, "Навык \"Ударная Волна\" "),
    UPGRADE_DOUBLEXP(0, 1, "Двойной навык XP"),
    UPGRADE_SHIELDSPAWN(0, 1, "Щит отродясь мастерства"),
    ACID_Volume(0, 10, "Громкость капель"),
    RAIN_Volume(0, 10, "Громкость дождя");

    public static Properties properties = new Properties();
    private static int AcidSpawner_rate;
    private static int AcidSpawner_variation;
    private static int AcidSpawner_count;
    private static boolean DEBUG_MODE;


    public static boolean isDebugMode() {
        return DEBUG_MODE;
    }

    public static int getAcidSpawner_rate() {
        return AcidSpawner_rate;
    }

    public static int getAcidSpawner_variation() {
        return AcidSpawner_variation;
    }

    public static int getAcidSpawner_count() {
        return AcidSpawner_count;
    }

    private static final String SER_FILE = "playdata.ser";
    private static final String config_file = "configData.conf";

    private final int min;
    private final int max;
    private final String name;

    private int value;

    public static GameData[] getStatistics() {
        return new GameData[]{STAT_COUNT_EXPERIENCE, STAT_COUNT_DAMAGE, STAT_COUNT_SHIELD,
                STAT_COUNT_NODES, STAT_COUNT_STARS, STAT_COUNT_JUMP, STAT_COUNT_SKILLACTIVATION};
    }

    public static GameData[] getPlayerProperties() {
        return new GameData[]{PLAYER_LEVEL, PLAYER_POINTS, PLAYER_SELECTEDSKILL, PLAYER_HEALTH};
    }

    public static GameData[] getUpgrades() {
        return new GameData[]{UPGRADE_MOVEMENT, UPGRADE_POWERRATE, UPGRADE_SHOCKWAVE,
                UPGRADE_DOUBLEXP, UPGRADE_SHIELDSPAWN};
    }

    public static void load() {
        try {
            read();


        } catch (Exception e) {
            try {
                write();
            } catch (Exception f) {
                f.printStackTrace();
            }
        }
    }

    public static void save() {
        try {
            write();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void read() throws ClassNotFoundException, IOException {
        FileInputStream fiStream = new FileInputStream(new File(GameData.SER_FILE));
        ObjectInputStream oiStream = new ObjectInputStream(fiStream);

        for (int i = 0; i < GameData.values().length; i++) {
            int reading = oiStream.readInt();
            if (reading < GameData.values()[i].min) {
                reading = GameData.values()[i].min;
            } else if (reading > GameData.values()[i].max) {
                reading = GameData.values()[i].max;
            }

            GameData.values()[i].value = reading;
        }

        oiStream.close();
        fiStream.close();
    }

    private static void write() throws IOException {
        FileOutputStream foStream = new FileOutputStream(new File(GameData.SER_FILE), false);
        ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
        ooStream.reset();

        for (int i = 0; i < GameData.values().length; i++) {
            ooStream.writeDouble(GameData.values()[i].value);
            /*System.out.print(GameData.values()[i].name);
            System.out.println(GameData.values()[i].value);*/

        }

        ooStream.close();
        foStream.close();
    }

    GameData(int defaultValue, int max, String name) {
        this.value = defaultValue;
        this.max = max;
        this.min = defaultValue;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public double getMax() {
        return this.max;
    }

    public double getMin() {
        return this.min;
    }

    public int getValue() {
        return this.value;
    }
    public double getValueD() {
        return this.value;
    }
    public void increment() {
        if (this.value < this.max) {
            this.value++;
        }
    }

    public void incrementBy(float value) {
        if (this.value + value > this.max) {
            this.value = this.max;
        } else if (this.value + value < this.min) {
            this.value = this.min;
        } else {
            this.value += value;
        }
    }

    public void setValue(int value) {
        if (value > this.max) {
            this.value = this.max;
        } else if (value < this.min) {
            this.value = this.min;
        } else {
            this.value = value;
        }
    }

    public static void readC() throws IOException {

        InputStream inputStream = new FileInputStream(GameData.config_file);
        properties.load(inputStream);
        AcidSpawner_rate = Integer.parseInt(properties.getProperty("AcidSpawner_rate"));
        AcidSpawner_variation = Integer.parseInt(properties.getProperty("AcidSpawner_variation"));
        AcidSpawner_count = Integer.parseInt(properties.getProperty("AcidSpawner_count"));
        DEBUG_MODE = Boolean.parseBoolean(properties.getProperty("DEBUG_MODE"));


    }

    public static void writeC() throws IOException {
        OutputStream fileOutputStream = new FileOutputStream(GameData.config_file);
        properties.setProperty("WIDTH", "1");
        properties.setProperty("HEIGHT_MIN", "10");
        properties.setProperty("HEIGHT_MAX", "40");
        properties.setProperty("SPEED_X", "0");
        properties.setProperty("SPEED_Y_MIN", "10");
        properties.setProperty("SPEED_Y_MAX", "40");
        properties.setProperty("AcidSpawner_rate", "10");
        properties.setProperty("AcidSpawner_variation", "3");
        properties.setProperty("AcidSpawner_count", "1");
        properties.setProperty("DEBUG_MODE", "false");
        properties.setProperty("PLAYER_HEALTH", "1");
        properties.store(fileOutputStream, null);

    }


}
