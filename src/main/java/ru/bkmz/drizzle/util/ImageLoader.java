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

    IMAGE_LOADER;

    private static final File EXTERNAL_IMAGE_FOLDER = new File("");
    private static final boolean EXTERNAL_IMAGE_ENABLE = EXTERNAL_IMAGE_FOLDER.isDirectory();

    private final Map<String, Image> bufferImege = new HashMap<>();
    private String suffix = "";
    private boolean enableExternalSources = false;


    public void setCommonSuffix(String suffix) {
        this.suffix = Objects.requireNonNull(suffix);
    }

    private InputStream toInternalInputStream(String token) {
        return ClassLoader.class.getResourceAsStream("/" + token + this.suffix);
    }

    private InputStream toExternalInputStream(String token) throws IOException {
        return new FileInputStream(new File(token + this.suffix));
    }

    public void preferExternalSources(boolean prefer) {
        this.enableExternalSources = prefer;
    }

    public void loading(String token) {
        try {
            loading(token, -1, -1, false, false);
        } catch (Exception e) {

            Application.addError(ImageLoader.class.getName() + ": " + "ошибка загрузки: " + token + " Error: " + e);
        }
    }

    public void loading(String token, int width, int height) {
        loading(token, width, height, false, false);
    }

    public void loading(String token, int width, int height, boolean keepAspectRatio, boolean smooth) {
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

        this.bufferImege.put(token, image);
    }

    public Image getImage(String token) {
        if (!this.bufferImege.containsKey(token)) {
            loading(token);
        }

        return this.bufferImege.get(token);
    }

}
