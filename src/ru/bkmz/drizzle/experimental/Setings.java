

package ru.bkmz.drizzle.experimental;

import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.util.Commons;

import static ru.bkmz.drizzle.level.GameData.*;

public class Setings extends BorderPane {
    private VBox vbox2;
    private HBox Hbox1, Hbox2;
    private static Text rainVolume = new Text(), acidVolume = new Text();

    private static void addEntry(HBox HBox1, HBox hBox, HBox HBox3, VBox vbox2, String s1, String s2) {
        Text t2 = new Text();
        rainVolume.setText(s1);
        t2.setText(s2);

        rainVolume.setFill(Color.CORNFLOWERBLUE);
        t2.setFill(Color.CORNFLOWERBLUE);

        rainVolume.setFont(Font.font("", FontWeight.BOLD, 20));
        t2.setFont(Font.font("", FontWeight.BOLD, 20));

        int valve = ACID_Volume.getValue() + 1;
        rainVolume.setText(valve + "");

        hBox.getChildren().add(t2);
        hBox.getChildren().add(HBox1);
        hBox.getChildren().add(rainVolume);
        hBox.getChildren().add(HBox3);

    }

    public static void setRainVolume(int volume) {
        if (volume != -1) {
            rainVolume.setText(volume + "");
        }

    }
    public static void setAcidVolume(int volume) {
        if (volume != -1) {
            acidVolume.setText(volume + "");
        }
    }


    public Setings() {
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

        Hbox1 = new HBox(10);
        Hbox1.setAlignment(Pos.CENTER);

        Hbox2 = new HBox(10);
        Hbox2.setAlignment(Pos.CENTER);

        vbox2 = new VBox(10);
        vbox2.setAlignment(Pos.CENTER);

        hbox.getChildren().addAll(Hbox1, vbox2, Hbox2);
        vbox.getChildren().addAll(label, hbox);
    }


    public void refresh() {
        Hbox1.getChildren().clear();
        Hbox2.getChildren().clear();
        vbox2.getChildren().clear();

        rainVolume.setText("");
        rainVolume.setFill(Color.CORNFLOWERBLUE);
        rainVolume.setFont(Font.font("", FontWeight.BOLD, 20));
        int valveRain = RAIN_Volume.getValue();
        rainVolume.setText(valveRain + "");

        acidVolume.setText("");
        acidVolume.setFill(Color.CORNFLOWERBLUE);
        acidVolume.setFont(Font.font("", FontWeight.BOLD, 20));
        int valveAcid = ACID_Volume.getValue();
        acidVolume.setText(valveAcid + "");

        addEntry2(RAIN_Volume.getName(), StateEvent.RAIN_VOLUME_PLUS, StateEvent.RAIN_VOLUME_MINUS, Hbox2, vbox2, rainVolume);
        addEntry2(ACID_Volume.getName(), StateEvent.ACID_VOLUME_PLUS, StateEvent.ACID_VOLUME_MINUS, Hbox1, vbox2, acidVolume);



    }

    private void addEntry2(String names, EventType<StateEvent> eventTypeL, EventType<StateEvent> eventTypeR, HBox hBox, VBox vBox, Text setT) {
        Text name = new Text(names);

        name.setFill(Color.CORNFLOWERBLUE);
        name.setFont(Font.font("", FontWeight.BOLD, 20));

        EventButton eventButtonL = new EventButton(eventTypeL, false);
        EventButton eventButtonR = new EventButton(eventTypeR, true);

        hBox.getChildren().add(name);
        hBox.getChildren().add(eventButtonL);
        hBox.getChildren().add(setT);
        hBox.getChildren().add(eventButtonR);

        vBox.getChildren().add(hBox);
    }


}