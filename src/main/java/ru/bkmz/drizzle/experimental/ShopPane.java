package ru.bkmz.drizzle.experimental;

import javafx.scene.effect.Glow;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.util.Commons;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static ru.bkmz.drizzle.util.Sound.playEffectClik;

public class ShopPane extends BorderPane {

    private VBox vbox1, vbox2, vbox3, vbox4;
    private Text text = new Text();

    private static void addEntry(ShopPane p, VBox v, String name, GameData d, int id) {


        Text t1 = new Text(name);
        Glow glow = new Glow(0);
        if (d.getValue() == d.getMax()) {
            if (GameData.PLAYER_SELECTEDSKILL.getValue() == id) {
                t1.setFill(Color.LAWNGREEN);
            } else {
                t1.setFill(Color.AQUAMARINE);
            }
        } else {
            t1.setFill(Color.DARKBLUE);
        }
        t1.setOpacity(0.5);
        t1.setFont(Font.font("", FontWeight.BOLD, 20));
        t1.setOnMouseClicked(event -> {
            if (d.getValue() == d.getMax()) {
                if (GameData.PLAYER_SELECTEDSKILL.getValue() == id) {
                    GameData.PLAYER_SELECTEDSKILL.setVolume(0);
                } else {
                    GameData.PLAYER_SELECTEDSKILL.setVolume(id);
                }

                p.refresh();
            }
        });

        t1.setOnMouseEntered(event -> {
            playEffectClik();
            t1.setOpacity(1);
            glow.setLevel(1);
            t1.setEffect(glow);
        });

        t1.setOnMouseExited(event -> {
            t1.setOpacity(0.5);
            glow.setLevel(0);
            t1.setEffect(glow);
        });

        v.getChildren().add(t1);
    }
    private static void addEntry(ShopPane p, VBox v1, VBox v2, VBox v3, GameData pd) {
        Glow glow = new Glow(0);
        Text t1 = new Text(pd.getName());
        t1.setOpacity(0.5);

        if (pd.getValue() < pd.getMax()) {
            if (GameData.PLAYER_POINTS.getValue() > 0) {
                t1.setFill(Color.CORNSILK);
            } else {
                t1.setFill(Color.BLUE);
            }
        } else {
            t1.setFill(Color.GOLD);
        }

        t1.setFont(Font.font("", FontWeight.BLACK, 20));
        t1.setOnMouseClicked(event -> {
            if (GameData.PLAYER_POINTS.getValue() > 0) {
                if (pd.getValue() < pd.getMax()) {
                    pd.increment();
                    GameData.PLAYER_POINTS.incrementBy(-1);
                    GameData.save();
                    p.refresh();
                }
            }
        });

        t1.setOnMouseEntered(event -> {
            playEffectClik();
            t1.setOpacity(1);
            glow.setLevel(0.5);
            t1.setEffect(glow);
        });

        t1.setOnMouseExited(event -> {
            t1.setOpacity(0.5);
            glow.setLevel(0);
            t1.setEffect(glow);
        });

        Text t2 = new Text("" + pd.getValue());
        t2.setFill(Color.MEDIUMAQUAMARINE);
        t2.setFont(Font.font("", FontWeight.NORMAL, 20));

        v1.getChildren().add(t1);
        v2.getChildren().add(t2);
    }

    public ShopPane() {
        Text cleaning = new Text("Сбросс всего");
        cleaning.setOnMouseClicked(event -> {
            fireEvent(new StateEvent(StateEvent.COLLECTION));
            refresh();

        });
        cleaning.setFont(Font.font("", FontWeight.BOLD, 30));
        cleaning.setFill(Commons.GRADIENT2);
        cleaning.setUnderline(true);
        cleaning.setOpacity(0.5);
        cleaning.setOnMouseEntered(event -> {
            cleaning.setOpacity(1);
            Glow glow = new Glow(1000);
            cleaning.setEffect(glow);
        });

        cleaning.setOnMouseExited(event -> {
            cleaning.setOpacity(0.5);
            Glow glow = new Glow(0);
            cleaning.setEffect(glow);
        });
        this.setBottom(cleaning);

        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));

        text.setFill(Color.BLUE);
        text.setFont(Font.font("", FontWeight.BOLD, 20));

        HBox hbox = new HBox(new MenuButton(StateEvent.MENU), text);
        hbox.setSpacing(700);
        this.setTop(hbox);

        VBox vbox = new VBox(30);
        this.setCenter(vbox);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.TOP_CENTER);

        Text label = new Text("улучшение");
        label.setFont(Font.font("", FontWeight.BOLD, 30));
        label.setFill(Commons.GRADIENT2);
        label.setUnderline(true);

        HBox hbox2 = new HBox(60);
        hbox2.setAlignment(Pos.CENTER);

        vbox1 = new VBox(10);
        vbox1.setAlignment(Pos.CENTER);

        vbox2 = new VBox(10);
        vbox2.setAlignment(Pos.CENTER_LEFT);

        vbox3 = new VBox(10);
        vbox3.setAlignment(Pos.CENTER_RIGHT);

        hbox2.getChildren().addAll(vbox2, vbox1, vbox3);

        vbox4 = new VBox(15);
        vbox4.setAlignment(Pos.CENTER);

        Text label2 = new Text("скилы");
        label2.setFont(Font.font("", FontWeight.BOLD, 30));
        label2.setFill(Commons.GRADIENT2);
        label2.setUnderline(true);

        vbox4.getChildren().add(label2);

        vbox.getChildren().addAll(label, hbox2, vbox4);
    }

    public void refresh() {
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();

        text.setText("улучшения: " + GameData.PLAYER_POINTS.getValue());

        addEntry(this, vbox1, vbox2, vbox3, GameData.PLAYER_HEALTH);
        addEntry(this, vbox1, vbox2, vbox3, GameData.UPGRADE_POWERRATE);
        addEntry(this, vbox1, vbox2, vbox3, GameData.UPGRADE_MOVEMENT);
        addEntry(this, vbox1, vbox2, vbox3, GameData.UPGRADE_DOUBLEXP);
        addEntry(this, vbox1, vbox2, vbox3, GameData.UPGRADE_SHIELDSPAWN);
        addEntry(this, vbox1, vbox2, vbox3, GameData.UPGRADE_SHOCKWAVE);

        if (vbox4.getChildren().size() > 1) {
            vbox4.getChildren().subList(1, vbox4.getChildren().size()).clear();
        }

        addEntry(this, vbox4, "КУПУТ ДОЖДЮ", GameData.UPGRADE_SHOCKWAVE, 1);
        addEntry(this, vbox4, "ЩИТ НЕ СПАСЁТ", GameData.UPGRADE_SHIELDSPAWN, 2);
        addEntry(this, vbox4, "ФААААРМММ", GameData.UPGRADE_DOUBLEXP, 3);
    }
}
