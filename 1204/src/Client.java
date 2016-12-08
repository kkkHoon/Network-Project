import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;


public class Client {

	BufferedReader in;
	PrintWriter out;
	private static JFrame frame;
	private Test temp;
	
	public Client(){
		temp = new Test(this);
		frame = new JFrame("Client");
		frame.add(temp);
		frame.setBounds(250,150,1500,800);
	};
// �״��� ����Ʈ�� �״��� �׽�Ʈ�� ��
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
			System.out.println("I got this :: "+line);
			if (line.startsWith("SUBMITNAME")) {
				out.println(getName());
			} else if (line.startsWith("NAMEACCEPTED")) {
				frame.setVisible(true);
			} else if (line.startsWith("Broadcast")) {
				temp.show_message(line.substring(9));
				//temp.stopwatch();
				//out.println("ANSWER4");
				//out.println("USE5");
				//out.println("USE4"); // ��Ŵ� �����ʿ�
				
			} else if (line.startsWith("SEND")){
				temp.make_info(line.substring(4));
				temp.exchangeOpen();
			} else if (line.startsWith("START")){
				temp.start_on();
				temp.logo_on(0);  // �ΰ� �״ٰ�
				try {
					Thread.sleep(5000); // 5�ʵ��� ��ٷȴٰ�
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				temp.logo_off(0);  // �ΰ� ����.
			} else if (line.startsWith("WHO")) {
				System.out.println("who~");
				// ������ �������� ����
				// ���ݰ����ϴµ� �ð����������Ƿ� timer �۵�
			} else if (line.startsWith("ATK")) {
				System.out.println("My probelm is "+Integer.valueOf(line.substring(3)));
				// ���ݹ޾Ҵٴ� �ΰ� ����
				// ���ݹ޾��� �� ���� Ʋ��
				// �ڹ��� �̹����� ä��â�� ��� �Ⱥ��̰� �Ʊ� �Ǵ� inpt_text �� ��ȭ�������� �ϱ�
				// ���� ��ư�鿡�� �̺�Ʈ �߰�.
				// ���� �� ������ȣ�� ã�Ƽ� ���� Label�κп��ٰ� ������
				// timer �۵�
			} else if (line.startsWith("NATK")) {
				// ���� �� ������ȣ�� ã�Ƽ� ���� Lbel�κп��ٰ� ������
			} else if (line.startsWith("CORRECT")) {
				// correct �ΰ� ����ֱ�
				// �¾Ҵٴ� ���� ��� Ʋ���ֱ� �縵~ �׸��� �ٽ� ����������� ��ȣ��
			} else if (line.startsWith("WRONG")) {
				// wrong �ΰ� ����ֱ�
				// Ʋ�ȴٴ� ���� ��� Ʋ���ֱ�
			} else if (line.startsWith("LOSE")) {
				// ���ٴ� �ΰ� ����ֱ�
				// �׾��ٴ� �������
				// �� ���� �����ִ� card_info�� string���·� ��ȯ�ؼ� �������� �����ֱ�
			} else if (line.startsWith("YOUR_TURN")) {
				// �� ���̶�� �ΰ� ����ֱ�
			} else if (line.startsWith("WIN")) {
				// �̰�ٴ� �̹��� �ΰ� ����ֱ�
				// �̰�ٴ� �������
			} else if (line.startsWith("LIST")) {
				// ����â �̹��� ������Ʈ ���ֱ�
				// �ٲ� ������ LABEL�κп� border�� �������� �״� ���ٸ� �ݺ��ϴ� �Լ��� �����Ű�� �����Ŀ� �� �Լ� ��������
				// ���� ������ ������ �ֱ� �쐇~
			}
		}
	}

	public static void main(String[] args) throws Exception{
		Client client = new Client();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.run();
	}

}
