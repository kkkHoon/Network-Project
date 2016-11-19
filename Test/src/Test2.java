import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Test2 { //jw
	
	public static void main(String[] args) {
		int counter = 10;
		ImageIcon back = new ImageIcon("back.jpg");
		ImageIcon heart = new ImageIcon("heart.jpg");
		ImageIcon send = new ImageIcon("send.jpg");
		JFrame cp = new JFrame("The card game");
		JPanel text_input = new JPanel();
		JPanel card_panel = new JPanel();
		JPanel chat_panel = new JPanel();
		JLabel timer = new JLabel("30 seconds");
		JTextArea problem = new JTextArea();
		JPanel Problem_panel = new JPanel();
		JTextField input_line = new JTextField("type here",10);
		JTextArea MessageArea = new JTextArea("Chatting Message here");
		
		card_panel.setLayout(null);
		card_panel.setBounds(50, 450, 1060, 250);
		card_panel.setBackground(new Color(136,133,164));
		
		JButton button1 = new JButton(back);
		JButton button2 = new JButton(back);
		JButton button3 = new JButton(back);
		JButton button4 = new JButton(back);
		JButton button5 = new JButton(back);
		card_panel.add(button1);
		card_panel.add(button2);
		card_panel.add(button3);
		card_panel.add(button4);
		card_panel.add(button5);
		button1.setBounds(10,10,200,230);
		button2.setBounds(220,10,200,230);
		button3.setBounds(430,10,200,230);
		button4.setBounds(640,10,200,230);
		button5.setBounds(850,10,200,230);
		
		button1.addMouseListener(new MouseListener(){


			public void mouseClicked(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {
				button1.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			}

			public void mouseExited(MouseEvent e) {button1.setBorder(null);}

			public void mousePressed(MouseEvent e) {
				//button1.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			}

			public void mouseReleased(MouseEvent e) {
				//button1.setBorder(null);		
			}	
		});
		
		JLabel life = new JLabel("X "+counter,heart,SwingConstants.CENTER);
		life.setBounds(1180,600,100,34);
		life.setBackground(new Color(245,209,183));
		
		JButton send_button = new JButton(send);
		send_button.setBounds(265,5,80,30);
		text_input.add(send_button);

		input_line.setEditable(true);
		input_line.setBounds(10,5,250,30);
		text_input.add(input_line);
		text_input.setLayout(null);
		text_input.setBounds(10, 280, 350, 40);
		text_input.setBackground(new Color(136,133,150));
		
		MessageArea.setBackground(new Color(136,133,150));
		MessageArea.setEditable(false);
		MessageArea.setBounds(10, 10, 350, 320);
		
		chat_panel.setLayout(null);
		chat_panel.setBounds(1100, 50, 370, 345);
		chat_panel.setBorder(BorderFactory.createSoftBevelBorder(0));
		chat_panel.add(text_input);
		chat_panel.add(MessageArea);
		
		timer.setBounds(900,15,100,30);
		timer.setBorder(BorderFactory.createBevelBorder(1));
		timer.setForeground(Color.red);
		timer.setFont(new Font(Font.DIALOG, Font.ITALIC, 14));
		
		problem.setBounds(10,60,1000,300);
		problem.setBorder(null);

		Problem_panel.setLayout(null);
		Problem_panel.setBounds(50,50,1020,380);
		Problem_panel.setBackground(new Color(122,154,130));
		Problem_panel.add(timer);
		Problem_panel.add(problem);
		
		cp.setLayout(null);
		cp.setBounds(250,150,1500,800);
		//cp.setBackground(new Color(136,133,164));
		cp.add(life);
		cp.add(card_panel);
		cp.add(chat_panel);
		cp.add(Problem_panel);
		cp.setVisible(true);
	
	cp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}