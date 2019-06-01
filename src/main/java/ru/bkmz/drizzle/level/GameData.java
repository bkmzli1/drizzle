package ru.bkmz.drizzle.level;

import java.awt.*;
import java.io.*;
import java.util.Properties;

public enum GameData {
    //данные игры
    STAT_COUNT_EXPERIENCE(0, Integer.MAX_VALUE, "ОПЫТ"),
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
    UPGRADE_SHOCKWAVE(0, 1, "Навык Ударная Волна"),
    UPGRADE_DOUBLEXP(0, 1, "Двойной навык XP"),
    UPGRADE_SHIELDSPAWN(0, 1, "Щит отродясь мастерства"),

    Settings_Effect_Volume(0, 10, "ГРОМКОСТЬ ЭФФЕКТОВ"),
    Settings_FPS(0, 2, "Settings_FPS"),
    Settings_RAIN_Volume(0, 10, "ГРОМКОСТЬ ДОЖДЯ"),
    Settings_AcidSpawner_rate(10, 10, "Скорость"),
    Settings_AcidSpawner_variation(10, 10, "отклонение"),
    Settings_AcidSpawner_count(1, 3, "повтор"),

    Settings_SCREEN(0, 1, "ЭКРАН"),
    SCENE_WIDTH((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 1.2f), Integer.MAX_VALUE, "WIDTH"),
    SCENE_HEIGHT((int) (Toolkit.getDefaultToolkit().getScreenSize().height / 1.2f), Integer.MAX_VALUE, "HEIGHT"),
    Settings_BACKGROUND(1, 7, "ФОН"),
    Settings_LANGUAGE(0, 1, "ЯЗЫК");
    private static boolean DEBUG_MODE;

    public static boolean isDebugMode() {
        return DEBUG_MODE;
    }

    public static final String appdata = System.getenv("APPDATA") + "\\.drizzle\\";
    private static final String SER_FILE = appdata + "playdata.ser";
    public static final String config_file = appdata + "configData.conf";

    private final int min;
    private final int max;
    private final String name;

    private int value;


    //вывод определённых данных
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

    public static GameData[] getSettings() {
        return new GameData[]{Settings_Effect_Volume, Settings_FPS, Settings_RAIN_Volume,
                Settings_AcidSpawner_rate, Settings_AcidSpawner_variation, Settings_AcidSpawner_count,
                Settings_SCREEN, Settings_BACKGROUND, Settings_LANGUAGE};
    }

    //загруза
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

    //сохронение
    public static void save() {
        try {
            write(SER_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //чтение и запись в GameData
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

    //запись в фаил GameData
    private static void write(String file) throws IOException {
        File file1 = new File(appdata);
        if (!file1.exists()) {
            file1.mkdir();
        }
        FileOutputStream foStream = new FileOutputStream(new File(file), false);
        ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
        ooStream.reset();

        for (int i = 0; i < GameData.values().length; i++) {
            ooStream.writeInt(GameData.values()[i].value);
        }

        ooStream.close();
        foStream.close();
    }

    //создание GameData
    GameData(int defaultValue, int max, String name) {
        this.value = defaultValue;
        this.max = max;
        this.min = defaultValue;
        this.name = name;
    }

    //получение определённого параметра
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

    //прибовление к параметру
    public void increment() {
        if (this.value < this.max) {
            this.value++;
        }
    }

    //прибовление определёного значения к параметру
    public void incrementBy(float value) {
        if (this.value + value > this.max) {
            this.value = this.max;
        } else if (this.value + value < this.min) {
            this.value = this.min;
        } else {
            this.value += value;
        }
    }

    //изменение параметра
    public void setVolume(int value) {
        if (value > this.max) {
            this.value = this.max;
        } else if (value < this.min) {
            this.value = this.min;
        } else {
            this.value = value;
        }
    }

    //очистка GameData
    public static void cleaningPlayer() {
        GameData[] gd = getStatistics();

        for (int i = 0; i < gd.length; i++) {
            gd[i].setVolume(0);
        }
        gd = getPlayerProperties();
        for (int i = 0; i < gd.length; i++) {
            gd[i].setVolume(0);
        }
        gd = getUpgrades();
        for (int i = 0; i < gd.length; i++) {
            gd[i].setVolume(0);
        }
        save();
        load();
    }

    public static void cleaningSettings() {
        GameData[] gd = getSettings();
        for (int i = 0; i < gd.length; i++) {
            gd[i].setVolume(gd[i].min);
        }

        load();
    }

}
