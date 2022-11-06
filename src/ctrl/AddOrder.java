package ctrl;

import main.AppCore;
import view.CustomerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
/*
* This action calls the method in a database implementation which executes the query to add newly created order to database
* */
public class AddOrder extends AbstractAction {

    public AddOrder(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AppCore.getInstance().getDb().addOrder(CustomerView.getInstance().getNewOrder());
    }
}
