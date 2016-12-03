import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Exchange extends JFrame{ //전체 프레임에서 내 deck info랑 새로운 또는 상대방 deck info를 받아서 실제 Deck클래스에 insert해서 이미지를 변경해준다.

	private static final int card_max = 5;
	private JLabel my_list;
	private JLabel new_list;
	private Deck my_deck;
	private Deck new_deck;
	private ArrayList<Integer> my_deck_info;
	private ArrayList<Integer> new_deck_info;
	private JButton get;
	private ImageIcon get_img;
	private JButton drop;
	private ImageIcon drop_img;
	private ImageIcon background_img;
	
	public Exchange(Deck original)
	{
		this.setName("Shift card");
		this.setLayout(null);
		this.setBounds(50, 100, 1150, 860);
		this.getContentPane().setBackground(new Color(244,101,40));
		new_deck = new Deck(); // 0으로 초기화 되있음 생성하면
		new_deck.setBounds(50,80,1060,250);
		new_deck_info = new_deck.get_info();
		add(new_deck);
		
		my_deck_info = original.get_info();
		my_deck = new Deck();
		for(int i=0; i<my_deck_info.size(); i++)
			my_deck.Insert(my_deck_info.get(i));
		my_deck.reset();
		my_deck.setBounds(50,450,1060,250);
		add(my_deck);
		
		my_list = new JLabel("Your Deck ");
		my_list.setBounds(50,400,100,10);
		add(my_list);
		
		new_list = new JLabel("New Deck ");
		new_list.setBounds(50,30,100,10);
		add(new_list);

		get = new JButton();
		get_img = new ImageIcon("get.png");
		get.setIcon(get_img);
		get.setBounds(330,730,103,58);
		add(get);
		
		drop = new JButton();
		drop_img = new ImageIcon("drop.jpg");
		drop.setIcon(drop_img);
		drop.setBounds(720,730,103,58);
		add(drop);	
	}
}


