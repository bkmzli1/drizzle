package ru.bkmz.drizzle.experimental;

import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ru.bkmz.drizzle.Application;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.util.Commons;


import java.awt.*;
import java.io.IOException;

import static ru.bkmz.drizzle.level.GameData.*;
import static ru.bkmz.drizzle.util.Language.getLanguageMap;
import static ru.bkmz.drizzle.util.Language.sqlite;


public class SettingsPane extends BorderPane {
    private VBox vBox;
    private HBox HRainVolume, HAcidVolume, HDifficulty, HScreen, HBackground, HFps, HLanguage;


    protected static Level level;

    public SettingsPane() {
        Text reset = new Text(getLanguageMap("Reset"));
        reset.setOnMouseClicked(event -> {

                cleaningSettings();

            save();
            refresh();
            fireEvent(new StateEvent(StateEvent.SCREEN));
        });

        reset.setFont(Font.font("", FontWeight.BOLD, 30));
        reset.setFill(Commons.GRADIENT2);
        reset.setUnderline(true);
        reset.setOpacity(0.5);
        reset.setOnMouseEntered(event -> {
            reset.setOpacity(1);
            Glow glow = new Glow(1000);
            reset.setEffect(glow);
        });

        reset.setOnMouseExited(event -> {
            reset.setOpacity(0.5);
            Glow glow = new Glow(0);
            reset.setEffect(glow);
        });
        this.setBottom(reset);


        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));

        MenuButton menu = new MenuButton(StateEvent.MENU);
        this.setTop(menu);

        VBox vbox = new VBox(30);
        this.setCenter(vbox);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.TOP_CENTER);

        Text label = new Text(getLanguageMap("SETTINGS"));
        label.setFont(Font.font("", FontWeight.BOLD, 30));
        label.setFill(Commons.GRADIENT2);
        label.setUnderline(true);

        HBox hbox = new HBox(60);
        hbox.setAlignment(Pos.CENTER);

        HRainVolume = new HBox(10);
        HRainVolume.setAlignment(Pos.CENTER);


        HAcidVolume = new HBox(10);
        HAcidVolume.setAlignment(Pos.CENTER);

        HLanguage = new HBox(10);
        HLanguage.setAlignment(Pos.CENTER);

        HDifficulty = new HBox(10);
        HDifficulty.setAlignment(Pos.CENTER);

        HScreen = new HBox(10);
        HScreen.setAlignment(Pos.CENTER);

        HBackground = new HBox(10);
        HBackground.setAlignment(Pos.CENTER);

        HFps = new HBox(10);
        HFps.setAlignment(Pos.CENTER);

        vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        hbox.getChildren().addAll(HRainVolume, vBox, HAcidVolume);
        vbox.getChildren().addAll(label, hbox);
    }

    public void refresh() {
        vBox.getChildren().clear();


        addEntryScreen(Settings_SCREEN, HScreen, StateEvent.SCREEN, getLanguageMap("SCREEN"));
        addEntryTwo(HBackground, Settings_BACKGROUND, getLanguageMap("BACKGROUND"));
        addEntryTwo(HRainVolume, Settings_RAIN_Volume, getLanguageMap("RAIN Volume"));
        addEntryTwo(HAcidVolume, Settings_Effect_Volume, getLanguageMap("Effect Volume"));
        addEntryTwo(HFps, Settings_FPS, getLanguageMap("FPS"));

        String[] complexity = {getLanguageMap("Easy"), getLanguageMap("normal"),
                getLanguageMap("hard")}, language = {"РУССКИЙ", "ENGLISH"};
        addEntryOne(getLanguageMap("Complexity:"), Settings_AcidSpawner_rate, Settings_AcidSpawner_variation, Settings_AcidSpawner_count, HDifficulty, complexity);
        addEntryOne2(getLanguageMap("Language"), Settings_LANGUAGE, HLanguage, language);

        vBox.getChildren().add(HScreen);
        vBox.getChildren().add(HBackground);
        vBox.getChildren().add(HRainVolume);
        vBox.getChildren().add(HAcidVolume);
        vBox.getChildren().add(HDifficulty);
        vBox.getChildren().add(HFps);
        vBox.getChildren().add(HLanguage);
    }

    private void addEntryScreen(GameData pd, HBox hBox, EventType<StateEvent> eventType, String names) {
        hBox.getChildren().clear();
        Text name = new Text(names);
        Text setT = new Text();
        setT.setFill(Commons.colorTexOff);
        setT.setFont(Font.font("", FontWeight.BOLD, 20));
        if (pd.getValue() == 0) {
            setT.setText(getLanguageMap("WINDOW MODE"));

        } else if (pd.getValue() == 1) {
            setT.setText(getLanguageMap("FULL SCREENS MODE"));
        }

        name.setFill(Commons.colorTexOff);
        name.setFont(Font.font("", FontWeight.BOLD, 20));

        EventButton eventButtonL = new EventButton(null, 0);
        EventButton eventButtonR = new EventButton(null, 180);

        eventButtonL.setOnMouseClicked(event -> {
            eventButtons(pd, pd.getValue() - 1, setT);
            fireEvent(new StateEvent(eventType));
            SCENE_WIDTH.setVolume((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 1.2f));
            SCENE_HEIGHT.setVolume((int) (Toolkit.getDefaultToolkit().getScreenSize().height / 1.2f));
            System.out.println("SCENE_WIDTH=" + SCENE_WIDTH.getValue());
            System.out.println("SCENE_HEIGHT=" + SCENE_HEIGHT.getValue());
            save();
        });
        eventButtonR.setOnMouseClicked(event -> {
            eventButtons(pd, pd.getValue() + 1, setT);
            Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
            SCENE_WIDTH.setVolume(sSize.width);
            SCENE_HEIGHT.setVolume(sSize.height);
            System.out.println("SCENE_WIDTH=" + SCENE_WIDTH.getValue());
            System.out.println("SCENE_HEIGHT=" + SCENE_HEIGHT.getValue());
            fireEvent(new StateEvent(eventType));
            save();
        });
        hBox.getChildren().add(name);
        hBox.getChildren().add(eventButtonL);
        hBox.getChildren().add(setT);
        hBox.getChildren().add(eventButtonR);

    }


    private void addEntryTwo(HBox hBox, GameData pd, String names) {
        hBox.getChildren().clear();
        Text name = new Text(names);
        Text setT = new Text();
        setT.setFill(Commons.colorTexOff);
        setT.setFont(Font.font("", FontWeight.BOLD, 20));
        setT.setText(pd.getValue() + "");

        name.setFill(Commons.colorTexOff);
        name.setFont(Font.font("", FontWeight.BOLD, 20));

        EventButton eventButtonL = new EventButton(null, 0);
        EventButton eventButtonR = new EventButton(null, 180);

        eventButtonL.setOnMouseClicked(event -> {
            eventButtons(pd, pd.getValue() - 1, setT);

        });
        eventButtonR.setOnMouseClicked(event -> {
            eventButtons(pd, pd.getValue() + 1, setT);

        });

        if (pd.getName().equals(Settings_FPS.getName())) {
            if (pd.getValue() == 0) {
                setT.setText(getLanguageMap("off"));
            } else if (pd.getValue() == 1) {
                setT.setText(getLanguageMap("average FPS"));
            } else if (pd.getValue() == 2) {
                setT.setText(getLanguageMap("instant FPS"));
            }
        }
        hBox.getChildren().add(name);
        hBox.getChildren().add(eventButtonL);
        hBox.getChildren().add(setT);
        hBox.getChildren().add(eventButtonR);


    }

    private void eventButtons(GameData pd, int value, Text setT) {

        if (pd.getName().equals(Settings_RAIN_Volume.getName())) {
            Application.setVolume(value);
            pd.setVolume(value);
        } else if (pd.getName().equals(Settings_Effect_Volume.getName())) {
            // SoundEffects.setVolume(value);
            SoundEffects.playNew("Hard_kick_drum.wav");
            pd.setVolume(value);
        } else if (pd.getName().equals(Settings_SCREEN.getName())) {

            pd.setVolume(value);
        }

        if (pd.getName().equals(Settings_BACKGROUND.getName())) {
            pd.setVolume(value);
        }
        if (pd.getName().equals(Settings_FPS.getName())) {

            pd.setVolume(value);
        }

        save();
        this.refresh();
    }

    private void addEntryOne(String names, GameData rate, GameData variation, GameData count, HBox hBox, String[] states) {
        hBox.getChildren().clear();
        Text name = new Text(names);
        Text difficulty = new Text();

        difficulty.setFill(Commons.colorTexOff);
        difficulty.setFont(Font.font("", FontWeight.BOLD, 20));

        name.setFill(Commons.colorTexOff);
        name.setFont(Font.font("", FontWeight.BOLD, 20));

        difficulty.setText(states[count.getValue() - 1]);
        difficulty.setOnMouseClicked(event -> {
            if (count.getValue() == 1) {
                rate.setVolume(5);
                variation.setVolume(5);
                count.setVolume(2);
            } else if (count.getValue() == 2) {
                rate.setVolume(1);
                variation.setVolume(10);
                count.setVolume(3);
            } else if (count.getValue() == 3) {
                rate.setVolume(10);
                variation.setVolume(1);
                count.setVolume(1);
            }
            save();
            this.refresh();
        });

        difficulty.setOnMouseEntered(event -> {
            difficulty.setFill(Commons.colorTexOff);
            Glow glow = new Glow(1000);
            difficulty.setEffect(glow);
        });

        difficulty.setOnMouseExited(event -> {
            difficulty.setFill(Commons.colorTexOff);
            Glow glow = new Glow(0);
            difficulty.setEffect(glow);
        });

        hBox.getChildren().add(name);
        hBox.getChildren().add(difficulty);

    }

    private void addEntryOne2(String names, GameData gd, HBox hBox, String[] states) {
        hBox.getChildren().clear();
        Text name = new Text(names);
        Text difficulty = new Text();

        difficulty.setFill(Commons.colorTexOff);
        difficulty.setFont(Font.font("", FontWeight.BOLD, 20));

        name.setFill(Commons.colorTexOff);
        name.setFont(Font.font("", FontWeight.BOLD, 20));

        difficulty.setText(states[gd.getValue()]);
        difficulty.setOnMouseClicked(event -> {
            if (gd.getValue() == 1) {
                gd.setVolume(0);
                sqlite(Settings_LANGUAGE.getValue());
                Application.pane();

            } else if (gd.getValue() == 0) {
                gd.setVolume(1);
                sqlite(Settings_LANGUAGE.getValue());
                Application.pane();
                //new StateEvent(StateEvent.MENU);

            }
            save();

            fireEvent(new StateEvent(StateEvent.SETTINGS));
        });

        difficulty.setOnMouseEntered(event -> {

            difficulty.setFill(Commons.colorTexOff);
            Glow glow = new Glow(1000);
            difficulty.setEffect(glow);
        });

        difficulty.setOnMouseExited(event -> {
            difficulty.setFill(Commons.colorTexOff);
            Glow glow = new Glow(0);
            difficulty.setEffect(glow);
        });

        hBox.getChildren().add(name);
        hBox.getChildren().add(difficulty);

    }

}