package states;

import model.Order;

public interface OrderState{

    void next(Order o);
    void previous(Order o);
    void printStatus();
}
