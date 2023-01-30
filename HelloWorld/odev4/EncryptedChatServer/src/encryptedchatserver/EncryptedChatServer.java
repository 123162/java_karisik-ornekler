package odev4;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;

import javax.crypto.*;
import javax.swing.WindowConstants;

import javax.swing.*;

public class EncryptedChatServer {

    // Socket for listening for incoming connections
    ServerSocket serverSocket;

    // Secret key for encryption/decryption
    SecretKey key;

    public static void main(String[] args) {

        // ------------------ Sayfa Başlığı -----
        JFrame frame = new JFrame("SERVER UYGULAMASI");

        // ------------------ Label, TextField, Button, TextArea ----------------------------------------------------------------------
        JLabel label2 = new JLabel("Dinlenecek Port Numarası ");
        label2.setBounds(30, 10, 200, 25);
        frame.add(label2, BorderLayout.NORTH);

        JTextField userName = new JTextField();
        userName.setBounds(200, 10, 75, 25);
        frame.add(userName);

        JButton button1 = new JButton("Serverı Başlat");
        button1.setBounds(305, 10, 125, 25);
        frame.add(button1, BorderLayout.SOUTH);

        JTextArea textArea1 = new JTextArea();
        textArea1.setBounds(30, 50, 400, 300);
        frame.add(textArea1);

        JTextArea textArea2 = new JTextArea();
        textArea2.setBounds(30, 360, 300, 75);
        frame.add(textArea2);
        
        JButton button2 = new JButton("Gönder");
        button2.setBounds(340, 360, 90, 75);
        frame.add(button2);

        // --------------------------- Sayfanın Büyüklüğü ----------------------------------------------------
        frame.setSize(480, 500);
        frame.setLayout(null);

        // ---------------------- Çarpıya Basınca Kapatma Kodu ----------------------------------------------
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        new EncryptedChatServer();
    }

    public EncryptedChatServer() {
        // Generate secret key for encryption/decryption
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            key = keyGen.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start listening for incoming connections
        try {
            serverSocket = new ServerSocket(12345);
            
            // Dosyaya yazma işlemini gerçekleştirecek FileWriter nesnesini oluşturun
            FileWriter fileWriter = new FileWriter("server_log.txt");
            
            // FileWriter nesnesini BufferedWriter nesnesine wrap edin
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            while (true) {
                Socket socket = serverSocket.accept();
                
                // İstemciyle iletişim kurmak için InputStreamReader ve BufferedReader nesnelerini oluşturun
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                // İstemciden gelen veriyi okuyun
                String data = bufferedReader.readLine();

                // Gelen veriyi log dosyasına yazın
                bufferedWriter.write(data);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                
                // İstemciyle iletişim kurmak için OutputStreamWriter ve BufferedWriter nesnelerini oluşturun
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                BufferedWriter outputBufferedWriter = new BufferedWriter(outputStreamWriter);

                // İstemciye veri gönderin
                outputBufferedWriter.write("Veri alındı");
                outputBufferedWriter.newLine();
                outputBufferedWriter.flush();
                
                ClientHandler handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inner class for handling incoming messages from a client
    class ClientHandler implements Runnable {

        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;

        public ClientHandler(Socket socket) {
            this.socket = socket;

            // Set up input and output streams for this client
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                // Send the secret key to the client
                out.writeObject(key);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                while (true) {
                    // Read an encrypted message from the client
                    byte[] encryptedMessage = (byte[]) in.readObject();

                    // Decrypt the message using the secret key
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE, key);
                    String message = new String(cipher.doFinal(encryptedMessage));

                    // Print the decrypted message to the console
                    System.out.println(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}