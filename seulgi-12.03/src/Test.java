import java.awt.*;
import javax.swing.*;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;

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
	private Deck card_panel , temp_panel;
	private JPanel life_panel;
	private JPanel total_life;
	
	private JLabel timer;
	
	private JLabel life_name_1;
	private JLabel life_name_2;
	private JLabel life_name_3;
	private JLabel life_name_4;
	private JLabel life_heart_1;
	private JLabel life_heart_2;
	private JLabel life_heart_3;
	private JLabel life_heart_4;
	private JLabel life_1_num;
	private JLabel life_2_num;
	private JLabel life_3_num;
	private JLabel life_4_num;
	
	
	private JTextArea problem;

	private JTextField input_line;
	private JTextArea MessageArea;
	private JScrollPane Message_pane;
	private ArrayList<Integer> card_info;
	private Exchange card_exchange;
	
	static int timer_set = 30;


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
		card_panel = new Deck();
		life_panel = new JPanel();

		life_name_1 = new JLabel("1");
		life_name_2 = new JLabel("2");
		life_name_3 = new JLabel("3");
		life_name_4 = new JLabel("4");
		life_heart_1 = new JLabel(heart);
		life_heart_2 = new JLabel(heart);
		life_heart_3 = new JLabel(heart);
		life_heart_4 = new JLabel(heart);
		life_1_num = new JLabel("10");
		life_2_num = new JLabel("10");
		life_3_num = new JLabel("10");
		life_4_num = new JLabel("10");
		
		timer = new JLabel("30 seconds");
		total_life = new JPanel();
		problem = new JTextArea();

		input_line = new JTextField("Chat message input line",10);
		MessageArea = new JTextArea("");
		Message_pane = new JScrollPane();

		JLabel life_arr[] = new JLabel[10];
		boolean life_state[] = {true,true,true,true,true,true,true,true,true,true};
		
		life_counter = 10;

		
		total_life.setLayout(new GridLayout(4,3,2,2));
		total_life.setBounds(1150, 450, 250, 130);
		total_life.setBackground(new Color(136,133,150));
		total_life.add(life_name_1);
		total_life.add(life_heart_1);
		total_life.add(life_1_num);
		total_life.add(life_name_2);
		total_life.add(life_heart_2);
		total_life.add(life_2_num);
		total_life.add(life_name_3);
		total_life.add(life_heart_3);
		total_life.add(life_3_num);
		total_life.add(life_name_4);
		total_life.add(life_heart_4);
		total_life.add(life_4_num);
	
		
		
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

		card_panel.setBounds(50,450,1060,250);
		this.setLayout(null);
		setBounds(250,150,1500,800);
		add(total_life);
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

	//1초씩 delay
	public void pause(int time){

	    try {

	      Thread.sleep(time);

	    } catch (InterruptedException e) { }

	}

	//30초 카운트다운 여기에 아직 하트 -1안했음
	public void stopwatch(){
		while(timer_set>=0){
			pause(1000);
			timer_set--;
			timer.setText(String.valueOf(timer_set)+" seconds.");
			if(timer_set==0){
				life_counter--;
				timer.setText("---");//이거는 그저 확인용 나중에는 지우기
				timer_set=30;
			}
		}
	}
	
	//array가 시작 부분이 0인지 1인지 물어보기
	public void changLife(int[] array){
		for(int i=1;i<5;i++)
			aboutLife(i,array[i-1]);
	}
	

	public void aboutLife(int order, int life){
		if(order==1){
			life_1_num.setText(String.valueOf(life));
			if(life==0)
				life_heart_1.setIcon(heart2);
		}
		else if(order==2){
			life_2_num.setText(String.valueOf(life));
			if(life==0)
				life_heart_2.setIcon(heart2);
		}else if(order==3){
			life_3_num.setText(String.valueOf(life));
			if(life==0)
				life_heart_3.setIcon(heart2);
		}else if(order==4){
			life_4_num.setText(String.valueOf(life));
			if(life==0)
				life_heart_4.setIcon(heart2);
		}
	}
	
	public void SetMyLifeOrder(int order,String name){
		if(order==1)
			life_name_1.setText(name);
		else if(order==2)
			life_name_2.setText(name);
		else if(order==3)
			life_name_3.setText(name);
		else if(order==4)
			life_name_4.setText(name);
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
	
	public void newCard(ArrayList<Integer> temp)
	{
		
	}
	
	
}