package ru.bkmz.drizzle.experimental;

import javafx.scene.effect.Glow;
import ru.bkmz.drizzle.event.StateEvent;

import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class MenuItem extends HBox {

    private static final Font FONT_LAYOUT_ITEM = Font.font("", FontWeight.BOLD, 18);

    private Color color1 = Color.rgb(0, 145, 225), color2 = Color.rgb(0, 225, 225);

    MenuItem(String label, EventType<StateEvent> eventType) {
        Text labelText = new Text(label);
        labelText.setFont(FONT_LAYOUT_ITEM);
        labelText.setFill(color1);

        labelText.setOnMouseClicked(event -> {
            fireEvent(new StateEvent(eventType));
        });

        labelText.setOnMouseEntered(event -> {
            labelText.setFill(color2);
            Glow glow = new Glow(1000);
            labelText.setEffect(glow);
        });

        labelText.setOnMouseExited(event -> {
            labelText.setFill(color1);
            Glow glow = new Glow(0);
            labelText.setEffect(glow);
        });

        this.getChildren().add(labelText);
        this.setAlignment(Pos.BASELINE_CENTER);
    }


}
class MenuItemS extends HBox {

    private static final Font FONT_LAYOUT_ITEM = Font.font("", FontWeight.BOLD, 18);


    private Color color1 = Color.rgb(0, 145, 225), color2 = Color.rgb(0, 225, 225);

    MenuItemS(String label, EventType<StateEvent> eventType) {
        Text labelText = new Text(label);
        labelText.setFont(FONT_LAYOUT_ITEM);
        labelText.setFill(color1);

        labelText.setOnMouseClicked(event -> {
            fireEvent(new StateEvent(eventType));
        });

        labelText.setOnMouseEntered(event -> {
            labelText.setFill(color2);
            Glow glow = new Glow(1000);
            labelText.setEffect(glow);
        });

        labelText.setOnMouseExited(event -> {
            labelText.setFill(color1);
            Glow glow = new Glow(0);
            labelText.setEffect(glow);
        });

        this.getChildren().add(labelText);
        this.setAlignment(Pos.BASELINE_CENTER);
    }


}