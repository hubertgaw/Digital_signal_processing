package pl.cps;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.cps.signal.emiters.*;
import pl.cps.signal.model.*;
import pl.cps.view.MainLayout;

import java.util.ArrayList;
import java.util.List;


public class App extends Application {

    private String windowTitle;
    static Double ampValue, strTimeValue, durValue, termValue, freqValue, possValue, kwValue, jumpValue;
    private static MainLayout mainLayout;
    private static GridPane mainPane = new GridPane();
    static Menu signalOneMenu = new Menu(), signalTwoMenu = new Menu(), operationMenu = new Menu(),
            barsNumberMenu = new Menu();
    private static List<Signal> selectedSignals = new ArrayList<>();
    private static String selectedOperation;
    private VBox signalCalculatedDetails = new VBox();
    private Text avgValue = new Text(), absAvgValue = new Text(), avgPower = new Text(),
            variation = new Text(), effectiveValue = new Text();
    private int showingSignalCounter = 1;
    private List<Data> resultPoints = new ArrayList<Data>();


    public static String getSelectedOperation() {
        return selectedOperation;
    }

    public static void setSelectedOperation(String selectedOperation) {
        App.selectedOperation = selectedOperation;
    }

    public static List<Signal> getSelectedSignals() {
        return selectedSignals;
    }

    public static void setSelectedSignals(List<Signal> selectedSignals) {
        App.selectedSignals = selectedSignals;
    }

    public static Menu getSignalOneMenu() {
        return signalOneMenu;
    }

    public static Menu getSignalTwoMenu() {
        return signalTwoMenu;
    }

