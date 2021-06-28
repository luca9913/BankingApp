package GUI.GUI_Banker;

import javax.swing.*;
import GUI.GUI_Login.GUI_Login;
import GUI.HelpMethods;
import Konto.*;
import Login.Login;
import Person.Banker;
import Person.Banker.*;
import Database.ProdBase;
import Person.Customer;

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
    private JTextField txtTurnoverUsage;
    private JTextField txtTurnoverDate;
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
    private JLabel tblTurnoversSendOrReceive;
    private JList dispoList;

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
        //Kunden-ComboBox Listener
        cbbCurrentCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerSelected();
            }
        });
        //Listener Auswahl in Kontenliste
        listAccountOverview.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                insertBalance();
                insertAllTransfers();
                ListData tmp = (ListData)listAccountOverview.getModel();
                int index = listAccountOverview.getSelectedIndex();
                if(index != -1) {
                    if (tmp.getStatus(index) == 0) {
                        btnBlockAccount.setBackground(new Color(175, 0, 0));
                        btnBlockAccount.setText("Konto sperren");
                        btnBlockAccount.setEnabled(true);
                        btnDeleteAccount.setEnabled(true);
                    } else if (tmp.getStatus(index) == 1) {
                        btnBlockAccount.setBackground(new Color(0, 109, 77));
                        btnBlockAccount.setText("Konto entsperren");
                        btnBlockAccount.setEnabled(true);
                        btnDeleteAccount.setEnabled(true);
                    } else {
                        btnBlockAccount.setEnabled(false);
                        btnDeleteAccount.setEnabled(false);
                    }
                    for(int i = 0; i < tblTurnovers.getColumnCount(); i++){
                        TableColumn col = tblTurnovers.getColumnModel().getColumn(i);
                        col.setCellRenderer(new TransferRenderer());
                    }
                }
                txtTurnoverAccountNr.setText("");
                txtTurnoverDate.setText("");
                txtTurnoverNr.setText("");
                txtTurnoverUsage.setText("");
            }
        });
        btnRefreshAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerSelected();
            }
        });
        tblTurnovers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tblTurnoversClicked();
            }
        });
        btnBlockAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListData tmp = (ListData) listAccountOverview.getModel();
                int index = listAccountOverview.getSelectedIndex();
                int id = tmp.getSelectedID(index);
                int status = 1 - tmp.getStatus(index);
                admin.un_lockAccount(id, status);
                tmp.setValueAt(index, 2, status);
                listAccountOverview.getListSelectionListeners()[0].valueChanged(new ListSelectionEvent(this, index, index, false));
            }
        });
        btnDeleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListData tmp = (ListData) listAccountOverview.getModel();
                int index = listAccountOverview.getSelectedIndex();
                if(admin.deleteAccount(tmp.getSelectedID(index))){
                    System.out.println("Deletion succesful");
                }
                tmp.delete(index);
            }
        });
        dispoList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListData tmp = (ListData)dispoList.getModel();
                txtCurrentDispo.setText(tmp.getDispo(dispoList.getSelectedIndex()));
                txtCurrentDispo.setEnabled(true);
                btnApproveDispo.setEnabled(true);
            }
        });
        btnApproveDispo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListData tmp = (ListData)dispoList.getModel();
                //TODO: dispo updatebar machen
                admin.updateAccData(tmp.getSelectedID(dispoList.getSelectedIndex()), 3, txtCurrentDispo.getText());
                tmp.setValueAt(dispoList.getSelectedIndex(), 3, txtCurrentDispo.getText());
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

    }

    private void closeAndOpenLogin() {
        admin.closeConnections();
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
        ListData tmp = (ListData) admin.getCustomerModel();
        cbbCurrentCustomer.setModel(tmp);
        cbbCurrentCustomer.setSelectedIndex(0);

        // Initialisiert Kundenübersicht - Finanzübersicht
        lblAccountBalance.setText("Konto wählen...");
        tmp = admin.getAccountModel(-1);
        tmp.addElement(0, new Object[]{"Alle", -1, -1});
        listAccountOverview.setModel(tmp);
        listAccountOverview.setSelectedIndex(0);
        listAccountOverview.setCellRenderer(new AccountRenderer());
        int[]ids = new int[tmp.getSize()];
        for(int i = 0; i < ids.length; i++){
            ids[i] = tmp.getSelectedID(i);
        }
        tblTurnovers.setModel(admin.getTransferModel(ids));
        btnRefreshAccount.setEnabled(true);
        btnBlockAccount.setEnabled(false);
        btnDeleteAccount.setEnabled(false);
        txtSearchTurnover.setEnabled(false);

        // Initialisiere Kundenübersicht - Dispo
        lblDispoAcc.setText("Wähle Konto");
        tmp = admin.getAccountModel(-1);
        dispoList.setModel(tmp);
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

    void customerSelected(){
        ListData tmp = (ListData)cbbCurrentCustomer.getModel();
        ListData newmodel = admin.getAccountModel(tmp.getSelectedID(cbbCurrentCustomer.getSelectedIndex()));
        newmodel.addElement(0, new Object[]{"Alle", -1, -1});
        listAccountOverview.setModel(newmodel);
        listAccountOverview.setSelectedIndex(0);
        dispoList.setModel(admin.getAccountModel(tmp.getSelectedID(cbbCurrentCustomer.getSelectedIndex())));
        insertAllTransfers();
    }

    void updateRequestStatus(int newstatus){
        int row = tblAccountApproval.getSelectedRow();
        admin.modifyRequest((Integer)tblAccountApproval.getValueAt(row, 0), newstatus);
        TableData model = (TableData)tblAccountApproval.getModel();
        model.setValueAt(newstatus, row, 5);
        btnApproveAccount.setEnabled(false);
        btnDeclineAccount.setEnabled(false);
    }

    void insertAllTransfers(){
        ListData tmp = (ListData)listAccountOverview.getModel();
        int[] ind = listAccountOverview.getSelectedIndices();
        if(ind.length == 0 || ind[0] == 0){
            ind = new int[tmp.getSize()];
            ind[0] = -1;
            for(int i = 1; i < tmp.getSize(); i++){
                ind[i] = tmp.getSelectedID(i);
            }
            tblTurnovers.setModel(admin.getTransferModel(ind));
        }else{
            for(int i = 0; i < ind.length; i++){
                ind[i] = tmp.getSelectedID(ind[i]);
            }
            tblTurnovers.setModel(admin.getTransferModel(ind));
        }
    }

    void insertBalance(){
        ListData tmp = (ListData)listAccountOverview.getModel();
        int index = listAccountOverview.getSelectedIndex();
        if(index == -1){
            lblAccountBalance.setText(admin.getBalance(-1));
        }else{
            lblAccountBalance.setText(admin.getBalance(tmp.getSelectedID(index)));
        }
    }

    void tblTurnoversClicked(){
        TableData tmp = (TableData)tblTurnovers.getModel();
        if(tmp.getColumnCount() == 4){
            txtTurnoverAccountNr.setText(tmp.getValueAt(tblTurnovers.getSelectedRow(), 1).toString() + " -> " + tmp.getValueAt(tblTurnovers.getSelectedRow(), 2).toString());
            txtTurnoverNr.setText(tmp.getValueAt(tblTurnovers.getSelectedRow(), 0).toString());
            txtTurnoverUsage.setText(tmp.getValueAt(tblTurnovers.getSelectedRow(), 4).toString());
            txtTurnoverDate.setText(tmp.getValueAt(tblTurnovers.getSelectedRow(), 5).toString());
        }else{
            int row = tblTurnovers.getSelectedRow();
            if(tmp.getValueAt(tblTurnovers.getSelectedRow(), 5).toString().equals("in")){
                tblTurnoversSendOrReceive.setText("Sender: ");
            }else{
                tblTurnoversSendOrReceive.setText("Empfänger: ");
            }
            txtTurnoverAccountNr.setText(tmp.getValueAt(tblTurnovers.getSelectedRow(), 1).toString());
            txtTurnoverNr.setText(tmp.getValueAt(tblTurnovers.getSelectedRow(), 0).toString());
            txtTurnoverUsage.setText(tmp.getValueAt(tblTurnovers.getSelectedRow(), 3).toString());
            txtTurnoverDate.setText(tmp.getValueAt(tblTurnovers.getSelectedRow(), 4).toString());
        }

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

    class TransferRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            TableData tmp = (TableData)tblTurnovers.getModel();
            if(tmp.getColumnCount() == 3){
                if(tmp.getValueAt(row, 5).toString().equals("out")){
                    c.setBackground(new Color(175, 0 , 0));
                    c.setForeground(Color.white);
                }else if(tmp.getValueAt(row, 5).toString().equals("in")){
                    c.setBackground(new Color(0, 109, 77));
                    c.setForeground(Color.white);
                }else{
                    c.setBackground(Color.white);
                    c.setForeground(Color.black);
                }
            }
            return c;
        }
    }

    class AccountRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            ListData tmp = (ListData)listAccountOverview.getModel();
            if(tmp.getStatus(index) == 1){
                c.setBackground(new Color(175, 0 , 0));
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
