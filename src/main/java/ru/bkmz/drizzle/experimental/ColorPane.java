package ru.bkmz.drizzle.experimental;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ru.bkmz.drizzle.Application;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.util.Commons;

import static ru.bkmz.drizzle.util.Commons.*;
import static ru.bkmz.drizzle.util.Language.getLanguageMap;


public class ColorPane extends BorderPane {
    private VBox vBoxColor, vBoxT;
    private HBox hBox, hBox2, hBoxT, hBoxT2, hBoxPrevy;
    private Text label, tipColor,cleaning, text, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11;
    private int idColor = 0;


    public ColorPane() {
        text();
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.TOP_CENTER);


        label = new Text(textProcessor("Settings theme"));
        label.setFont(Font.font("", FontWeight.BOLD, 30));
        label.setUnderline(true);

        tipColor = new Text();
        tipColor.setFont(Font.font("", FontWeight.BOLD, 30));
        tipColor.setUnderline(true);

        hBox = new HBox(25);
        hBox.setAlignment(Pos.TOP_CENTER);

        hBox2 = new HBox(25);
        hBox2.setAlignment(Pos.TOP_CENTER);

        hBoxPrevy = new HBox(10);
        hBoxPrevy.setAlignment(Pos.TOP_CENTER);


        vBoxColor = new VBox(10);
        vBoxColor.setAlignment(Pos.TOP_CENTER);


        hBoxT = new HBox(20);
        hBoxT2 = new HBox(20);

        hBoxT.setAlignment(Pos.TOP_CENTER);
        hBoxT2.setAlignment(Pos.TOP_CENTER);


        vBoxT = new VBox(20);
        vBoxT.getChildren().addAll(hBoxT, hBoxT2);

        hBoxPrevy.getChildren().addAll(vBoxT);

        vBox.getChildren().addAll(label, hBox, hBox2, tipColor, vBoxColor, hBoxPrevy);

        cleaning = new Text(getLanguageMap("reset threads"));
        cleaning.setOnMouseClicked(event -> {
            Commons.nullColor();
            Application.pane();
            refresh();

        });
        cleaning.setFont(Font.font("", FontWeight.BOLD, 30));
        cleaning.setFill(Commons.GRADIENT2);
        cleaning.setUnderline(true);
        cleaning.setOpacity(0.5);
        cleaning.setOnMouseEntered(event -> {
            cleaning.setOpacity(1);
            Glow glow = new Glow(1000);
            cleaning.setEffect(glow);
        });

        cleaning.setOnMouseExited(event -> {
            cleaning.setOpacity(0.5);
            Glow glow = new Glow(0);
            cleaning.setEffect(glow);
        });
        this.setBottom(cleaning);

