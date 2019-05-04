package ru.bkmz.drizzle.util;

import javafx.scene.media.Media;
import ru.bkmz.drizzle.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public enum MediaLoader {
    INSTANCE;
    private static final File EXTERNAL_MEDIA_FOLDER = new File("");
    private static final boolean EXTERNAL_MEDIA_ENABLE = EXTERNAL_MEDIA_FOLDER.isDirectory();


    private final Map<String, String> buffer = new HashMap<String, String>();
    private String prefix = "";
    private String suffix = "";
    private boolean enableExternalSources = false;

    public void setCommonPrefix(String prefix) {
        this.prefix = Objects.requireNonNull(prefix);
    }

    public void setCommonSuffix(String suffix) {
        this.suffix = Objects.requireNonNull(suffix);
    }


    private InputStream toInternalInputStream(String token) {
        return ClassLoader.class.getResourceAsStream("/" + this.prefix + token + this.suffix);
    }

    private InputStream toExternalInputStream(String token) throws IOException {
        return new FileInputStream(new File(this.prefix + token + this.suffix));
    }

    public void preferExternalSources(boolean prefer) {
        this.enableExternalSources = prefer;
    }

    public void load(String token) {

        try {

            Media media = new Media(new File(token).toURI().toString());



        } catch (Exception e) {
            Application.ERROR += e + "\n";
        }
        this.buffer.put(token, token);
    }

    public String getMedia(String token) {
        if (!this.buffer.containsKey(token)) {
            load(token);
        }
        return this.buffer.get(token);
    }
}

