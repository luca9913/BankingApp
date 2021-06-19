package GUI.GUI_Customer;

import GUI.GUI_Customer_Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


public class GUI_Customer extends JFrame {
    private JTabbedPane tabbedPaneMain;
    private JPanel Hauptpanel;
    private JPanel JPanelLinks;
    private JPanel JPanelRechts;
    private JList listAccounts1;
    private JTextField txtSearch;
    private JTable tableTurnover;
    private JButton btnRefresh1;
    private JTextField txtAccNr;
    private JTextField txtReceiver;
    private JTextField txtAmount;
    private JTextField txtUsage;
    private JList listAccounts2;
    private JButton btnTransfer;
    private JTextField txtNameTo;
    private JTextField txtIbanTo;
    private JTextField txtAmountTo;
    private JTextField txtUsageTo;
    private JLabel image;
    private JTextField txtName;
    private JButton btnCustomerDataChanges;
    private JButton btnRefresh3;
    private JTextField txtPrename;
    private JTextField txtZip;
    private JTextField txtCity;
    private JTextField txtAddress;
    private JButton btnDeleteAccount;
    private JButton btnNewAccount;
    private JButton btnRefresh2;
    private JButton btnDispoNew;
    private JButton btnChangeTransferLimit;
    private JTextField txtTransferLimit;
    private JTextField txtDispo;
    private JButton btnSave;
    private JList listAccounts3;
    private JScrollPane tblTransfers;



    public GUI_Customer() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());

        add(Hauptpanel);
        pack();
        setLocationRelativeTo(null);
        setTitle("Turing Banking App");
        setSize(550, 385);
        setResizable(false);

        //Action für den Button "Aktualisieren im Tab Finanzübersicht"
        btnRefresh1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action für den Button "Überweisen im Tab überweisen"
        btnTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action für den Button "Aktualisieren im Tab Konten"
        btnRefresh2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action für den Button "Konto auflösen im Tab Konten"
        btnDeleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GUI_Customer_Connector.openDelete();
                //setEnabled(false);

            }
        });

        //Action für den Button "Neues Konto erstellen im Tab Konten"
        btnNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GUI_Customer_Connector.openCreate();
                //setEnabled(false);

            }
        });

        //Action für den Button "Disporahmen im Tab Konten"
        btnDispoNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action für den Button "Überweisungsrahmen ändern im Tab Konten"
        btnChangeTransferLimit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action für den Button "Aktualisieren im Tab Benutzerdaten"
        btnRefresh3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action für den Button "Persönliche Daten ändern im Tab Benutzerdaten"
        btnCustomerDataChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action für den Button "Speichern im Tab Benutzerdaten"
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

}
