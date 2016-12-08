import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;



public class Server {
	static int client_count =0;
	private int turn = 1;
	private int start = 0;

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
        private int count;
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
                System.out.println("current added :: "+client_count);
                     
                for (PrintWriter writer : writers.keySet())  // 한 명 씩 들어올 때마다 메시지창에 누가 들어왔는지 broadcast 해줌
                    writer.println("SYSTEM"+name+" entered");
                				
                if(client_count == 4) // 마지막 한 명이 들어올 때 start 버튼을 눌러서 다른 모든 thread의 out으로 메시지를 보냄.
                {
                	for(PrintWriter writer : writers.keySet())
                		writer.println("START"+"1"+"2"+"3"); // 1번 2번 3번 카드는 각각 Level 1~3의 카드를 의미함. 처음 시작할 때 모두에게 3장식 랜덤으로 부여하고 시작
                	//현재 turn 변수에 맞는 out으로 println("YOUR_TURN"+i)해주기.
                }
                while (true) {
                    String input = in.readLine();
                    System.out.println("input :"+input);
                    if (input == null) {
                        return;
                    }
                    else if(input.startsWith("Message")) {
                        for (PrintWriter writer : writers.keySet()) {
                            writer.println("Broadcast" + name + ": " + input.substring(7));
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
    public static int getLeft(int array_num)
    {
    	int location = client_array.indexOf(array_num);
    	if(location == 0)
    		return client_array.get(client_array.size()-1);
    	else
    		return client_array.get(location-1);
    }
    
    public static int getRight(int array_num)
    {
    	int location = client_array.indexOf(array_num);
    	if(location == client_array.size()-1)
    		return client_array.get(0);
    	else
    		return client_array.get(location+1);
    }
    public static void extractClient(int array_num)
    {
    	int location = client_array.indexOf(array_num);
    	client_array.remove(location);
    }

}