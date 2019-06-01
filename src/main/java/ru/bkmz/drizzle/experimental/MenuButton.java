package ru.bkmz.drizzle.experimental;


import javafx.scene.effect.Glow;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.util.Commons;
import ru.bkmz.drizzle.util.ImageLoader;

import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import ru.bkmz.drizzle.util.Language;


class MenuButton extends HBox {


    MenuButton(EventType<StateEvent> eventType) {
        Text menu = new Text(Language.getLanguageMap("MENU"));
        menu.setFont(Font.font("", FontWeight.BOLD, 30));
        menu.setFill(Commons.colorTexOff);

        Glow glow = new Glow(0);
        menu.setEffect(glow);
        menu.setOpacity(0.6);
        menu.setEffect(glow);

        this.setOnMouseClicked(event -> {
            fireEvent(new StateEvent(eventType));
        });

        this.setOnMouseEntered(event -> {
            SoundEffects.playNew("Hard_kick_drum.wav");
            menu.setFill(Commons.colorTexOn);
            menu.setOpacity(0.8);
            glow.setLevel(1000);
            menu.setEffect(glow);
        });

        this.setOnMouseExited(event -> {
            menu.setFill(Commons.colorTexOff);
            menu.setOpacity(0.6);
            glow.setLevel(0);
            menu.setEffect(glow);
        });

        this.getChildren().add(menu);
        this.setAlignment(Pos.TOP_LEFT);
    }
}

class EventButton extends HBox {

    EventButton(EventType<StateEvent> eventType, int rotate) {

        ImageView imageView = new ImageView(ImageLoader.IMAGE_LOADER.getImage("gui/icons/back"));
        imageView.setOpacity(0.3);
        imageView.setLayoutY(0);
        imageView.setScaleY(1);
        imageView.setTranslateY(10);

        imageView.setRotate(rotate);

        this.setOnMouseClicked(event -> {
            fireEvent(new StateEvent(eventType));
        });
        this.setOnMouseEntered(event -> {
            SoundEffects.playNew("Hard_kick_drum.wav");
            imageView.setOpacity(0.8);
            Glow glow = new Glow(1000);
            imageView.setEffect(glow);
        });

        this.setOnMouseExited(event -> {
            imageView.setOpacity(0.3);
            Glow glow = new Glow(0);
            imageView.setEffect(glow);
        });
        this.getChildren().add(imageView);
        this.setAlignment(Pos.CENTER);
    }
}