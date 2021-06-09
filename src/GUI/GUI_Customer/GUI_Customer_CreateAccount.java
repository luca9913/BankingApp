package GUI.GUI_Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Customer_CreateAccount extends JFrame{
    private JPanel PanelCreate;
    private JRadioButton girokontoRadioButton;
    private JButton abbrechenButton;
    private JButton kontoAnlegenButton;

    public GUI_Customer_CreateAccount() {

        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());

        add(PanelCreate);
        setTitle("Turing Banking App");
        setSize(550, 385);
        setResizable(false);

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

            }
        });
    }

}
