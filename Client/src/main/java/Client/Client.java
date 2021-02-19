package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private final  int PORT = 8189;
    private  final String IP_ADRESS = "localhost";
    private Thread threadOut;


    public static void main(String[] args) {
                new Client();
            }

    public  Client () {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        String srt = in.readUTF();
                        System.out.println(srt);
                        if (srt.equals("/end")) {
                            System.out.println("Server disconnected");
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            threadOut = new Thread(new Runnable() {
                Scanner input = new Scanner(System.in);
                @Override
                public void run() {
                    while (true) {
                        if (input.hasNext()) {
                            String q = input.next();
                            sendMsg("Client: " + q);
                            if (q.equalsIgnoreCase("close")) break;
                        }
                    }
                }
            });
            threadOut.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg (String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

