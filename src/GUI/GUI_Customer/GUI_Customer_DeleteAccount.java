package GUI.GUI_Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Customer_DeleteAccount extends JFrame{
    private JList listAccountToRemove;
    private JList listAccountToReceive;
    private JButton btnRemoveAccount;
    private JButton btnCancel;
    private JPanel PanelDelete;

    public GUI_Customer_DeleteAccount() {

        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());

        add(PanelDelete);
        setTitle("Turing Banking App");
        setSize(550, 385);
        setResizable(false);

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

}
