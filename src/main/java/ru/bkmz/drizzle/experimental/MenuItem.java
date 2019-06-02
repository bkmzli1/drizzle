package ru.bkmz.drizzle.experimental;

import javafx.scene.effect.Glow;
import javafx.scene.text.TextAlignment;
import ru.bkmz.drizzle.event.StateEvent;

import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static ru.bkmz.drizzle.util.Commons.getColor;


class MenuItem extends HBox {

    private static final Font FONT_LAYOUT_ITEM = Font.font("", FontWeight.BOLD, 18);


    MenuItem(String label, EventType<StateEvent> eventType) {

        Text labelText = new Text(label);
        labelText.setFont(FONT_LAYOUT_ITEM);
        labelText.setFill(getColor("colorTexOff"));
        labelText.setOpacity(0.8);
        labelText.setOnMouseClicked(event -> {
            fireEvent(new StateEvent(eventType));
        });
        labelText.setTextAlignment(TextAlignment.CENTER);

        labelText.setOnMouseEntered(event -> {
            SoundEffects.playNew("Hard_kick_drum.wav");
            labelText.setFill(getColor("colorTexOn"));
            Glow glow = new Glow(1000);
            labelText.setOpacity(1);
            labelText.setEffect(glow);
        });

        labelText.setOnMouseExited(event -> {
            labelText.setFill(getColor("colorTexOff"));
            Glow glow = new Glow(0);
            labelText.setOpacity(0.8);
            labelText.setEffect(glow);
        });

        this.getChildren().add(labelText);
        this.setAlignment(Pos.BASELINE_CENTER);
    }


}