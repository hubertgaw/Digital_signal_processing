package pl.cps;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import pl.cps.view.MainLayout;
import pl.cps.view.SinChartComponent;

/**
 * @author Thomas Darimont
 */

public class App extends Application {

    /**
     * Note that this is configured in application.properties
     */

    private String windowTitle;

    private SinChartComponent sinChartComponent = new SinChartComponent();
    private MainLayout mainLayout = new MainLayout(sinChartComponent);

    @Override
    public void start(Stage stage) throws Exception {

        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));

        stage.setTitle(windowTitle);
        stage.setScene(new Scene(mainLayout));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}