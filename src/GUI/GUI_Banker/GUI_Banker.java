package GUI.GUI_Banker;

import javax.swing.*;
import GUI.GUI_Login.GUI_Login;
import GUI.HelpMethods;
import Login.Login;
import Person.Banker;
import Person.Banker.*;
import Database.ProdBase;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI_Banker extends JFrame implements KeyListener{

    private JPanel mainPanel;
    private JTextField txtNewCustomerName;
    private JTextField txtNewCustomerSurname;
    private JTextField txtNewCustomerBirth;
    private JTextField txtNewCustomerAdress;
    private JTextField txtNewCustomerZIP;
    private JTextField txtNewCustomerCity;
    private JButton btnCreateNewCustomer;
    private JComboBox cbbCurrentCustomer;
    private JList listAccountOverview;
    private JButton btnRefreshAccount;
    private JTextField txtSearchTurnover;
    private JTable tblTurnovers;
    private JTextField txtTurnoverAccountNr;
    private JTextField txtTurnoverNr;
    private JTextField txtTurnoverAmount;
    private JTextField txtTurnoverPurpose;
    private JButton btnBlockAccount;
    private JButton btnDeleteAccount;
    private JTextField txtCurrentDispo;
    private JTextField txtCustomerID;
    private JTextField txtCustomerName;
    private JTextField txtCustomerSurname;
    private JTextField txtCustomerBirth;
    private JTextField txtCustomerAdress;
    private JTextField txtCustomerZIP;
    private JTextField txtCustomerCity;
    private JButton btnApproveDispo;
    private JTable tblAccountApproval;
    private JButton btnDeclineAccount;
    private JButton btnApproveAccount;
    private JTable tblDispoAccounts;
    private JButton btnSaveCustomerData;
    private JButton btnExit;
    private JButton btnNewCustomerCancel;
    private JLabel lblSupervisingCustomers;
    private JLabel lblAccountApprovalOrders;
    private JLabel lblDispoOverwritingAccounts;
    private JLabel lblWelcome;
    private JTabbedPane tabbedPane1;
    private JLabel lblAccountBalance;
    private JLabel lblDispoAcc;
    private JTextField txtNewCustomerEmail;
    private JTextField txtNewCustomerPhone;
    private JLabel lblEmail;
    private JTextField textField1;
    private JTextField textField2;
    private JScrollPane dispoAccOverview;

    //private int bankerID;
    private Banker admin;
    private Login login;


    public GUI_Banker(Banker banker, Login login) {
        this.admin = banker;
        this.login = login;
        initialize();

        //Table-Listener Kontenfreigabe
        tblAccountApproval.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tblAccountApprovalClicked();
            }
        });
        //Freigabe ablehnen (Listener)
        btnDeclineAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRequestStatus(-1);
            }
        });
        //Freigabe akzeptieren (Listener)
        btnApproveAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRequestStatus(1);
            }
        });
        //TODO: setup listeners from here
        //Kunden-ComboBox Listener
        cbbCurrentCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListData tmp = (ListData)cbbCurrentCustomer.getModel();
                listAccountOverview.setModel(admin.getAccountModel(tmp.getSelectedID(cbbCurrentCustomer.getSelectedIndex())));
            }
        });
        //Listener Auswahl in Kontenliste
        listAccountOverview.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                insertBalance();
                insertAllTransfers();
            }
        });
        btnCreateNewCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewCustomer();
            }
        });
        btnNewCustomerCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetNewCustomer();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAndOpenLogin();
            }

        });
        cbbCurrentCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Dropdown");
            }
        });

    }


    // TODO: Stammdatenbank Prodbase wird neu inizialisiert --> Fehler beheben
    private void closeAndOpenLogin() {
        GUI_Login newView = new GUI_Login(login);
        newView.setVisible(true);
        this.dispose();
    }

    private void initialize() {
        // Title Bar Icon
        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());

        setTitle("Turing Banking App");
        setSize(800, 600);
        setResizable(true);

        tblDispoAccounts.setCellSelectionEnabled(true);
        tblDispoAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Initialisiert Startseite-Tab
        // Namen des Bankers einfügen
        lblWelcome.setText("Herzlich Willkommen " + admin.preName + " " + admin.name);
        // Aktuell betreuende Kunden einfügen
        lblAccountApprovalOrders.setText("Aktuell betreuende Kunden: " + admin.allcustomers.size());
        // Kontofreigabeaufträge einfügen
        lblSupervisingCustomers.setText("Freigabeaufträge: " + admin.relatedrequests.size());
        // Dispoüberschreitende Konten einfügen
        lblDispoOverwritingAccounts.setText("Dispoüberschreitende Konten: " + admin.getDispoModel().getRowCount());


        // Initialisiert Kontenfreigabe-Tab
        // Tabelle mit Konten füllen
        tblAccountApproval.setModel(admin.getRequestModel());
        for(int i = 0; i < 5; i++){
            TableColumn col = tblAccountApproval.getColumnModel().getColumn(i);
            col.setCellRenderer(new RequestRenderer());
        }
        tblAccountApproval.setCellSelectionEnabled(false);
        tblAccountApproval.setColumnSelectionAllowed(false);
        tblAccountApproval.setRowSelectionAllowed(true);
        btnApproveAccount.setEnabled(false);
        btnDeclineAccount.setEnabled(false);

        // Initialisiert Dispokonten-Tab
        // Tabelle mit Konten im Dispo füllen
        tblDispoAccounts.setModel(admin.getDispoModel());

        // Initialisiert Kundenübersicht-Tab
        cbbCurrentCustomer.setModel((ComboBoxModel) admin.getCustomerModel());
        cbbCurrentCustomer.setSelectedIndex(0);

        // Initialisiert Kundenübersicht - Finanzübersicht
        ListData tmp = (ListData)cbbCurrentCustomer.getModel();
        listAccountOverview.setModel(admin.getAccountModel(-1));
        tblTurnovers.setModel(admin.getTransferModel(new int[]{-1}));
        btnRefreshAccount.setEnabled(false);
        btnBlockAccount.setEnabled(false);
        btnDeleteAccount.setEnabled(false);
        txtSearchTurnover.setEnabled(false);

        // Initialisiere Kundenübersicht - Dispo
        lblDispoAcc.setText("Wähle Konto");
        txtCurrentDispo.setEnabled(false);
        btnApproveDispo.setEnabled(false);

        // Initialisiere Kundenübersicht - Benutzerdaten
        txtCustomerID.setEnabled(false);
        txtCustomerName.setEnabled(false);
        txtCustomerSurname.setEnabled(false);
        txtCustomerBirth.setEnabled(false);
        txtCustomerAdress.setEnabled(false);
        txtCustomerZIP.setEnabled(false);
        txtCustomerCity.setEnabled(false);
        btnSaveCustomerData.setEnabled(false);

        //Inititalisiere NeuerKunde-Tab
        txtNewCustomerName.addKeyListener(this);
        txtNewCustomerSurname.addKeyListener(this);
        txtNewCustomerBirth.addKeyListener(this);
        txtNewCustomerAdress.addKeyListener(this);
        txtNewCustomerZIP.addKeyListener(this);
        txtNewCustomerCity.addKeyListener(this);
        txtNewCustomerEmail.addKeyListener(this);
        txtNewCustomerPhone.addKeyListener(this);

        // Panel hinzufügen
        add(mainPanel);
    }

    private void tblAccountApprovalClicked(){
        if((Integer)tblAccountApproval.getModel().getValueAt(tblAccountApproval.getSelectedRow(), 5) == 0){
            btnDeclineAccount.setEnabled(true);
            btnApproveAccount.setEnabled(true);
        }
    }

    void updateRequestStatus(int newstatus){
        int row = tblAccountApproval.getSelectedRow();
        admin.modifyRequest((Integer)tblAccountApproval.getValueAt(row, 0), newstatus);
        TableData model = (TableData)tblAccountApproval.getModel();
        model.setValueAt(newstatus, row, 5);
        btnApproveAccount.setEnabled(false);
        btnDeclineAccount.setEnabled(false);
    }

    void insertAllCustomers(){
    }

    void insertAllAccounts(){
    }

    void insertAllTransfers(){

    }

    void insertBalance(){
        String balance = admin.getBalance(listAccountOverview.getSelectedValue().toString());
        lblAccountBalance.setText("Kontostand: " + balance + " €");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            System.out.println("ENTER-Pressed");
            createNewCustomer();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    class RequestRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if(admin.getRequestStatus((Integer)tblAccountApproval.getModel().getValueAt(row, 0)) == -1){
                c.setBackground(new Color(175, 0 , 0));
                c.setForeground(Color.white);
            }else if(admin.getRequestStatus((Integer)tblAccountApproval.getValueAt(row, 0)) == 1){
                c.setBackground(new Color(0, 109, 77));
                c.setForeground(Color.white);
            }else{
                c.setBackground(Color.white);
                c.setForeground(Color.black);
            }
            return c;
        }
    }


    private void createNewCustomer(){
        HelpMethods h = new HelpMethods();
        String[] newCustomer = {"","","","","","","",""};

        Border failedBorder = BorderFactory.createLineBorder(new Color(175, 0 , 0));
        Border correctBorder = BorderFactory.createLineBorder(new Color(0,109,77));

        resetNewCustomerView();

        // Vorname überprüfen
        if(h.onlyString(txtNewCustomerName.getText(), true, 2)) {
            System.out.println("Gültiger Vorname");
            newCustomer[0] = txtNewCustomerName.getText();
            txtNewCustomerName.setBorder(correctBorder);
        } else {
            txtNewCustomerName.setBorder(failedBorder);
        }

        // Nachname überprüfen
        if(h.onlyString(txtNewCustomerSurname.getText(), true, 2)) {
            System.out.println("Gültiger Nachname");
            newCustomer[1] = txtNewCustomerSurname.getText();
            txtNewCustomerSurname.setBorder(correctBorder);
        } else {
            txtNewCustomerSurname.setBorder(failedBorder);
        }

        // Geburtstag überprüfen
        if(h.correctDateFormat(txtNewCustomerBirth.getText(), true)){
            System.out.println("Gültiges Geburtsdatum");
            newCustomer[2] = txtNewCustomerBirth.getText();
            txtNewCustomerBirth.setBorder(correctBorder);
        } else {
            txtNewCustomerBirth.setBorder(failedBorder);
        }

        // Adresse überprüfen
        if(txtNewCustomerAdress.getText().length() > 5) {
            System.out.println("Gültige Adresse");
            newCustomer[3] = txtNewCustomerAdress.getText();
            txtNewCustomerAdress.setBorder(correctBorder);
        } else {
            txtNewCustomerAdress.setBorder(failedBorder);
        }

        //Postleitzahl überprüfen
        if(h.onlyInt(txtNewCustomerZIP.getText())){
            System.out.println("Correct ZIP");
            newCustomer[4] = txtNewCustomerZIP.getText();
            txtNewCustomerZIP.setBorder(correctBorder);
        } else {
            txtNewCustomerZIP.setBorder(failedBorder);
        }

        // Stadt überprüfen
        if(h.onlyString(txtNewCustomerCity.getText(), true, 2)) {
            System.out.println("Correct City");
            newCustomer[5] = txtNewCustomerCity.getText();
            txtNewCustomerCity.setBorder(correctBorder);
        } else {
            txtNewCustomerCity.setBorder(failedBorder);
        }

        // E-Mail überprüfen
        if(txtNewCustomerEmail.getText().length() > 7){
            System.out.println("Gültige E-Mail Adresse");
            newCustomer[6] = txtNewCustomerEmail.getText();
            txtNewCustomerEmail.setBorder(correctBorder);
        } else {
            txtNewCustomerEmail.setBorder(failedBorder);
        }

        // Telefonnummer überprüfen
        if(h.onlyInt(txtNewCustomerPhone.getText())) {
            System.out.println("Correct Phone Number");
            newCustomer[7] = txtNewCustomerPhone.getText();
            txtNewCustomerPhone.setBorder(correctBorder);
        } else {
            txtNewCustomerPhone.setBorder(failedBorder);
        }

        for(int i=0; i<newCustomer.length; i++){
            if(newCustomer[i] == ""){
                JOptionPane.showMessageDialog(null,"Korrigieren Sie die markierten Eingaben. Das Geburtsdatum muss im Format dd.mm.yyyy angegeben werden.","Fehlerhafte Eingabe(n)", JOptionPane.CANCEL_OPTION);
                return;
            }
        }

        //[Prename, Name, Birth, Address, ZIP, City, EMail, Phone Number]
        admin.insertCustomer(newCustomer);
    }

    private void resetNewCustomerView() {
        txtNewCustomerName.setBorder(null);
        txtNewCustomerSurname.setBorder(null);
        txtNewCustomerBirth.setBorder(null);
        txtNewCustomerAdress.setBorder(null);
        txtNewCustomerZIP.setBorder(null);
        txtNewCustomerCity.setBorder(null);
        txtNewCustomerPhone.setBorder(null);
        txtNewCustomerEmail.setBorder(null);
    }


    private void resetNewCustomer() {
        resetNewCustomerView();
        txtNewCustomerName.setText("");
        txtNewCustomerSurname.setText("");
        txtNewCustomerBirth.setText("");
        txtNewCustomerAdress.setText("");
        txtNewCustomerZIP.setText("");
        txtNewCustomerCity.setText("");
        txtNewCustomerPhone.setText("");
        txtNewCustomerEmail.setText("");
    }

}
