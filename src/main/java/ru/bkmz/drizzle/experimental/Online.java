package ru.bkmz.drizzle.experimental;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.input.Keyboard;
import ru.bkmz.drizzle.util.Commons;

import static ru.bkmz.drizzle.util.Language.getLanguageMap;

public class Online extends BorderPane {


    public Online(Keyboard keyboard) {
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));

        MenuButton menu = new MenuButton(StateEvent.MENU);
        this.setTop(menu);

        Text text = new Text(getLanguageMap("TEMPORARILY NOT WORKING"));
        text.setFont(Font.font("", FontWeight.BOLD, 30));


        VBox vbox = new VBox(30);
        this.setCenter(vbox);

        vbox.setAlignment(Pos.TOP_CENTER);


        vbox.getChildren().addAll(text);



    }
}
