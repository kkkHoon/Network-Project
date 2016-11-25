import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;


public class Client {

	BufferedReader in;
	PrintWriter out;
	private static JFrame frame;
	private Test temp;
	
	public Client() {
		temp = new Test(this);
		frame = new JFrame("Client");
		frame.add(temp);
		frame.setBounds(250,150,1500,800);
	};
// 테더링 스마트폰 테더링 테스트할 때
	private String getServerAddress() {
		return JOptionPane.showInputDialog(
				frame,
				"Enter IP Address of the Server:",
				"Welcome to the Chatter",
				JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Prompt for and return the desired screen name.
	 */
	private String getName() {
		return JOptionPane.showInputDialog(
				frame,
				"Choose a screen name:",
				"Screen name selection",
				JOptionPane.PLAIN_MESSAGE);
	}


	private void run() throws IOException {

		String serverAddress = getServerAddress();
		Socket socket = new Socket(serverAddress, 9001);
		in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		while (true) {
			String line = in.readLine();
			if (line.startsWith("SUBMITNAME")) {
				out.println(getName());
			} else if (line.startsWith("NAMEACCEPTED")) {
				frame.setVisible(true);
			} else if (line.startsWith("Broadcast")) {
				temp.show_message(line.substring(9));
			}	
		}
	}

	public static void main(String[] args) throws Exception{
		Client client = new Client();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.run();
	}

}
