package ru.bkmz.drizzle.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    private boolean isRunning = true;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void ServerStarter() {
        try {
            server = new ServerSocket(port, 100);
            while (isRunning) {
                connection = server.accept();
                output = new ObjectOutputStream(connection.getOutputStream());
                output.flush();
                input = new ObjectInputStream(connection.getInputStream());
                output.writeObject("Вы прислали: " + input.readObject());
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }


    private void close() {
        try {
            output.close();
            input.close();
            connection.close();
            isRunning = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ServerStarter();
    }

}
