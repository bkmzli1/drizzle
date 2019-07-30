package ru.bkmz.drizzle.experimental;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.input.Keyboard;
import ru.bkmz.drizzle.server.Connector;
import ru.bkmz.drizzle.server.Server;
import ru.bkmz.drizzle.util.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ru.bkmz.drizzle.util.Language.getLanguageMap;

public class Online extends BorderPane {
    static Server s = new Server(7777);
    static Thread th = new Thread(s);
    static Connector cn = new Connector(7777);
    public static Text text;

    private VBox vbox = new VBox(30);

    public Online() {
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));

        MenuButton menu = new MenuButton(StateEvent.MENU);
        this.setTop(menu);

        text = new Text(getLanguageMap("TEMPORARILY NOT WORKING"));
        text.setFont(Font.font("", FontWeight.BOLD, 30));
        vbox.setAlignment(Pos.CENTER);


        this.setCenter(vbox);
    }

    public static boolean b = false;

    public void refresh() {
        vbox.getChildren().addAll(text);
        constryktor1(vbox);

    }

    private void constryktor1(VBox vbox) {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        Text port = new Text("порт");
        Text number1 = new Text("7");
        Text number2 = new Text("7");
        Text number3 = new Text("7");
        Text number4 = new Text("7");


        hBox.getChildren().addAll(number1, number2, number3, number4);
        vbox.getChildren().addAll(port,hBox);
    }

}
