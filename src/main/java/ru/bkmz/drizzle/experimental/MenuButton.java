package ru.bkmz.drizzle.experimental;


import javafx.scene.effect.Glow;

import javafx.scene.paint.Color;
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


import static ru.bkmz.drizzle.util.Sound.playEffectClik;

class MenuButton extends HBox {


    MenuButton(EventType<StateEvent> eventType) {
        ImageView imageView = new ImageView(ImageLoader.INSTANCE.getImage("gui/icons/back"));

        Text menu = new Text("МЕНЮ");
        menu.setFont(Font.font("", FontWeight.BOLD, 30));
        menu.setFill(Commons.color3);

        Glow glow = new Glow(0);
        menu.setEffect(glow);
        menu.setOpacity(0.3);
        menu.setEffect(glow);

        this.setOnMouseClicked(event -> {
            fireEvent(new StateEvent(eventType));
        });

        this.setOnMouseEntered(event -> {
            playEffectClik();
            menu.setOpacity(0.8);
            glow.setLevel(1000);
            menu.setEffect(glow);
        });

        this.setOnMouseExited(event -> {
            menu.setOpacity(0.3);
            glow.setLevel(0);
            menu.setEffect(glow);
        });

        this.getChildren().add(menu);
        this.setAlignment(Pos.TOP_LEFT);
    }
}

class EventButton extends HBox {

    EventButton(EventType<StateEvent> eventType, int rotate) {

        ImageView imageView = new ImageView(ImageLoader.INSTANCE.getImage("gui/icons/back"));
        imageView.setOpacity(0.1);
        imageView.setLayoutY(0);
        imageView.setScaleY(1);
        imageView.setTranslateY(10);

        imageView.setRotate(rotate);

        this.setOnMouseClicked(event -> {
            fireEvent(new StateEvent(eventType));
        });
        this.setOnMouseEntered(event -> {
            playEffectClik();
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