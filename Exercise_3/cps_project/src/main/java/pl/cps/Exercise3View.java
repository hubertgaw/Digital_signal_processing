package pl.cps;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import pl.cps.signal.adc.Sampler;
import pl.cps.signal.emiters.Signal;
import pl.cps.signal.emiters.TriangularSignal;
import pl.cps.signal.emiters.UnitJump;
import pl.cps.signal.exercise3.Correlation;
import pl.cps.signal.exercise3.filtration.LowPassFiltration;
import pl.cps.signal.exercise3.filtration.windows.HanningWindow;
import pl.cps.signal.exercise3.filtration.windows.RectangularWindow;
import pl.cps.signal.exercise3.filtration.windows.Window;
import pl.cps.signal.model.Data;
import pl.cps.view.ChartComponent;

import java.util.ArrayList;
import java.util.List;


public class Exercise3View {
    GridPane pane = new GridPane();
    Stage stage;
    Double sampleFreq;
    Integer filterRow;
    Double cutOffFreq;
    Integer signalNumber1;
    Integer signalNumber2;
    Integer selectedWindowIndex = 0;


    public Exercise3View(Stage stage) {
        this.stage = stage;
        Stage main = new Stage();
        Scene mainScene = new Scene(pane,1000,700);
        main.setTitle("Filtracja oraz próbkowanie");
        main.initModality(Modality.APPLICATION_MODAL);
        main.initOwner(stage);
        main.setScene(mainScene);
        main.show();
        pane.setAlignment(Pos.BASELINE_CENTER);
    }

    public void init() {
        pane.getChildren().clear();

        Button filtrationBtn = new Button("Filtracja"),correlationBtn = new Button("Korelacja"),
                convolutionBtn = new Button("Splot");
        VBox vbox = new VBox();
        filtrationBtn.setOnMouseClicked((a) -> {
            askWindowForFiltrationParameters();
        });
        correlationBtn.setOnMouseClicked((a) -> {
            correlation();
        });
        convolutionBtn.setOnMouseClicked((a) -> {
            convolution();
        });
        vbox.getChildren().add(filtrationBtn);
        vbox.getChildren().add(correlationBtn);
        pane.add(vbox,1,1);
    }
    public void filtration(){
        pane.getChildren().clear();
        Button backBtn = new Button("Wroc");
        backBtn.setOnMouseClicked((a) -> {this.init();});
        pane.add(backBtn,0,0);
//        pane.add(new TextField("jakis tam"),0,1);
        ChartComponent chart = new ChartComponent();
        LowPassFiltration lowPassFiltration = new LowPassFiltration();
        List<Data> pointsAfterFiltration = new ArrayList<>();
        Window window;
        if (selectedWindowIndex == 0) {
            window = new RectangularWindow();
        } else {
            window = new HanningWindow();
        }
        if (signalNumber1 == 0) {
            pointsAfterFiltration = lowPassFiltration.calculate(App.getSelectedSignals().get(0).
                    calculateAndReturnPoints(sampleFreq), filterRow, cutOffFreq, sampleFreq, window);
        } else if (signalNumber1 == 1) {
            pointsAfterFiltration = lowPassFiltration.calculate(
                    App.getSelectedSignals().get(1).calculateAndReturnPoints(sampleFreq), filterRow, cutOffFreq,
                    sampleFreq, window);
        } else {
            pointsAfterFiltration = lowPassFiltration.calculate(
                    Sampler.sampling(App.getResultPoints(),sampleFreq), filterRow, cutOffFreq,
                    sampleFreq, window);
        }

        chart.generateSignal(pointsAfterFiltration);
        chart.drawDiscreteChart("Filtr");

        pane.add(chart, 1,0);
    }

