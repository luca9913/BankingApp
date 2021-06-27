package GUI.GUI_Customer;

import GUI.GUI_Customer_Connector;
import GUI.GUI_Login.GUI_Login;
import GUI.HelpMethods;
import Person.Customer;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Vector;

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
    private JButton btnLogoff;
    private JButton aktualisierenButton;
    private static int changeUserData = 0;
    private Border defaultBorder;
    private static int dkRahmen = 0;
    private static int ueRahmen = 0;
    Border failedBorder = BorderFactory.createLineBorder(new Color(175, 0 , 0));
    Border correctBorder = BorderFactory.createLineBorder(new Color(0,109,77));

    /**
     * Dieser Konstruktor ist für die Actions und weitere Optionen des CUstomer-Gui´s zuständig.
     */
    public GUI_Customer() {
        initialize();
        HelpMethods hm = new HelpMethods();
        defaultBorder = txtName.getBorder();

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
                int maxUeberweisung = (Integer)GUI_Customer_Connector.kunde.data.executeCustomQuery("SELECT transfer_limit FROM account WHERE account_id= "+ GUI_Customer_Connector.kunde.getId() + ";").get(0)[0];
                double restbetrag = 0;//listAccounts2.getSelectedValue();
                if(hm.onlyDouble(txtAmountTo.getText()) == true && hm.parseDouble(txtAmountTo.getText()) > maxUeberweisung){
                    if(hm.parseDouble(txtAmountTo.getText()) > restbetrag ){
                        JOptionPane.showMessageDialog(null,"Ihr ausgewähltes Konto ist nicht ausreichend gedeckt.\n" +
                                "bitte wählen Sie ein anderes Konto aus oder passen\n" +
                                "Sie den Überweisungsbetrag an.","Konto nicht ausreichend gedeckt", JOptionPane.CANCEL_OPTION);
                        txtAmountTo.setText("");
                        txtAmountTo.setBorder(failedBorder);
                    }
                    else{
                        //GUI_Customer_Connector.kunde.refreshUserData(listAccounts2.getSelectedIndex(), hm.parseInt(txtIbanTo.getText()), hm.parseDouble(txtAmountTo.getText()), txtUsageTo.getText());
                        JOptionPane.showMessageDialog(null,"Ihre überweisung wurde erfolgreich getätigt.","Überweisung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                        txtAmountTo.setBorder(defaultBorder);
                    }
                }

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
                switch(dkRahmen){
                    case 0:
                        txtDispo.setEditable(true);
                        dkRahmen = 1;
                        btnDispoNew.setText("Dispo- / Kreditrahmen speichern");
                        break;
                    case 1:
                        if(hm.onlyInt(txtDispo.getText()) == true &&
                           hm.parseInt(txtDispo.getText()) >= 0 &&
                           hm.parseInt(txtDispo.getText()) <= 2000){

                            txtDispo.setEditable(false);
                            txtDispo.setBorder(defaultBorder);
                            dkRahmen = 0;
                            btnDispoNew.setText("Dispo- / Kreditrahmen ändern");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Bitte wiederholen Sie Ihre Eingabe.","Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);
                        }
                        break;
                }
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
                switch(ueRahmen){
                    case 0:
                        txtTransferLimit.setEditable(true);
                        ueRahmen = 1;
                        btnChangeTransferLimit.setText("Dispo- / Kreditrahmen speichern");
                        break;
                    case 1:
                        if(hm.onlyInt(txtTransferLimit.getText()) == true &&
                                hm.parseInt(txtTransferLimit.getText()) >= 0 &&
                                hm.parseInt(txtTransferLimit.getText()) <= 20000){

                            txtTransferLimit.setEditable(false);
                            txtTransferLimit.setBorder(defaultBorder);
                            ueRahmen = 0;
                            btnChangeTransferLimit.setText("Überweisungsrahmen ändern");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Bitte wiederholen Sie Ihre Eingabe.","Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);
                        }
                        break;
                }
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
                switch(changeUserData){
                    case 0:
                        txtName.setEditable(true);
                        txtPrename.setEditable(true);
                        txtZip.setEditable(true);
                        txtCity.setEditable(true);
                        txtAddress.setEditable(true);
                        txtPhone.setEditable(true);
                        txtMail.setEditable(true);

                        txtName.setBorder(correctBorder);
                        txtPrename.setBorder(correctBorder);
                        txtZip.setBorder(correctBorder);
                        txtCity.setBorder(correctBorder);
                        txtAddress.setBorder(correctBorder);
                        txtPhone.setBorder(correctBorder);
                        txtMail.setBorder(correctBorder);

                        changeUserData = 1;
                        btnCustomerDataChanges.setText("Abbrechen");
                        break;
                    case 1:
                        txtName.setEditable(false);
                        txtPrename.setEditable(false);
                        txtZip.setEditable(false);
                        txtCity.setEditable(false);
                        txtAddress.setEditable(false);
                        txtPhone.setEditable(false);
                        txtMail.setEditable(false);

                        txtName.setBorder(defaultBorder);
                        txtPrename.setBorder(defaultBorder);
                        txtZip.setBorder(defaultBorder);
                        txtCity.setBorder(defaultBorder);
                        txtAddress.setBorder(defaultBorder);
                        txtPhone.setBorder(defaultBorder);
                        txtMail.setBorder(defaultBorder);
                        changeUserData = 0;
                        btnCustomerDataChanges.setText("Persönliche Daten ändern");
                        break;
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
                   txtAddress.getText().length() >= 5 &&
                   hm.onlyInt(txtPhone.getText()) == true && txtPhone.getText().length() >= 5 &&
                   hm.onlyString(txtMail.getText(), false, 5) == true){

                    //GUI_Customer_Connector.kunde.changeMyData(txtName.getText(), txtPrename.getText(), txtZip.getText(), txtCity.getText(), txtAddress.getText(), txtPhone.getText(), txtMail.getText());
                    txtName.setBorder(defaultBorder);
                    txtPrename.setBorder(defaultBorder);
                    txtZip.setBorder(defaultBorder);
                    txtCity.setBorder(defaultBorder);
                    txtAddress.setBorder(defaultBorder);
                    txtPhone.setBorder(defaultBorder);
                    txtMail.setBorder(defaultBorder);

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
                        txtAddress.getText().length() < 5 ||
                        hm.onlyInt(txtPhone.getText()) == false || txtPhone.getText().length() < 5 ||
                        hm.onlyString(txtMail.getText(), false, 5) == false){

                    txtName.setBorder(correctBorder);
                    txtPrename.setBorder(correctBorder);
                    txtZip.setBorder(correctBorder);
                    txtCity.setBorder(correctBorder);
                    txtAddress.setBorder(correctBorder);
                    txtPhone.setBorder(correctBorder);
                    txtMail.setBorder(correctBorder);

                    if(hm.onlyString(txtName.getText(), false, 2) == false){
                        txtName.setBorder(failedBorder);
                    }
                    if(hm.onlyString(txtPrename.getText(), false, 2) == false){
                        txtPrename.setBorder(failedBorder);
                    }
                    if(hm.onlyInt(txtZip.getText()) == false || txtZip.getText().length() < 5){
                        txtZip.setBorder(failedBorder);
                    }
                    if(hm.onlyString(txtCity.getText(), true, 5) == false){
                        txtCity.setBorder(failedBorder);
                    }
                    if(hm.onlyString(txtAddress.getText(), true, 5) == false){
                        txtAddress.setBorder(failedBorder);
                    }
                    if(hm.onlyInt(txtPhone.getText()) == false || txtPhone.getText().length() < 5){
                        txtPhone.setBorder(failedBorder);
                    }
                    if(hm.onlyString(txtMail.getText(), false, 5) == false){
                        txtMail.setBorder(failedBorder);
                    }
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

        /**
         * Die Action des Buttons "Ausloggen" im Tab Startseite ist für das Ausloggen des Users zuständig.
         */
        btnLogoff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Hier fehlt noch Code sheesh
            }
        });

        /**
         * Die Action des Buttons "Aktualisieren" im Tab Überweisen ist für das Aktualisieren der Liste und der Konten zuständig.
         * Wenn sich Änderungen ergeben haben dann werden diese durch das Aktualisieren in der Liste übernommen.
         */
        aktualisierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

            Vector temp = new Vector(GUI_Customer_Connector.kunde.getAllAccounts());
            listAccounts1.setListData(temp);


    }

}