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


public class OrderAction implements ActionListener {
	public void actionPerformed(ActionEvent e){
		JButton b = (JButton)e.getSource();
		
		if(b.getText().equals("Click")){
			b.setText("Clicked");
        	b.setBackground(new Color(255,128,0));
        	b.setEnabled(false);
		}			
	}
}
