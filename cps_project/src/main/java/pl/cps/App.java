package pl.cps;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.cps.signal.emiters.*;
import pl.cps.view.MainLayout;


public class App extends Application {


    private String windowTitle;

    Double ampValue, strTimeValue, durValue, termValue, freqValue, possValue, kwValue, jumpValue;

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
            startCalculating(stage);
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

    private void loadNextDiagram() {
        System.out.println("NEXT");
    }

    private void startCalculating(Stage stage) {
        System.out.println("OBLICZAM");
        getSellections(stage);

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

    public Object[] getSellections(Stage stage) {
        Object[] ret = {getSignalFromMenuItemName(getSellectdOptionFromMenu(signalOneMenu), stage),
                getSignalFromMenuItemName(getSellectdOptionFromMenu(signalTwoMenu), stage),
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

    private Signal getSignalFromMenuItemName(String name, Stage stage) {
        Signal ret = null;
        setParametersDialogShow(stage, name);
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
        return ret;
    }

    private void setParametersDialogShow(Stage stage, String name) {
        Stage dialog = new Stage();
        Button saveBtn = new Button("Dalej");
        VBox ampBox = new VBox(), strtBox = new VBox(), durBox = new VBox(), termBox = new VBox(),
                freqBox = new VBox(), possBox = new VBox(), kwBox = new VBox(), jumpBox = new VBox();
        TextField amp = new TextField("0"), strTime = new TextField("0"), dur = new TextField("0"),
                term = new TextField("0"), freq = new TextField("0"), poss = new TextField("0"), kw = new TextField("0"), jump = new TextField("0");
        Text ampText = new Text("Amplitude:"), strTimeText = new Text("Start w sekundzie:"),
                durText = new Text("Czas trwania w sekundach"),
                termText = new Text("term:"), freqText = new Text("czestotliwosc:"), possText = new Text("Prawdopodobienstwo:"),
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
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(ampBox);
        dialogVbox.getChildren().add(strtBox);
        dialogVbox.getChildren().add(durBox);
        dialogVbox.getChildren().add(termBox);
        dialogVbox.getChildren().add(freqBox);
        dialogVbox.getChildren().add(possBox);
        dialogVbox.getChildren().add(kwBox);
        dialogVbox.getChildren().add(jumpBox);
        dialogVbox.getChildren().add(saveBtn);
        Scene dialogScene = new Scene(dialogVbox, 600, 800);
        dialog.setScene(dialogScene);
        dialog.show();
        saveBtn.setOnMouseClicked((action) -> {
            reloadValues(amp, strTime, dur, term, freq, poss, kw, jump);
            dialog.getScene().getWindow().hide();
        });

    }

    private void reloadValues(TextField amp, TextField strTime, TextField dur, TextField term, TextField freq,
                              TextField poss, TextField kw, TextField jump) {
        System.out.println("AMP:" + ampValue);
        ampValue = convertStringToDouble(amp.getText());
        strTimeValue = convertStringToDouble(strTime.getText());
        durValue = convertStringToDouble(dur.getText());
        termValue = convertStringToDouble(term.getText());
        freqValue = convertStringToDouble(freq.getText());
        possValue = convertStringToDouble(poss.getText());
        kwValue = convertStringToDouble(kw.getText());
        jumpValue = convertStringToDouble(jump.getText());
        System.out.println("AMP:" + ampValue);
        System.out.println("================");
    }

    private double convertStringToDouble(String str) {
        return Double.parseDouble(str.replaceAll("[^\\d]", ""));
    }

    public static void main(String[] args) {
        launch(args);
    }

}