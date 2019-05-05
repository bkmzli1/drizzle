package ru.bkmz.drizzle.experimental;

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


import static ru.bkmz.drizzle.level.GameData.*;

public class SetingsPane extends BorderPane {
    private VBox vBox;
    private HBox hBox1, hBox2, hBox3;

    private static Text rainVolume = new Text(), acidVolume = new Text(), difficulty = new Text();
    private Color color1 = Color.rgb(0, 145, 225), color2 = Color.rgb(0, 225, 225);


    public SetingsPane() {
        Text reset = new Text("Сбросс настроек");
        reset.setOnMouseClicked(event -> {
            eventButtons(RAIN_Volume, 0);
            eventButtons(ACID_Volume, 0);
            AcidSpawner_rate.setVolume(10);
            AcidSpawner_variation.setVolume(10);
            AcidSpawner_count.setVolume(1);
            save();
            refresh();
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

        hBox1 = new HBox(10);
        hBox1.setAlignment(Pos.CENTER);

        hBox2 = new HBox(10);
        hBox2.setAlignment(Pos.CENTER);

        hBox3 = new HBox(10);
        hBox3.setAlignment(Pos.CENTER);

        vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        hbox.getChildren().addAll(hBox1, vBox, hBox2);
        vbox.getChildren().addAll(label, hbox);
    }


    public void refresh() {
        hBox1.getChildren().clear();
        hBox2.getChildren().clear();
        hBox3.getChildren().clear();
        vBox.getChildren().clear();


        rainVolume.setFill(Color.CORNFLOWERBLUE);
        rainVolume.setFont(Font.font("", FontWeight.BOLD, 20));
        int valveRain = RAIN_Volume.getValue();
        rainVolume.setText(valveRain + "");


        acidVolume.setFill(Color.CORNFLOWERBLUE);
        acidVolume.setFont(Font.font("", FontWeight.BOLD, 20));
        int valveAcid = ACID_Volume.getValue();
        acidVolume.setText(valveAcid + "");


        difficulty.setFill(Color.CORNFLOWERBLUE);
        difficulty.setFont(Font.font("", FontWeight.BOLD, 20));


        addEntryTwo(RAIN_Volume.getName(), hBox1, rainVolume, RAIN_Volume);
        //addEntryTwo(ACID_Volume.getName(), hBox2, acidVolume, ACID_Volume);
        addEntryOne("Сложность:", AcidSpawner_rate, AcidSpawner_variation, AcidSpawner_count, hBox3);
        vBox.getChildren().add(hBox1);
        //vBox.getChildren().add(hBox2);
        vBox.getChildren().add(hBox3);
    }

    private void addEntryOne(String names, GameData rate, GameData variation, GameData count, HBox hBox) {
        Text name = new Text(names);
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
                variation.setVolume(1);
                count.setVolume(3);
            } else if (count.getValue() == 3) {
                rate.setVolume(10);
                variation.setVolume(10);
                count.setVolume(1);
            }
            save();
            this.refresh();
        });

        difficulty.setOnMouseEntered(event -> {
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

    private void addEntryTwo(String names, HBox hBox, Text setT, GameData pd) {
        Text name = new Text(names);

        name.setFill(Color.CORNFLOWERBLUE);
        name.setFont(Font.font("", FontWeight.BOLD, 20));

        EventButton eventButtonL = new EventButton(null, 0);
        EventButton eventButtonR = new EventButton(null, 180);

        eventButtonL.setOnMouseClicked(event -> {
            int value = Integer.parseInt(setT.getText()) + 1;
            System.out.println("+");
            eventButtons(pd, value);

        });
        eventButtonR.setOnMouseClicked(event -> {
            int value = Integer.parseInt(setT.getText()) - 1;
            System.out.println("-");
            eventButtons(pd, value);

        });
        hBox.getChildren().add(name);
        hBox.getChildren().add(eventButtonL);
        hBox.getChildren().add(setT);
        hBox.getChildren().add(eventButtonR);


    }

    private void eventButtons(GameData pd, int Volume) {
        if (0 <= Volume && Volume <= 10) {
            float VolumeF = Volume/10f;
            if (pd.getName().equals(RAIN_Volume.getName())) {
                System.out.println("Application");
                Application.mediaPlayer.setVolume(VolumeF);
                pd.setVolume(Volume);
            } else if (pd.getName().equals(ACID_Volume.getName())) {
                System.out.println("Acid");
                pd.setVolume(Volume);
            }
            save();
            this.refresh();
        }
    }


}