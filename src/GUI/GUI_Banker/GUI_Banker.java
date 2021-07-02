package GUI.GUI_Banker;

import javax.swing.*;
import GUI.GUI_Login.GUI_Login;
import GUI.HelpMethods;
import Login.Login;
import Person.Banker;
import Person.Banker.*;
import Person.Customer;
import Person.Person.TableData;

import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Hier Text einfügen
 */
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
    private JTextField txtCustomerMail;
    private JTextField txtCustomerTel;
    private JLabel tblTurnoversSendOrReceive;
    private JList dispoList;
    private JButton btnLogout;
    private Border defaultBorder;
    private Banker admin;
    private Customer customer;

    /**
     * Hier Text einfügen
     * @param banker
     */
    public GUI_Banker(Banker banker) {
        this.admin = banker;
        initialize();

        /**
         * Hier Text einfügen//Table-Listener Kontenfreigabe
         * @param e
         */
        tblAccountApproval.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tblAccountApprovalClicked();
            }
        });

        /**
         * Hier Text einfügen //Freigabe ablehnen (Listener)
         * @param e
         */
        btnDeclineAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRequestStatus(-1);
            }
        });

        /**
         * Hier Text einfügen//Freigabe akzeptieren (Listener)
         * @param e
         */
        btnApproveAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRequestStatus(1);
            }
        });

        /**
         * Hier Text einfügen //Kunden-ComboBox Listener
         * @param e
         */
        cbbCurrentCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerSelected();
            }
        });

        /**
         * Hier Text einfügen//Listener Auswahl in Kontenliste
         * @param e
         */
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

        /**
         * Hier Text einfügen
         * @param e
         */
        btnRefreshAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerSelected();
            }
        });

        /**
         * Hier Text einfügen
         * @param e
         */
        tblTurnovers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tblTurnoversClicked();
            }
        });

        /**
         * Hier Text einfügen
         * @param e
         */
        btnBlockAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListData tmp = (ListData) listAccountOverview.getModel();
                int index = listAccountOverview.getSelectedIndex();
                int id = tmp.getSelectedID(index);
                int status = 1 - tmp.getStatus(index);
                if(admin.un_lockAccount(id, status)){
                    tmp.setValueAt(index, 2, status);
                    listAccountOverview.getListSelectionListeners()[0].valueChanged(new ListSelectionEvent(this, index, index, false));
                    JOptionPane.showMessageDialog(mainPanel, "Änderung des Kontostatus erfolgreich!", "Kontostatus geändert", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(mainPanel, "Kontostatus konnte nicht geändert werden!", "Fehler Kontostatus", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /**
         * Hier Text einfügen
         * @param e
         */
        btnDeleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListData tmp = (ListData) listAccountOverview.getModel();
                int index = listAccountOverview.getSelectedIndex();
                if(admin.deleteAccount(tmp.getSelectedID(index))){
                    JOptionPane.showMessageDialog(mainPanel, "Kontolöschung erfolgreich!", "Account gelöscht", JOptionPane.INFORMATION_MESSAGE);
                    tmp.delete(index);
                }else{
                    JOptionPane.showMessageDialog(mainPanel, "Konto konnte nicht gelöscht werden!", "Fehler Kontolöschung", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        /**
         * Hier Text einfügen
         * @param e
         */
        dispoList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListData tmp = (ListData)dispoList.getModel();
                lblDispoAcc.setText("");
                txtCurrentDispo.setText(tmp.getDispo(dispoList.getSelectedIndex()));
                txtCurrentDispo.setEnabled(true);
                btnApproveDispo.setEnabled(true);
            }
        });

        /**
         * Hier Text einfügen
         * @param e
         */
        btnApproveDispo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListData tmp = (ListData)dispoList.getModel();
                JDialog status = new JDialog();
                status.setTitle("Dispoänderung");
                //TODO: Popup mit Statusmeldung
                if(admin.updateAccData(tmp.getSelectedID(dispoList.getSelectedIndex()), 3, txtCurrentDispo.getText())){
                    tmp.setValueAt(dispoList.getSelectedIndex(), 3, txtCurrentDispo.getText());
                    JOptionPane.showMessageDialog(mainPanel, "Dispo erfolgreich auf " + txtCurrentDispo.getText() + " geändert!", "Dispoänderung", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(mainPanel, "Dispo konnte nicht geändert werden!", "Fehler Dispoänderung", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        /**
         * Hier Text einfügen
         * @param e
         */
        btnSaveCustomerData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customer.preName = txtCustomerName.getText();
                customer.name = txtCustomerSurname.getText();
                customer.birthDate = txtCustomerBirth.getText();
                customer.zip = Integer.parseInt(txtCustomerZIP.getText());
                customer.city = txtCustomerCity.getText();
                customer.address = txtCustomerAdress.getText();
                customer.email = txtCustomerMail.getText();
                customer.tel = txtCustomerTel.getText();
                if(admin.updateUserData(customer)){
                    JOptionPane.showMessageDialog(mainPanel, "Kundendaten erfolgreich geändert!", "Kundendaten", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(mainPanel, "Kundendaten konnten nicht geändert werden!", "Fehler Kundendaten", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /**
         * Hier Text einfügen
         * @param e
         */
        btnCreateNewCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewCustomer();
            }
        });

        /**
         * Hier Text einfügen
         * @param e
         */
        btnNewCustomerCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetNewCustomer();
            }
        });

        /**
         * Hier Text einfügen
         * @param e
         */
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAndOpenLogin();
            }

        });

        /**
         * Hier Text einfügen
         * @param e
         */
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Hier Text einfügen
     */
    private void closeAndOpenLogin() {
        admin.closeConnections();
        GUI_Login newView = new GUI_Login(new Login());
        newView.setVisible(true);
        this.dispose();
    }

    /**
     * Hier Text einfügen
     */
    private void initialize() {
        // Set default border
        defaultBorder = txtNewCustomerName.getBorder();

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
        txtCustomerMail.setEnabled(false);
        txtCustomerTel.setEnabled(false);
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

    /**
     * Hier Text einfügen
     */
    private void tblAccountApprovalClicked(){
        if((Integer)tblAccountApproval.getModel().getValueAt(tblAccountApproval.getSelectedRow(), 5) == 0){
            btnDeclineAccount.setEnabled(true);
            btnApproveAccount.setEnabled(true);
        }
    }

    /**
     * Hier Text einfügen
     */
    void customerSelected(){
        ListData tmp = (ListData)cbbCurrentCustomer.getModel();
        int id = tmp.getSelectedID(cbbCurrentCustomer.getSelectedIndex());
        ListData newmodel = admin.getAccountModel(id);
        newmodel.addElement(0, new Object[]{"Alle", -1, -1});
        listAccountOverview.setModel(newmodel);
        listAccountOverview.setSelectedIndex(0);
        dispoList.setModel(admin.getAccountModel(tmp.getSelectedID(cbbCurrentCustomer.getSelectedIndex())));
        insertAllTransfers();
        if(id != -1) {
            customer = admin.getUserData(id);
            txtCustomerID.setText(String.valueOf(customer.getId()));
            txtCustomerName.setText(customer.preName);
            txtCustomerName.setEnabled(true);
            txtCustomerSurname.setText(customer.name);
            txtCustomerSurname.setEnabled(true);
            txtCustomerBirth.setText(customer.birthDate);
            txtCustomerBirth.setEnabled(true);
            txtCustomerAdress.setText(customer.address);
            txtCustomerAdress.setEnabled(true);
            txtCustomerZIP.setText(String.valueOf(customer.zip));
            txtCustomerZIP.setEnabled(true);
            txtCustomerCity.setText(customer.city);
            txtCustomerCity.setEnabled(true);
            txtCustomerMail.setText(customer.email);
            txtCustomerMail.setEnabled(true);
            txtCustomerTel.setText(customer.tel);
            txtCustomerTel.setEnabled(true);
            btnSaveCustomerData.setEnabled(true);
        }else{
            txtCustomerName.setEnabled(false);
            txtCustomerSurname.setEnabled(false);
            txtCustomerBirth.setEnabled(false);
            txtCustomerAdress.setEnabled(false);
            txtCustomerZIP.setEnabled(false);
            txtCustomerCity.setEnabled(false);
            txtCustomerMail.setEnabled(false);
            txtCustomerTel.setEnabled(false);
        }
    }

    /**
     * Hier Text einfügen
     * @param newstatus
     */
    void updateRequestStatus(int newstatus){
        int row = tblAccountApproval.getSelectedRow();
        admin.modifyRequest((Integer)tblAccountApproval.getValueAt(row, 0), newstatus);
        TableData model = (TableData)tblAccountApproval.getModel();
        model.setValueAt(newstatus, row, 5);
        btnApproveAccount.setEnabled(false);
        btnDeclineAccount.setEnabled(false);
    }

    /**
     * Hier Text einfügen
     */
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

    /**
     * Hier Text einfügen
     */
    void insertBalance(){
        ListData tmp = (ListData)listAccountOverview.getModel();
        int index = listAccountOverview.getSelectedIndex();
        if(index == -1){
            lblAccountBalance.setText(admin.getBalance(-1));
        }else{
            lblAccountBalance.setText(admin.getBalance(tmp.getSelectedID(index)));
        }
    }

    /**
     * Hier Text einfügen
     */
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

    /**
     * Diese Methode muss vorhanden sein, da der KeyListener diese verlangt.
     * @param e Parameter für das Key-Event.
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Diese Methode muss vorhanden sein, da der KeyListener diese verlangt.
     * @param e Parameter für das Key-Event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            System.out.println("ENTER-Pressed");
            createNewCustomer();
        }
    }

    /**
     * Diese Methode muss vorhanden sein, da der KeyListener diese verlangt.
     * @param e Parameter für das Key-Event.
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Hier Text einfügen
     */
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

    /**
     * Hier Text einfügen
     */
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

    /**
     * Hier Text einfügen
     */
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

    /**
     * Hier Text einfügen
     */
    private void createNewCustomer(){
        HelpMethods h = new HelpMethods();
        String[] newCustomer = {"","","","","","","","",""};

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
            newCustomer[2] = h.convertStringIntoDateFormat(txtNewCustomerBirth.getText());
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

        // Banker ID einfügen
        newCustomer[8] = String.valueOf(admin.getId());

        for(int i=0; i<newCustomer.length; i++){
            if(newCustomer[i] == ""){
                JOptionPane.showMessageDialog(null,"Korrigieren Sie die markierten Eingaben. Das Geburtsdatum muss im Format dd.mm.yyyy angegeben werden.","Fehlerhafte Eingabe(n)", JOptionPane.CANCEL_OPTION);
                return;
            }
        }

        //[Prename, Name, Birth, Address, ZIP, City, EMail, Phone Number]
        admin.insertCustomer(newCustomer);

        resetNewCustomer();
    }

    /**
     * Hier Text einfügen
     */
    private void resetNewCustomerView() {
        txtNewCustomerName.setBorder(defaultBorder);
        txtNewCustomerSurname.setBorder(defaultBorder);
        txtNewCustomerBirth.setBorder(defaultBorder);
        txtNewCustomerAdress.setBorder(defaultBorder);
        txtNewCustomerZIP.setBorder(defaultBorder);
        txtNewCustomerCity.setBorder(defaultBorder);
        txtNewCustomerPhone.setBorder(defaultBorder);
        txtNewCustomerEmail.setBorder(defaultBorder);
    }

    /**
     * Hier Text einfügen
     */
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
