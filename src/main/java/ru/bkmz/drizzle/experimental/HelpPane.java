package ru.bkmz.drizzle.experimental;

import ru.bkmz.drizzle.event.StateEvent;
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
import javafx.scene.image.ImageView;

import ru.bkmz.drizzle.util.ImageLoader;




public class HelpPane extends BorderPane {


    private static void addEntry(VBox v1, VBox v2, String s1, String s2) {
        Text t1 = new Text(s1);
        t1.setFill(Color.BLUE);
        t1.setFont(Font.font("", FontWeight.BOLD, 20));

        Text t2 = new Text(s2);
        t2.setFill(Commons.color2);
        t2.setFont(Font.font("", FontWeight.NORMAL, 20));

        v1.getChildren().add(t1);
        v2.getChildren().add(t2);
    }

    private void addEntry2(VBox v1, VBox v2, String s1, String s2) {
        ImageView imageView = new ImageView(ImageLoader.INSTANCE.getImage(s1));
        Text t2 = new Text(s2);
        t2.setFill(Commons.color2);
        t2.setFont(Font.font("", FontWeight.NORMAL, 20));

        v1.getChildren().add(imageView);
        v2.getChildren().add(t2);
    }

    public HelpPane() {
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));

        MenuButton menu = new MenuButton(StateEvent.MENU);
        this.setTop(menu);

        VBox vbox = new VBox(30);
        this.setCenter(vbox);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.TOP_CENTER);

        Text label = new Text("УПРОВЛЕНИЕ");
        label.setFont(Font.font("", FontWeight.BOLD, 30));
        label.setFill(Commons.GRADIENT2);
        label.setUnderline(true);

        HBox hbox = new HBox(60);
        hbox.setAlignment(Pos.CENTER);

        VBox vbox1 = new VBox(10);
        vbox1.setAlignment(Pos.CENTER);

        VBox vbox2 = new VBox(10);
        vbox2.setAlignment(Pos.CENTER_LEFT);

        addEntry(vbox1, vbox2, "A", "ВЛЕВО");
        addEntry(vbox1, vbox2, "D", "ВПРАВО");
        addEntry(vbox1, vbox2, "SPACE", "ПРЫЖОК");
        addEntry(vbox1, vbox2, "F", "СКИЛЛ");

        hbox.getChildren().addAll(vbox1, vbox2);

        Text label2 = new Text("ИНФОРМАЦЫЯ");
        label2.setFont(Font.font("", FontWeight.BOLD, 30));
        label2.setFill(Commons.GRADIENT2);
        label2.setUnderline(true);

        HBox hbox2 = new HBox(60);
        hbox2.setAlignment(Pos.CENTER);

        VBox vbox3 = new VBox(10);
        vbox3.setAlignment(Pos.CENTER);

        VBox vbox4 = new VBox(10);
        vbox4.setAlignment(Pos.CENTER_LEFT);

        addEntry2(vbox3, vbox4, "entity/acid", "ЭТО КАПЛИ И ОТ НИХ НАДО УВОРАЧИВАТЬСЯ");
        addEntry2(vbox3, vbox4, "entity/armor", "ЭТО БРОНЯ ОНА ДАЁТ ЗАЩИТУ ТОЛЬКО ПО ВЕРХ XP");
        addEntry2(vbox3, vbox4, "entity/energy", "ЭТО ЭНЕРГИЯ ОНА ЗАРЯЖАЕТ СПОСОБНОСТИ");
        addEntry2(vbox3, vbox4, "entity/star", "ЭТО ЗВЕЗДА И ОНА 10 EX");

        hbox2.getChildren().addAll(vbox3, vbox4);

        vbox.getChildren().addAll(label, hbox, label2, hbox2);
    }


}
