package states;

import model.Order;

import java.io.Serializable;

public class OrderedState implements OrderState, Serializable {
    @Override
    public void next(Order o) {
            o.setState(new ReceivedState());
    }

    @Override
    public void previous(Order o) {
        System.out.println("The order is in initialized state.");
    }

    @Override
    public void printStatus() {
        System.out.println("Your order is being processed.");
    }

    @Override
    public String toString() {
        return "Ordered";
    }
}
