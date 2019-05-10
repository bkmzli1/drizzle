package ru.bkmz.drizzle.experimental;

import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ru.bkmz.drizzle.Application;
import ru.bkmz.drizzle.entity.mob.Acid;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.util.Commons;


import java.awt.*;

import static ru.bkmz.drizzle.level.GameData.*;
import static ru.bkmz.drizzle.util.Sound.playEffectClik;

public class SetingsPane extends BorderPane {
    private VBox vBox;
    private HBox HRainVolume, HAcidVolume, HDifficulty, HScreen, Hbackground;

    private Color color1 = Color.rgb(0, 145, 225), color2 = Color.rgb(0, 225, 225);


    public SetingsPane() {
        Text reset = new Text("Сбросс настроек");
        reset.setOnMouseClicked(event -> {
            eventButtons(RAIN_Volume, 0);
            eventButtons(Effect_Volume, 0);
            AcidSpawner_rate.setVolume(10);
            AcidSpawner_variation.setVolume(10);
            AcidSpawner_count.setVolume(1);
            SCENE_WIDTH.setVolume((int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.2f));
            SCENE_HEIGHT.setVolume((int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.2f));
            SCREEN.setVolume(0);
            System.out.println("SCENE_WIDTH=" + SCENE_WIDTH.getValue());
            System.out.println("SCENE_HEIGHT=" + SCENE_HEIGHT.getValue());
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

        Text label = new Text("Настройки");
        label.setFont(Font.font("", FontWeight.BOLD, 30));
        label.setFill(Commons.GRADIENT2);
        label.setUnderline(true);

        HBox hbox = new HBox(60);
        hbox.setAlignment(Pos.CENTER);

        HRainVolume = new HBox(10);
        HRainVolume.setAlignment(Pos.CENTER);

        HAcidVolume = new HBox(10);
        HAcidVolume.setAlignment(Pos.CENTER);

        HDifficulty = new HBox(10);
        HDifficulty.setAlignment(Pos.CENTER);

        HScreen = new HBox(10);
        HScreen.setAlignment(Pos.CENTER);

        Hbackground = new HBox(10);
        Hbackground.setAlignment(Pos.CENTER);

        vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        hbox.getChildren().addAll(HRainVolume, vBox, HAcidVolume);
        vbox.getChildren().addAll(label, hbox);
    }

    public void refresh() {
        vBox.getChildren().clear();



        addEntryScreen(SCREEN, HScreen, StateEvent.SCREEN);
        addEntryTwo(Hbackground,  BACKGROUND);
        addEntryTwo(HRainVolume,  RAIN_Volume);
        addEntryTwo(HAcidVolume,  Effect_Volume);
        addEntryOne("Сложность:", AcidSpawner_rate, AcidSpawner_variation, AcidSpawner_count, HDifficulty);

        vBox.getChildren().add(HScreen);
        vBox.getChildren().add(Hbackground);
        vBox.getChildren().add(HRainVolume);
        vBox.getChildren().add(HAcidVolume);
        vBox.getChildren().add(HDifficulty);
    }

    private void addEntryScreen(GameData pd, HBox hBox, EventType<StateEvent> eventType) {
        hBox.getChildren().clear();
        Text name = new Text(pd.getName());
        Text setT = new Text();
        setT.setFill(Color.CORNFLOWERBLUE);
        setT.setFont(Font.font("", FontWeight.BOLD, 20));
        if (pd.getValue() == 0) {
            setT.setText("ОКОННЫЙ РЕЖИМ");

        } else if (pd.getValue() == 1) {
            setT.setText("ПОЛНО ЭКРАННЫ РЕЖИМ");
        }

        name.setFill(Color.CORNFLOWERBLUE);
        name.setFont(Font.font("", FontWeight.BOLD, 20));

        EventButton eventButtonL = new EventButton(null, 0);
        EventButton eventButtonR = new EventButton(null, 180);

        eventButtonL.setOnMouseClicked(event -> {
            eventButtons(pd, pd.getValue() - 1);
            fireEvent(new StateEvent(eventType));
            SCENE_WIDTH.setVolume((int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.2f) );
            SCENE_HEIGHT.setVolume((int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.2f));
            System.out.println("SCENE_WIDTH=" + SCENE_WIDTH.getValue());
            System.out.println("SCENE_HEIGHT=" + SCENE_HEIGHT.getValue());
            save();
        });
        eventButtonR.setOnMouseClicked(event -> {
            eventButtons(pd, pd.getValue() + 1);
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

    private void addEntryOne(String names, GameData rate, GameData variation, GameData count, HBox hBox) {
        hBox.getChildren().clear();
        Text name = new Text(names);
        Text difficulty  = new Text();
        difficulty.setFill(Color.CORNFLOWERBLUE);
        difficulty.setFont(Font.font("", FontWeight.BOLD, 20));
        name.setFill(Color.CORNFLOWERBLUE);
        name.setFont(Font.font("", FontWeight.BOLD, 20));
        if (count.getValue() == 1) {
            difficulty.setText("ЛЕГКАЯ");
        } else if (count.getValue() == 2) {
            difficulty.setText("СРЕДНЯЯ");
        } else if (count.getValue() == 3) {
            difficulty.setText("СЛОЖНАЯ");
        }
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
            playEffectClik();
            difficulty.setFill(color2);
            Glow glow = new Glow(1000);
            difficulty.setEffect(glow);
        });

        difficulty.setOnMouseExited(event -> {
            difficulty.setFill(color1);
            Glow glow = new Glow(0);
            difficulty.setEffect(glow);
        });

        hBox.getChildren().add(name);
        hBox.getChildren().add(difficulty);

    }

    private void addEntryTwo(HBox hBox,  GameData pd) {
        hBox.getChildren().clear();
        Text name = new Text(pd.getName());
        Text setT = new Text();
        setT.setFill(Color.CORNFLOWERBLUE);
        setT.setFont(Font.font("", FontWeight.BOLD, 20));
        setT.setText(pd.getValue() + "");

        name.setFill(Color.CORNFLOWERBLUE);
        name.setFont(Font.font("", FontWeight.BOLD, 20));

        EventButton eventButtonL = new EventButton(null, 0);
        EventButton eventButtonR = new EventButton(null, 180);

        eventButtonL.setOnMouseClicked(event -> {
            eventButtons(pd, Integer.parseInt(setT.getText()) - 1);

        });
        eventButtonR.setOnMouseClicked(event -> {
            eventButtons(pd, Integer.parseInt(setT.getText()) + 1);

        });
        hBox.getChildren().add(name);
        hBox.getChildren().add(eventButtonL);
        hBox.getChildren().add(setT);
        hBox.getChildren().add(eventButtonR);


    }

    private void eventButtons(GameData pd, int value) {
        if (0 <= value && value <= 10) {
            float valueF = value / 10f;
            if (pd.getName().equals(RAIN_Volume.getName())) {
                System.out.println("Application");
                Application.mediaPlayer.setVolume(valueF);
                pd.setVolume(value);
            } else if (pd.getName().equals(Effect_Volume.getName())) {
                System.out.println("Acid");
                Acid.sound.setVolume(valueF);
                pd.setVolume(value);
            } else if (pd.getName().equals(SCREEN.getName())) {
                System.out.println("Screen");
                pd.setVolume(value);
            }

        }
        if (pd.getName().equals(BACKGROUND.getName())) {
            pd.setVolume(value);
            fireEvent(new StateEvent(StateEvent.BACKGROUND));
        }
        save();
        this.refresh();
    }


}