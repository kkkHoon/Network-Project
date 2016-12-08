import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Deck extends JPanel{

	private static final int card_max = 5;
	private static final int card_width = 200;
	private static final int card_height = 230;
	private int card_num;
	private JButton button1,button2,button3,button4,button5;
	private ArrayList<Integer> card_info;
	private ImageIcon back = new ImageIcon("back.jpg");
	private ImageIcon one = new ImageIcon("Level1.jpg");
	private ImageIcon two = new ImageIcon("Level2.jpg");
	private ImageIcon three = new ImageIcon("Level3.jpg");
	private ImageIcon point = new ImageIcon("Point.png");
	private ImageIcon reflect = new ImageIcon("Reflect.png");
	
	private int i;
	
	public Deck()
	{

		card_num = 0;
		card_info = new ArrayList<Integer>();
		for(i=0; i<card_max; i++)
			card_info.add(i,0);
		button1 = new JButton(back);
		button2 = new JButton(back);
		button3 = new JButton(back);
		button4 = new JButton(back);
		button5 = new JButton(back);
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.add(button4);
		this.add(button5);
		button1.setBounds(10,10,card_width,card_height);
		button2.setBounds(220,10,card_width,card_height);
		button3.setBounds(430,10,card_width,card_height);
		button4.setBounds(640,10,card_width,card_height);
		button5.setBounds(850,10,card_width,card_height);
		
		button1.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				button1.setPressedIcon(button1.getIcon());
			}
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
		this.setLayout(null);
		this.setBounds(50, 450, 1060, 250);
		this.setBackground(new Color(136,133,164));
	}
	
	public void Insert(int card_id) // 1 ~ 3 �� Level 1,2,3 �� �ǹ�. 4�� point 5�� reflect card�� �ǹ�.
	{
		for(int k=0; k<card_max; k++)
		{
			if(isZero(k)) // for���� ������ ���� ī�帮��Ʈ�߿��� ���� ó������ ���� ������ٰ� ���ο� ī�带 �������.
			{
				card_info.set(k,card_id);
				break;
			}
		}
		reset(); // ī�嵦 ������ �������Ƿ� ���� �������� �̹��� �籸��.
	}
	
	public void Delete(int k) // card_info array���� k��°�� �ִ� ī�带 ������.
	{
		card_info.set(k, 0);
		getBtn(k).setIcon(back); // �� ���� ī���̹����� ����.
	}
	
	public boolean isZero(int i) // card info array���� i���� ��ġ�� ī�尡 �ֳ� ������ üũ
	{
		if(card_info.get(i) == 0)
			return true;
		else
			return false;
	}
	
	public void reset()
	{
		for(int k=0; k<card_max; k++)
		{
			switch(card_info.get(k))
			{
			case 0 : getBtn(k).setIcon(back); break;
			case 1 : getBtn(k).setIcon(one); break;
			case 2 : getBtn(k).setIcon(two); break;
			case 3 : getBtn(k).setIcon(three); break;
			case 4 : getBtn(k).setIcon(point); break;
			case 5 : getBtn(k).setIcon(reflect); break;
			}
		}
	}
	
	public int send(int k) // ī�嵦���� k��° ī�带 �����ؼ� �����°� �ƴ϶� ���� �������� ���ְ� �ٸ� ���� return���ֱ� ���ؼ� ���� �Լ�
	{
		int temp = card_info.get(k);
		Delete(k);
		return temp;
	}
	
	public ArrayList<Integer> get_info() // ���� ���� �������ִ� ���¸� return ���ش�.
	{
		return card_info;
	}
	
	public JButton getBtn(int k)
	{
		switch(k)
		{
		case 0 : return button1; 
		case 1 : return button2; 
		case 2 : return button3;
		case 3 : return button4;
		case 4 : return button5;
		default : return button5;
		}
	}
}
