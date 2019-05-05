package ru.bkmz.drizzle.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FailCopi {
    private static String urlout;
    public static void failCopi(String url,String fileName){
        fileResources("res");
        fileResources("res/"+url);
        try {
            InputStream inpStream = FailCopi.class.getClassLoader().getResourceAsStream(url + fileName);

            if (inpStream == null) throw new FileNotFoundException(url+fileName + " not found");
            Path target = Paths.get(urlout+fileName);
            Files.copy(inpStream, target, REPLACE_EXISTING);
            inpStream.close();
        } catch (IOException e) {
            System.out.println("Облом: ");
            e.printStackTrace(System.out);
        }
    }
    private static void fileResources(String name){
        File file = new File(name);
        urlout = name;
        if (!file.exists()){
            file.mkdir();
        }

    }
}