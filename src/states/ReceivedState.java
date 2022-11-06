package states;

import model.Order;

import java.io.Serializable;

public class ReceivedState implements OrderState, Serializable {
    @Override
    public void next(Order o) {
        o.setState(new DeliveredState());
    }

    @Override
    public void previous(Order o) {
        o.setState(new OrderedState());
    }

    @Override
    public void printStatus() {
        System.out.println("Your order has been received.");
    }

    @Override
    public String toString() {
        return "Received";
    }
}
