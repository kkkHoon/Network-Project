import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public class Test{

	public static void main(String[] args) {
		int counter = 10;
		ImageIcon temp = new ImageIcon("heart.jpg");
		JFrame frame = new JFrame("Basic structure GUI");

		JPanel zero_low = new JPanel();  // 시간초 , 입력창
		JPanel first_low = new JPanel(); // 문제창 , 대화창
		JPanel second_low = new JPanel(); // 목숨 수 , 아이템 버튼들

		second_low.setLayout(new FlowLayout());
		first_low.setLayout(new FlowLayout());
		zero_low.setLayout(new FlowLayout(0,150,0));
		
		JLabel timer = new JLabel("30 seconds");
		timer.setHorizontalAlignment(SwingConstants.LEFT);
		JTextField chat_input = new JTextField("This is for chatting input field",20);
		//chat_input.setHorizontalAlignment(SwingConstants.RIGHT);
		zero_low.add(timer);  
		zero_low.add(chat_input);
		frame.add(zero_low,"North");

		JTextArea Problem = new JTextArea(20,30);
		Problem.setEditable(false);
		Problem.setBorder(BorderFactory.createTitledBorder("Difficulty : 3"));
		JTextArea Chatting = new JTextArea(20,30);
		Chatting.setEditable(false);
		first_low.add(Problem);
		first_low.add(Chatting);
		frame.add(first_low,"Center");

		JLabel life = new JLabel(": x"+counter,temp,SwingConstants.CENTER);
		life.setOpaque(true);
		JButton button1 = new JButton("1");
		JButton button2 = new JButton("2");
		JButton button3 = new JButton("3");
		JButton button4 = new JButton("4");
		JButton button5 = new JButton("5");
		second_low.add(life);
		second_low.add(button1);
		second_low.add(button2);
		second_low.add(button3);
		second_low.add(button4);
		second_low.add(button5);
		frame.add(second_low,"South");
		
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
