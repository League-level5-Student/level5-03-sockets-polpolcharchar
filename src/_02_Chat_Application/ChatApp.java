package _02_Chat_Application;

import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import _00_Click_Chat.networking.Client;
import _00_Click_Chat.networking.Server;


/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp extends JFrame{

	
	JPanel p = new JPanel();
	JTextField f = new JTextField(10);
	JLabel l = new JLabel("");
	JButton sendButton = new JButton("Send");
	JButton updateButton = new JButton("Update");
	
	Client c;
	Server s;
	
	public static void main(String[] args) throws IOException {
		new ChatApp();
	}
	
	public ChatApp() throws IOException {
		String response;
		do {
			response = JOptionPane.showInputDialog("Would you like to host a server connection or be a client? (S / C): ");
		}while(!(response.equalsIgnoreCase("C") || response.equalsIgnoreCase("S")));
		if(response.equalsIgnoreCase("S")) {
			setTitle("Server");
			s = new Server(8080);
			System.out.println("Server started\nIP: " + s.getIPAddress() + "\nPort: " + s.getPort());
			p.add(f);
			p.add(sendButton);
			p.add(updateButton);
			p.add(l);
			sendButton.addActionListener((e)->{
				s.messages += f.getText();
				s.sendMessage(f.getText());
				f.setText("");
			});
			updateButton.addActionListener((e)->{
				l.setText(s.messages);
				System.out.println("Server update: " + s.messages);
			});
			add(p);
			setSize(500, 500);
			setVisible(true);
			s.start();
		}else {
			setTitle("Client");
			c = new Client(JOptionPane.showInputDialog("Enter IP address: "), Integer.parseInt(JOptionPane.showInputDialog("Enter a port: ")));
			p.add(f);
			p.add(sendButton);
			p.add(updateButton);
			p.add(l);
			sendButton.addActionListener((e)->{
				c.messages += f.getText();
				c.sendMessage(f.getText());
				f.setText("");
			});
			updateButton.addActionListener((e)->{
				l.setText(c.messages);
				System.out.println("Client update: " + c.messages);
			});
			add(p);
			setSize(500, 500);
			setVisible(true);
			c.start();
		}
	}
	
	
}
