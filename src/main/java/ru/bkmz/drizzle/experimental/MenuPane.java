package ru.bkmz.drizzle.experimental;

import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.text.TextAlignment;
import ru.bkmz.drizzle.Application;
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

import static ru.bkmz.drizzle.util.Language.getLanguageMap;

public class MenuPane extends GridPane {


    public MenuPane() {
        Text label = new Text(getLanguageMap("gameName"));
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setVgap(10);
        this.setHgap(10);
        this.setPadding(new Insets(10, 10, 10, 15));
        this.setAlignment(Pos.CENTER);
        label.setFont(Font.font("", FontWeight.LIGHT, 100));
        label.setFill(Commons.GRADIENT);
        label.setOpacity(0.6);
        label.setUnderline(true);
        label.setTextAlignment(TextAlignment.CENTER);
        Glow glow = new Glow(1000);
        label.setEffect(glow);

        Reflection reflection = new Reflection();


        reflection.setBottomOpacity(0.0);


        reflection.setTopOpacity(0.);

        reflection.setTopOffset(20);


        reflection.setFraction(0.7);
        reflection.setInput(glow);

        label.setEffect(reflection);


        this.add(label, 0, 0);

        VBox vbox = new VBox(17);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(new MenuItem(getLanguageMap("game"), StateEvent.PLAY),
                new MenuItem(getLanguageMap("onlineGame"), StateEvent.ONLINE),
                new MenuItem(getLanguageMap("IMPROVEMENT"), StateEvent.SHOP),
                new MenuItem(getLanguageMap("STATISTICS"), StateEvent.STAT),
                new MenuItem(getLanguageMap("SETTINGS"), StateEvent.SETTINGS),
                new MenuItem(getLanguageMap("MANAGEMENT_And_INFORMATION"), StateEvent.HELP), new HBox(),
                new MenuItem(getLanguageMap("exit"), StateEvent.QUIT));
        this.add(vbox, 0, 1);
    }

}
