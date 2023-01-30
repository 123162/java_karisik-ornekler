package odev4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import javax.crypto.*;

public class EncryptedChatClient extends JFrame implements ActionListener {

    // Socket for connecting to server
    Socket socket;

    JTextField serverIP;
    JTextArea textArea1,textArea2;
    // Streams for sending and receiving data
    ObjectOutputStream out;
    ObjectInputStream in;

    // Secret key for encryption/decryption
    SecretKey key;

    public static void main(String[] args) {
        new EncryptedChatClient();
    }

    public EncryptedChatClient() {
        // ------------------ Sayfa Başlığı ----------------------------------------------------------------------
        JFrame frame = new JFrame("CLIENT UYGULAMASI");

        // ------------------ Label, TextField, Button, TextArea ----------------------------------------------------------------------
        JLabel label2 = new JLabel("Server IP ");
        label2.setBounds(30, 10, 100, 25);
        frame.add(label2, BorderLayout.NORTH);

        serverIP = new JTextField();
        serverIP.setBounds(90, 10, 130, 25);
        frame.add(serverIP);

        JLabel label3 = new JLabel("Port ");
        label3.setBounds(230, 10, 50, 25);
        frame.add(label3);

        JTextField port = new JTextField();
        port.setBounds(260, 10, 50, 25);
        frame.add(port);

        JButton button1 = new JButton("BAĞLAN");
        button1.setBounds(315, 10, 115, 25);
        frame.add(button1, BorderLayout.NORTH);

        textArea1 = new JTextArea();
        textArea1.setBounds(30, 50, 400, 300);
        frame.add(textArea1);

        textArea2 = new JTextArea();
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
        
        
        // Connect to server and set up streams
        try {
            socket = new Socket("192.168.91.126", 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            // İstemciyle iletişim kurmak için OutputStreamWriter ve BufferedWriter nesnelerini oluşturun
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            // Sunucuya veri gönderin
            bufferedWriter.write("Merhaba sunucu!");
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // Sunucudan cevap almak için InputStreamReader ve BufferedReader nesnelerini oluşturun
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // Sunucudan gelen veriyi okuyun
            String data = bufferedReader.readLine();
            System.out.println(data);

            // Dosyaya yazma işlemini gerçekleştirecek FileWriter nesnesini oluşturun
            FileWriter fileWriter = new FileWriter("client_log.txt");
            // FileWriter nesnesini BufferedWriter nesnesine wrap edin
            BufferedWriter logBufferedWriter = new BufferedWriter(fileWriter);

            // Gelen veriyi log dosyasına yazın
            logBufferedWriter.write(data);
            logBufferedWriter.newLine();
            logBufferedWriter.flush();

            // Get the secret key from the server
            key = (SecretKey) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start a separate thread for receiving messages
        Thread t = new Thread(new MessageReceiver());
        t.start();
    }

    // Called when a user clicks the send button or presses Enter in the input field
    public void actionPerformed(ActionEvent e) {
        String message = serverIP.getText();
        serverIP.setText("");

        // Encrypt the message using the secret key
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedMessage = cipher.doFinal(message.getBytes());

            // Send the encrypted message to the server
            out.writeObject(encryptedMessage);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
// Inner class for receiving messages from the server
    class MessageReceiver implements Runnable {

        public void run() {
            try {
                while (true) {
                    // Read an encrypted message from the server
                    byte[] encryptedMessage = (byte[]) in.readObject();

                    // Decrypt the message using the secret key
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE, key);
                    String message = new String(cipher.doFinal(encryptedMessage));

                    // Display the decrypted message in the message area
                    textArea1.append(message + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}