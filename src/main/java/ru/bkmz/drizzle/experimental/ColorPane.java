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

import static ru.bkmz.drizzle.util.Commons.colorName;
import static ru.bkmz.drizzle.util.Commons.getColor;
import static ru.bkmz.drizzle.util.Language.getLanguageMap;


public class ColorPane extends BorderPane {
    private VBox vBoxColor, vBoxT;
    private HBox hBox, hBox2, hBoxT, hBoxT2, hBoxPrevy;
    private Text label, tipColor, text, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11;
    private int idColor = 0;


    public ColorPane() {
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
        text();


        hBoxT = new HBox(20);
        hBoxT2 = new HBox(20);

        hBoxT.setAlignment(Pos.TOP_CENTER);
        hBoxT2.setAlignment(Pos.TOP_CENTER);

        hBoxT.getChildren().addAll(text, text2, text3, text4, text5, text6);
        hBoxT2.getChildren().addAll(text7, text8, text9, text10, text11);

        vBoxT = new VBox(20);
        vBoxT.getChildren().addAll(hBoxT, hBoxT2);

        hBoxPrevy.getChildren().addAll(vBoxT);

        vBox.getChildren().addAll(label, hBox, hBox2, tipColor, vBoxColor, hBoxPrevy);

        Text cleaning = new Text(getLanguageMap("reset threads"));
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
        hBox.getChildren().clear();
        hBox2.getChildren().clear();
        vBoxColor.getChildren().clear();

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

        if (name.equals(textProcessor("red"))) {
            text.setFill(Color.RED);
        } else if (name.equals(textProcessor("green"))) {
            text.setFill(Color.GREEN);
        } else if (name.equals(textProcessor("blue"))) {
            text.setFill(Color.BLUE);
        }
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
        if (name.equals(textProcessor("red"))) {
            valve.setFill(Color.RED);
        } else if (name.equals(textProcessor("green"))) {
            valve.setFill(Color.GREEN);
        } else if (name.equals(textProcessor("blue"))) {
            valve.setFill(Color.BLUE);
        }

        EventButton eventButtonL = new EventButton(null, 0);
        EventButton eventButtonR = new EventButton(null, 180);

        eventButtonL.setOnMouseClicked(event -> {
            if (name.equals(textProcessor("red"))) {
                Commons.setColorRed(colorName[idColor], colorValve - 1);
            } else if (name.equals(textProcessor("green"))) {
                Commons.setColorGreen(colorName[idColor], colorValve - 1);
            } else if (name.equals(textProcessor("blue"))) {
                Commons.setColorBlue(colorName[idColor], colorValve - 1);
            }
            try {
                Commons.write();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Application.pane();
            refresh();

        });
        eventButtonR.setOnMouseClicked(event -> {
            if (name.equals(textProcessor("red"))) {
                Commons.setColorRed(colorName[idColor], colorValve + 1);
            } else if (name.equals(textProcessor("green"))) {
                Commons.setColorGreen(colorName[idColor], colorValve + 1);
            } else if (name.equals(textProcessor("blue"))) {
                Commons.setColorBlue(colorName[idColor], colorValve + 1);
            }
            try {
                Commons.write();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Application.pane();
            refresh();
        });
        hBox.getChildren().addAll(text, eventButtonL, valve, eventButtonR);
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

}
