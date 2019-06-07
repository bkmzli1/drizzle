package ru.bkmz.drizzle.experimental;

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

import static ru.bkmz.drizzle.util.Commons.getColor;
import static ru.bkmz.drizzle.util.Language.getLanguageMap;

public class StatPane extends BorderPane {

    private VBox vbox1, vbox2;


    private static void addEntry(VBox v1, VBox v2, GameData pd, String name) {
        Text t1 = new Text(name + ":");
        t1.setFill(getColor("colorTexOff"));
        t1.setFont(Font.font("", FontWeight.BOLD, 20));

        Text t2 = new Text("" + pd.getValue());
        t2.setFill(getColor("numbers"));
        t2.setFont(Font.font("", FontWeight.NORMAL, 20));

        v1.getChildren().add(t1);
        v2.getChildren().add(t2);
    }

    public StatPane() {
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));

        MenuButton menu = new MenuButton(StateEvent.MENU);
        this.setTop(menu);

        VBox vbox = new VBox(30);
        this.setCenter(vbox);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.TOP_CENTER);

        Text label = new Text(getLanguageMap("STATISTICS"));
        label.setFont(Font.font("", FontWeight.BOLD, 30));
        label.setFill(Commons.GRADIENT2);
        label.setUnderline(true);

        HBox hbox = new HBox(60);
        hbox.setAlignment(Pos.CENTER);

        vbox1 = new VBox(10);
        vbox1.setAlignment(Pos.CENTER_LEFT);

        vbox2 = new VBox(10);
        vbox2.setAlignment(Pos.CENTER_LEFT);

        hbox.getChildren().addAll(vbox1, vbox2);
        vbox.getChildren().addAll(label, hbox);
    }

    public void refresh() {
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();

        addEntry(vbox1, vbox2, GameData.PLAYER_LEVEL, getLanguageMap("level"));
        addEntry(vbox1, vbox2, GameData.STAT_COUNT_DAMAGE, getLanguageMap("DAMAGE"));
        addEntry(vbox1, vbox2, GameData.STAT_COUNT_EXPERIENCE, getLanguageMap("EXPERIENCE"));
        addEntry(vbox1, vbox2, GameData.STAT_COUNT_JUMP, getLanguageMap("JUMP"));
        addEntry(vbox1, vbox2, GameData.STAT_COUNT_NODES, getLanguageMap("NODES"));
        addEntry(vbox1, vbox2, GameData.STAT_COUNT_STARS, getLanguageMap("STARS"));
        addEntry(vbox1, vbox2, GameData.STAT_COUNT_SHIELD, getLanguageMap("SHIELD"));
        addEntry(vbox1, vbox2, GameData.STAT_COUNT_SKILLACTIVATION, getLanguageMap("SKILL ACTIVATION"));
    }

}
