package GUI.GUI_Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Customer_DeleteAccount extends JFrame{
    private JList listAccountToRemove;
    private JList listAccountToReceive;
    private JButton btnRemoveAccount;
    private JButton btnCancel;

    public GUI_Customer_DeleteAccount() {

        initialize();

        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());

        //Action für den Button "Konto löschen"
        btnRemoveAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Action für den Button "Abbrechen"
        btnCancel.addActionListener(new ActionListener() {
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
