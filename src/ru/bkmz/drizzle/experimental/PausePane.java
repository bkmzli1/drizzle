package ru.bkmz.drizzle.experimental;

import javafx.scene.effect.Glow;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.util.Commons;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PausePane extends BorderPane {

    public PausePane() {
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));

        Text exit = new Text("ВЫХОД");
        exit.setOnMouseClicked(event -> {
            fireEvent(new StateEvent(StateEvent.STOP));
        });
        exit.setFont(Font.font("", FontWeight.BOLD, 30));
        exit.setFill(Commons.GRADIENT2);
        exit.setUnderline(true);
        exit.setOpacity(0.5);
        exit.setOnMouseEntered(event -> {
            exit.setOpacity(1);
            Glow glow = new Glow(1000);
            exit.setEffect(glow);
        });

        exit.setOnMouseExited(event -> {
            exit.setOpacity(0.5);
            Glow glow = new Glow(0);
            exit.setEffect(glow);
        });
        this.setBottom(exit);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Text label = new Text("ВРЕМЯ КАПУТ");
        label.setFont(Font.font("", FontWeight.BOLD, 30));
        label.setFill(Commons.GRADIENT2);
        label.setUnderline(true);

        Text text = new Text("Для востановление времени нажмите на ESC");
        text.setFont(Font.font("", FontWeight.BOLD, 15));
        text.setFill(Color.rgb(0,225,225));

        vbox.getChildren().addAll(label, text);

        this.setCenter(vbox);
    }

}
