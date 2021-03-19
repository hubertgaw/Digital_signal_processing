//package pl.cps.view;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import javafx.stage.WindowEvent;
//import javafx.util.Duration;
//
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.LinkedBlockingDeque;
//
//public class ChartController {
//
//    @FXML
//    LineChart<Integer, Float> plot;
//    @FXML
//    NumberAxis xAxis;
//    @FXML
//    NumberAxis yAxis;
//
//    private XYChart.Series<Integer, Float> series = new XYChart.Series<>();
//
//    private Random rand = new Random();
//
//    private int xMaxValue=0;
//
//    private int xPlotMaxValue=0;
//
//    private static final int SAMPLE_RATE = 16 * 1024;
//    private double period = (double) SAMPLE_RATE / 500;
//
//    private boolean isRunning = true;
//
//    private LinkedBlockingDeque<Float> data = new LinkedBlockingDeque<>(100);
//
//    private Stage myStage;
//
//    @FXML private AnchorPane ap;
//
//    @FXML
//    public void initialize() {
//        ap.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
//            if (oldScene == null && newScene != null) {
//                // scene is set for the first time. Now its the time to listen stage changes.
//                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
//                    if (oldWindow == null && newWindow != null) {
//                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
//                        newWindow.setOnCloseRequest(event -> isRunning = false);
//                    }
//                });
//            }
//        });
//
//        xAxis.setAutoRanging(false);
//        xAxis.setLowerBound(0);
//        xAxis.setUpperBound(100);
//        xAxis.setTickUnit(1);
//
//        xAxis.setForceZeroInRange(false);
//
//        yAxis.setAutoRanging(false);
//        yAxis.setLowerBound(-1);
//        yAxis.setUpperBound(1);
//        yAxis.setTickUnit(0.1);
//
//        plot.setAnimated(false);
//
//        plot.getData().add(series);
//        series.setName("sinus");
//        //plot.setCreateSymbols(false);
//    }
//
//    public void onStartClick(ActionEvent actionEvent) {
//        Timeline addPointEverySecond = new Timeline(new KeyFrame(Duration.millis(5), event->{
//            //series.getData().add(new XYChart.Data<>(xPlotMaxValue, (float) rand.nextDouble()));
//            //series.getData().add(new XYChart.Data<>(xMaxValue, (float) Math.sin(2.0 * Math.PI * xMaxValue / period)));
//            Float newData = data.poll();
//            if (newData !=null) {
//                series.getData().add((new XYChart.Data<>(xPlotMaxValue, newData)));
//                if (series.getData().size()>100) {
//                    series.getData().remove(0);
//                    double low = xAxis.getLowerBound();
//                    low++;
//                    xAxis.setLowerBound(low);
//                    xAxis.setUpperBound(low+100);
//                }
//                xPlotMaxValue++;
//                // System.out.println(" -- Aktualizacja");
//            } else {
//                // System.out.println(" -- Brak danych do narysowania");
//            }
//
//        }));
//
//        addPointEverySecond.setCycleCount(Timeline.INDEFINITE);
//        addPointEverySecond.playFromStart();
//
//        new Thread(() -> {
//            try {
//                while (isRunning) {
//                    //  System.out.println("Zaraz umieszczę...");
//                    if (data.offer((float) Math.sin(2.0 * Math.PI * xMaxValue / period))) {
//                        //    System.out.println("Umieszczone dane, czekam");
//                    } else {
//                        System.out.println("Zabrakło miejsca");
//                    }
//
//                    xMaxValue++;
//
//                    Thread.sleep(6);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "newData").start();
//    }
//
//    public void setStage(Stage stage) {
//        myStage = stage;
//    }
//}