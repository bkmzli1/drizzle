package ru.bkmz.drizzle;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.experimental.*;
import ru.bkmz.drizzle.input.Keyboard;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.util.Commons;
import ru.bkmz.drizzle.util.ImageLoader;
import com.sun.javafx.application.LauncherImpl;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static ru.bkmz.drizzle.level.GameData.*;

public class Application extends javafx.application.Application {
    private ThendlerConsol consol = new ThendlerConsol();

    private static final String VERSION = "v3.6";
    private static final String TITLE_DEBUG_PREFIX = "[DEBUG MODE]";
    private static final String TITLE = "Rain";
    private static final String ARG_DEBUG = "debug";
    public static boolean DEBUG_MODE = GameData.isDebugMode();

    private Group root;
    private Group group;

    private Keyboard keyboard;

    private Game game;

    private Pane paneMenu;
    private Pane paneShop;
    private Pane paneStat;
    private Pane paneHelp;
    private Pane paneSetings;
    private Pane panePause;
    private String filemedia = "res/media/sine.mp3";
    public static void main(String... args) {

        try {

            readC();
        } catch (IOException e) {
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
        ImageLoader.INSTANCE.load("entity/player2");
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

        this.paneMenu = new MenuPane();
        this.paneShop = new ShopPane();
        this.paneStat = new StatPane();
        this.paneHelp = new HelpPane();
        this.paneSetings = new Setings();
        this.panePause = new PausePane();

        this.keyboard = new Keyboard();
        this.game = new Game(this.keyboard);

        this.group = new Group();
        this.root = new Group(this.game.getCanvas(), this.group);

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
                switchPane(this.group, this.paneMenu);
            } else if (eventType == StateEvent.PLAY) {
                switchPane(this.group, null);
                this.game.play();
            } else if (eventType == StateEvent.SHOP) {
                ((ShopPane) this.paneShop).refresh();
                switchPane(this.group, this.paneShop);
            } else if (eventType == StateEvent.STAT) {
                ((StatPane) this.paneStat).refresh();
                switchPane(this.group, this.paneStat);
            } else if (eventType == StateEvent.STINGS){
                switchPane(this.group, this.paneSetings);
            } else if (eventType == StateEvent.QUIT) {
                Platform.exit();
                System.exit(0);
            } else if (eventType == StateEvent.HELP) {
                switchPane(this.group, this.paneHelp);
            } else if (eventType == StateEvent.PAUSE) {
                switchPane(this.group, this.panePause);
                this.game.pause();
            } else if (eventType == StateEvent.UNPAUSE) {
                switchPane(this.group, null);
                this.game.unpause();
            } else if (eventType == StateEvent.STOP) {
                switchPane(this.group, this.paneMenu);
                this.game.close();
            }

            event.consume();
        });
        consol.start();
        stage.setTitle((DEBUG_MODE ? TITLE_DEBUG_PREFIX + " " : "") + TITLE + " " + VERSION);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        oracleVid.setCycleCount(MediaPlayer.INDEFINITE);
        oracleVid.play();
        stage.fireEvent(new StateEvent(StateEvent.MENU));

        /*
        Media media = new Media(new File(filemedia).toURI().toString());
        player = new MediaPlayer(media);
        MediaView view = new MediaView(player);
        root.getChildren().addAll(view);
        stage.setScene(scene);
        stage.show();
        player.play();*/
    }
    private final MediaPlayer oracleVid = new MediaPlayer(
            new Media(new File(filemedia).toURI().toString())
    );

}
