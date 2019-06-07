package ru.bkmz.drizzle;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.experimental.*;
import ru.bkmz.drizzle.input.Keyboard;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.util.*;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.*;


import static ru.bkmz.drizzle.level.GameData.*;
import static ru.bkmz.drizzle.util.Commons.colorLoader;
import static ru.bkmz.drizzle.util.Language.*;


public class Application extends javafx.application.Application {
    private static final String VERSIONS = "v3.10";
    private static final String DEBUG_MODE1 = "[DEBUG_MODE]";
    private static final String NAME_GAME = "drizzle";
    private static final String ARG_DEBUG = "debug";


    public static boolean DEBUG_MODE = isDebugMode();

    private Group root;
    private Group users;
    public Scene scene;

    private  Keyboard keyboard;

    private Games games;

    private static Pane paneMenu;
    private static Pane panePause;
    private static Pane paneHelp;
    private static Pane paneStat;
    private static Pane paneSettings;
    private static Pane paneShop;
    private static Pane paneOnline;
    private static Pane paneColor;

    private static String error = "";

    private static Media media;

    public static void main(String... args) {
        //включение режима отладки
        for (String arg : args) {
            if (arg.equals(ARG_DEBUG)) {
                System.out.println("on:" + DEBUG_MODE1);
                DEBUG_MODE = true;
                Comands.run();
                System.out.println("Консоль on");
            }
        }
        //старт игры
        LauncherImpl.launchApplication(Application.class, args);


    }

    //создание окон
    private static void switchPane(Group parent, Pane pane) {

        parent.getChildren().clear();
        if (Objects.nonNull(pane)) {
            parent.getChildren().add(pane);
        }
    }

    @Override
    public void init() {
        load();
        colorLoader();

        ImageLoader.IMAGE_LOADER.preferExternalSources(true);

        ImageLoader.IMAGE_LOADER.setCommonSuffix(".jpg");
        for (int i = 2; i <= 2; i++) {
            ImageLoader.IMAGE_LOADER.loading("background/background" + i);
        }


        ImageLoader.IMAGE_LOADER.setCommonSuffix(".png");
        ImageLoader.IMAGE_LOADER.loading("background/background1");

        ImageLoader.IMAGE_LOADER.loading("entity/acid");
        ImageLoader.IMAGE_LOADER.loading("entity/armor");
        ImageLoader.IMAGE_LOADER.loading("entity/energy");
        ImageLoader.IMAGE_LOADER.loading("entity/player4");
        ImageLoader.IMAGE_LOADER.loading("entity/star");

        for (int i = 1; i <= 10; i++) {
            ImageLoader.IMAGE_LOADER.loading("gui/bars/armor" + i);
        }
        ImageLoader.IMAGE_LOADER.loading("gui/bars/energy");
        ImageLoader.IMAGE_LOADER.loading("gui/bars/experience");
        ImageLoader.IMAGE_LOADER.loading("gui/bars/frame");
        for (int i = 1; i <= 10; i++) {
            ImageLoader.IMAGE_LOADER.loading("gui/bars/health" + i);
        }


        ImageLoader.IMAGE_LOADER.loading("gui/icons/ability");
        ImageLoader.IMAGE_LOADER.loading("gui/icons/back");
        ImageLoader.IMAGE_LOADER.loading("gui/icons/energy");
        ImageLoader.IMAGE_LOADER.loading("gui/icons/experience");
        ImageLoader.IMAGE_LOADER.loading("gui/icons/frame");
        ImageLoader.IMAGE_LOADER.loading("gui/icons/health");

        //копирование файлов из jar
        CopyFiles.failCopi("media/", "sine.mp3");
        CopyFiles.failCopi("media/", "Acid.wav");
        CopyFiles.failCopi("media/", "electric.wav");
        CopyFiles.failCopi("media/", "Shield.wav");
        CopyFiles.failCopi("media/", "star.wav");
        CopyFiles.failCopi("media/", "Hard_kick_drum.wav");

        CopyFiles.failCopi("DD/", "language");


        //определение окона
        sqlite(Settings_LANGUAGE.getValue());
        pane();

        keyboard = new Keyboard();
        this.games = new Games(keyboard);

        this.users = new Group();
        this.root = new Group(this.games.getCanvas(), this.users);

    }

