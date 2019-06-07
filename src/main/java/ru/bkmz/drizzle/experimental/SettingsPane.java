package ru.bkmz.drizzle.experimental;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ru.bkmz.drizzle.Application;
import ru.bkmz.drizzle.event.StateEvent;
import ru.bkmz.drizzle.level.GameData;
import ru.bkmz.drizzle.level.Level;
import ru.bkmz.drizzle.util.Commons;

import java.awt.*;

import static ru.bkmz.drizzle.level.GameData.*;
import static ru.bkmz.drizzle.util.Commons.getColor;
import static ru.bkmz.drizzle.util.Language.getLanguageMap;
import static ru.bkmz.drizzle.util.Language.sqlite;

public class SettingsPane extends BorderPane {
    private String[] complexity, language, fps;
    private VBox vBox1, vBox2;
    private HBox hBox;
    Text label;

    public SettingsPane() {
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));


        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.TOP_CENTER);


        label = new Text(getLanguageMap("SETTINGS"));
        label.setFont(Font.font("", FontWeight.BOLD, 30));
        label.setFill(Commons.GRADIENT2);
        label.setUnderline(true);


        vBox.getChildren().addAll(label);

        this.vBox1 = new VBox(6);
        this.vBox1.setAlignment(Pos.TOP_LEFT);

        this.vBox2 = new VBox(1);
        this.vBox2.setAlignment(Pos.TOP_CENTER);

        hBox = new HBox(0);
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.getChildren().addAll(vBox1, vBox2);

        vBox.getChildren().addAll(hBox);

        this.setCenter(vBox);
    }

    public void refresh() {
        vBox1.getChildren().clear();
        vBox2.getChildren().clear();

        label.setFill(Commons.GRADIENT2);

        MenuButton menu = new MenuButton(StateEvent.MENU);
        this.setTop(menu);

        complexity = new String[]{getLanguageMap("Easy"), getLanguageMap("normal"), getLanguageMap("hard")};
        language = new String[]{"РУССКИЙ", "ENGLISH"};
        fps = new String[]{getLanguageMap("off"), getLanguageMap("average FPS"), getLanguageMap("instant FPS")};

        settingsButtons(vBox1, vBox2, Settings_SCREEN, getLanguageMap("SCREEN"));
        settingsButtons(vBox1, vBox2, Settings_BACKGROUND, getLanguageMap("BACKGROUND"));
        settingsButtons(vBox1, vBox2, Settings_RAIN_Volume, getLanguageMap("RAIN Volume"));
        settingsButtons(vBox1, vBox2, Settings_Effect_Volume, getLanguageMap("Effect Volume"));
        settingsButtons(vBox1, vBox2, POWER_OF_RAIN, getLanguageMap("POWER_OF_RAIN"));
        settingsClic(vBox1, vBox2, Settings_AcidSpawner_count, getLanguageMap("Complexity:"));
        settingsClic(vBox1, vBox2, Settings_FPS, getLanguageMap("FPS"));
        settingsButtons(vBox1, vBox2, Settings_LANGUAGE, getLanguageMap("Language"));
        settingsButtons(vBox1, vBox2, THEME, getLanguageMap("theme"));

        if (THEME.getValue()== 2){
            Text t1 = new Text(getLanguageMap("repeat"));
            t1.setTextAlignment(TextAlignment.CENTER);
            t1.setFill(getColor("colorTexOff"));
            t1.setFont(Font.font("", FontWeight.BOLD, 20));

            t1.setOnMouseClicked(event -> {
                fireEvent(new StateEvent(StateEvent.COLOR));
            });

            t1.setOnMouseEntered(event -> {
                t1.setFill(getColor("colorTexOn"));
                Glow glow = new Glow(1000);
                t1.setEffect(glow);
            });

            t1.setOnMouseExited(event -> {
                t1.setFill(getColor("colorTexOff"));
                Glow glow = new Glow(0);
                t1.setEffect(glow);
            });


           vBox2.getChildren().addAll(t1);
        }
    }

    private void settingsButtons(VBox v1, VBox v2, GameData gd, String name) {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.TOP_CENTER);

        Text text = new Text(name);
        text.setFill(getColor("colorTexOff"));
        text.setFont(Font.font("", FontWeight.BOLD, 20));

        Text information = new Text(textProcessing(gd));
        information.setFill(getColor("colorTexOff"));
        information.setFont(Font.font("", FontWeight.BOLD, 20));

        EventButton eventButtonL = new EventButton(null, 0);
        EventButton eventButtonR = new EventButton(null, 180);

        eventButtonL.setOnMouseClicked(event -> {
            actL(gd);

        });
        eventButtonR.setOnMouseClicked(event -> {
            actR(gd);
        });

        v1.getChildren().addAll(text);
        hBox.getChildren().addAll(eventButtonL);
        hBox.getChildren().addAll(information);
        hBox.getChildren().addAll(eventButtonR);
        v2.getChildren().addAll(hBox);


    }

    private void settingsClic(VBox v1, VBox v2, GameData gd, String name) {

        Text text = new Text(name);
        text.setFill(getColor("colorTexOff"));
        text.setFont(Font.font("", FontWeight.BOLD, 20));

        Text information = new Text(textProcessing(gd));
        information.setFill(getColor("colorTexOff"));
        information.setFont(Font.font("", FontWeight.BOLD, 20));

        information.setOnMouseClicked(event -> {
            actC(gd);
        });

        information.setOnMouseEntered(event -> {
            information.setFill(getColor("colorTexOn"));
            Glow glow = new Glow(1000);
            information.setEffect(glow);
        });

        information.setOnMouseExited(event -> {
            information.setFill(getColor("colorTexOff"));
            Glow glow = new Glow(0);
            information.setEffect(glow);
        });
        v1.getChildren().addAll(text);
        v2.getChildren().addAll(information);


    }

    private void actC(GameData gd) {
        if (gd == Settings_AcidSpawner_count) {

            switch (gd.getValue()) {
                case 1:
                    gd.setVolume(2);
                    break;
                case 2:
                    gd.setVolume(3);
                    break;
                case 3:
                    gd.setVolume(1);
                    break;

            }

        } else if (gd == Settings_FPS) {

            switch (gd.getValue()) {
                case 0:
                    gd.setVolume(1);
                    break;
                case 1:
                    gd.setVolume(2);
                    break;
                case 2:
                    gd.setVolume(0);
                    break;
            }

        }
        save();
        this.refresh();
    }


    private void actL(GameData gd) {

        if (gd == Settings_SCREEN) {
            Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (gd.getValue() == 1) {
                SCENE_WIDTH.setVolume((int) (sSize.width / 1.2f));
                SCENE_HEIGHT.setVolume((int) (sSize.height / 1.2f));
                gd.setVolume(0);
                fireEvent(new StateEvent(StateEvent.SCREEN));
            }
        } else if (gd == Settings_BACKGROUND) {
            gd.setVolume(gd.getValue() - 1);
            Commons.colorLoader();
            Application.pane();


        } else if (gd == Settings_RAIN_Volume) {
            Application.setVolume(gd.getValue() - 1);
            gd.setVolume(gd.getValue() - 1);
        } else if (gd == Settings_Effect_Volume) {
            SoundEffects.playNew("Hard_kick_drum.wav");
            gd.setVolume(gd.getValue() - 1);
        } else if (gd == POWER_OF_RAIN || gd == Settings_FPS) {
            gd.setVolume(gd.getValue() - 1);
        } else if (gd == Settings_LANGUAGE) {
            gd.setVolume(gd.getValue() - 1);
            sqlite(Settings_LANGUAGE.getValue());
            Application.pane();
        } else if (gd == THEME) {
            if (gd.getValue() != 1) {
                gd.setVolume(gd.getValue() - 1);
            }
            Commons.colorLoader();
            Application.pane();
        }

        save();
        this.refresh();
    }

    private void actR(GameData gd) {

        if (gd == Settings_SCREEN) {
            Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (gd.getValue() == 0) {
                SCENE_WIDTH.setVolume(sSize.width);
                SCENE_HEIGHT.setVolume(sSize.height);
                gd.setVolume(1);
                fireEvent(new StateEvent(StateEvent.SCREEN));
            }
        } else if (gd == Settings_BACKGROUND) {
            gd.setVolume(gd.getValue() + 1);
            Commons.colorLoader();
            Application.pane();
        } else if (gd == Settings_RAIN_Volume) {
            Application.setVolume(gd.getValue() + 1);
            gd.setVolume(gd.getValue() + 1);
        } else if (gd == Settings_Effect_Volume) {
            SoundEffects.playNew("Hard_kick_drum.wav");
            gd.setVolume(gd.getValue() + 1);
        } else if (gd == POWER_OF_RAIN || gd == Settings_FPS || gd == Settings_AcidSpawner_count) {
            gd.setVolume(gd.getValue() + 1);
        } else if (gd == Settings_LANGUAGE) {
            gd.setVolume(gd.getValue() + 1);
            sqlite(Settings_LANGUAGE.getValue());
            Application.pane();
        } else if (gd == THEME) {
            if (gd.getValue() != 3) {
                gd.setVolume(gd.getValue() + 1);
            }
            Commons.colorLoader();
            Application.pane();
        }

        save();
        this.refresh();
    }

    String textProcessing(GameData gd) {
        String s = null;
        if (gd == Settings_SCREEN) {
            if (gd.getValue() == 0) {
                s = getLanguageMap("WINDOW MODE");
            } else if (gd.getValue() == 1) {
                s = getLanguageMap("FULL SCREENS MODE");
            }

        } else if (gd == Settings_BACKGROUND || gd == Settings_RAIN_Volume || gd == Settings_Effect_Volume ||
                gd == POWER_OF_RAIN || gd == THEME) {
            s = String.valueOf(gd.getValue());
        } else if (gd == Settings_AcidSpawner_count) {
            s = complexity[Settings_AcidSpawner_count.getValue() - 1];
        } else if (gd == Settings_LANGUAGE) {
            s = language[Settings_LANGUAGE.getValue()];
        } else if (gd == Settings_FPS) {
            s = fps[Settings_FPS.getValue()];
        }

        return s;
    }
}