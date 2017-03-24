import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 24.03.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(80);
        ss.accept();
        while (true) {
            Socket s = ss.accept();
            Runnable action=new Runnable() {
                @Override
                public void run() {

                        handle(s);

                }

            };
            new Thread(action).start();
            //handle(s);
        }
    }

    static void handle(Socket s){
        try {
            try {
                BufferedReader rdr=new BufferedReader(new InputStreamReader(s.getInputStream(),"US-ASCII"));
                Pattern p= Pattern.compile("GET\\s+(\\S+)");
                while(true){
                    String line=rdr.readLine();
                    if (line==null||line.isEmpty())
                        break;
                    System.out.println(">>"+line);
                    Matcher m = p.matcher(line);
                    if (m.find()){
                        String resource=m.group(1);
                        System.out.println(resource);
                        OutputStream os=s.getOutputStream();
                        os.write(("You don't need "+resource+". You only need dimini.tk").getBytes("US-ASCII"));
                    }
                }
               //OutputStream os=s.getOutputStream();
                // os.write("dimini.tk".getBytes("US-ASCII"));
            } finally {
                s.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
