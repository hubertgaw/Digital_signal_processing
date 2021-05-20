package pl.cps;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import pl.cps.signal.emiters.Signal;
import pl.cps.signal.emiters.TriangularSignal;
import pl.cps.signal.emiters.UnitJump;
import pl.cps.signal.exercise3.Correlation;
import pl.cps.signal.model.Data;
import pl.cps.view.ChartComponent;

import java.util.ArrayList;
import java.util.List;


public class Exercise3View {
    GridPane pane = new GridPane();
    Stage stage;

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

        Button filtrationBtn = new Button("Filtracja"),correlationBtn = new Button("Korelacja");
        VBox vbox = new VBox();
        filtrationBtn.setOnMouseClicked((a) -> {
            filtration();
        });
        correlationBtn.setOnMouseClicked((a) -> {
            correlation();
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
        pane.add(new TextField("jakis tam"),0,1);



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

}
