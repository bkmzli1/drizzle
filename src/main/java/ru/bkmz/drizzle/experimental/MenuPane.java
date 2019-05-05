package ru.bkmz.drizzle.experimental;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.util.Commons;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuPane extends GridPane {

    public MenuPane() {
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setVgap(10);
        this.setHgap(10);
        this.setPadding(new Insets(10, 10, 10, 15));
        this.setAlignment(Pos.CENTER);

        Text label = new Text("ДОЖДЬ");
        label.setFont(Font.font("", FontWeight.LIGHT, 100));
        label.setFill(Commons.GRADIENT);
        label.setUnderline(true);
        Glow glow = new Glow(1000);
        label.setEffect(glow);

        Reflection reflection = new Reflection();


        reflection.setBottomOpacity(0.0);


        reflection.setTopOpacity(0.1);

        reflection.setTopOffset(10);


        reflection.setFraction(0.7);
        reflection.setInput(glow);

        label.setEffect(reflection);


        this.add(label, 0, 0);

        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(new MenuItem("ИГРАТЬ", StateEvent.PLAY),
                                  new MenuItem("УЛУЧШЕНИЕ", StateEvent.SHOP),
                                  new MenuItem("СТАТИСТИКА", StateEvent.STAT),
                                  new MenuItem("НАСТРОЙКИ",StateEvent.STINGS),
                                  new MenuItem("  УПРОВЛЕНИЕ\n             И\nИНФОРМАЦЫЯ", StateEvent.HELP), new HBox(),
                                  new MenuItem("ВЫХОД", StateEvent.QUIT));

        this.add(vbox, 0, 1);
    }
}
