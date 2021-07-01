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
    private JRadioButton rdbDepot;
    private JRadioButton rdbFest;
    private JRadioButton rdbKredit;

    /**
     * Dieser Konstruktor ist für die Actions und weitere Optionen des Neben-Gui´s CreateAccount zuständig.
     */
    public GUI_Customer_CreateAccount() {

        initialize();

        /**
         * Die Action des Buttons "Konto anlegen" ist für das Erstellen eines neuen Kontos zuständig.
         */
        kontoAnlegenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flex = 0;
                if(rdbGiro.isSelected()){
                    flex = 1;
                }
                else if(rdbDepot.isSelected()){
                    flex = 2;
                }
                else if(rdbFest.isSelected()){
                    flex = 3;
                }
                else if(rdbKredit.isSelected()){
                    flex = 4;
                }
                switch(flex){
                    case 1:
                        GUI_Customer_Connector.kunde.createAccount("Girokonto", 0);
                        GUI_Customer_Connector.closeCreate();
                        break;
                    case 2:
                        GUI_Customer_Connector.kunde.createAccount("Depot", 0);
                        GUI_Customer_Connector.closeCreate();
                        break;
                    case 3:
                        GUI_Customer_Connector.kunde.createAccount("Festgeldkonto", 0);
                        GUI_Customer_Connector.closeCreate();
                        break;
                    case 4:
                        GUI_Customer_Connector.kunde.createAccount("Kreditkarte", 0);
                        GUI_Customer_Connector.closeCreate();
                        break;
                }
                JOptionPane.showMessageDialog(null,"Ihr Bänker prüpft Ihre Kontoerstellung.\n" +
                        "Bitte haben Sie geduld.","Erstellung wird geprüft", JOptionPane.INFORMATION_MESSAGE);



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
