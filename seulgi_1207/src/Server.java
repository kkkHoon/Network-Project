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
                     
                for (PrintWriter writer : writers.keySet())  // 한 명 씩 들어올 때마다 메시지창에 누가 들어왔는지 broadcast 해줌
                    writer.println("Broadcast"+"<SYSTEM>"+ name+" entered");
                				
                if(client_count == 4) // 마지막 한 명이 들어올 때 start 버튼을 눌러서 다른 모든 thread의 out으로 메시지를 보냄.
                {
                	for(PrintWriter writer : writers.keySet())
                	{
                		writer.println("START"); // 1번 2번 3번 카드는 각각 Level 1~3의 카드를 의미함. 처음 시작할 때 모두에게 3장식 랜덤으로 부여하고 시작
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
                    		if(writer.getValue() != attack_turn) // 현재 문제를 풀고있는 attack_turn은 대화를 할 수 없다.
                    			writer.getKey().println("Broadcast" + name + ": " + input.substring(7));
                        }                       
                    }
                    else if(input.startsWith("TURN_ACK")) // 오로지 YOUR_TURN을 받은 client만 TURN_ACK을 보냄. 응 내차례구나 알았어~
                    	out.println("WHO");
                    else if(input.startsWith("ATTACK")) // ATTACKL3 // 왼쪽유저에게 3 난이도 카드로 공격!
            		{
                    	char direction = input.charAt(6); // L or R
                    	if(direction == 'L')
                    		attack_turn = getLeft(turn-1);
                    	else
                    		attack_turn = getRight(turn-1);
                    	
            			for (java.util.Map.Entry<PrintWriter, Integer> writer : writers.entrySet()) 
                    	{
                    		if(writer.getValue() == attack_turn)  // timer작동시켜주기
                    			writer.getKey().println("ATK"+65);  //turn_input(7)에 의해 random한 문제골라서 주기.
                    		else
                    			writer.getKey().println("NATK"+65); // 얘도 마찬가지로 아까 선택된 random한 문제 주기.
                        } 
                    }
                    else if(input.startsWith("USE"))  //example : USE4F OR USE5
                    {
                    	if(input.charAt(3)-'0' == 5) // reflect card
                    	{
                    		attack_turn = turn;
                    		for (java.util.Map.Entry<PrintWriter, Integer> writer : writers.entrySet()) 
                        	{
                        		if(writer.getValue() == attack_turn)  // timer작동시켜주기
                        			writer.getKey().println("ATK"+65);  //turn_input(7)에 의해 random한 문제골라서 주기.
                        		else
                        			writer.getKey().println("NATK"+65); // 얘도 마찬가지로 아까 선택된 random한 문제 주기.
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
                        		if(writer.getValue() == attack_turn)  // timer작동시켜주기
                        			writer.getKey().println("ATK"+64);  //turn_input(7)에 의해 random한 문제골라서 주기.
                        		else
                        			writer.getKey().println("NATK"+64); // 얘도 마찬가지로 아까 선택된 random한 문제 주기.
                            } 
                    	}
                    }
                    else if(input.startsWith("ANSWER"))  // life_list 최신화해주고 넘겨주기~!!!!!!!!!!
                    {
                    	if(input.charAt(6) - '0' == 4) // 정답이 4라면
                    		out.println("CORRECT");
                    	else
                    	{
                    		life = life - 3; // 추후 getDifficulty(문제넘버)해서 난이도가 1인지 2인지 3인지에 따라 마이너스 해주는걸로 수정
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
                    	
                    	for(PrintWriter writer : writers.keySet())  // 예를들면 LIST27 : 2번째 유저의 라이프는 7개로 줄어듬!
                    		writer.println("LIST"+attack_turn+life_list.get(attack_turn-1)); // 틀린 클라이언트의 변화된 life를 업데이트 해준다.
                    	}
                    }
                    else if(input.startsWith("ACK")) // CORRECT 또는 WRONG을 받은 CLIENT가 보낸 메시지 // (CORRECT,WRONG) -> ACK -> 다음차례
                    {
                    	turn = (turn + 1) % client_count;
                    	PrintWriter next = getOut(turn);
                    	next.println("YOUR_TURN");
                    	next.println("SEND"+"5");
                    }
                    else if(input.startsWith("LOOT")) // LOSE 를 받은 CLIENT가 보낸 메시지 // (LOSE) -> LOOT -> 다음차례
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
                    		break; // 게임 끝!
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
    private static int getLeft(int array_index) //현재 turn인 유저의 index를 받고 그 왼쪽에 위치한 유저의 고유 ID를 return해준다.
    {
    	if(array_index == 0)
    		return client_array.get(client_array.size()-1); // index부분이므로 크기 - 1을 해줘야한다.
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
    
    private static void extractArray(int array_index) //공격받은 attack_turn의 index를 받아서 더이상 그가 게임에 참여하지 못하도록 turn이 넘어가도 차례가오지않게 client_array에서 빼줌
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