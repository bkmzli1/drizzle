package ru.bkmz.drizzle.experimental;

import javafx.scene.effect.Glow;
import javafx.scene.text.TextAlignment;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.util.Commons;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static ru.bkmz.drizzle.util.Commons.getColor;
import static ru.bkmz.drizzle.util.Language.getLanguageMap;


public class ShopPane extends BorderPane {

    private VBox vbox1, vbox2, vbox3,vbox4;
    private Text text = new Text();
    private HBox hbox;
    private static void addEntry(ShopPane p, VBox v, String name, GameData d, int id) {


        Text t1 = new Text(name);
        Glow glow = new Glow(0);
        if (d.getValue() == d.getMax()) {
            if (GameData.PLAYER_SELECTEDSKILL.getValue() == id) {
                t1.setFill(getColor("skillOn"));
            } else {
                t1.setFill(getColor("skillOff"));
            }
        } else {
            t1.setFill(getColor("skillDisabled"));
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
            SoundEffects.playNew("Hard_kick_drum.wav");
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

    private static void addEntry(ShopPane p, VBox v4, VBox v1, VBox v2, GameData pd, String name) {
        Glow glow = new Glow(0);
        Text t1 = new Text(name);
        t1.setOpacity(0.5);
        t1.setFont(Font.font("", FontWeight.BLACK, 20));
        t1.setTextAlignment(TextAlignment.CENTER);
        if (pd.getValue() < pd.getMax()) {
            if (GameData.PLAYER_POINTS.getValue() > 0) {

                t1.setFill(getColor("canBuy"));
            } else {
                t1.setFill(getColor("notBuy"));
            }
        } else {
            t1.setFill(getColor("buyFull"));
        }


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
            SoundEffects.playNew("Hard_kick_drum.wav");
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
        t2.setFill(getColor("colorTexOff"));
        t2.setFont(Font.font("", FontWeight.NORMAL, 20));
        Text t3 = new Text("" + pd.getValue());
        t3.setFill(getColor("colorTexOff"));
        t3.setFont(Font.font("", FontWeight.NORMAL, 20));

        v1.getChildren().add(t1);
        v2.getChildren().add(t2);
        v4.getChildren().add(t3);
    }

    public ShopPane() {
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));

        text.setFill(getColor("numbers"));
        text.setFont(Font.font("", FontWeight.BOLD, 20));

        hbox = new HBox(new MenuButton(StateEvent.MENU));

        //hbox.setSpacing(GameData.SCENE_WIDTH.getValue() - 350 - (String.valueOf( GameData.PLAYER_POINTS.getValue()).length()*21));
        //380
        this.setTop(hbox);

        VBox vbox = new VBox(30);
        this.setCenter(vbox);
        int x = 0;
        vbox.setPadding(new Insets(x, x, x, x));
        vbox.setAlignment(Pos.TOP_CENTER);

        Text label = new Text(getLanguageMap("IMPROVEMENT"));
        label.setFont(Font.font("", FontWeight.BOLD, 30));
        label.setFill(Commons.GRADIENT2);
        label.setUnderline(true);

        HBox hbox2 = new HBox(10);
        hbox2.setAlignment(Pos.TOP_CENTER);

        vbox1 = new VBox(10);
        vbox1.setAlignment(Pos.TOP_CENTER);

        vbox2 = new VBox(10);
        vbox2.setAlignment(Pos.TOP_CENTER);

        vbox4 = new VBox(10);
        vbox4.setAlignment(Pos.TOP_CENTER);

        hbox2.getChildren().addAll(vbox2, vbox1, vbox4);

        vbox3 = new VBox(15);
        vbox3.setAlignment(Pos.TOP_CENTER);

        Text label2 = new Text(getLanguageMap("skill"));
        label2.setFont(Font.font("", FontWeight.BOLD, 30));
        label2.setFill(Commons.GRADIENT2);
        label2.setUnderline(true);

        vbox3.getChildren().add(label2);

        vbox.getChildren().addAll(label,text, hbox2, vbox3);

        Text cleaning = new Text(getLanguageMap("Reset all"));
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
    }

    public void refresh() {
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox4.getChildren().clear();


        text.setText(getLanguageMap("Number of points:") + GameData.PLAYER_POINTS.getValue());

        addEntry(this, vbox4,vbox1, vbox2, GameData.PLAYER_HEALTH,getLanguageMap("heal"));
        addEntry(this, vbox4, vbox1, vbox2, GameData.UPGRADE_POWERRATE,getLanguageMap("Electricity Tariff"));
        addEntry(this, vbox4, vbox1, vbox2, GameData.UPGRADE_MOVEMENT,getLanguageMap("motion update"));
        addEntry(this, vbox4, vbox1, vbox2, GameData.UPGRADE_DOUBLEXP,getLanguageMap("doubling experience"));
        addEntry(this, vbox4, vbox1, vbox2, GameData.UPGRADE_SHIELDSPAWN,getLanguageMap("Shield"));
        addEntry(this, vbox4, vbox1, vbox2, GameData.UPGRADE_SHOCKWAVE,getLanguageMap("Shock Wave Skill"));
        if (vbox3.getChildren().size() > 1) {
            vbox3.getChildren().subList(1, vbox3.getChildren().size()).clear();
        }

        addEntry(this, vbox3, getLanguageMap("power WAVE"), GameData.UPGRADE_SHOCKWAVE, 1);
        addEntry(this, vbox3, getLanguageMap("SHIELD"), GameData.UPGRADE_SHIELDSPAWN, 2);
        addEntry(this, vbox3, getLanguageMap("PHARM"), GameData.UPGRADE_DOUBLEXP, 3);
    }
}
