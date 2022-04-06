package _02_Chat_Application;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import _00_Click_Chat.networking.Client;
import _00_Click_Chat.networking.Server;


/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp extends JFrame{

	
	JPanel p = new JPanel();
	JTextField f = new JTextField(10);
	JTextArea l = new JTextArea("");
	
	Client c;
	Server s;
	
	public static void main(String[] args) throws IOException {
		new ChatApp();
	}
	
	public ChatApp() throws IOException {
		l.setEnabled(false);
		l.setDisabledTextColor(Color.black);
		l.setFont(new Font("name?", 1, 20));
		String response;
		do {
			response = JOptionPane.showInputDialog("Would you like to host a server connection or be a client? (S / C): ");
		}while(!(response.equalsIgnoreCase("C") || response.equalsIgnoreCase("S")));
		if(response.equalsIgnoreCase("S")) {
			setTitle("Server");
			s = new Server(8080, this);
			System.out.println("Server started\nIP: " + s.getIPAddress() + "\nPort: " + s.getPort());
			p.add(f);
			p.add(l);
			f.addKeyListener(s);
			add(p);
			setSize(500, 500);
			setVisible(true);
			s.start();
			
		}else {
			setTitle("Client");
			c = new Client(JOptionPane.showInputDialog("Enter IP address: "), Integer.parseInt(JOptionPane.showInputDialog("Enter a port: ")), this);

			p.add(f);
			p.add(l);
			f.addKeyListener(c);
			add(p);
			setSize(500, 500);
			setVisible(true);
			c.start();
		}
	}
	
	public void setText(String s){
		System.out.println(s);
		
		l.setText(s);
		f.setText("");
	}

	
	
	
	
}
