package main;

import ctrl.ActionManager;
import db.DBImpl;
import db.DBInit;
import db.Database;
import view.MainView;

public class AppCore {

    private static AppCore instance = null;
    private Database db;
    private DBInit dbInit;
    private MainView view;
    private ActionManager actionManager;

    private AppCore(){
        //dbInit = new DBInit();
        this.db = new DBImpl();
        view = MainView.getInstance();
        actionManager = new ActionManager();
    }

    public static AppCore getInstance(){
        if(instance == null){
            instance = new AppCore();
        }
        return instance;
    }

    public Database getDb() {
        return db;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }
}
