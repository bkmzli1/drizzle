package ru.bkmz.drizzle.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum MediaLoader {
    INSTANCE;
    private static final File EXTERNAL_IMAGE_FOLDER = new File("");
    private static final boolean EXTERNAL_IMAGE_ENABLE = EXTERNAL_IMAGE_FOLDER.isDirectory();
    private String prefix = "";
    private String suffix = "";
    private String musicFile = "res/media/sine" + suffix;
    private final Map<String, String> buffer = new HashMap<String, String>();
    private boolean enableExternalSources = false;

    public void setCommonPrefix(String prefix) {
        this.prefix = Objects.requireNonNull(prefix);
    }

    public void setCommonSuffix(String suffix) {

        this.suffix = Objects.requireNonNull(suffix);
    }


    private String toInternalInputStream(String token) {
        musicFile = token;
        return prefix + token + suffix;
    }

    public void load(String token) {
        musicFile = token;
        this.buffer.put(token, toInternalInputStream(token));
    }

    public String getMedia(String token) {
        if (!this.buffer.containsKey(token)) {
            load(token);
        }

        return this.buffer.get(token);
    }
}

