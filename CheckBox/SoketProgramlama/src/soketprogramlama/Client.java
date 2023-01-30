package soketprogramlama;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        String gonderilecek;

        try {
            socket = new Socket("10.10.3.205", 5555);
            //mesaj göndermek için
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            //mesaj almak için
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader data = new BufferedReader(new InputStreamReader(System.in));

            while ((gonderilecek = data.readLine()) != null) {

                out.println(gonderilecek);
                System.out.println("Server'dan gelen:" + in.readLine());

            }
            out.close();
            in.close();;
            data.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("Client Hatası !!!");
        }

    }

}
