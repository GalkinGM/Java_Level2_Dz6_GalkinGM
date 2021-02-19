package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler {
    private Server server;
    private  Socket socket1;
    private  DataInputStream in;
    private  DataOutputStream out;
    private String clientName1;
    private Thread threadOut;

    public ClientHandler(Server server, Socket socket, String clientName) {
        try {
            this.server = server;
            this.socket1 = socket;
            this.clientName1 = clientName;

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true) {

                        String srt = in.readUTF();
                        System.out.println( srt);


                        if (srt.equals("/end")) {
                            System.out.println("Client disconnected");
                            break;
                        }

//                        if(srt.startsWith("/")){
//                            if (srt.startsWith("/chengName ")) {
//                                clientName1 = srt.split("\\s", 2)[1];
//                            }
//                        }
//                        server.broadcastMsg(clientName1 + "\n" + srt);
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
                            sendMsg("Server: "+q);
                            if (q.equalsIgnoreCase("close")) break;
                        }
                    }
                }
            });
            threadOut.start();


        }catch (IOException e){
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
