package guı;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

public class Guı {

    public static void main(String[] args) {
        // ------------------ TİTLE -------------------------------------
        JFrame frame = new JFrame("Diller");

        // ------------------ MENU --------------------------------------
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("AnaMenu");

        // ------------------ ALT MENU 1 ------------------------------------
        JMenu subMenu1 = new JMenu("AltMenu 1");
        menu.add(subMenu1);

        JMenuItem a1 = new JMenuItem("AltMenu 1");
        subMenu1.add(a1);
        JMenuItem a2 = new JMenuItem("AltMenu 2");
        subMenu1.add(a2);

        // ------------------ ALT MENU 2 ---------------------------------
        JMenu subMenu2 = new JMenu("AltMenu 2");
        menu.add(subMenu2);

        JMenuItem a3 = new JMenuItem("AltMenu 1");
        subMenu2.add(a3);
        JMenuItem a4 = new JMenuItem("AltMenu 2");

        subMenu2.add(a4);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // ----------------------- User Name and Password Alanı ----------------
        JLabel label2 = new JLabel("User Name ");
        label2.setBounds(50, 10, 100, 25);
        frame.add(label2);

        JTextField userName = new JTextField();
        userName.setBounds(200, 10, 100, 25);
        frame.add(userName);

        JLabel label1 = new JLabel("Password ");
        label1.setBounds(50, 50, 100, 25);
        frame.add(label1);

        JPasswordField password = new JPasswordField();
        password.setBounds(200, 50, 100, 25);
        frame.add(password);

        JButton button1 = new JButton("Giriş");
        button1.setBounds(50, 100, 100, 25);
        frame.add(button1);

        // ----------- Giriş butonuna basınca şifre ekranda görünsün istiyorsak bunu yazalım -------------------
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passwordd = new String(password.getPassword());
                label1.setText(label1.getText() + passwordd);
            }
        });

        // ---------------------------- CheckBox -----------------------------------------------------------------------------------
        JLabel label = new JLabel("Aşağıdaki programlama dillerinden seçim yapınız..");
        label.setBounds(50, 120, 1550, 50);
        frame.add(label);
        JCheckBox c1 = new JCheckBox("Java ");
        c1.setBounds(50, 150, 150, 50);
        frame.add(c1);
        JCheckBox c2 = new JCheckBox("C# ");
        c2.setBounds(50, 200, 150, 50);
        frame.add(c2);
        JCheckBox c3 = new JCheckBox("Python ");
        c3.setBounds(50, 250, 150, 50);
        frame.add(c3);
        JCheckBox c4 = new JCheckBox("Go ");
        c4.setBounds(50, 300, 150, 50);
        frame.add(c4);
        JButton button = new JButton("Gönder");
        button.setBounds(50, 350, 150, 30);
        frame.add(button);

        // ---------------------- ItemListener --------------------
        c1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                label.setText("Java seçim kutusu " + (e.getStateChange() == 1 ? "Seçildi" : "Seçilmedi"));
            }
        });
        c2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                label.setText("C# seçim kutusu " + (e.getStateChange() == 1 ? "Seçildi" : "Seçilmedi"));
            }
        });
        c3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                label.setText("Python seçim kutusu " + (e.getStateChange() == 1 ? "Seçildi" : "Seçilmedi"));
            }
        });
        c4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                label.setText("Go seçim kutusu " + (e.getStateChange() == 1 ? "Seçildi" : "Seçilmedi"));
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (c1.isSelected()) {
                    System.out.println(c1.getText() + "Seçildi");
                }
                if (c2.isSelected()) {
                    System.out.println(c2.getText() + "Seçildi");
                }
                if (c3.isSelected()) {
                    System.out.println(c3.getText() + "Seçildi");
                }
                if (c4.isSelected()) {
                    System.out.println(c4.getText() + "Seçildi");
                }
            }
        });

        // --------------------------- Sayfanın Büyüklüğü ---------------------
        frame.setSize(500, 500);
        frame.setLayout(null);

        // ---------------------- Çarpıya Basınca Kapatma Kodu ----------------
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}