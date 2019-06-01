package ru.bkmz.drizzle.util;

import ru.bkmz.drizzle.level.GameData;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Language {
    private static Map<String, String> languageMapName = new HashMap<String, String>();
    private static Map languageMapId = new HashMap();
    private static Connection con;

    public static String getLanguageMap(String name) {
        String s;
        try {
            s = languageMapName.get(name).toUpperCase();
        } catch (Exception e) {
            s = "null";
        }

        return s;
    }

    public static String getLanguageId(int id) {
        return String.valueOf(languageMapId.get(id)).toUpperCase();
    }

    public static void sqlite(int idLanguage) {
        String lg = null;
        switch (idLanguage) {
            case 0:
                lg = "ru";
                break;
            case 1:
                lg = "en";
                break;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + GameData.appdata + "/res/DD/language");
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM " + lg);
            while (rs.next()) {
                languageMapName.put(rs.getString("name"), rs.getString("value"));
                languageMapId.put(rs.getInt("id"), rs.getString("value"));

            }

        } catch (Exception e) {
            System.err.println(e);
        }
        close();
    }

    static void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


