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
    private JRadioButton girokontoRadioButton;
    private JButton abbrechenButton;
    private JButton kontoAnlegenButton;

    /**
     * Die Methode ist für die Initialisierung der Neben-GUI zuständig und enthält alle Actions und
     * auch Optionen, wie das GUI initialisiert werden soll.
     */
    public GUI_Customer_CreateAccount() {

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

        //Action für den Button "Konto erstellen"
        kontoAnlegenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action für den Button "Abbrechen"
        abbrechenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GUI_Customer_Connector.closeCreate();

            }
        });
    }

}