    public static void pane() {
        paneMenu = new MenuPane();
        paneShop = new ShopPane();
        paneStat = new StatPane();
        paneHelp = new HelpPane();
        paneOnline = new Online();
        paneSettings = new SettingsPane();
        panePause = new PausePane();
        paneColor = new ColorPane();

    }


    @Override
    public void start(Stage stage) {


        this.keyboard.addEventSource(stage);//добовление ивентоов
        stage.initStyle(StageStyle.TRANSPARENT);//тип окна

        scene = new Scene(this.root, Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);//определение окна
        stage.setScene(scene);//создание окна
        //stage.sizeToScene();
        //оброботка ивентов
        stage.addEventHandler(StateEvent.STATE, event -> {
            final EventType<? extends Event> eventType = event.getEventType();

            if (eventType == StateEvent.MENU) {

                switchPane(this.users, paneMenu);

            } else if (eventType == StateEvent.PLAY) {
                switchPane(this.users, null);
                this.games.play();

            } else if (eventType == StateEvent.ONLINE) {
                switchPane(this.users, paneOnline);

            } else if (eventType == StateEvent.SHOP) {

                ((ShopPane) paneShop).refresh();
                switchPane(this.users, paneShop);

            } else if (eventType == StateEvent.STAT) {

                ((StatPane) paneStat).refresh();
                switchPane(this.users, paneStat);

            } else if (eventType == StateEvent.SETTINGS) {

                ((SettingsPane) paneSettings).refresh();
                switchPane(this.users, paneSettings);

            } else if (eventType == StateEvent.QUIT) {
                Platform.exit();
                System.exit(0);

            } else if (eventType == StateEvent.HELP) {

                switchPane(this.users, paneHelp);

            } else if (eventType == StateEvent.PAUSE) {

                switchPane(this.users, panePause);
                this.games.pause();

            } else if (eventType == StateEvent.UNPAUSE) {

                switchPane(this.users, null);
                this.games.unpause();

            } else if (eventType == StateEvent.STOP) {

                switchPane(this.users, paneMenu);
                this.games.close();

            } else if (eventType == StateEvent.MENU_SETTINGS) {

                switchPane(this.users, paneSettings);


            }else if (eventType == StateEvent.COLOR) {
                ((ColorPane) paneColor).refresh();
                switchPane(this.users, paneColor);


            } else if (eventType == StateEvent.SCREEN) {
                notification(getLanguageMap("NOTIFICATION"), getLanguageMap("RESTART"));
            } else if (eventType == StateEvent.COLLECTION) {

                cleaningPlayer();
            }
            event.consume();
        });

        stage.setTitle((DEBUG_MODE ? DEBUG_MODE1 + " " : "") + NAME_GAME + " " + VERSIONS);//Определяет заголовок
        stage.setResizable(false);// запрет на изменение окна
        stage.show();//сборка окна


        //режим окна
        if (Settings_SCREEN.getValue() == 0) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }


        stage.fireEvent(new StateEvent(StateEvent.MENU));//открытие меню


        media = new Media(appdata + "res/media/sine.mp3");
        media.volume(Settings_RAIN_Volume.getValue());
        media.indefinite();
        media.play();


        if (!error.equals("")) {
            writer();
            addError("SAVED IN:  " + appdata + "log");
            notification("error", error);
        }

    }

    private void writer() {
        File file = new File(appdata + "log");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy_MM_dd hh_mm_ss");
            file = new File(file + "/" + formatForDateNow.format(dateNow) + ".log");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(error);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //оповещение что нужна перезагрузка
    private void notification(String name, String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(name);
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
    }

    public static void setVolume(int volume) {
        media.volume(volume);
        if (volume == 0) {
            media.stop();
        } else {
            media.stop();
            media.play();
        }

    }

    public static void addError(String e) {
        error += e + "\n";
    }
}
