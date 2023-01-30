package checkbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class CheckBox {

    public static void main(String[] args) {
        JFrame frame = new JFrame("DİLLER");
        JTextField text=new JTextField();
        text.setBounds(0, 0, 100, 40);
        frame.add(text);
        
        JMenuBar mb=new JMenuBar();
        JMenu menu=new JMenu("ana menü");
        JMenu submenu=new JMenu("alt menu");
        
        JMenuItem i1=new JMenuItem("menü 1");
        menu.add(i1);
        
        JMenuItem a1=new JMenuItem("Alt menü1");
        submenu.add(a1);
        
        JMenuItem a2=new JMenuItem("Alt menü2");
        submenu.add(a2);
        menu.add(submenu);
        mb.add(menu);
        
        
        JLabel lbl = new JLabel("bir dil seçiniz");
        lbl.setBounds(100, 50, 150, 50);
        frame.add(lbl);

        JCheckBox c1 = new JCheckBox("JAVA ");
        c1.setBounds(100, 100, 150, 50);
        frame.add(c1);

        JCheckBox c2 = new JCheckBox("PYTHON ");
        c2.setBounds(100, 150, 150, 50);
        frame.add(c2);

        JCheckBox c3 = new JCheckBox("C# ");
        c3.setBounds(100, 200, 150, 50);
        frame.add(c3);

        JCheckBox c4 = new JCheckBox("C ");
        c4.setBounds(100, 250, 150, 50);
        frame.add(c4);

        JButton button = new JButton("Gönder");
        button.setBounds(100, 350, 150, 30);
        frame.add(button);

        c1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                lbl.setText("java seçim kutusu" + (e.getStateChange() == 1 ? " seçildi" : "seçilmedi"));
            }

        });
        c2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                lbl.setText("python seçim kutusu" + (e.getStateChange() == 1 ? " seçildi" : "seçilmedi"));
            }

        });
        c3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                lbl.setText("C# seçim kutusu" + (e.getStateChange() == 1 ? " seçildi" : "seçilmedi"));
            }

        });
        c4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                lbl.setText("C seçim kutusu" + (e.getStateChange() == 1 ? " seçildi" : "seçilmedi"));
            }

        });

        button.addActionListener(new ActionListener() {
            int c=1;
            @Override
            public void actionPerformed(ActionEvent e) {
                
                lbl.setText("butona "+ c++ +" kez tıklandı");
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
        
        frame.setJMenuBar(mb);
        frame.setSize(500, 500);
        frame.setLayout(null);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
