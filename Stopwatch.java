
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;


public class Stopwatch {

	static int interval=20;
	static Timer timer;
	static Timer timer2;
	static TimerTask task;
	static JFrame frame= new JFrame("Test");;
	static JLabel label= new JLabel("20");;
	static int delay;
    static int period;
	
	public void makeFrame(){
		frame.add(label);
		frame.setLocation(300, 200);
		frame.setPreferredSize(new Dimension(500, 400));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		delay = 1000;
		period = 1000;
	}
	public static void main(String[] args) {
		Stopwatch a = new Stopwatch();
		a.makeFrame();
		
	    timer = new Timer();
	    timer2 = new Timer();
	    task = new Mytimer();
	    

    	timer2.schedule(task, 5000);
	    timer.scheduleAtFixedRate(new TimerTask() {

	        public void run() {
	            label.setText(String.valueOf(setInterval()));
	        }
	    }, delay, period);
	    
	}

	private static final int setInterval() {
	    if (interval == 1){
	        timer.cancel();
		    timer2.cancel();
	    }
	    return --interval;
	}
	
}
