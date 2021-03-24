package pl.cps;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.cps.signal.emiters.*;
import pl.cps.view.MainLayout;


public class App extends Application {


    private String windowTitle;


    private MainLayout mainLayout = new MainLayout();

    Menu signalOneMenu = new Menu(), signalTwoMenu = new Menu(), operationMenu = new Menu();

    @Override
    public void start(Stage stage) throws Exception {

        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(mainLayout));
        stage.setResizable(true);
        stage.centerOnScreen();
        setMenu(stage);
        stage.show();
    }

    private void setMenu(Stage stage) {
        VBox buttonBox = new VBox();
        MenuBar menuBar = new MenuBar();
        Button calculateButton = new Button(), nextButton = new Button();
        calculateButton.setText("Oblicz i pokaz");
        nextButton.setText("Nastepny wykres");
        mainLayout.add(calculateButton, 1, 1);
        mainLayout.add(nextButton, 1, 2);
        calculateButton.setOnMouseClicked((action) -> {
            startCalculating();
        });
        nextButton.setOnMouseClicked((action) -> {
            loadNextDiagram();
        });
        signalOneMenu.setText("Sygnal nr 1");
        signalTwoMenu.setText("Sygnal nr 2");
        operationMenu.setText("Operacja");
        String[] signals = {"Szum Gaussowski",
                "Szum Impulsowy",
                "Sygnał sinusoidalny wyprostowany jednopolowkowo",
                "Sygnał sinusoidalny wyprostowany dwopolowkowo",
                "Sygnal sinusoidalny",
                "Sygnal prostokatny",
                "Sygnal prostokatny symetryczny",
                "Sygnal trojkatny",
                "Szum o rozkladzie jednostajnym",
                "Impuls jednostkowy",
                "Skok jednostkowy"},
                operations = {"Dodawanie",
                        "Odejmowanie",
                        "Mnożenie",
                        "Dzielenie"};

        addItemsToMenu(signals, signalOneMenu);
        addItemsToMenu(signals, signalTwoMenu);
        addItemsToMenu(operations, operationMenu);
        menuBar.getMenus().add(signalOneMenu);
        menuBar.getMenus().add(operationMenu);
        menuBar.getMenus().add(signalTwoMenu);
        VBox vBox = new VBox(menuBar);
        mainLayout.add(vBox, 0, 0);
    }

    private void loadNextDiagram(){
        System.out.println("NEXT");
    }

    private void startCalculating() {
        System.out.println("OBLICZAM");
        System.out.println(getSellections()[0]);
        System.out.println(getSellections()[1]);
        System.out.println(getSellections()[2]);
        System.out.println("=============");
    }

    private void addItemsToMenu(String[] items, Menu menu) {
        for (String item : items) {
            CheckMenuItem mItem = new CheckMenuItem(item);
            menu.getItems().add(mItem);
            mItem.setOnAction((action) -> {
                CheckMenuItem it = (CheckMenuItem) action.getSource();
                for (MenuItem item1 : it.getParentMenu().getItems()) {
                    CheckMenuItem tmp = (CheckMenuItem) item1;
                    tmp.setSelected(false);
                }
                it.setSelected(true);
            });
        }
    }

    public Object[] getSellections() {
        Object[] ret = {getSignalFromMenuItemName(getSellectdOptionFromMenu(signalOneMenu)),
                getSignalFromMenuItemName(getSellectdOptionFromMenu(signalTwoMenu)),
                getSellectdOptionFromMenu(operationMenu)};
        return ret;
    }

    private String getSellectdOptionFromMenu(Menu menu) {
        String ret = null;
        for (MenuItem item : menu.getItems()) {
            CheckMenuItem tmp = (CheckMenuItem) item;
            if (tmp.isSelected()) {
                return tmp.getText();
            }
        }
        return ret;
    }

    private Signal getSignalFromMenuItemName(String name) {
        Signal ret = null;
        if (name == "Szum Gaussowski")
            ret = new GaussianNoise(1, 1, 1);
        if (name == "Szum Impulsowy")
            ret = new ImpulseNoise(1, 1, 1, 1, 1);
        if (name == "Sygnał sinusoidalny wyprostowany jednopolowkowo")
            ret = new OneHalfSinusoidalSignal(1, 1, 1, 1);
        if (name == "Sygnał sinusoidalny wyprostowany dwopolowkowo")
            ret = new TwoHalfSinusoidalSignal(1, 1, 1, 1);
        if (name == "Sygnal sinusoidalny")
            ret = new SinusoidalSignal(1, 1, 1, 1);
        if (name == "Sygnal prostokatny")
            ret = new SquareSignal(1, 1, 1, 1, 1);
        if (name == "Sygnal prostokatny symetryczny")
            ret = new SymmetricalSquareSignal(1, 1, 1, 1, 1);
        if (name == "Sygnal trojkatny")
            ret = new TriangularSignal(1, 1, 1, 1, 1);
        if (name == "Szum o rozkladzie jednostajnym")
            ret = new UniformlyDistributedNoise(1, 1, 1);
        if (name == "Impuls jednostkowy")
            ret = new UnitImpulse(1, 1, 1, 1, 1);
        if (name == "Skok jednostkowy")
            ret = new UnitJump(1, 1, 1, 1);
        return ret;
    }

    public static void main(String[] args) {
        launch(args);
    }

}