package odev44;
import java.awt.BorderLayout;
import java.io.*;
import javax.crypto.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

public class Odev44 {

    private static final int PORT = 12345;
    private static final String SECRET_KEY = "abcdefghijklmnopqrstuvwxyz123456";

    public static void main(String[] args) throws Exception {
        // Sayfa Başlığı 
        JFrame frame = new JFrame("SERVER UYGULAMASI");

        // Label, TextField, Button, TextArea
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

        // Sayfanın Büyüklüğü 
        frame.setSize(480, 500);
        frame.setLayout(null);

        // ---------------------- Çarpıya Basınca Kapatma Kodu ----------------------------------------------
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Create a server socket to listen for client connections
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server portu dinliyor " + PORT + "...");

        // Accept the first client that connects
        Socket socket = serverSocket.accept();
        System.out.println("Clientla bağlantı sağlandı " + socket.getInetAddress());

        // Set up input and output streams for sending and receiving data
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // Create a secret key for encryption/decryption
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        // Create a FileWriter object to write the log to a file
        FileWriter fileWriter = new FileWriter("server_log.txt");
        // Wrap the FileWriter object in a BufferedWriter object to improve performance
        BufferedWriter logWriter = new BufferedWriter(fileWriter);

        // Enter the main loop
        while (true) {
            // Read an encrypted message from the client
            String encryptedMessage = (String) in.readObject();

            // Decrypt the message using the secret key
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedMessage = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            String message = new String(decryptedMessage);

            // Print the decrypted message
            System.out.println("Client: " + message);

            // Get the current time and date
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            // Use a SimpleDateFormat object to format the date and time as a string
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String timeAndDate = dateFormat.format(date);

            // Write the message to the log file
            logWriter.write("[" + timeAndDate + "]" + "[Client]" + "["+message+"]");
            logWriter.newLine();
            logWriter.flush();

            // Read a response from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Server: ");
            String response = reader.readLine();

            // Encrypt the response using the secret key
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedResponse = cipher.doFinal(response.getBytes());
            String encryptedResponseString = Base64.getEncoder().encodeToString(encryptedResponse);

            // Send the encrypted response to the client
            out.writeObject(encryptedResponseString);
            out.flush();
        }
    }
}
