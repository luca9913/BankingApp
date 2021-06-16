package GUI.GUI_Banker;

import javax.swing.*;
import Person.Banker;
import Database.ProdBase;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
    //TODO: Felder für Telefon und E-Mail hinzufügen

    //private int bankerID;
    private Banker admin;

    // Mark: -
    public GUI_Banker(Banker banker) {
        this.admin = banker;
        initialize();

        //System.out.println(admin.name);
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
        // TODO: Namen des Bankers einfügen
        lblWelcome.setText("Herzlich Willkommen ");
        // TODO: Aktuell betreuende Kunden einfügen
        lblAccountApprovalOrders.setText("Aktuell betreuende Kunden: ");
        // TODO: Kontofreigabeaufträge einfügen
        lblSupervisingCustomers.setText("Kontofreigabeaufträge: ");
        // TODO: Dispoüberschreitende Konten einfügen
        lblDispoOverwritingAccounts.setText("Dispoüberschreitende Konten: ");


        // Initialisiert Kontenfreigabe-Tab
        // TODO: Tabelle mit Konten füllen
        btnApproveAccount.setEnabled(false);
        btnDeclineAccount.setEnabled(false);

        // Initialisiert Dispokonten-Tab
        // TODO: Tabelle mit Konten im Dispo füllen

        // Initialisiert Kundenübersicht-Tab
        // Initialisiert Kundenübersicht - Finanzübersicht
        listAccountOverview.setEnabled(false);
        btnRefreshAccount.setEnabled(false);
        btnBlockAccount.setEnabled(false);
        btnDeleteAccount.setEnabled(false);
        txtSearchTurnover.setEnabled(false);

        // Initialisiere Kundenübersicht - Dispo
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
        btnCreateNewCustomer.setEnabled(false);





        // Panel hinzufügen
        add(mainPanel);
    }

}
