package _00_Click_Chat.networking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import _02_Chat_Application.ChatApp;

public class Client implements KeyListener {
	private String ip;
	private int port;

	Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;
	
	public String messages = "";
	public String next = "";
	
	ChatApp c;

	public Client(String ip, int port, ChatApp c) {
		this.ip = ip;
		this.port = port;
		this.c = c;
	}

	public void start(){
		try {

			connection = new Socket(ip, port);
			

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while (connection.isConnected()) {
			try {
				
				String incoming = is.readObject().toString();
				messages += incoming;
				c.setText(messages);
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(String s) {
		try {
			if (os != null) {
				os.writeObject(s);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(Character.isAlphabetic(e.getKeyChar()) || e.getKeyChar() == '\n' || e.getKeyChar() == ' ') {
			//messages += e.getKeyChar();
			next += e.getKeyChar();
			if(e.getKeyChar() == '\n') {
				messages += (next + "\n");
				
				
				
				sendMessage(next + "\n");
				next = "";
				System.out.println(messages);
				c.setText(messages);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
