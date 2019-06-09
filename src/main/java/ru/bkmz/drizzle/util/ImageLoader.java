package ru.bkmz.drizzle.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.scene.image.Image;
import ru.bkmz.drizzle.Application;

public enum ImageLoader {

    INSTANCE;

    private static final File EXTERNAL_IMAGE_FOLDER = new File("");
    private static final boolean EXTERNAL_IMAGE_ENABLE = EXTERNAL_IMAGE_FOLDER.isDirectory();

    private final Map<String, Image> buffer = new HashMap<>();
    private String prefix = "";
    private String suffix = "";
    private boolean enableExternalSources = false;


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
            load(token, -1, -1, false, false);
        }catch (Exception e){
            Application.error += "ошибка загрузки: "+token+"\n";
        }
    }

    public void load(String token, int width, int height) {
        load(token, width, height, false, false);
    }

    public void load(String token, int width, int height, boolean keepAspectRatio, boolean smooth) {
        InputStream stream;

        if (this.enableExternalSources && EXTERNAL_IMAGE_ENABLE) {
            try {
                stream = toExternalInputStream(token);
            } catch (IOException e) {
                stream = toInternalInputStream(token);
            }
        } else {
            stream = toInternalInputStream(token);
        }

        Image image;
        if (width > 0 && height > 0) {
            image = new Image(stream, width, height, keepAspectRatio, smooth);
        } else {
            image = new Image(stream);
        }

        this.buffer.put(token, image);
    }

    public Image getImage(String token) {
        if (!this.buffer.containsKey(token)) {
            load(token);
        }

        return this.buffer.get(token);
    }

}
