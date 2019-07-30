package ru.bkmz.drizzle.server;

import ru.bkmz.drizzle.experimental.Online;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Класс для связи с сервером
 * 
 * <p>
 * Подготовленно в образовательных целях, специально для канала Zhirni Toni Inc.
 * </p>
 * 
 * <p>
 * Класс, выполняемый в отдельном потоке, 
 * предназначенный для установления соединения 
 * между клиентом и сервером
 * </p>
 * 
 * @author Cvazer
 * @created 09.07.2013
 * @version 1.0
 * @since 1.0
 */
public class Connector implements Runnable{
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private static Socket connection;
	private static boolean isRunning = true;
	private int port;
	public Connector(int port){
		this.port = port;
	}
	/**
	 * Метод стартующий при запуске потока.
	 * 
	 * Устанавливает соединения и начинает читать input.
	 */
	@Override
	public void run() {
		try {
			while(isRunning){
				connection = new Socket(InetAddress.getByName("127.0.0.1"), port);
				output = new ObjectOutputStream(connection.getOutputStream());
				output.flush();
				input = new ObjectInputStream(connection.getInputStream());
				Online.text.setText("Соединение установленно");
				JOptionPane.showMessageDialog(null, (String)input.readObject());
			}
			close();
		}catch(Exception e) {e.printStackTrace();}
	}
	
	/**
	 * Передает данные на сервер.
	 * 
	 * @param s Строка, которую нужно передать.
	 */
	public void sendData(String s){
		try {
			output.flush();
			output.writeObject(s);
			output.flush();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * Закрывает соединения
	 * 
	 * Метод закрывает соединения, чтобы не возникало 
	 * дальнейших проблем при последующем запуске приложения
	 */
	public void close() {
		try {
			isRunning = false;
			output.close();
			input.close();
			connection.close();
		} catch (Exception e) {e.printStackTrace();}
	}

}
