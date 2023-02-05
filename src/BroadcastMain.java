import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BroadcastMain extends Thread{
    ArrayList clients = new ArrayList<Socket>();
    int nbClient = 0;
    public static void main(String[] args) throws IOException {
        new BroadcastMain().start();
    }

    @Override
    public void run() {
        System.out.println("DEMARRAGE DU SERVEUR.......");
        try
        {
            ServerSocket ss = new ServerSocket(1234);

            while (true)
            {
                Socket socket = ss.accept();
                clients.add(socket);
                nbClient++;
                Conversation conversation = new Conversation(socket,nbClient);
                conversation.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("Thread en cours d'exécution...");
        }
    }

    private class Conversation extends Thread{
        private Socket socket;
        private int numClient;
        public Conversation(Socket socket, int numClient) {
            super();
            this.socket = socket;
            this.numClient = numClient;
        }

        public void Broadcast(String message, Socket socket, int numClient)//La méthode pour la diffusion des requêtes
        {
            for(int i=0; i<clients.size(); i++)
            {
                try {
                    OutputStream os = socket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void run()
        {
            try {
                InputStream is =  socket.getInputStream();//Pour la récupération des octets
                InputStreamReader isr = new InputStreamReader(is);//Pour la récupération des caractères
                BufferedReader br = new BufferedReader(isr);//Pour la récupération des chaînes de caractères

                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);//Pour envoyer les chaînes de caractères

                System.out.println("Connexion du client numero "+numClient );
                String IP = socket.getRemoteSocketAddress().toString();
                pw.println("Connexion du client numero "+numClient+ " pour adresse ip: "+IP);

                while (true)
                {
                    String req = br.readLine();
                    if (req != null)
                    {
                        pw.println("Le client numero " +numClient+ " a envoye " +req);
                        int longChaine = req.length();//pour connaitre la longueuer de chaine de caractere de la requete
                        pw.println("Longueur :" +longChaine);
                    }
                }
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
        }
    }
}