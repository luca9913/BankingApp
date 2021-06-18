package GUI.GUI_Banker;

import javax.swing.*;
import Person.Banker;
import Database.ProdBase;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
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
        lblAccountApprovalOrders.setText("Aktuell betreuende Kunden: " + admin.allcustomers.size());
        // TODO: Kontofreigabeaufträge einfügen
        lblSupervisingCustomers.setText("Freigabeaufträge: " + (admin.relatedrequests.size()-1));
        // TODO: Dispoüberschreitende Konten einfügen
        lblDispoOverwritingAccounts.setText("Dispoüberschreitende Konten: " + admin.dispoaccounts.length);


        // Initialisiert Kontenfreigabe-Tab
        // TODO: Tabelle mit Konten füllen
        String[] colnames = {"ID", "Name", "Art", "Alter Wert", "Neuer Wert"};
        TableModel requestdata = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return admin.relatedrequests.size() - 1;
            }

            @Override
            public int getColumnCount() {
                return 5;
            }

            @Override
            public String getColumnName(int column){
                return colnames[column];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                String name;
                if(columnIndex == 1){
                    int id = (Integer)admin.relatedrequests.get(rowIndex + 1)[3];
                    if(id == 0){
                        id = (Integer)admin.relatedrequests.get(rowIndex + 1)[2];
                        int i = 0;
                        while((Integer)admin.allcustomers.get(i)[0] != id){
                            i++;
                        }
                        return admin.allcustomers.get(i)[2].toString();
                    }else{
                        return "Konto: " + id;
                    }
                }else{
                    switch(columnIndex){
                        case 0: return admin.relatedrequests.get(rowIndex + 1)[columnIndex];
                        case 2: return admin.relatedrequests.get(rowIndex + 1)[5];
                        case 3: return admin.relatedrequests.get(rowIndex + 1)[6];
                        case 4: return admin.relatedrequests.get(rowIndex + 1)[7];
                        default: return null;
                    }
                }
            }
        };
        tblAccountApproval.setModel(requestdata);
        btnApproveAccount.setEnabled(false);
        btnDeclineAccount.setEnabled(false);

        // Initialisiert Dispokonten-Tab
        // TODO: Tabelle mit Konten im Dispo füllen
        TableModel dispodata = new AbstractTableModel() {
            String[] colnames = {"Konto-ID", "Name", "Dispo", "Saldo"};

            @Override
            public int getRowCount() {
                return admin.dispoaccounts.length;
            }

            @Override
            public int getColumnCount() {
                return admin.dispoaccounts[0].length;
            }

            @Override
            public String getColumnName(int column){
                return colnames[column];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return admin.dispoaccounts[rowIndex][columnIndex];
            }
        };
        tblDispoAccounts.setModel(dispodata);

        // Initialisiert Kundenübersicht-Tab
        cbbCurrentCustomer.addItem("Alle");
        for(Object[] obj : admin.allcustomers){
            cbbCurrentCustomer.addItem(obj[0] + " - " + obj[2]);
        }
        // Initialisiert Kundenübersicht - Finanzübersicht
        ListModel listModel = new AbstractListModel() {
            @Override
            public int getSize() {
                return admin.allaccounts.size() - 1;
            }

            @Override
            public Object getElementAt(int index) {
                Object[] arr = admin.allaccounts.get(index + 1);
                return "ID: " + arr[0] + " - " + arr[1];
            }
        };
        listAccountOverview.setModel(listModel);
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
