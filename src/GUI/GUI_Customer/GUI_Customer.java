package GUI.GUI_Customer;

import Login.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Customer extends JFrame {
    private JTabbedPane tabbedPaneMain;
    private JPanel Hauptpanel;
    private JPanel JPanelLinks;
    private JPanel JPanelRechts;
    private JList listAccounts;
    private JTextField txtSearch;
    private JTable tableTurnover;
    private JButton aktualisierenButton;
    private JTextField txtAccNr;
    private JTextField txtTurnoverNr;
    private JTextField txtAmount;
    private JTextField txtUsage;
    private JButton button1;
    private JList listAccFrom;
    private JButton btnTransfer;
    private JTextField txtNameTo;
    private JTextField txtIbanTo;
    private JTextField txtAmountTo;
    private JTextField txtUsageTo;
    private JLabel image;
    private JTextField txtName;
    private JButton persönlicheDatenÄndernButton;
    private JButton aktualisierenButton1;
    private JTextField txtPrename;
    private JTextField txtZip;
    private JTextField txtCity;
    private JTextField txtAddress;


    public GUI_Customer() {

        initialize();

        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());

    }

    private void initialize() {
        setTitle("Turing Banking App");
        setSize(400, 500);
        setResizable(false);

    }

}
