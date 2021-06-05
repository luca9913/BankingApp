package GUI.GUI_Customer;

import Login.Login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI_Customer extends JFrame{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton btnRefresh;
    private JTextField txtSearch;
    private JList listAccounts;
    private JTable tableTurnover;

    public GUI_Customer() {

        initialize();

        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());

    }

    private void initialize() {
        setTitle("Turing Banking App");
        setSize(1000, 700);
        setResizable(false);

        add(panel1);
    }

}
