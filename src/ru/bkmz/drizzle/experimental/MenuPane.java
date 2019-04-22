/*
 * DISCLAIMER:
 * 
 * Content of this class is purely experimental and should not be used as a measurement of quality
 * of this project. It is distributed AS-IS without any guarantees or rights reserved.
 */

package ru.bkmz.drizzle.experimental;

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

    /*
     * Constructors
     */
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
