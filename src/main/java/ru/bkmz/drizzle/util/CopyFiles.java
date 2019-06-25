package ru.bkmz.drizzle.util;

import ru.bkmz.drizzle.Application;
import ru.bkmz.drizzle.level.GameData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CopyFiles {
    private static String urlout;

    public static void failCopi(String url, String fileName) {
        File f = new File(GameData.appdata + "res/" + url + fileName);
        if (!f.exists() || (fileName.equals("language") && Application.DEBUG_MODE)) {
            System.out.println("loading jar:"+ url + fileName);
            fileResources(GameData.appdata + "res");
            fileResources(GameData.appdata + "res/" + url);

            try {
                InputStream inpStream = CopyFiles.class.getClassLoader().getResourceAsStream(url + fileName);

                if (inpStream == null) throw new FileNotFoundException(url + fileName + " not found");
                Path target = Paths.get(urlout + fileName);
                Files.copy(inpStream, target, REPLACE_EXISTING);
                inpStream.close();
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    private static void fileResources(String name) {
        File file = new File(name);
        urlout = name;
        if (!file.exists()) {
            file.mkdir();
        }

    }
}