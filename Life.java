
import javax.swing.*;
import java.awt.*;


public class Life extends JFrame{
	int count =10;
	Life(){
		setTitle("TEST");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(4,3);
		grid.setVgap(5);
		ImageIcon p1 = new ImageIcon("img\\gg.png");
		setLayout(grid);
		add(new JLabel("a"));
		add(new JLabel(p1));
		add(new JLabel(String.valueOf(count)));
		add(new JLabel("b"));
		add(new JLabel(p1));
		add(new JLabel(String.valueOf(count)));
		add(new JLabel("c"));
		add(new JLabel(p1));
		add(new JLabel("10"));
		
		setSize(300,200);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Life();
	}

}
