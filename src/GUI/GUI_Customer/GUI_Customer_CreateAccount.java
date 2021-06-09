package GUI.GUI_Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Customer_CreateAccount extends JFrame{
    private JPanel panel1;
    private JRadioButton girokontoRadioButton;
    private JButton abbrechenButton;
    private JButton kontoAnlegenButton;

    public GUI_Customer_CreateAccount() {

        initialize();

        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());

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

    private void initialize() {
        setTitle("Turing Banking App");
        setSize(250, 250);
        setResizable(false);

    }
}
