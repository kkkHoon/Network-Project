import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Test extends JPanel{ //jw


	private int life_counter;

	private ImageIcon back;
	private ImageIcon heart;
	private ImageIcon heart2;
	private ImageIcon send;
	private ImageIcon lock;

	private JPanel Problem_panel;
	private JPanel chat_panel;
	private JPanel text_btn_panel;
	private JPanel card_panel;
	private JPanel life_panel;

	private JLabel timer;
	private JTextArea problem;

	private JTextField input_line;
	private JTextArea MessageArea;
	private JScrollPane Message_pane;

	public Test(Client client)
	{
		back = new ImageIcon("back.jpg");
		heart = new ImageIcon("heart.jpg");
		heart2 = new ImageIcon("heart2.jpg");
		send = new ImageIcon("send.jpg");
		lock = new ImageIcon("lock.png");

		Problem_panel = new JPanel();
		chat_panel = new JPanel();
		text_btn_panel = new JPanel();
		card_panel = new JPanel();
		life_panel = new JPanel();

		timer = new JLabel("30 seconds");
		problem = new JTextArea();

		input_line = new JTextField("Chat message input line",10);
		MessageArea = new JTextArea("");
		Message_pane = new JScrollPane();

		JLabel life_arr[] = new JLabel[10];
		boolean life_state[] = {true,true,true,true,true,true,true,true,true,true};

		life_counter = 10;

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

		life_panel.setLayout(null);
		setlife(life_panel,life_arr,heart);
		life_panel.setBounds(1150, 600, 200, 68);
		life_panel.setBackground(new Color(245,209,183));

		JButton send_button = new JButton(send);
		send_button.setBounds(265,5,80,30);
		text_btn_panel.add(send_button);

		input_line.setEditable(true);
		input_line.setBounds(10,5,250,30);
		text_btn_panel.add(input_line);
		text_btn_panel.setLayout(null);
		text_btn_panel.setBounds(10, 280, 350, 40);
		text_btn_panel.setBackground(new Color(136,133,150));

		MessageArea.setBackground(new Color(136,133,150));
		MessageArea.setEditable(false);
		MessageArea.setLayout(null);
		MessageArea.setBounds(0, 0, 350, 260);
		MessageArea.setLineWrap(true);

		Message_pane.setBounds(10,10,350,260);
		Message_pane.setViewportView(MessageArea);
		Message_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Message_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		chat_panel.setLayout(null);
		chat_panel.setBounds(1100, 50, 370, 345);
		chat_panel.setBorder(BorderFactory.createSoftBevelBorder(0));
		chat_panel.add(text_btn_panel);
		chat_panel.add(Message_pane);

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

		this.setLayout(null);
		setBounds(250,150,1500,800);
		add(life_panel);
		add(card_panel);
		add(chat_panel);
		add(Problem_panel);
		setVisible(true);
		//this.setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//ä�� �Է�â�� ����Ű�� ������ ������ �޽����� ���۵ȴ�.
		input_line.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				client.out.println("Message" +input_line.getText()+"");		
				input_line.setText("");
			}	
		});

		//ä�� �Է�â�� ���콺 Ŭ���ϸ� �̹� ������ �ִ� ������ ���� �齺���̽��� �����鼭 �����ʿ���� �ѹ��� ��������.
		input_line.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				input_line.setText("");
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});

		//����Ű�� ������ �ʾƵ� �����ִ� ��ư�� Ŭ�������ν� ���� ȿ���� �� �� �ִ�.
		send_button.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent arg0) {
				client.out.println("Message" +input_line.getText()+"");	
				input_line.setText("");
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}

		});

		// �� ��ư���ٰ� �ش� ��ư���� ���콺�� �ö󰡰� ������ �� �̺�Ʈó���� �߰��ߴ�.
		button1.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				button1.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			}
			public void mouseExited(MouseEvent e) {button1.setBorder(null);}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		button2.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				button2.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			}
			public void mouseExited(MouseEvent e) {button2.setBorder(null);}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		button3.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				button3.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			}
			public void mouseExited(MouseEvent e) {button3.setBorder(null);}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		button4.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				button4.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			}
			public void mouseExited(MouseEvent e) {button4.setBorder(null);}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		button5.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				button5.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			}
			public void mouseExited(MouseEvent e) {button5.setBorder(null);}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
	}


	public void setlife(JPanel life_panel,JLabel life_arr[],ImageIcon heart) // initial setting
	{
		int i;
		int width = 40 , height = 34;

		for(i=0; i<10; i++)
		{
			life_arr[i] = new JLabel();
			life_arr[i].setIcon(heart);
			life_arr[i].setBounds((i%5)*width,(i/5)*height,width,height);
			life_panel.add(life_arr[i]);
		}
	}

	public void update_life(boolean life_state[],JLabel life_arr[],ImageIcon heart2,int counter) // ActionListener call this method to update current life.
	{		
		int i;
		int width = 40 , height = 34;

		for(i=counter; i<10; i++)
		{
			if(life_state[i] == true)
				life_state[i] = false;
			life_arr[i].setIcon(heart2);
			life_arr[i].setBounds((i%5)*width,(i/5)*height,width,height);
		}
	}

	public void show_message(String str)
	{
		MessageArea.append(str+"\n");
	}
}