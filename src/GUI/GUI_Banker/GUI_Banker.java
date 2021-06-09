package GUI.GUI_Banker;

import javax.swing.*;

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

    private int bankerID;

    // Mark: -
    public GUI_Banker() {
        this.bankerID = bankerID;
        initialize();
    }


    private void initialize() {
        setTitle("Turing Banking App");
        setSize(400, 500);
        setResizable(false);

        add(mainPanel);
    }

}
