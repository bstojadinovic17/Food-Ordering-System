package ctrl;
/*
* This class is used for managing all actions that are being implemented in this package.
* Every action is being called through ActionManager class
* */
public class ActionManager {

    private AddOrder addOrder;
    private ExportOrder exportOrder;
    private UpdateOrder updateOrder;
    private ViewOrders viewOrders;


    public ActionManager(){
        initialization();
    }

    private void initialization() {
        addOrder = new AddOrder();
        exportOrder = new ExportOrder();
        updateOrder = new UpdateOrder();
        viewOrders = new ViewOrders();
    }

    public AddOrder getAddOrder() {
        return addOrder;
    }

    public ExportOrder getExportOrder() {
        return exportOrder;
    }

    public UpdateOrder getUpdateOrder() {
        return updateOrder;
    }

    public ViewOrders getViewOrders() {
        return viewOrders;
    }
}
