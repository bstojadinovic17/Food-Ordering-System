package view;

import javax.swing.*;


/*
* This view appears afrer the WelcomeView in the application start, to determine
* which user type wants to access the application.
* */

public class MainView extends JFrame {
    private static MainView instance = null;
    private int IsRestaurant;
    private WelcomeView welcomeView;

    private MainView(){

    }
    private void initialize() {
        initializeView();
    }

    private void initializeView() {
        welcomeView = new WelcomeView();
        if(welcomeView.getResult() == 0){
            //setIsRestaurant(0);
            CustomerView.getInstance();
            RestaurantView.getInstance().setVisible(false);
        }else{
            //setIsRestaurant(1);
            LoginView.getInstance();
            LoginView.getInstance().setIsRestaurant(1);
            //RestaurantView.getInstance();
        }
    }

    public static MainView getInstance(){
        if(instance == null){
            instance = new MainView();
            instance.initialize();
        }
        return instance;
    }

    public int getIsRestaurant() {
        return IsRestaurant;
    }

    public void setIsRestaurant(int isRestaurant) {
        IsRestaurant = isRestaurant;
    }

    public WelcomeView getWelcomeView() {
        return welcomeView;
    }

    public void setWelcomeView(WelcomeView welcomeView) {
        this.welcomeView = welcomeView;
    }
}
