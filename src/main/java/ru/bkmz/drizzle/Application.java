package ru.bkmz.drizzle;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.bkmz.drizzle.entity.mob.Acid;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.experimental.*;
import ru.bkmz.drizzle.input.Keyboard;
import ru.bkmz.drizzle.level.player.PlayerProperties;
import ru.bkmz.drizzle.util.Commons;
import ru.bkmz.drizzle.util.CopyFiles;
import ru.bkmz.drizzle.util.ImageLoader;
import ru.bkmz.drizzle.util.Sound;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static ru.bkmz.drizzle.level.GameData.*;

public class Application extends javafx.application.Application {
    private static final String VERSION = "v3.8.8";
    private static final String TITLE_DEBUG_PREFIX = "[DEBUG MODE]";
    private static final String TITLE = "drizzle";
    private static final String ARG_DEBUG = "debug";

    private static File file = new File(config_file);

    public static boolean DEBUG_MODE = isDebugMode();

    private Group root;
    private Group users;
    public Scene scene;
    private Keyboard keyboard;

    private Games games;

    private Pane paneMenu;
    private Pane paneShop;
    private Pane paneStat;
    private Pane paneHelp;
    private Pane paneSettings;
    private Pane panePause;


    public static MediaPlayer mediaPlayer;

    public static void main(String... args) {
        //включение режима отладки
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (file.exists()) {
                    DEBUG_MODE = true;
                    System.out.println("Консоль on");
                    Comands.comands();
                } else {
                    System.out.println("Консоль off");
                }
            }
        }).start();
        for (String arg : args) {
            if (arg.equals(ARG_DEBUG)) {
                DEBUG_MODE = true;
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
        load();//загрузка GameData
        /*
         * preferExternalSources тип загрузки
         * setCommonSuffix формат
         * load загрузка
         * */
        ImageLoader.INSTANCE.preferExternalSources(true);

        ImageLoader.INSTANCE.setCommonSuffix(".jpg");
        ImageLoader.INSTANCE.load("background/background2");
        ImageLoader.INSTANCE.load("background/background3");
        ImageLoader.INSTANCE.load("background/background4");
        ImageLoader.INSTANCE.load("background/background5");
        ImageLoader.INSTANCE.load("background/background6");
        ImageLoader.INSTANCE.load("background/background7");

        ImageLoader.INSTANCE.setCommonSuffix(".png");
        ImageLoader.INSTANCE.load("background/background1");
        ImageLoader.INSTANCE.load("entity/acid");
        ImageLoader.INSTANCE.load("entity/armor");
        ImageLoader.INSTANCE.load("entity/energy");
        ImageLoader.INSTANCE.load("entity/player4");
        ImageLoader.INSTANCE.load("entity/star");
        ImageLoader.INSTANCE.load("gui/bars/armor");
        ImageLoader.INSTANCE.load("gui/bars/energy");
        ImageLoader.INSTANCE.load("gui/bars/experience");
        ImageLoader.INSTANCE.load("gui/bars/frame");
        ImageLoader.INSTANCE.load("gui/bars/health");
        ImageLoader.INSTANCE.load("gui/icons/ability");
        ImageLoader.INSTANCE.load("gui/icons/back");
        ImageLoader.INSTANCE.load("gui/icons/energy");
        ImageLoader.INSTANCE.load("gui/icons/experience");
        ImageLoader.INSTANCE.load("gui/icons/frame");
        ImageLoader.INSTANCE.load("gui/icons/health");
        //копирование файлов из jar
        CopyFiles.failCopi("media/", "sine.mp3");
        CopyFiles.failCopi("media/", "Acid.wav");
        CopyFiles.failCopi("media/", "electric.wav");
        CopyFiles.failCopi("media/", "Shield.wav");
        CopyFiles.failCopi("media/", "star.wav");
        CopyFiles.failCopi("media/", "Hard_kick_drum.wav");

        //определение музыки
        mediaPlayer = new MediaPlayer(
                new Media(new File("res/media/sine.mp3").toURI().toString())

        );
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(RAIN_Volume.getValue() / 10f);


        Acid.sound = new Sound(new File("res/media/Acid.wav"));
        Acid.sound.setVolume(Effect_Volume.getValue() / 10f);

        PlayerProperties.soundEnergy = new Sound(new File("res/media/electric.wav"));
        PlayerProperties.soundEnergy.setVolume(Effect_Volume.getValue() / 10f);

        PlayerProperties.soundShield = new Sound(new File("res/media/Shield.wav"));
        PlayerProperties.soundShield.setVolume(Effect_Volume.getValue() / 10f);

        PlayerProperties.soundStar = new Sound(new File("res/media/star.wav"));
        PlayerProperties.soundStar.setVolume(Effect_Volume.getValue() / 10f);

        Sound.sound = new Sound(new File("res/media/Hard_kick_drum.wav"));
        Sound.sound.setVolume(Effect_Volume.getValue() / 10f);

        //определение окона
        this.paneMenu = new MenuPane();
        this.paneShop = new ShopPane();
        this.paneStat = new StatPane();
        this.paneHelp = new HelpPane();
        this.paneSettings = new SetingsPane();
        this.panePause = new PausePane();

        this.keyboard = new Keyboard();
        this.games = new Games(this.keyboard);

        this.users = new Group();
        this.root = new Group(this.games.getCanvas(), this.users);

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

                switchPane(this.users, this.paneMenu);

            } else if (eventType == StateEvent.PLAY) {
                switchPane(this.users, null);
                this.games.play();

            } else if (eventType == StateEvent.SHOP) {

                ((ShopPane) this.paneShop).refresh();
                switchPane(this.users, this.paneShop);

            } else if (eventType == StateEvent.STAT) {

                ((StatPane) this.paneStat).refresh();
                switchPane(this.users, this.paneStat);

            } else if (eventType == StateEvent.STINGS) {

                ((SetingsPane) this.paneSettings).refresh();
                switchPane(this.users, this.paneSettings);

            } else if (eventType == StateEvent.QUIT) {

                Platform.exit();
                System.exit(0);

            } else if (eventType == StateEvent.HELP) {

                switchPane(this.users, this.paneHelp);

            } else if (eventType == StateEvent.PAUSE) {

                switchPane(this.users, this.panePause);
                this.games.pause();

            } else if (eventType == StateEvent.UNPAUSE) {

                switchPane(this.users, null);
                this.games.unpause();

            } else if (eventType == StateEvent.STOP) {

                switchPane(this.users, this.paneMenu);
                this.games.close();

            } else if (eventType == StateEvent.SCREEN) {
                reboot();
            } else if (eventType == StateEvent.BACKGROUND) {
                reboot();
            } else if (eventType == StateEvent.COLLECTION) {
                try {
                    cleaning();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            event.consume();
        });
        stage.setTitle((DEBUG_MODE ? TITLE_DEBUG_PREFIX + " " : "") + TITLE + " " + VERSION);//Определяет заголовок
        stage.setResizable(false);// запрет на изменение окна
        stage.show();//сборка окна
        //режим окна
        if (SCREEN.getValue() == 0) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
        stage.fireEvent(new StateEvent(StateEvent.MENU));//открытие меню
        mediaPlayer.play();//запуск фоновой музыки
    }

    //оповещение что нужна перезагрузка
    void reboot() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("ДЛЯ ИЗМЕНЕНИЯ ДАННОЙ НАСТРОЙКИ ПЕРЕЗАГРУЗИТЕ ИГРУ");
        alert.showAndWait();
    }

}