        this.setCenter(vBox);

    }

    public void refresh() {
        text();
        cleaning.setFill(Commons.GRADIENT2);
        hBox.getChildren().clear();
        hBox2.getChildren().clear();
        vBoxColor.getChildren().clear();
        hBoxT.getChildren().clear();
        hBoxT2.getChildren().clear();
        hBoxT.getChildren().addAll(text, text2, text3, text4, text5, text6);
        hBoxT2.getChildren().addAll(text7, text8, text9, text10, text11);

        label.setFill(Commons.GRADIENT2);
        text.setFill(Commons.GRADIENT);
        text2.setFill(Commons.GRADIENT2);
        tipColor.setFill(Commons.GRADIENT2);

        tipColor.setText(textProcessor(Commons.colorName[idColor]));
        tipColor.setTextAlignment(TextAlignment.CENTER);


        MenuButton menu = new MenuButton(StateEvent.MENU);
        this.setTop(menu);
        for (int i = 0; i < Commons.colorName.length; i++) {
            settingsClic(Commons.colorName[i], i);
        }
        settingsButtons(textProcessor("red"), (int) (Commons.getColor(colorName[idColor]).getRed() * 255));
        settingsButtons(textProcessor("green"), (int) (Commons.getColor(colorName[idColor]).getGreen() * 255));
        settingsButtons(textProcessor("blue"), (int) (Commons.getColor(colorName[idColor]).getBlue() * 255));
        settingsButtons(textProcessor("opacity"), (int) (Commons.getColor(colorName[idColor]).getOpacity() * 100));
        text();

    }

    private void text() {

        text = new Text("TEXT");
        text.setFont(Font.font("", FontWeight.LIGHT, 30));
        text.setUnderline(true);
        text.setFill(Commons.GRADIENT);

        text2 = new Text("TEXT");
        text2.setFont(Font.font("", FontWeight.BOLD, 30));
        text2.setUnderline(true);
        text2.setFill(Commons.GRADIENT2);

        text3 = new Text("TEXT");
        text3.setFill(getColor("colorTexOff"));
        text3.setFont(Font.font("", FontWeight.BOLD, 30));

        text4 = new Text("TEXT");
        text4.setFont(Font.font("", FontWeight.BLACK, 30));
        text4.setFill(getColor("colorTexOn"));
        Glow glow = new Glow(0);
        text4.setOpacity(1);
        glow.setLevel(0.5);
        text4.setEffect(glow);


        text5 = new Text("TEXT");
        text5.setFont(Font.font("", FontWeight.BLACK, 30));
        text5.setFill(getColor("canBuy"));

        text6 = new Text("TEXT");
        text6.setFont(Font.font("", FontWeight.BLACK, 30));
        text6.setFill(getColor("notBuy"));

        text7 = new Text("TEXT");
        text7.setFont(Font.font("", FontWeight.BLACK, 30));
        text7.setFill(getColor("buyFull"));

        text8 = new Text("1234");
        text8.setFont(Font.font("", FontWeight.BLACK, 30));
        text8.setFill(getColor("numbers"));

        text9 = new Text("TEXT");
        text9.setFont(Font.font("", FontWeight.BLACK, 30));
        text9.setFill(getColor("skillOn"));

        text10 = new Text("TEXT");
        text10.setFont(Font.font("", FontWeight.BLACK, 30));
        text10.setFill(getColor("skillOff"));


        text11 = new Text("TEXT");
        text11.setFont(Font.font("", FontWeight.BLACK, 30));
        text11.setFill(getColor("skillDisabled"));
    }


    private void settingsButtons(String name, int colorValve) {
        String colorValveS = String.valueOf(colorValve);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.TOP_CENTER);

        Text text = new Text(name);

        getCollorText(text, name, colorValve);
        text.setFont(Font.font("", FontWeight.BOLD, 23));
        text.setTextAlignment(TextAlignment.CENTER);

        if (colorValveS.length() == 1) {
            colorValveS = "00" + colorValve;
        } else if (colorValveS.length() == 2) {
            colorValveS = "0" + colorValve;
        }

        Text valve = new Text(colorValveS);
        valve.setFill(getColor("numbers"));
        valve.setFont(Font.font("", FontWeight.BOLD, 23));
        getCollorText(valve, name, colorValve);

        Text addOneL = new Text("  -1  ");
        Text addTenL = new Text("  -10  ");
        Text addAHundredL = new Text("  -100  ");

        Text addOneR = new Text("  +1  ");
        Text addTenR = new Text("  +10  ");
        Text addAHundredR = new Text("  +100  ");

        buttonText(addOneL, name, Integer.parseInt(addOneL.getText().replace(" ","")), colorValve);
        buttonText(addTenL, name, Integer.parseInt(addTenL.getText().replace(" ","")), colorValve);
        buttonText(addAHundredL, name, Integer.parseInt(addAHundredL.getText().replace(" ","")), colorValve);

        buttonText(addOneR, name, Integer.parseInt(addOneR.getText().replace(" ","")), colorValve);
        buttonText(addTenR, name, Integer.parseInt(addTenR.getText().replace(" ","")), colorValve);
        buttonText(addAHundredR, name, Integer.parseInt(addAHundredR.getText().replace(" ","")), colorValve);

        hBox.getChildren().addAll(text, addAHundredL, addTenL, addOneL, valve, addOneR, addTenR, addAHundredR);
        vBoxColor.getChildren().addAll(hBox);


    }


    private void settingsClic(String colorName, int id) {

        Text text = new Text(textProcessor(colorName));

        text.setFont(Font.font("", FontWeight.BOLD, 20));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(getColor("colorTexOff"));
        Glow glow = new Glow(0);
        text.setEffect(glow);

        text.setOnMouseClicked(event -> {
            idColor = id;
            tipColor.setText(Commons.colorName[idColor]);
            refresh();
        });
        text.setOnMouseEntered(event -> {
            text.setFill(getColor("colorTexOn"));
            glow.setLevel(1000);
            text.setEffect(glow);
        });

        text.setOnMouseExited(event -> {
            text.setFill(getColor("colorTexOff"));
            glow.setLevel(0);
            text.setEffect(glow);
        });
        if (id <= 5) {
            hBox.getChildren().addAll(text);
        } else {
            hBox2.getChildren().addAll(text);
        }
    }

    private String textProcessor(String text) {
        return getLanguageMap(text);
    }

    private void buttonText(Text text, String name, int offset, int colorValve) {

        text.setFont(Font.font("", FontWeight.BOLD, 23));
        text.setTextAlignment(TextAlignment.CENTER);
        Glow glow = new Glow(0);
        getCollorText(text, name, colorValve);
        text.setOnMouseEntered(event -> {

            glow.setLevel(1000);
            text.setEffect(glow);
        });

        text.setOnMouseExited(event -> {

            glow.setLevel(0);
            text.setEffect(glow);
        });

        text.setOnMouseClicked(
                event -> {
                    collorSettings(name, offset, colorValve);

                });
    }



    private void collorSettings(String name, int offset, int colorValve) {
        if (name.equals(textProcessor("red"))) {
            Commons.setColorRed(colorName[idColor], colorValve + offset);
        } else if (name.equals(textProcessor("green"))) {
            Commons.setColorGreen(colorName[idColor], colorValve + offset);
        } else if (name.equals(textProcessor("blue"))) {
            Commons.setColorBlue(colorName[idColor], colorValve + offset);
        } else if (name.equals(textProcessor("opacity"))) {
            if (colorValve <= 100 && colorValve >= 0) {
                Commons.setColorOpacity(colorName[idColor],colorValve+offset);
            }
        }


        Application.pane();
        refresh();
        try {
            Commons.write();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
