package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Server {

        private static final int PORT =8189;
        private static ServerSocket server;
        private static  Socket socket1;
        private List <ClientHandler> clients;
        private static int newClientIndex = 1;
        private String clientName;

    public static void main (String [] array){
        new Server();}

        public Server (){
            clients = new CopyOnWriteArrayList<>();

            try{
                server = new  ServerSocket(PORT);
                System.out.println("Server start");

                while (true){
                    socket1 = server.accept();
                    clientName = "Client " + newClientIndex;
                    newClientIndex++;

                    System.out.println("Client connected");
                    System.out.println("Client: " + clientName);
                    clients.add (new ClientHandler(this, socket1, clientName));

                }

            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    socket1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
//public void broadcastMsg (String msgr){
//
//            for (ClientHandler c : clients){
//                c.sendMsg(msgr);
//            }
//}
}
