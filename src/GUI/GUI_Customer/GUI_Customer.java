package GUI.GUI_Customer;

import GUI.GUI_Customer_Connector;
import GUI.HelpMethods;
import Person.Customer;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Die Klasse ist für die Hauptgui des Customers zuständig. Dort werden die Components
 * und Actions verwaltet und weitere Optionen für die GUI festgelegt.
 */
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
    private JButton btnExit;
    private JTextField txtPhone;
    private JTextField txtMail;
    private static int changeUserData = 0;

    /**
     * Dieser Konstruktor ist für die Actions und weitere Optionen des CUstomer-Gui´s zuständig.
     */
    public GUI_Customer() {

        initialize();
        HelpMethods hm = new HelpMethods();

        /**
         * Die Action des Buttons "Aktualisieren" im Tab Finanzübersicht ist für das Aktualisieren der Tabelle und der Konten zuständig.
         * Wenn sich Änderungen ergeben haben dann werden diese durch das Aktualisieren in der Tabelle übernommen.
         */
        btnRefresh1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /**
         * Die Action des Buttons "Überweisen" im Tab Überweisen triggert die Überweisung.
         */
        btnTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /**
         * Die Action des Buttons "Aktualisieren" im Tab Konten ist für das Aktualisieren der Tabelle und der Konten zuständig.
         * Wenn sich Änderungen ergeben haben dann werden diese durch das Aktualisieren in der Tabelle übernommen.
         */
        btnRefresh2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /**
         * Die Action des Buttons "Konto auflösen" im Tab Konten öffnet das Neben-Gui´s GUI_Customer_DeleteAccount
         */
        btnDeleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GUI_Customer_Connector.openDelete();

            }
        });

        /**
         * Die Action des Buttons "Neues Konto erstellen" im Tab Konten öffnet das Neben-Gui´s GUI_Customer_CreateAccount
         */
        btnNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GUI_Customer_Connector.openCreate();

            }
        });

        /**
         * Die Action des Buttons "Dispo- / Kreditrahmen ändern" im Tab Konten ist für das Ändern und Speichern
         * des Disporahmens eines vorher selektierten Kontos und die Aktivierung bzw. Deaktivierung des Textfeldes
         * für den Disporahmen zuständig.
         */
        btnDispoNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /**
         * Die Action des Buttons "Überweisungsrahmen ändern" im Tab Konten ist für das Ändern und Speichern
         * des Überweisungsrahmens eines vorher selektierten Kontos und die Aktivierung bzw. Deaktivierung
         * des Textfeldes für den Überweisungsrahmen zuständig.
         */
        btnChangeTransferLimit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /**
         * Die Action des Buttons "Aktualisieren" im Tab Benutzerdaten ist für das Aktualisieren der Tabelle und der Konten zuständig.
         * Wenn sich Änderungen ergeben haben dann werden diese durch das Aktualisieren in der Tabelle übernommen.
         */
        btnRefresh3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /**
         * Die Action des Buttons "Persönliche Daten ändern" im Tab Benutzerdaten ist für die Aktivierung der
         * Textfelder für die Änderung der Benutzerdaten im Tab Benutzerdaten zuständig.
         */
        btnCustomerDataChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(changeUserData == 0){
                    txtName.setEditable(true);
                    txtPrename.setEditable(true);
                    txtZip.setEditable(true);
                    txtCity.setEditable(true);
                    txtAddress.setEditable(true);
                    txtPhone.setEditable(true);
                    txtMail.setEditable(true);
                    changeUserData = 1;
                    btnCustomerDataChanges.setText("Abbrechen");
                }
                if(changeUserData == 1){
                    txtName.setEditable(false);
                    txtPrename.setEditable(false);
                    txtZip.setEditable(false);
                    txtCity.setEditable(false);
                    txtAddress.setEditable(false);
                    txtPhone.setEditable(false);
                    txtMail.setEditable(false);
                    changeUserData = 0;
                    btnCustomerDataChanges.setText("Persönliche Daten ändern");
                }

            }
        });

        /**
         * Die Action des Buttons "Speichern" im Tab Benutzerdaten ist für die Speicherung und der
         * Deaktivierung der Textfelder für die Änderung der Benutzerdaten im Tab Benutzerdaten zuständig.
         */
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hm.onlyString(txtName.getText(), false, 2) == true &&
                   hm.onlyString(txtPrename.getText(), false, 2) == true &&
                   hm.onlyInt(txtZip.getText()) == true && txtZip.getText().length() >= 5 &&
                   hm.onlyString(txtCity.getText(), true, 5) == true &&
                   hm.onlyString(txtAddress.getText(), true, 5) == true &&
                   hm.onlyInt(txtPhone.getText()) == true && txtPhone.getText().length() >= 5 &&
                   hm.onlyString(txtMail.getText(), false, 5) == true){

                    //Customer.changeUserData(txtName.getText(), txtPrename.getText(), txtZip.getText(), txtCity.getText(), txtAddress.getText(), txtPhone.getText(), txtMail.getText());
                    txtName.setEditable(false);
                    txtPrename.setEditable(false);
                    txtZip.setEditable(false);
                    txtCity.setEditable(false);
                    txtAddress.setEditable(false);
                    txtPhone.setEditable(false);
                    txtMail.setEditable(false);
                }
                else if(hm.onlyString(txtName.getText(), false, 2) == false ||
                        hm.onlyString(txtPrename.getText(), false, 2) == false ||
                        hm.onlyInt(txtZip.getText()) == false || txtZip.getText().length() < 5 ||
                        hm.onlyString(txtCity.getText(), true, 5) == false ||
                        hm.onlyString(txtAddress.getText(), true, 5) == false ||
                        hm.onlyInt(txtPhone.getText()) == false || txtPhone.getText().length() < 5 ||
                        hm.onlyString(txtMail.getText(), false, 5) == false){

                    if(hm.onlyString(txtName.getText(), false, 2) == false){txtName.setText("");}
                    if(hm.onlyString(txtPrename.getText(), false, 2) == false){txtPrename.setText("");}
                    if(hm.onlyInt(txtZip.getText()) == false || txtZip.getText().length() < 5){txtZip.setText("");}
                    if(hm.onlyString(txtCity.getText(), true, 5) == false){txtCity.setText("");}
                    if(hm.onlyString(txtAddress.getText(), true, 5) == false){txtAddress.setText("");}
                    if(hm.onlyInt(txtPhone.getText()) == false || txtPhone.getText().length() < 5){txtPhone.setText("");}
                    if(hm.onlyString(txtMail.getText(), false, 5) == false){txtMail.setText("");}
                    JOptionPane.showMessageDialog(null,"Bitte wiederholen Sie Ihre Eingabe.","Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);

                }

            }
        });

        /**
         * Die Action des Buttons "Programm beenden" im Tab Startseite ist für das Beenden des programmes zuständig.
         */
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_Customer_Connector.closeCreate();
                GUI_Customer_Connector.closeDelete();
                GUI_Customer_Connector.closeCustomer();
            }
        });
    }

    /**
     * Die Methode ist für die Initialisierung der GUI_Customer und deren Optionen zuständig.
     */
    public void initialize(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());
        add(Hauptpanel);
        pack();
        setLocationRelativeTo(null);
        setTitle("Turing Banking App");
        setSize(550, 385);
        setResizable(false);
    }

}