import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.*;
import javax.swing.Timer;
import javax.swing.ImageIcon;


class Card extends Frame{
	Card(){
		setLayout(new FlowLayout());
		ImageIcon i = new ImageIcon("ugc.png");
		JButton b1 = new JButton(i);
		JButton b2 = new JButton("Click");
		JButton b3 = new JButton("Click");
		JButton b4 = new JButton("Click");
		
		OrderAction listener1 = new OrderAction();
		OrderAction listener2 = new OrderAction();
		OrderAction listener3 = new OrderAction();
		OrderAction listener4 = new OrderAction();
		Timer t = new Timer(10000,new ActionListener(){
			public void actionPerformed (ActionEvent e){
				if(b1.getText().equals("Click"))
					b1.doClick();
				if(b2.getText().equals("Click"))
					b2.doClick();
				if(b3.getText().equals("Click"))
					b3.doClick();
				if(b4.getText().equals("Click"))
					b4.doClick();
			}
		});
		t.start();
		t.setRepeats(false);
		
		
		b1.addActionListener(listener1);
		b2.addActionListener(listener2);
		b3.addActionListener(listener3);
		b4.addActionListener(listener4);
		
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		b1.setSize(100, 200);
		setSize(1000,2000);
		setVisible(true);
		
	}
}


public class Order {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Card c = new Card();
	}

}
