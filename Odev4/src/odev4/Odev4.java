package odev4;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.*;

public class Odev4 {
  public static void main(String[] args) {
    // Arayüz penceresini oluşturma
    JFrame frame = new JFrame("My Interface");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);

    // Düğme oluşturma ve bir ActionListener ekleme
    JButton button = new JButton("Click me");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Düğmeye tıklandığında bir mesaj gönderme
        JOptionPane.showMessageDialog(frame, "You clicked the button!");
      }
    });

    // Arayüz elemanını pencereye ekleme
    frame.add(button, BorderLayout.SOUTH);

    // Pencereyi görünür hale getirme
    frame.setVisible(true);
  }
}
