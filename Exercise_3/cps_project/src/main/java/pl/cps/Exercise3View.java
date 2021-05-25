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
import pl.cps.signal.exercise3.Splot;
import pl.cps.signal.exercise3.filtration.LowPassFiltration;
import pl.cps.signal.exercise3.filtration.MiddlePassFiltration;
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
    Double sampleFreq1;
    Double sampleFreq2;
    Integer filterRow;
    Double cutOffFreq;
    Integer signalNumber1;
    Integer signalNumber2;
    Integer selectedWindowIndex = 0;
    Integer selectedFilterIndex = 0;
    Integer selectedCorrelationTypeIndex = 0;


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
            askWindowForConvolutionAndCorrelationParameters("korelacja");
        });
        convolutionBtn.setOnMouseClicked((a) -> {
            askWindowForConvolutionAndCorrelationParameters("splot");
        });
        vbox.getChildren().add(filtrationBtn);
        vbox.getChildren().add(correlationBtn);
        vbox.getChildren().add(convolutionBtn);
        pane.add(vbox,1,1);
    }
    public void filtration(){
        pane.getChildren().clear();
        Button backBtn = new Button("Wroc");
        backBtn.setOnMouseClicked((a) -> {this.init();});
        pane.add(backBtn,0,0);
//        pane.add(new TextField("jakis tam"),0,1);
        ChartComponent filterChart = new ChartComponent();
        List<Data> filterPoints = new ArrayList<>();
        Window window;
        if (selectedWindowIndex == 0) {
            window = new RectangularWindow();
        } else {
            window = new HanningWindow();
        }
        LowPassFiltration filtration;
        if (selectedFilterIndex == 0) {
            filtration = new LowPassFiltration();
        } else {
            filtration = new MiddlePassFiltration(); // middlePass dziedziczy po Low więc chyba git
        }

        if (signalNumber1 == 2) {
            filterPoints = filtration.calculate(
                    Sampler.sampling(App.getResultPoints(), sampleFreq1), filterRow, cutOffFreq,
                    sampleFreq1, window);
        } else {
            filterPoints = filtration.calculate(
                    App.getSelectedSignals().get(signalNumber1).calculateAndReturnPoints(sampleFreq1), filterRow, cutOffFreq,
                    sampleFreq1, window);
        }
        filterChart.generateSignal(filterPoints);
        filterChart.drawDiscreteChart("Filtr");

        ChartComponent chartAfterFiltration = new ChartComponent();
        List<Data> pointsAfterFiltration = new ArrayList<>();
        if (signalNumber1 == 2) {
            pointsAfterFiltration = Splot.calculate(App.getResultPoints(), filterPoints);
        } else {
            pointsAfterFiltration = Splot.calculate(
                    filterPoints,
                    App.getSelectedSignals().get(signalNumber1).calculateAndReturnPoints(sampleFreq1));
        }
        chartAfterFiltration.generateSignal(pointsAfterFiltration);
        chartAfterFiltration.drawDiscreteChart("Sygnał po filtracji");

        pane.add(filterChart, 1,0);
        pane.add(chartAfterFiltration, 1,1);
    }

    public void correlation(){
        List<Data> firstSignalPoints = new ArrayList<>();
        List<Data> secondSignalPoints = new ArrayList<>();
        preparePoints(firstSignalPoints, secondSignalPoints);
        List<Data> correlationPoints;
        if (selectedCorrelationTypeIndex == 0) {
            correlationPoints = Correlation.calculateDirect(firstSignalPoints, secondSignalPoints);
        } else {
            correlationPoints = Correlation.calculateWithConvolution(firstSignalPoints, secondSignalPoints);
        }
        ChartComponent chart = new ChartComponent();
        chart.generateSignal(correlationPoints);
        chart.drawDiscreteChart("Wynik korelacji sygnału " + signalNumber1 + " i " + signalNumber2);

        pane.add(chart, 1, 0);
    }

    public void convolution() {
        List<Data> firstSignalPoints = new ArrayList<>();
        List<Data> secondSignalPoints = new ArrayList<>();
        preparePoints(firstSignalPoints, secondSignalPoints);
        List<Data> convolutionPoints;
        convolutionPoints = Splot.calculate(firstSignalPoints, secondSignalPoints);
        ChartComponent chart = new ChartComponent();
        chart.generateSignal(convolutionPoints);
        chart.drawDiscreteChart("Wynik splotu sygnału " + signalNumber1 + " i " + signalNumber2);

        pane.add(chart, 1, 0);
    }

    private void preparePoints(List<Data> firstSignalPoints, List<Data> secondSignalPoints) {
        pane.getChildren().clear();
        Button backBtn = new Button("Wroc");
        backBtn.setOnMouseClicked((a) -> {this.init();});
        pane.add(backBtn,0,0);

        if (signalNumber1 == 2) {
            firstSignalPoints.addAll(App.getResultPoints());
        } else {
            firstSignalPoints.addAll(App.getSelectedSignals().get(signalNumber1).calculateAndReturnPoints(sampleFreq1));
        }
        if (signalNumber2 == 2) {
            secondSignalPoints.addAll(App.getResultPoints());
        } else {
            secondSignalPoints.addAll(App.getSelectedSignals().get(signalNumber2).calculateAndReturnPoints(sampleFreq2));
        }
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

        Text askFilterTypeText = new Text("Wybierz typ filtra");
        ChoiceBox filterCB = new ChoiceBox(FXCollections.observableArrayList(
                "Filtr dolnoprzepustowy", "Filtr pasmowy")
        );
        filterCB.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        selectedFilterIndex = newValue.intValue();
                    }
                }

        );
        VBox askFilterTypeVBox = new VBox();
        askFilterTypeVBox.getChildren().add(askFilterTypeText);
        askFilterTypeVBox.getChildren().add(filterCB);


        Button nextBtn = new Button("Dalej");

        VBox askParametersVBox = new VBox();
        askParametersVBox.getChildren().add(askSignalNumberVBox);
        askParametersVBox.getChildren().add(askFreqVBox);
        askParametersVBox.getChildren().add(askFilterRowVBox);
        askParametersVBox.getChildren().add(askCutOffFreqVBox);
        askParametersVBox.getChildren().add(askWindowVBox);
        askParametersVBox.getChildren().add(askFilterTypeVBox);
        askParametersVBox.getChildren().add(nextBtn);

        nextBtn.setOnMouseClicked((action) -> {
            signalNumber1 = Integer.parseInt(askSignalNumberField.getText());
            sampleFreq1 = Double.parseDouble(askFreqField.getText());
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

    private void askWindowForConvolutionAndCorrelationParameters(String name) {
        Stage askValueStage = new Stage();
        askValueStage.setTitle("Parametry dla: " + name);

        Text askFirstSignalNumberText = new Text("Numer pierwszego sygnału");
        TextField askFirstSignalNumberField = new TextField("0");
        VBox askFirstSignalNumberVBox = new VBox();
        askFirstSignalNumberVBox.getChildren().add(askFirstSignalNumberText);
        askFirstSignalNumberVBox.getChildren().add(askFirstSignalNumberField);

        Text askFirstSignalFreqText = new Text("Częstotliwość próbkowania dla pierwszego sygnału");
        TextField askFirstSignalFreqField = new TextField("0");
        VBox askFirstSignalFreqVBox = new VBox();
        askFirstSignalFreqVBox.getChildren().add(askFirstSignalFreqText);
        askFirstSignalFreqVBox.getChildren().add(askFirstSignalFreqField);

        Text askSecondSignalNumberText = new Text("Numer drugiego sygnału");
        TextField askSecondSignalNumberField = new TextField("0");
        VBox askSecondSignalNumberVBox = new VBox();
        askSecondSignalNumberVBox.getChildren().add(askSecondSignalNumberText);
        askSecondSignalNumberVBox.getChildren().add(askSecondSignalNumberField);

        Text askSecondSignalFreqText = new Text("Częstotliwość próbkowania dla drugiego sygnału");
        TextField askSecondSignalFreqField = new TextField("0");
        VBox askSecondSignalFreqVBox = new VBox();
        askSecondSignalFreqVBox.getChildren().add(askSecondSignalFreqText);
        askSecondSignalFreqVBox.getChildren().add(askSecondSignalFreqField);

        Button nextBtn = new Button("Dalej");

        VBox askParametersVBox = new VBox();
        askParametersVBox.getChildren().add(askFirstSignalNumberVBox);
        askParametersVBox.getChildren().add(askFirstSignalFreqVBox);
        askParametersVBox.getChildren().add(askSecondSignalNumberVBox);
        askParametersVBox.getChildren().add(askSecondSignalFreqVBox);

        if (name.equals("korelacja")) {
            Text askCorrelationTypeText = new Text("Wybierz typ korelacji");
            ChoiceBox correlationCB = new ChoiceBox(FXCollections.observableArrayList(
                    "bezpośrednia", "z użyciem splotu"
            ));
            correlationCB.getSelectionModel().selectedIndexProperty().addListener(
                    new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            selectedCorrelationTypeIndex = newValue.intValue();
                        }
                    }
            );
            VBox askCorrelationTypeVBox = new VBox();
            askCorrelationTypeVBox.getChildren().add(askCorrelationTypeText);
            askCorrelationTypeVBox.getChildren().add(correlationCB);
            askParametersVBox.getChildren().add(askCorrelationTypeVBox);
        }
        askParametersVBox.getChildren().add(nextBtn);

        nextBtn.setOnMouseClicked((action) -> {
            signalNumber1 = Integer.parseInt(askFirstSignalNumberField.getText());
            signalNumber2 = Integer.parseInt(askSecondSignalNumberField.getText());
            sampleFreq1 = Double.parseDouble(askFirstSignalFreqField.getText());
            sampleFreq2 = Double.parseDouble(askSecondSignalFreqField.getText());
            if (name.equals("splot")) {
                convolution();
            } else {
                correlation();
            }
            askValueStage.close();
        });

        Scene askParametersScene = new Scene(askParametersVBox, 300, 300);

        askValueStage.initModality(Modality.APPLICATION_MODAL);
        askValueStage.initOwner(stage);
        askValueStage.setScene(askParametersScene);
        askValueStage.show();
    }

}