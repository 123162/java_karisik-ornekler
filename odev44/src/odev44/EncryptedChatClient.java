package odev44;
import java.awt.BorderLayout;
import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

public class EncryptedChatClient {

    private static final String SERVER_IP = "10.10.7.27";
    private static final int PORT = 12345;
    private static final String SECRET_KEY = "abcdefghijklmnopqrstuvwxyz123456";

    public static void main(String[] args) throws Exception {
        // Sayfa Başlığı 
        JFrame frame = new JFrame("CLIENT UYGULAMASI");

        //Label, TextField, Button, TextArea
        JLabel label2 = new JLabel("Server IP ");
        label2.setBounds(30, 10, 100, 25);
        frame.add(label2, BorderLayout.NORTH);

        JTextField serverIP = new JTextField();
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

        // Çarpıya Basınca Kapatma Kodu
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Create a socket to connect to the server
        Socket socket = new Socket(SERVER_IP, PORT);
        System.out.println("Serverla bağlantı kuruluyor " + SERVER_IP + ":" + PORT);

        // Set up input and output streams for sending and receiving data
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // Create a secret key for encryption/decryption
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        FileWriter fileWriter = new FileWriter("client_log.txt");
        // Wrap the FileWriter object in a BufferedWriter object to improve performance
        BufferedWriter logWriter = new BufferedWriter(fileWriter);

        // Enter the main loop
        while (true) {
            // Read a message from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Client: ");
            String message = reader.readLine();

            // Encrypt the message using the secret key
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedMessage = cipher.doFinal(message.getBytes());
            String encryptedMessageString = Base64.getEncoder().encodeToString(encryptedMessage);

            // Send the encrypted message to the server
            out.writeObject(encryptedMessageString);
            out.flush();

            // Get the current time and date
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            // Use a SimpleDateFormat object to format the date and time as a string
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String timeAndDate = dateFormat.format(date);

            // Write the message and time/date to the log file
            logWriter.write("["+timeAndDate+"]" + "[Client]" +"["+ message+"]");
            logWriter.newLine();
            logWriter.flush();

            // Read an encrypted response from the server
            String encryptedResponse = (String) in.readObject();

            // Decrypt the response using the secret key
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedResponse = cipher.doFinal(Base64.getDecoder().decode(encryptedResponse));
            String response = new String(decryptedResponse);

            // Print the decrypted response
            System.out.println("Server: " + response);
        }
    }
}