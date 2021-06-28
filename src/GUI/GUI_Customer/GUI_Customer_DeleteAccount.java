package GUI.GUI_Customer;

import GUI.GUI_Customer_Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Die Klasse ist für das Nebengui des Customers zur Löschung eines bestehenden Kontos zuständig.
 * Hier werden die Components und Actions verwaltet und weitere Optionen für die GUI festgelegt.
 */
public class GUI_Customer_DeleteAccount extends JFrame{
    private JList listAccountToRemove;
    private JList listAccountToReceive;
    private JButton btnRemoveAccount;
    private JButton btnCancel;
    private JPanel PanelDelete;

    /**
     * Dieser Konstruktor ist für die Actions und weitere Optionen des Neben-Gui´s DeleteAccount zuständig.
     */
    public GUI_Customer_DeleteAccount() {

        initialize();

        /**
         * Die Action des Buttons "Konto auflösen" ist für das Löschen eines ausgewählten Kontos zuständig.
         */
        btnRemoveAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /**
         * Die Action des Buttons "Abbrechen" ist für das Schließen der GUI_Customer_DeleteAccount zuständig.
         */
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GUI_Customer_Connector.closeDelete();

            }
        });
    }

    /**
     * Die Methode ist für die Initialisierung der GUI_Customer_DeleteAccount und deren Optionen zuständig.
     */
    public void initialize(){
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());
        add(PanelDelete);
        pack();
        setLocationRelativeTo(null);
        setTitle("Konto auflösen");
        setSize(450, 350);
        setResizable(false);
        toFront();

        Vector tempRemove = new Vector(GUI_Customer_Connector.kunde.getAllAccounts());
        tempRemove.toString();
        listAccountToRemove.setListData(tempRemove);

        Vector tempReceive = new Vector(GUI_Customer_Connector.kunde.getAllAccounts());
        tempReceive.toString();
        listAccountToReceive.setListData(tempReceive);
    }

}
