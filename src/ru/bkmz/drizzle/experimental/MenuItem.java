/*
 * DISCLAIMER:
 *
 * Content of this class is purely experimental and should not be used as a measurement of quality
 * of this project. It is distributed AS-IS without any guarantees or rights reserved.
 */

package ru.bkmz.drizzle.experimental;

import ru.bkmz.drizzle.event.StateEvent;

import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class MenuItem extends HBox {

    /*
     * Definitions
     */
    private static final Font FONT_LAYOUT_ITEM = Font.font("", FontWeight.BOLD, 18);

    /*
     * Constructors
     */
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
        });

        labelText.setOnMouseExited(event -> {
            labelText.setFill(color1);
        });

        this.getChildren().add(labelText);
        this.setAlignment(Pos.BASELINE_CENTER);
    }

}