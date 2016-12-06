import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;



public class Server {
	private static int client_count =0;
	private static int turn = 1;
	private static int attack_turn = 0;
	private static ArrayList<Integer> life_list = new ArrayList<Integer>();
	private int start = 0;
	private static int timer=30;

	private static final int PORT = 9001;
	private static HashSet<String> names = new HashSet<String>();

 
    private static HashMap<PrintWriter,Integer> writers = new HashMap<PrintWriter,Integer>();
    private static ArrayList<Integer> client_array = new ArrayList<Integer>();

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT,4);

        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }


    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private int client_number;
        private int life;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        
        public void run() {
        	
            try {
               if(client_count==4){
                  socket.close();
               }
               
               client_count = client_count +1;
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                life = 10;
                
                while (true) {
                    out.println("SUBMITNAME");
                    name = in.readLine();
                    
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {
                            names.add(name);
                            break;
                        }
                    }
                }

                out.println("NAMEACCEPTED");
                writers.put(out,client_count);
                client_array.add(client_count);
                life_list.add(life);
                client_number = client_count;
                     
                for (PrintWriter writer : writers.keySet())  // �� �� �� ���� ������ �޽���â�� ���� ���Դ��� broadcast ����
                    writer.println("Broadcast"+"<SYSTEM>"+ name+" entered");
                				
                if(client_count == 4) // ������ �� ���� ���� �� start ��ư�� ������ �ٸ� ��� thread�� out���� �޽����� ����.
                {
                	for(PrintWriter writer : writers.keySet())
                	{
                		writer.println("START"); // 1�� 2�� 3�� ī��� ���� Level 1~3�� ī�带 �ǹ���. ó�� ������ �� ��ο��� 3��� �������� �ο��ϰ� ����
                		writer.println("SEND"+"1"+"2"+"3");
                	}
                	PrintWriter next = getOut(turn);
            		next.println("YOUR_TURN");
                }
                
                out.println("START");
                out.println("SEND"+1+2+3);
                try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                out.println("WHO");
                while (true) 
                {
                    String input = in.readLine();
                    System.out.println("input :"+input);
                    if (input == null)
                        return;
                    else if(input.startsWith("Message")) 
                    {
                    	for (java.util.Map.Entry<PrintWriter, Integer> writer : writers.entrySet()) 
                    	{
                    		if(writer.getValue() != attack_turn) // ���� ������ Ǯ���ִ� attack_turn�� ��ȭ�� �� �� ����.
                    			writer.getKey().println("Broadcast" + name + ": " + input.substring(7));
                        }                       
                    }
                    else if(input.startsWith("TURN_ACK")) // ������ YOUR_TURN�� ���� client�� TURN_ACK�� ����. �� �����ʱ��� �˾Ҿ�~
                    	out.println("WHO");
                    else if(input.startsWith("ATTACK")) // ATTACKL3 // ������������ 3 ���̵� ī��� ����!
            		{
                    	char direction = input.charAt(6); // L or R
                    	if(direction == 'L')
                    		attack_turn = getLeft(turn-1);
                    	else
                    		attack_turn = getRight(turn-1);
                    	
            			for (java.util.Map.Entry<PrintWriter, Integer> writer : writers.entrySet()) 
                    	{
                    		if(writer.getValue() == attack_turn)  // timer�۵������ֱ�
                    			writer.getKey().println("ATK"+65);  //turn_input(7)�� ���� random�� ������� �ֱ�.
                    		else
                    			writer.getKey().println("NATK"+65); // �굵 ���������� �Ʊ� ���õ� random�� ���� �ֱ�.
                        } 
                    }
                    else if(input.startsWith("USE"))  //example : USE4F OR USE5
                    {
                    	if(input.charAt(3)-'0' == 5) // reflect card
                    	{
                    		attack_turn = turn;
                    		for (java.util.Map.Entry<PrintWriter, Integer> writer : writers.entrySet()) 
                        	{
                        		if(writer.getValue() == attack_turn)  // timer�۵������ֱ�
                        			writer.getKey().println("ATK"+65);  //turn_input(7)�� ���� random�� ������� �ֱ�.
                        		else
                        			writer.getKey().println("NATK"+65); // �굵 ���������� �Ʊ� ���õ� random�� ���� �ֱ�.
                            } 
                    	}
                    	else
                    	{
                    		char direction = input.charAt(4);
                    		if(direction == 'L')
                        		attack_turn = getLeft(attack_turn-1);
                        	else if(direction == 'R')
                        		attack_turn = getRight(attack_turn-1);
                        	else
                        		attack_turn = getForward(attack_turn-1);
                        	
                			for (java.util.Map.Entry<PrintWriter, Integer> writer : writers.entrySet()) 
                        	{
                        		if(writer.getValue() == attack_turn)  // timer�۵������ֱ�
                        			writer.getKey().println("ATK"+64);  //turn_input(7)�� ���� random�� ������� �ֱ�.
                        		else
                        			writer.getKey().println("NATK"+64); // �굵 ���������� �Ʊ� ���õ� random�� ���� �ֱ�.
                            } 
                    	}
                    }
                    else if(input.startsWith("ANSWER"))  // life_list �ֽ�ȭ���ְ� �Ѱ��ֱ�~!!!!!!!!!!
                    {
                    	if(input.charAt(6) - '0' == 4) // ������ 4���
                    		out.println("CORRECT");
                    	else
                    	{
                    		life = life - 3; // ���� getDifficulty(�����ѹ�)�ؼ� ���̵��� 1���� 2���� 3������ ���� ���̳ʽ� ���ִ°ɷ� ����
                    		if(life > 0)
                    		{
                    			life_list.set(attack_turn-1, life); 
                    			out.println("WRONG");
                    		}
                    		else
                    		{
                    			life_list.set(attack_turn-1, 0);
                    			out.println("LOSE");
                    		}
                    	
                    	for(PrintWriter writer : writers.keySet())  // ������� LIST27 : 2��° ������ �������� 7���� �پ��!
                    		writer.println("LIST"+attack_turn+life_list.get(attack_turn-1)); // Ʋ�� Ŭ���̾�Ʈ�� ��ȭ�� life�� ������Ʈ ���ش�.
                    	}
                    }
                    else if(input.startsWith("ACK")) // CORRECT �Ǵ� WRONG�� ���� CLIENT�� ���� �޽��� // (CORRECT,WRONG) -> ACK -> ��������
                    {
                    	turn = (turn + 1) % client_count;
                    	PrintWriter next = getOut(turn);
                    	next.println("YOUR_TURN");
                    	next.println("SEND"+"5");
                    }
                    else if(input.startsWith("LOOT")) // LOSE �� ���� CLIENT�� ���� �޽��� // (LOSE) -> LOOT -> ��������
                    {
                    	getOut(turn).println("SEND"+input.substring(4));     
                    	extractArray(attack_turn-1);
                    	
                    	if(client_count != 1)
                    	{
                    		turn = (turn + 1) % client_count;
                    		PrintWriter next = getOut(turn);
                    		next.println("YOUR_TURN");
                    		next.println("SEND"+"5");
                    	}
                    	else
                    	{
                    		getOut(turn).println("WIN");
                    		break; // ���� ��!
                    	}      		
                    }
                }              
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    client_count --;
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
    private static int getLeft(int array_index) //���� turn�� ������ index�� �ް� �� ���ʿ� ��ġ�� ������ ���� ID�� return���ش�.
    {
    	if(array_index == 0)
    		return client_array.get(client_array.size()-1); // index�κ��̹Ƿ� ũ�� - 1�� ������Ѵ�.
    	else
    		return client_array.get(array_index-1);
    } 
    
    private static int getRight(int array_index)
    {
    	if(array_index == client_array.size()-1)
    		return client_array.get(0);
    	else
    		return client_array.get(array_index+1);
    }
    
    private static int getForward(int array_index)
    {
    	int enemey_index = (array_index + 2) % client_count;
    	if(enemey_index == array_index)
    		return getRight(array_index); // default
    	else
    		return client_array.get(enemey_index);
    }
    
    private static void extractArray(int array_index) //���ݹ��� attack_turn�� index�� �޾Ƽ� ���̻� �װ� ���ӿ� �������� ���ϵ��� turn�� �Ѿ�� ���ʰ������ʰ� client_array���� ����
    {
    	client_array.remove(array_index-1);
    	life_list.remove(array_index-1);
    	client_count--;
    }
    
    private static PrintWriter getOut(int turn_num)
    {
    	for (java.util.Map.Entry<PrintWriter, Integer> writer : writers.entrySet())
    	{
    		if(turn_num == writer.getValue())
    		{
    			return writer.getKey();
    		}
    	}
		return null;
    }

}