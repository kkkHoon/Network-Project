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
	private ImageIcon icon = new ImageIcon("icon.png");
	private int answer = 0;
	
	public Client(){
		temp = new Test(this);
		frame = new JFrame("�������ι�");
		frame.add(temp);
		frame.setBounds(250,150,1500,800);
		frame.setIconImage(icon.getImage());
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
				temp.logo_off(0);  // �ΰ� ����.
			} else if (line.startsWith("WHO")) {
				temp.whoStopwatch();
				
				System.out.println("who~");// ������ �������� ����
				// ���ݰ����ϴµ� �ð����������Ƿ� timer �۵�
			} else if (line.startsWith("ATK")) {
				//System.out.println("My probelm is "+Integer.valueOf(line.substring(3)));
				temp.setAttack(1); // �ڹ��� �̹����� ä��â�� ��� �Ⱥ��̰� �Ʊ� �Ǵ� inpt_text �� ��ȭ�������� �ϱ�
				temp.music_change(1); // ���ݹ޾��� �� ���� Ʋ��
				temp.logo_on(2);// ���ݹ޾Ҵٴ� �ΰ� ����
				temp.logo_off(2); // �ΰ� ����.
				while(temp.getClip().isOpen() && answer != 0){}; // Ÿ�̸Ӱ� �� �ǰų� ������ ������ �������¸� ��� while�� ���ư�.
				temp.start_re_on(); // �ٽ� ��������� Ų��.
				temp.setAttack(0);
				//temp.stopwatch();
				out.println("ANSWER"+answer); // ���� ��ư�鿡�� �̺�Ʈ �߰��Ǿ answer���� �ٲ� ��.
				
				// ���� �� ������ȣ�� ã�Ƽ� ���� Label�κп��ٰ� ������
				// timer �۵�
			} else if (line.startsWith("NATK")) {
				// ���� �� ������ȣ�� ã�Ƽ� ���� Lbel�κп��ٰ� ������
			} else if (line.startsWith("CORRECT")) {
				temp.music_change(2); // �¾Ҵٴ� ���� ��� Ʋ���ֱ� �縵~
				temp.logo_on(3);// correct �ΰ� ����ֱ�
				temp.logo_off(3);
				while(temp.getClip().isOpen()){}; // �¾Ҵٴ� ȿ���� ���� ������ ��ٷȴٰ�
				temp.start_re_on(); // ����� �κк��� �ٽ� ��������� Ų��
				out.println("ACK");			
			} else if (line.startsWith("WRONG")) {
				temp.music_change(3);
				temp.logo_on(4);
				temp.logo_off(4);
				while(temp.getClip().isOpen()){}; // �¾Ҵٴ� ȿ���� ���� ������ ��ٷȴٰ�
				temp.start_re_on(); // ����� �κк��� �ٽ� ��������� Ų��
				out.println("ACK");
			} else if (line.startsWith("LOSE")) {
				temp.music_change(5); // �׾��ٴ� �������
				temp.logo_on(6);// ���ٴ� �ΰ� ����ֱ�
				temp.logo_off(6);
				out.println("LOOT"+temp.getLoot()); // �� ���� �����ִ� card_info�� string���·� ��ȯ�ؼ� �������� �����ֱ�	 �������� ����ǰ	
			} else if (line.startsWith("YOUR_TURN")) {
				temp.logo_on(1);
				temp.logo_off(1);
			} else if (line.startsWith("WIN")) {
				temp.music_on(4); // �̰�ٴ� �������
				temp.logo_on(5); // �̰�ٴ� �̹��� �ΰ� ����ֱ�
				// ���� ��
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
	
	public void setAnswer(int k)
	{
		answer = k;
	}
}
