package ru.bkmz.drizzle.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class HttpHandlerBased implements HttpHandler {

    protected abstract String doGet(HttpExchange exchange, Map<String, String> params);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equalsIgnoreCase("GET")) {
            String uriStr = exchange.getRequestURI().getQuery();
            String id = UUID.randomUUID().toString().replace("-", "");
            log("[req " + id + "] " + uriStr);
            Map<String, String> pars = new HashMap<String, String>();
            String[] pairs;
            if (uriStr != null && uriStr.contains("=")) {
                if (uriStr.contains("&")) pairs = uriStr.split("&");
                else pairs = new String[]{uriStr};
                for (String pair : pairs) {
                    String[] p  = pair.split("=");
                    if (p.length == 2) {
                        pars.put(p[0], p[1]);
                    }
                }
            }
            String resp = doGet(exchange, pars);
            log("[rsp " + id + "] " + resp);
            exchange.sendResponseHeaders(200, resp.length());
            OutputStream body = exchange.getResponseBody();
            body.write(resp.getBytes("UTF-8"));
            body.close();
        }
    }

    public static void log(String msg) {
        System.out.println(new SimpleDateFormat("HH:mm:ss:SSS dd.MM.yy").format(new Date())+" - "+msg);
    }
}