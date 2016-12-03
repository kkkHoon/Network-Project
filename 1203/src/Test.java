import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class Test extends JPanel{ //jw


	private int life_counter;
	private int time;

	private ImageIcon back;
	private ImageIcon heart;
	private ImageIcon heart2;
	private ImageIcon send;
	private ImageIcon lock;
	private ImageIcon temp;

	private JPanel Problem_panel;
	private JPanel chat_panel;
	private JPanel text_btn_panel;
	private Deck card_panel , temp_panel;
	private JPanel life_panel;

	private JLabel timer;
	private JLabel problem;

	private JTextField input_line;
	private JTextArea MessageArea;
	private JScrollPane Message_pane;
	
	private ArrayList<Integer> card_info;
	private Exchange card_exchange;
	
	private JButton Answer1;
	private JButton Answer2;
	private JButton Answer3;
	private JButton Answer4;

	public Test(Client client)
	{
		back = new ImageIcon("back.jpg");
		heart = new ImageIcon("heart.jpg");
		heart2 = new ImageIcon("heart2.jpg");
		send = new ImageIcon("send.jpg");
		lock = new ImageIcon("lock.png");
		temp = new ImageIcon("back2.jpg");

		Problem_panel = new JPanel();
		chat_panel = new JPanel();
		text_btn_panel = new JPanel();
		card_panel = new Deck();
		life_panel = new JPanel();

		time = 30;
		timer = new JLabel(time+" seconds");
		problem = new JLabel();

		input_line = new JTextField("Chat message input line",10);
		MessageArea = new JTextArea("");
		Message_pane = new JScrollPane();

		JLabel life_arr[] = new JLabel[10];
		boolean life_state[] = {true,true,true,true,true,true,true,true,true,true};

		life_counter = 10;

		
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

		problem.setBounds(10,60,1000,250);
		problem.setBorder(null);
		problem.setIcon(temp);
		
		Answer1 = new JButton("one");
		Answer2 = new JButton("two");
		Answer3 = new JButton("three");
		Answer4 = new JButton("four");
		
		//Answer1.setLayout(null);
		Answer1.setBounds(150,320,100,50);
		//Answer2.setLayout(null);
		Answer2.setBounds(350,320,100,50);
		//Answer3.setLayout(null);
		Answer3.setBounds(550,320,100,50);
		//Answer4.setLayout(null);
		Answer4.setBounds(750,320,100,50);
		
		Problem_panel.setLayout(null);
		Problem_panel.setBounds(50,50,1020,380);
		Problem_panel.setBackground(new Color(122,154,130));
		Problem_panel.add(timer);
		Problem_panel.add(problem);
		Problem_panel.add(Answer1);
		Problem_panel.add(Answer2);
		Problem_panel.add(Answer3);
		Problem_panel.add(Answer4);

		card_panel.setBounds(50,450,1060,250);
		this.setLayout(null);
		setBounds(250,150,1500,800);
		add(life_panel);
		add(card_panel);
		add(chat_panel);
		add(Problem_panel);
		setVisible(true);
		//this.setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//card_panel.setLocation(50,450);
		
		card_info = new ArrayList<Integer>(5);
		card_info.add(0); // addAll 로 수정가능하면 하기.
		card_info.add(0);
		card_info.add(0);
		card_info.add(0);
		card_info.add(0);
		
		//temp_panel = card_panel;
		card_exchange = new Exchange(card_panel);
		card_exchange.setVisible(true);

		//채팅 입력창에 엔터키를 누르면 서버로 메시지가 전송된다.
		input_line.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				client.out.println("Message" +input_line.getText()+"");		
				input_line.setText("");
			}	
		});

		//채팅 입력창에 마우스 클릭하면 이미 기존에 있던 내용을 굳이 백스페이스바 누르면서 지울필요없이 한번에 없어진다.
		input_line.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				input_line.setText("");
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});

		//엔터키를 누르지 않아도 옆에있는 버튼을 클릭함으로써 같은 효과를 낼 수 있다.
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
	
	public void timedown()
	{
		for(int i = 30; i>=0; i--)
		{
			timer.setText(i+" seconds");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}