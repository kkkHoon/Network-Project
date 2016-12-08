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
	private static int current_turn = 0;

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
               if(client_count==5){
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
               
                /*
                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE "+"Welcome " + name+" !!!!" );
                } 
				*/
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