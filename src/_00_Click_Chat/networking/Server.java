package _00_Click_Chat.networking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import _02_Chat_Application.ChatApp;

public class Server implements KeyListener {
	private int port;

	private ServerSocket server;
	private Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;
	
	public String messages = "";
	public String next = "";
	
	ChatApp c;

	public Server(int port, ChatApp c) {
		this.port = port;
		this.c = c;
	}

	public void start(){
		try {
			server = new ServerSocket(port, 100);

			connection = server.accept();

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();

			while (connection.isConnected()) {
				try {
					String incoming = is.readObject().toString();
					messages += incoming;
					c.setText(messages);
					
				}catch(EOFException e) {
					JOptionPane.showMessageDialog(null, "Connection Lost");
					System.exit(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "ERROR!!!!!";
		}
	}

	public int getPort() {
		return port;
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
			next += e.getKeyChar();
			if(e.getKeyChar() == '\n') {
				messages += next + "\n";

				
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
