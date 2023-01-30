package soketprogramlama;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoketProgramlama {//server

    public static void main(String[] args) {
            String clientMesaj;
        ServerSocket serverSocket=null; 
        Socket clientSocket=null; 
        String degiskeMesaj="Merhaba mesajınız alındı";
        
        try {
        serverSocket= new ServerSocket(5555);
        System.out.println("SERVER BAŞLATILDI !!");
        clientSocket=serverSocket.accept();
        System.out.println("BAĞLANTI SAĞLANDI");

        //mesaj göndermek için
        PrintWriter out= new PrintWriter(clientSocket.getOutputStream(),true);
        
        //mesaj almak için
        BufferedReader in= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        while((clientMesaj=in.readLine())!=null){
        
            System.out.println("Gelen Mesaj="+clientMesaj);
            
            out.println(degiskeMesaj);
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
        }        
        
       
        }catch(Exception e)
        {
        System.out.println("Port numarası hatalı veya dolu!!!");
        }     
        
    }

}
