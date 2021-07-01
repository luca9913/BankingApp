package GUI.GUI_Customer;

import GUI.GUI_Customer_Connector;
import GUI.GUI_Login.GUI_Login;
import GUI.HelpMethods;
import Konto.Konto;
import Login.Login;
import Person.Customer;
import Person.Person;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private JLabel lblHello;
    private JLabel srlabel;
    private JTextField txtDate;
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
                updateAccountLists();
                tableTurnover.setModel(new DefaultTableModel());
            }
        });

        /**
         * Die Action des Buttons "Überweisen" im Tab Überweisen triggert die Überweisung.
         */
        btnTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double maxUeberweisung = GUI_Customer_Connector.kunde.allaccounts.get(listAccounts2.getSelectedIndex()).getLimit();
                double restbetrag = GUI_Customer_Connector.kunde.allaccounts.get(listAccounts2.getSelectedIndex()).getBalance();
                double dispo = GUI_Customer_Connector.kunde.allaccounts.get(listAccounts2.getSelectedIndex()).getDispo();
                double rahmen = restbetrag + dispo;
                double transfer = Integer.parseInt(txtAmountTo.getText()) - rahmen;
                if(listAccounts2.isSelectionEmpty() == true){
                    JOptionPane.showMessageDialog(null,"Bitte wählen Sie ein Konto aus der Liste aus.","Kein konto ausgewählt", JOptionPane.ERROR_MESSAGE);
                }
                else if(hm.onlyDouble(txtAmountTo.getText()) == true && hm.parseDouble(txtAmountTo.getText()) > 0 && transfer > 0 && maxUeberweisung > hm.parseDouble(txtAmountTo.getText())){
                    JOptionPane.showMessageDialog(null,"Ihre überweisung wurde erfolgreich getätigt.","Überweisung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                    String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
                    GUI_Customer_Connector.kunde.transfer(GUI_Customer_Connector.kunde.allaccounts.get(listAccounts2.getSelectedIndex()).getId(), hm.parseInt(txtIbanTo.getText()), hm.parseDouble(txtAmountTo.getText()), txtUsageTo.getText(), currentDate);
                    txtAmountTo.setBorder(defaultBorder);
                    txtNameTo.setText("");
                    txtIbanTo.setText("");
                    txtAmountTo.setText("");
                    txtUsageTo.setText("");
                }
                else if(hm.onlyDouble(txtAmountTo.getText()) == false || hm.parseDouble(txtAmountTo.getText()) < 0 || transfer < 0 || maxUeberweisung < hm.parseDouble(txtAmountTo.getText())){
                    if(transfer < 0){
                        JOptionPane.showMessageDialog(null,"Ihr ausgewähltes Konto ist nicht ausreichend gedeckt.\n" +
                                "bitte wählen Sie ein anderes Konto aus oder passen\n" +
                                "Sie den Überweisungsbetrag an.","Konto nicht ausreichend gedeckt", JOptionPane.CANCEL_OPTION);
                        txtAmountTo.setBorder(failedBorder);
                        txtAmountTo.setText("");
                    }
                    if(hm.onlyDouble(txtAmountTo.getText()) == false){
                        JOptionPane.showMessageDialog(null,"Ihre Eingabe war Fehlerhaft.","Betrag fehlerhaft", JOptionPane.ERROR_MESSAGE);
                        txtAmountTo.setBorder(failedBorder);
                        txtAmountTo.setText("");
                    }
                    if(maxUeberweisung < hm.parseDouble(txtAmountTo.getText())){
                        JOptionPane.showMessageDialog(null,"Ihr Überweisungsbetrag überschreitet Ihr Überweisungslimit.\n" +
                                "Bitte geben Sie den überweisungsbetrag erneut ein.","Betrag fehlerhaft", JOptionPane.ERROR_MESSAGE);
                        txtAmountTo.setBorder(failedBorder);
                        txtAmountTo.setText("");
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
                updateAccountLists();
            }
        });

        /**
         * Die Action des Buttons "Konto auflösen" im Tab Konten öffnet das Neben-Gui´s GUI_Customer_DeleteAccount
         */
        btnDeleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GUI_Customer_Connector.kunde.allaccounts.size() > 1){
                    GUI_Customer_Connector.openDelete();
                }else{
                    JOptionPane.showMessageDialog(null,"Um ihr letztes Konto aufzulösen kontaktieren Sie ihren zuständigen Banker.","Letztes Konto", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /**
         * Die Action des Buttons "Neues Konto erstellen" im Tab Konten öffnet das Neben-Gui´s GUI_Customer_CreateAccount
         */
        btnNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_Customer_Connector.openCreate();
                /*alternative method to invoke the new frames, must be written as a new class GUI_CreateAccount or something
                JFrame addAccount = new JFrame("Konto erstellen");
                addAccount.setIconImage(new ImageIcon("src/img/Turing Bank Square (32x32).png").getImage());
                addAccount.setLocationRelativeTo(null);
                addAccount.setTitle("Konto erstellen");
                addAccount.setSize(350, 270);
                addAccount.setResizable(false);
                addAccount.toFront();
                addAccount.setAlwaysOnTop(true);
                addAccount.isFocused();
                addAccount.setVisible(true);
                setEnabled(false); //sets the underlying Customer_GUI to the disabled state */
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
                            GUI_Customer_Connector.kunde.createDispoRequest("dispo", txtDispo.getText(), listAccounts3.getSelectedIndex());
                            txtDispo.setEditable(false);
                            txtDispo.setBorder(defaultBorder);
                            dkRahmen = 0;
                            btnDispoNew.setText("   Dispo- / Kreditrahmen ändern   ");
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
                        btnChangeTransferLimit.setText("Überweisungsrahmen speichern");
                        break;
                    case 1:
                        if(hm.onlyInt(txtTransferLimit.getText()) == true &&
                            hm.parseInt(txtTransferLimit.getText()) >= 0 &&
                            hm.parseInt(txtTransferLimit.getText()) <= 20000){
                            GUI_Customer_Connector.kunde.createLimitRequest("transferlimit", txtTransferLimit.getText(), listAccounts3.getSelectedIndex());
                            txtTransferLimit.setEditable(false);
                            txtTransferLimit.setBorder(defaultBorder);
                            ueRahmen = 0;
                            btnChangeTransferLimit.setText("   Überweisungsrahmen ändern   ");
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
                Object[] customerdata = GUI_Customer_Connector.kunde.data.getData(GUI_Customer_Connector.kunde.id, "customer").get(0);
                txtName.setText((String)customerdata[2]);
                txtPrename.setText((String)customerdata[1]);
                txtZip.setText(String.valueOf(customerdata[4]));
                txtCity.setText((String)customerdata[5]);
                txtAddress.setText((String)customerdata[6]);
                txtPhone.setText((String)customerdata[8]);
                txtMail.setText((String)customerdata[7]);
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
                   hm.onlyInt(txtZip.getText()) == true && txtZip.getText().length() >= 2 &&
                   hm.onlyString(txtCity.getText(), true, 5) == true &&
                   txtAddress.getText().length() >= 5 &&
                   hm.onlyInt(txtPhone.getText()) == true && txtPhone.getText().length() >= 5 &&
                   hm.onlyString(txtMail.getText(), false, 5) == true){

                    GUI_Customer_Connector.kunde.changeUserData(txtName.getText(), txtPrename.getText(), Integer.parseInt(txtZip.getText()), txtCity.getText(), txtAddress.getText(), txtPhone.getText(), txtMail.getText());
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
                        hm.onlyInt(txtZip.getText()) == false || txtZip.getText().length() < 2 ||
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

                    if(!hm.onlyString(txtName.getText(), false, 2)){
                        txtName.setBorder(failedBorder);
                    }
                    if(!hm.onlyString(txtPrename.getText(), false, 2)){
                        txtPrename.setBorder(failedBorder);
                    }
                    if(!hm.onlyInt(txtZip.getText()) || txtZip.getText().length() < 2){
                        txtZip.setBorder(failedBorder);
                    }
                    if(!hm.onlyString(txtCity.getText(), true, 5)){
                        txtCity.setBorder(failedBorder);
                    }
                    if(!hm.onlyString(txtAddress.getText(), true, 5)){
                        txtAddress.setBorder(failedBorder);
                    }
                    if(!hm.onlyInt(txtPhone.getText()) || txtPhone.getText().length() < 5){
                        txtPhone.setBorder(failedBorder);
                    }
                    if(!hm.onlyString(txtMail.getText(), false, 5)){
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
                GUI_Login login = new GUI_Login(new Login());
                login.setVisible(true);
                GUI_Customer_Connector.kunde.closeConnections();
                GUI_Customer_Connector.guiCustomer.dispose();
            }
        });

        /**
         * Die Action des Buttons "Aktualisieren" im Tab Überweisen ist für das Aktualisieren der Liste und der Konten zuständig.
         * Wenn sich Änderungen ergeben haben dann werden diese durch das Aktualisieren in der Liste übernommen.
         */
        aktualisierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAccountLists();
            }
        });

        /**
         * Die Tabelle "tableTurnover" im Tab Finanzübersicht zeigt alle Umsätze eines angeklickten Kontos.
         * Das Click-Event füllt die Textfelder unter der Tabelle mit den entsprechenden Einträgen aus der
         * Tabelle.
         */
        tableTurnover.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Person.TableData tmp = (Person.TableData) tableTurnover.getModel();
                int row = tableTurnover.getSelectedRow();
                txtAccNr.setText(tmp.getValueAt(row, 4).toString());
                srlabel.setText(tmp.getValueAt(row, 3).toString());
                txtReceiver.setText(tmp.getValueAt(row, 1).toString());
                txtUsage.setText(tmp.getValueAt(row, 5).toString());
                txtDate.setText(tmp.getValueAt(row, 6).toString());
            }
        });

        /**
         * Die Action der Liste "listAccounts1" im Tab Finanzübersicht sorgt dafür, wenn kein Konto in der
         * Liste ausgewählt wurde, dass die Textfelder für die Überweisungen leer sind.
         */
        listAccounts1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selected = listAccounts1.getSelectedIndex();
                if(selected != -1){
                    tableTurnover.setModel(GUI_Customer_Connector.kunde.getTransfers(listAccounts1.getSelectedIndex()));
                }
                txtAccNr.setText("");
                txtReceiver.setText("");
                txtUsage.setText("");
                txtDate.setText("");
            }
        });

        /**
         * Die Action der Liste "listAccounts3" im Tab Konten füllt die beiden Textfelder txtDispo und
         * txtTransferLimit mit den entsprechenden Werten des selektierten Kontos.
         */
        listAccounts3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                txtTransferLimit.setText(String.valueOf(GUI_Customer_Connector.kunde.allaccounts.get(listAccounts3.getSelectedIndex()).getLimit()));
                txtDispo.setText(GUI_Customer_Connector.kunde.allaccounts.get(listAccounts3.getSelectedIndex()).getDispo().toString());
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
        lblHello.setText("Herzlich Willkommen " + GUI_Customer_Connector.kunde.preName + " in der Banking-App der");

        updateAccountLists();

        Object[] customerdata = GUI_Customer_Connector.kunde.data.getData(GUI_Customer_Connector.kunde.id, "customer").get(0);
        txtName.setText((String)customerdata[2]);
        txtPrename.setText((String)customerdata[1]);
        txtZip.setText(String.valueOf(customerdata[4]));
        txtCity.setText((String)customerdata[5]);
        txtAddress.setText((String)customerdata[6]);
        txtPhone.setText((String)customerdata[8]);
        txtMail.setText((String)customerdata[7]);
    }

    void updateAccountLists(){
        DefaultListModel dlm = new DefaultListModel();
        int i = 0;
        for(Konto acc : GUI_Customer_Connector.kunde.allaccounts){
            dlm.add(i, acc.getId().toString() + "  |  " + acc.getType() + "  |  " + acc.getBalance().toString());
            i++;
        }
        listAccounts1.setModel(dlm);
        listAccounts2.setModel(dlm);
        listAccounts3.setModel(dlm);
    }

}