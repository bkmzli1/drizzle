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
    RAIN_Volume(0, 10, "Громкость дождя"),
    AcidSpawner_rate(10, 10, "Скорость"),
    AcidSpawner_variation(10, 10, "отклонение"),
    AcidSpawner_count(1, 3, "повтор");

    public static Properties properties = new Properties();
    private static boolean DEBUG_MODE;


    public static boolean isDebugMode() {
        return DEBUG_MODE;
    }

    private static final String SER_FILE = "playdata.ser";
    public static final String config_file = "configData.conf";

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
            read(SER_FILE);


        } catch (Exception e) {
            try {
                write(SER_FILE);
            } catch (Exception f) {
                f.printStackTrace();
            }
        }
    }

    public static void save() {
        try {
            write(SER_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void read(String file) throws ClassNotFoundException, IOException {
        FileInputStream fiStream = new FileInputStream(new File(file));
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

    private static void write(String file) throws IOException {
        FileOutputStream foStream = new FileOutputStream(new File(file), false);
        ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
        ooStream.reset();

        for (int i = 0; i < GameData.values().length; i++) {
            ooStream.writeInt(GameData.values()[i].value);
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

    public void setVolume(int value) {
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

        DEBUG_MODE = Boolean.parseBoolean(properties.getProperty("DEBUG_MODE"));

    }

    public static void writeC() throws IOException {
        OutputStream fileOutputStream = new FileOutputStream(GameData.config_file);
        properties.setProperty("DEBUG_MODE", "true");
        properties.store(fileOutputStream, null);
    }
    public static void cleaning() throws IOException {

        File file = new File(SER_FILE);
        file.delete();
        FileOutputStream foStream = new FileOutputStream(new File(SER_FILE), false);
        ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
        ooStream.reset();

        for (int i = 0; i < GameData.values().length; i++) {
            ooStream.writeInt(0);
        }

        ooStream.close();
        foStream.close();
        load();
    }


}
