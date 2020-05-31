package ru.bkmz.drizzle.input;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Keyboard implements EventHandler<KeyEvent> {

    private final Map<KeyCode, Boolean> currentMap = new HashMap<>();
    private final Map<KeyCode, Boolean> previousMap = new HashMap<>();

    public void addEventSource(Stage stage) {
        synchronized (Objects.requireNonNull(stage)) {
            stage.addEventHandler(KeyEvent.KEY_PRESSED, this);
            stage.addEventHandler(KeyEvent.KEY_RELEASED, this);
        }
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        final KeyCode keyCode = keyEvent.getCode();
        final EventType<KeyEvent> eventType = keyEvent.getEventType();

        if (eventType == KeyEvent.KEY_PRESSED) {
            currentMap.put(keyCode, true);
        } else if (eventType == KeyEvent.KEY_RELEASED) {
            currentMap.put(keyCode, false);
        }

        keyEvent.consume();
    }

    public boolean isHeld(KeyCode keyCode) {
        return currentMap.getOrDefault(keyCode, false);
    }

    

    public boolean isPressed(KeyCode keyCode) {
        return isHeld(keyCode) && !wasHeld(keyCode);
    }

    public void update() {
        previousMap.putAll(currentMap);
    }

    private boolean wasHeld(KeyCode keyCode) {
        return previousMap.getOrDefault(keyCode, false);
    }

    public boolean wasPressed(KeyCode keyCode) {
        return !isHeld(keyCode) && wasHeld(keyCode);
    }

}
