/*
 * DISCLAIMER:
 * 
 * Content of this class is purely experimental and should not be used as a measurement of quality
 * of this project. It is distributed AS-IS without any guarantees or rights reserved.
 */

package ru.bkmz.drizzle.experimental;

import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.util.ImageLoader;

import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

class MenuButton extends HBox {

    /*
     * Constructors
     */
    MenuButton(EventType<StateEvent> eventType) {
        ImageView imageView = new ImageView(ImageLoader.INSTANCE.getImage("gui/icons/back"));
        imageView.setOpacity(0.1);

        this.setOnMouseClicked(event -> {
            fireEvent(new StateEvent(eventType));
        });

        this.setOnMouseEntered(event -> {
            imageView.setOpacity(0.8);
        });

        this.setOnMouseExited(event -> {
            imageView.setOpacity(0.3);
        });

        this.getChildren().add(imageView);
        this.setAlignment(Pos.TOP_LEFT);
    }
}
