package ru.bkmz.drizzle.event;

import javafx.event.Event;
import javafx.event.EventType;

public class StateEvent extends Event {


    private static final long serialVersionUID = 4606669955706420844L;

    public static final EventType<StateEvent> STATE = new EventType<>(ANY, "STATE");

    public static final EventType<StateEvent> MENU = new EventType<>(STATE, "MENU");
    public static final EventType<StateEvent> MENU_SETTINGS = new EventType<>(STATE, "MENU_SETTINGS");
    public static final EventType<StateEvent> SHOP = new EventType<>(STATE, "SHOP");
    public static final EventType<StateEvent> STAT = new EventType<>(STATE, "STAT");
    public static final EventType<StateEvent> SETTINGS = new EventType<>(STATE, "SETTINGS");
    public static final EventType<StateEvent> HELP = new EventType<>(STATE, "HELP1");
    public static final EventType<StateEvent> QUIT = new EventType<>(STATE, "QUIT");
    public static final EventType<StateEvent> ONLINE = new EventType<>(STATE, "ONLINE");

    private static final EventType<StateEvent> GAME = new EventType<>(STATE, "GAME");

    public static final EventType<StateEvent> PLAY = new EventType<>(GAME, "PLAY");
    public static final EventType<StateEvent> PAUSE = new EventType<>(GAME, "PAUSE");
    public static final EventType<StateEvent> UNPAUSE = new EventType<>(GAME, "UNPAUSE");
    public static final EventType<StateEvent> STOP = new EventType<>(GAME, "STOP");
    public static final EventType<StateEvent> COLLECTION = new EventType<>(GAME, "COLLECTION");
    public static final EventType<StateEvent> BACKGROUND = new EventType<>(GAME, "BACKGROUND");


    public static final EventType<StateEvent> SCREEN  = new EventType<>(GAME, "SCREEN");
    public StateEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

}