    public static Menu getBarsNumberMenu() {
        return barsNumberMenu;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene mainScene = new Scene(mainPane);
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));
        stage.setTitle(windowTitle);
        stage.setScene(mainScene);
        stage.setResizable(true);
        stage.centerOnScreen();
        setMenu(stage);
        stage.show();
        stage.setMinHeight(600);
        stage.setMinWidth(800);
    }

    private void setMenu(Stage stage) {
        VBox buttonBox = new VBox();
        MenuBar menuBar = new MenuBar();
        Button calculateButton = new Button(), nextButton = new Button(), showButton = new Button();
        calculateButton.setText("Podaj parametry sygnałów");
        nextButton.setText("Nastepny wykres");
        showButton.setText("Oblicz i pokaż");
        buttonBox.getChildren().add(calculateButton);
        buttonBox.getChildren().add(nextButton);
        buttonBox.getChildren().add(showButton);
        mainPane.add(buttonBox, 1, 1);
        calculateButton.setOnMouseClicked((action) -> {
            startCalculating(stage);
        });
        nextButton.setOnMouseClicked((action) -> {
            loadNextDiagram();
        });
        showButton.setOnMouseClicked((action) -> {
            showDiagram();
        });
        signalOneMenu.setText("Sygnal nr 1");
        signalTwoMenu.setText("Sygnal nr 2");
        operationMenu.setText("Operacja");
        barsNumberMenu.setText("Liczba słupków");
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
                        "Dzielenie"},
                barsNumber = {"5", "10", "15", "20"};

        addItemsToMenu(signals, signalOneMenu);
        addItemsToMenu(signals, signalTwoMenu);
        addItemsToMenu(operations, operationMenu);
        addItemsToMenu(barsNumber, barsNumberMenu);
        menuBar.getMenus().add(signalOneMenu);
        menuBar.getMenus().add(operationMenu);
        menuBar.getMenus().add(signalTwoMenu);
        menuBar.getMenus().add(barsNumberMenu);
        VBox vBox = new VBox(menuBar);
        mainPane.add(vBox, 0, 0);


        signalCalculatedDetails.getChildren().add(avgValue);
        signalCalculatedDetails.getChildren().add(absAvgValue);
        signalCalculatedDetails.getChildren().add(avgPower);
        signalCalculatedDetails.getChildren().add(variation);
        signalCalculatedDetails.getChildren().add(effectiveValue);
        setSignalCalculatedDetails("-", "-", "-", "-", "-");
        mainPane.add(signalCalculatedDetails, 1, 0);

    }

    private void loadNextDiagram() {
        showingSignalCounter++;
        if (showingSignalCounter % 3 == 0) {
            setSignalCalculatedDetails(selectedSignals.get(0).getAvarageValue(),
                    selectedSignals.get(0).getAbsAvarageValue(),
                    selectedSignals.get(0).getAvaragePower(),
                    selectedSignals.get(0).getVariation(),
                    selectedSignals.get(0).getEffectiveValue());
        } else if (showingSignalCounter % 3 == 1) {
            setSignalCalculatedDetails(selectedSignals.get(1).getAvarageValue(),
                    selectedSignals.get(1).getAbsAvarageValue(),
                    selectedSignals.get(1).getAvaragePower(),
                    selectedSignals.get(1).getVariation(),
                    selectedSignals.get(1).getEffectiveValue());

        } else if (showingSignalCounter % 3 == 2) {
            calculateResultPoints();
            setSignalCalculatedDetails(GaussianNoise.getAvarageValue(resultPoints),//Zamiast GaussianNoise moze byc dowolny, chodzi o dostep do metod z Signal
                    GaussianNoise.getAbsAvarageValue(resultPoints),
                    GaussianNoise.getAvaragePower(resultPoints),
                    GaussianNoise.getVariation(resultPoints),
                    GaussianNoise.getEffectiveValue(resultPoints));
        }
        mainLayout.initChart(showingSignalCounter % 3);
        mainLayout.initHistogram(showingSignalCounter % 3);
        System.out.println("NEXT");
    }

    private void setSignalCalculatedDetails(double avgValueToSet, double absAvgValueToSet, double avgPowerToSet,
                                            double variationToSet, double effectiveValueToSet) {
        setSignalCalculatedDetails(Double.toString(avgValueToSet), Double.toString(absAvgValueToSet),
                Double.toString(avgPowerToSet), Double.toString(variationToSet), Double.toString(effectiveValueToSet));
    }

    private void setSignalCalculatedDetails(String avgValueToSet, String absAvgValueToSet, String avgPowerToSet,
                                            String variationToSet, String effectiveValueToSet) {
        avgValue.setText("Wartosc srednia: " + avgValueToSet);
        absAvgValue.setText("Wartosc srednia bezwzgledna: " + absAvgValueToSet);
        avgPower.setText("Moc srednia: " + avgPowerToSet);
        variation.setText("Wariacja: " + variationToSet);
        effectiveValue.setText("Wartosc skuteczna: " + effectiveValueToSet);
    }

    private void calculateResultPoints() {
        if (getSellectdOptionFromMenu(operationMenu).equalsIgnoreCase("dodawanie")) {
            resultPoints.clear();
            resultPoints.addAll(Addition.performCalculating(
                    this.getSelectedSignals().get(1).getPoints(),
                    this.getSelectedSignals().get(0).getPoints()));
        } else if (getSellectdOptionFromMenu(operationMenu).equalsIgnoreCase("odejmowanie")) {
            resultPoints.clear();
            resultPoints.addAll(Subtraction.performCalculating(
                    this.getSelectedSignals().get(1).getPoints(),
                    this.getSelectedSignals().get(0).getPoints()));
        } else if (getSellectdOptionFromMenu(operationMenu).equalsIgnoreCase("mnożenie")) {
            resultPoints.clear();
            resultPoints.addAll(Multiplication.performCalculating(
                    this.getSelectedSignals().get(1).getPoints(),
                    this.getSelectedSignals().get(0).getPoints()));
        } else if (getSellectdOptionFromMenu(operationMenu).equalsIgnoreCase("dzielenie")) {
            resultPoints.clear();
            resultPoints.addAll(Division.performCalculating(
                    this.getSelectedSignals().get(1).getPoints(),
                    this.getSelectedSignals().get(0).getPoints()));
        }

    }

    private void showDiagram() {
        //dodanie wykresu na okno

        mainPane.getChildren().remove(mainLayout);
        mainLayout = new MainLayout();
        mainLayout.initChart(2);
        mainLayout.initHistogram(2);
        //        System.out.println("Size show" + getSelectedSignals().size());
        mainPane.add(mainLayout, 0, 1);
    }

    private void startCalculating(Stage stage) {
        selectedSignals.clear();
        getSellections(stage)[2].toString();
        getSelectedSignals().size();
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

    public static Object[] getSellections(Stage stage) {
        Object[] ret = {getSignalFromMenuItemName(getSellectdOptionFromMenu(signalOneMenu), stage),
                getSignalFromMenuItemName(getSellectdOptionFromMenu(signalTwoMenu), stage),
                getSellectdOptionFromMenu(operationMenu)};
        return ret;
    }

    public static String getSellectdOptionFromMenu(Menu menu) {
        String ret = null;
        for (MenuItem item : menu.getItems()) {
            CheckMenuItem tmp = (CheckMenuItem) item;
            if (tmp.isSelected()) {
                setSelectedOperation(tmp.getText());
                return tmp.getText();
            }
        }
        return ret;
    }

    private static Signal getSignalFromMenuItemName(String name, Stage stage) {
        return setParametersDialogShow(stage, name);
    }

    private static Signal setParametersDialogShow(Stage stage, String name) {
        Stage dialog = new Stage();
        dialog.setTitle(name);
        Button saveBtn = new Button("Dalej");
        VBox ampBox = new VBox(), strtBox = new VBox(), durBox = new VBox(), termBox = new VBox(),
                freqBox = new VBox(), possBox = new VBox(), kwBox = new VBox(), jumpBox = new VBox();
        TextField amp = new TextField("0"), strTime = new TextField("0"), dur = new TextField("0"),
                term = new TextField("1"), freq = new TextField("0"), poss = new TextField("0"),
                kw = new TextField("0"), jump = new TextField("0");
        Text ampText = new Text("Amplitude:"), strTimeText = new Text("Start w sekundzie:"),
                durText = new Text("Czas trwania w sekundach"),
                termText = new Text("Okres:"), freqText = new Text("czestotliwosc:"), possText = new Text("Prawdopodobienstwo:"),
                kwText = new Text("wspolczynnik wypelnienia"), jumpText = new Text("Informacje odnośnie skoku");

        if (name.equals("Szum Gaussowski") || name.equals("Szum o rozkladzie jednostajnym")) {
            term.setDisable(true);
            freq.setDisable(true);
            poss.setDisable(true);
            kw.setDisable(true);
            jump.setDisable(true);
        } else if (name.equals("Sygnał sinusoidalny wyprostowany jednopolowkowo")
                || name.equals("Sygnał sinusoidalny wyprostowany dwopolowkowo")
                || name.equals("Sygnal sinusoidalny")) {
            freq.setDisable(true);
            poss.setDisable(true);
            kw.setDisable(true);
            jump.setDisable(true);
        } else if (name.equals("Sygnal prostokatny")
                || name.equals("Sygnal prostokatny symetryczny")
                || name.equals("Sygnal trojkatny")) {
            freq.setDisable(true);
            poss.setDisable(true);
            jump.setDisable(true);
        } else if (name.equals("Szum Impulsowy")) {
            term.setDisable(true);
            kw.setDisable(true);
            jump.setDisable(true);
        } else if (name.equals("Impuls jednostkowy")) {
            term.setDisable(true);
            poss.setDisable(true);
            kw.setDisable(true);
        } else if (name.equals("Skok jednostkowy")) {
            kw.setDisable(true);
            poss.setDisable(true);
            freq.setDisable(true);
            term.setDisable(true);
        }

        ampBox.getChildren().add(ampText);
        ampBox.getChildren().add(amp);
        strtBox.getChildren().add(strTimeText);
        strtBox.getChildren().add(strTime);
        durBox.getChildren().add(durText);
        durBox.getChildren().add(dur);
        termBox.getChildren().add(termText);
        termBox.getChildren().add(term);
        freqBox.getChildren().add(freqText);
        freqBox.getChildren().add(freq);
        possBox.getChildren().add(possText);
        possBox.getChildren().add(poss);
        kwBox.getChildren().add(kwText);
        kwBox.getChildren().add(kw);
        jumpBox.getChildren().add(jumpText);
        jumpBox.getChildren().add(jump);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        VBox dialogVbox = new VBox(10);
        dialogVbox.getChildren().add(ampBox);
        dialogVbox.getChildren().add(strtBox);
        dialogVbox.getChildren().add(durBox);
        dialogVbox.getChildren().add(termBox);
        dialogVbox.getChildren().add(freqBox);
        dialogVbox.getChildren().add(possBox);
        dialogVbox.getChildren().add(kwBox);
        dialogVbox.getChildren().add(jumpBox);
        dialogVbox.getChildren().add(saveBtn);
        Scene dialogScene = new Scene(dialogVbox, 250, 600);
        dialog.setScene(dialogScene);
        dialog.show();
        final Signal[] sig = {null};
        saveBtn.setOnMouseClicked((action) -> {
            reloadValues(amp, strTime, dur, term, freq, poss, kw, jump);
            dialog.getScene().getWindow().hide();
            sig[0] = generateSignal(name);
            selectedSignals.add(sig[0]);
        });
//        System.out.println(sig[0]);
        return sig[0];
    }

    private static void reloadValues(TextField amp, TextField strTime, TextField dur, TextField term, TextField freq,
                                     TextField poss, TextField kw, TextField jump) {
//        System.out.println("AMP:" + ampValue);
        ampValue = convertStringToDouble(amp.getText());
        strTimeValue = convertStringToDouble(strTime.getText());
        durValue = convertStringToDouble(dur.getText());
        termValue = convertStringToDouble(term.getText());
        freqValue = convertStringToDouble(freq.getText());
        possValue = convertStringToDouble(poss.getText());
        kwValue = convertStringToDouble(kw.getText());
        jumpValue = convertStringToDouble(jump.getText());
//        System.out.println("AMP:" + ampValue);
//        System.out.println("================");
    }

    private static double convertStringToDouble(String str) {
//        return Double.parseDouble(str.replaceAll("[^\\d]", ""));
        return Double.parseDouble(str);
    }

    private static Signal generateSignal(String name) {
        Signal ret = null;
        if (name == "Szum Gaussowski")
            ret = new GaussianNoise(ampValue, strTimeValue, durValue);
        if (name == "Szum Impulsowy")
            ret = new ImpulseNoise(ampValue, strTimeValue, durValue, freqValue, possValue);
        if (name == "Sygnał sinusoidalny wyprostowany jednopolowkowo")
            ret = new OneHalfSinusoidalSignal(ampValue, strTimeValue, durValue, termValue);
        if (name == "Sygnał sinusoidalny wyprostowany dwopolowkowo")
            ret = new TwoHalfSinusoidalSignal(ampValue, strTimeValue, durValue, termValue);
        if (name == "Sygnal sinusoidalny")
            ret = new SinusoidalSignal(ampValue, strTimeValue, durValue, termValue);
        if (name == "Sygnal prostokatny")
            ret = new SquareSignal(ampValue, strTimeValue, durValue, termValue, kwValue);
        if (name == "Sygnal prostokatny symetryczny")
            ret = new SymmetricalSquareSignal(ampValue, strTimeValue, durValue, termValue, kwValue);
        if (name == "Sygnal trojkatny")
            ret = new TriangularSignal(ampValue, strTimeValue, durValue, termValue, kwValue);
        if (name == "Szum o rozkladzie jednostajnym")
            ret = new UniformlyDistributedNoise(ampValue, strTimeValue, durValue);
        if (name == "Impuls jednostkowy")
            ret = new UnitImpulse(ampValue, strTimeValue, durValue, freqValue, jumpValue.intValue());
        if (name == "Skok jednostkowy")
            ret = new UnitJump(ampValue, strTimeValue, durValue, jumpValue);
        System.out.println("SIGNAL: " + ret);
        return ret;
    }

    public static void main(String[] args) {
        launch(args);
    }

}