package view;

import javax.swing.*;
import java.awt.*;

public class WelcomeView extends JFrame {
    int result;

    public WelcomeView(){
        setLocationRelativeTo(null);
        setSize(new Dimension(100,50));
        setResizable(false);
        setTitle("Welcome user!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Object[] options = { "Customer", "Restaurant"};

        JPanel panel = new JPanel();
        panel.add(new JLabel("Are you?"));


        result = JOptionPane.showOptionDialog(null, panel, "Welcome User!",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, null);


        add(panel);
    }

    public int getResult() {
        return result;
    }
}
