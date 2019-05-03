package ru.bkmz.drizzle;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.experimental.*;
import ru.bkmz.drizzle.input.Keyboard;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.util.Commons;
import ru.bkmz.drizzle.util.ImageLoader;
import ru.bkmz.drizzle.util.MediaLoader;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static ru.bkmz.drizzle.level.GameData.*;

public class Application extends javafx.application.Application {
    private static final String VERSION = "v3.8";
    private static final String TITLE_DEBUG_PREFIX = "[DEBUG MODE]";
    private static final String TITLE = "drizzle";
    private static final String ARG_DEBUG = "debug";
    public static boolean DEBUG_MODE = isDebugMode();
    private boolean consoleOn = true;
    private Group root;
    private Group users;

    private Keyboard keyboard;

    private Games games;

    private Pane paneMenu;
    private Pane paneShop;
    private Pane paneStat;
    private Pane paneHelp;
    private Pane paneSettings;
    private Pane panePause;
    private static final String MEDIA = MediaLoader.INSTANCE.getMedia("res/media/sine.mp3");

    public static void main(String... args) {

        File config_file = new File(GameData.config_file);
        if (config_file.exists()) {
            try {
                writeC();
                readC();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        for (String arg : args) {
            if (arg.equals(ARG_DEBUG)) {
                DEBUG_MODE = true;
            }
        }
        LauncherImpl.launchApplication(Application.class, args);
    }

    private static void switchPane(Group parent, Pane pane) {
        parent.getChildren().clear();

        if (Objects.nonNull(pane)) {
            parent.getChildren().add(pane);
        }
    }

    @Override
    public void init() {
        load();
        DEBUG_MODE = isDebugMode();
        ImageLoader.INSTANCE.setCommonPrefix("");
        ImageLoader.INSTANCE.setCommonSuffix(".png");
        ImageLoader.INSTANCE.preferExternalSources(true);

        ImageLoader.INSTANCE.load("background/background");
        ImageLoader.INSTANCE.load("entity/acid");
        ImageLoader.INSTANCE.load("entity/armor");
        ImageLoader.INSTANCE.load("entity/energy");
        ImageLoader.INSTANCE.load("entity/player3");
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

        MediaLoader.INSTANCE.setCommonPrefix("");
        MediaLoader.INSTANCE.setCommonSuffix(".mp3");

        MediaLoader.INSTANCE.load("res/media/sine");
        MediaLoader.INSTANCE.load("res/media/a_drop");

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

        this.keyboard.addEventSource(stage);
        Scene scene = new Scene(this.root, Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        stage.setScene(scene);
        stage.sizeToScene();

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

            } else if (eventType == StateEvent.COLLECTION) {
                try {
                    cleaning();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            event.consume();
        });

        stage.setTitle((DEBUG_MODE ? TITLE_DEBUG_PREFIX + " " : "") + TITLE + " " + VERSION);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        oracleVid.setCycleCount(MediaPlayer.INDEFINITE);
        float valveF = RAIN_Volume.getValue() / 10f;
        oracleVid.setVolume(valveF);
        oracleVid.play();


        stage.fireEvent(new StateEvent(StateEvent.MENU));
        if (consoleOn) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Comands.comands();
                }
            }).start();
            consoleOn = false;
        }

    }
    public static final MediaPlayer oracleVid = new MediaPlayer(
            new Media(new File(MEDIA).toURI().toString())

    );

}
