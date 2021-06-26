package GUI.GUI_Banker;

import javax.swing.*;

import GUI.HelpMethods;
import Person.Banker;
import Person.Banker.*;
import Database.ProdBase;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI_Banker extends JFrame{

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

    // Mark: -
    public GUI_Banker(Banker banker) {
        this.admin = banker;
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
        btnCreateNewCustomer.setEnabled(true);

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

        if(h.onlyString(txtNewCustomerName.getText(), true, 2) && h.onlyString(txtNewCustomerSurname.getText(), true, 2)){
            System.out.println("Namen-Eingabe gültig");
        } else {
            System.out.println("Namen-Eingabe ungültig");
        }

        if(h.correctDateFormat("dd.MM.yyyy", txtNewCustomerBirth.getText())){
            System.out.println("Date correct..");
        } else {
            System.out.println("Date incorrect...");
        }

        if(h.onlyInt(txtNewCustomerZIP.getText())) {
            System.out.println("Correct ZIP");
        }

        if(h.onlyString(txtNewCustomerCity.getText(), true, 2)) {
            System.out.println("Correct City");
        }

        if(h.onlyInt(txtNewCustomerPhone.getText())) {
            System.out.println("Correct Phone Number");
        }

//        admin.insertCustomer();
//        [Prename, Name, Birth, Address, ZIP, City, EMail, Phone Number]

    }
}
