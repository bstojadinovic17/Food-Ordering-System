package states;

import model.Order;

import java.io.Serializable;

public class DeliveredState implements OrderState, Serializable {


    @Override
    public void next(Order o) {
        System.out.println("Your order has been delivered.");
    }

    @Override
    public void previous(Order o) {
        o.setState(new ReceivedState());
    }

    @Override
    public void printStatus() {

    }

    @Override
    public String toString() {
        return "Delivered";
    }
}