    public void correlation(){
        pane.getChildren().clear();
        Button backBtn = new Button("Wroc");
        backBtn.setOnMouseClicked((a) -> {this.init();});
        pane.add(backBtn,0,0);

        ChartComponent chart = new ChartComponent(),
                chart1 = new ChartComponent(),
                chart2 = new ChartComponent(),
                chart3 = new ChartComponent(),
                chart4 = new ChartComponent(),
                chart5 = new ChartComponent();
        Correlation corr = new Correlation();
        Signal s1 = new UnitJump(1,0,1,0),
                s2 = new TriangularSignal(1,0,1,1,0.5);
        List<Data> d1 = new ArrayList<>(),
                d2 = new ArrayList<>();
        for (double i = -1; i < 0; i+=0.02) {
            d1.add(new Data(i,0.0));
            d2.add(new Data(i,0.0));
        }
        s1.calculateAndReturnPoints(10).stream().forEach( s -> d1.add(s));
        s2.calculateAndReturnPoints(10).stream().forEach( s -> d2.add(s));

        for (double i = 1; i < 2; i+=0.02) {
            d1.add(new Data(i,0.0));
            d2.add(new Data(i,0.0));
        }

        chart.generateSignal(corr.calculateWithConvolution(d1,d2));
        chart1.generateSignal(d1);
        chart2.generateSignal(d2);
        System.out.println(d1);
        chart3.generateSignal(corr.calculateDirect(d1,d2));
        chart1.drawContinuousChart("Prostok");
        chart2.drawContinuousChart("trojk");
        chart.drawContinuousChart("corr splot");
        chart3.drawContinuousChart("corr direct");

        pane.add(chart1,1,0);
        pane.add(chart2,1,1);
        pane.add(chart,2,0);
        pane.add(chart3,2,1);
    }

    public void convolution() {
        pane.getChildren().clear();

    }

    private void askWindowForFiltrationParameters() {
        Stage askValueStage = new Stage();
        askValueStage.setTitle("Parametry dla filtrowania");

        Text askSignalNumberText = new Text("Numer sygnału poddawanego filtracji");
        TextField askSignalNumberField = new TextField("0");
        VBox askSignalNumberVBox = new VBox();
        askSignalNumberVBox.getChildren().add(askSignalNumberText);
        askSignalNumberVBox.getChildren().add(askSignalNumberField);

        Text askFreqText = new Text("Częstotliwość próbkowania dla wybranego sygnału");
        TextField askFreqField = new TextField("0");
        VBox askFreqVBox = new VBox();
        askFreqVBox.getChildren().add(askFreqText);
        askFreqVBox.getChildren().add(askFreqField);

        Text askFilterRowText = new Text("Rząd filtra");
        TextField askFilterRowField = new TextField("0");
        VBox askFilterRowVBox = new VBox();
        askFreqVBox.getChildren().add(askFilterRowText);
        askFreqVBox.getChildren().add(askFilterRowField);

        Text askCutOffFreqText = new Text("Częstotliwość odcięcia");
        TextField askCutOffFreqField = new TextField("0");
        VBox askCutOffFreqVBox = new VBox();
        askFreqVBox.getChildren().add(askCutOffFreqText);
        askFreqVBox.getChildren().add(askCutOffFreqField);

        Text askWindowText = new Text("Wybierz okno");
        ChoiceBox windowCB = new ChoiceBox(FXCollections.observableArrayList(
                "Okno prostokątne", "Okno Hanninga")
        );
        windowCB.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        selectedWindowIndex = newValue.intValue();
                    }
                }

        );
        VBox askWindowVBox = new VBox();
        askWindowVBox.getChildren().add(askWindowText);
        askWindowVBox.getChildren().add(windowCB);


        Button nextBtn = new Button("Dalej");

        VBox askParametersVBox = new VBox();
        askParametersVBox.getChildren().add(askSignalNumberVBox);
        askParametersVBox.getChildren().add(askFreqVBox);
        askParametersVBox.getChildren().add(askFilterRowVBox);
        askParametersVBox.getChildren().add(askCutOffFreqVBox);
        askParametersVBox.getChildren().add(askWindowVBox);
        askParametersVBox.getChildren().add(nextBtn);

        nextBtn.setOnMouseClicked((action) -> {
            signalNumber1 = Integer.parseInt(askSignalNumberField.getText());
            sampleFreq = Double.parseDouble(askFreqField.getText());
            filterRow = Integer.parseInt(askFilterRowField.getText());
            cutOffFreq = Double.parseDouble(askCutOffFreqField.getText());
            filtration();
            askValueStage.close();
        });


        Scene askParametersScene = new Scene(askParametersVBox, 300, 300);

        askValueStage.initModality(Modality.APPLICATION_MODAL);
        askValueStage.initOwner(stage);
        askValueStage.setScene(askParametersScene);
        askValueStage.show();
    }
}