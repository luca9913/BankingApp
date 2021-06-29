package GUI.GUI_Customer;

import GUI.GUI_Customer_Connector;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die Klasse ist für das Nebengui des Customers zur Erstellung eines weiteren Kontos zuständig.
 * Hier werden die Components und Actions verwaltet und weitere Optionen für die GUI festgelegt.
 */
public class GUI_Customer_CreateAccount extends JFrame{
    private JPanel PanelCreate;
    private JRadioButton rdbGiro;
    private JButton abbrechenButton;
    private JButton kontoAnlegenButton;
    private JRadioButton rdbTages;
    private JRadioButton rdbSpar;
    private JRadioButton rdbfest;
    private JRadioButton rdbKredit;

    /**
     * Dieser Konstruktor ist für die Actions und weitere Optionen des Neben-Gui´s CreateAccount zuständig.
     */
    public GUI_Customer_CreateAccount() {

        initialize();

        /**
         * Die Action des Buttons "Konto erstellen" ist für das Erstellen eines neuen Kontos zuständig.
         */
        kontoAnlegenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rdbGiro.isSelected()){
                    //GUI_Customer_Connector.kunde.createAccount();
                }
                else if(rdbTages.isSelected()){
                    //GUI_Customer_Connector.kunde.createAccount();
                }
                else if(rdbSpar.isSelected()){
                    //GUI_Customer_Connector.kunde.createAccount();
                }
                else if(rdbfest.isSelected()){
                    //GUI_Customer_Connector.kunde.createAccount();
                }
                else if(rdbKredit.isSelected()){
                    //GUI_Customer_Connector.kunde.createAccount();
                }
            }
        });

        /**
         * Die Action des Buttons "Abbrechen" ist für das Schließen der GUI_Customer_CreateAccount zuständig.
         */
        abbrechenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GUI_Customer_Connector.closeCreate();

            }
        });
    }

    /**
     * Die Methode ist für die Initialisierung der GUI_Customer_CreateAccount und deren Optionen zuständig.
     */
    public void initialize(){
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());
        add(PanelCreate);
        pack();
        setLocationRelativeTo(null);
        setTitle("Konto erstellen");
        setSize(350, 270);
        setResizable(false);
        toFront();
    }

}
