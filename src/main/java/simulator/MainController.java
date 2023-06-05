package simulator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;


import java.io.IOException;
import java.lang.Math;

import simulator.core.IterationHandler;
import simulator.core.essence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private float minInfectiousness, maxInfectiousness;
    private int mode = 1; // Mode 1 - Until the infected die; Mode 2 - Exact amount iterations
    private int iterations = 0;
    private ArrayList<CountryPrepare> countries = new ArrayList<>();
    private int humanPopulation = 1, animalPopulation = 1;
    
    protected class CountryPrepare {
        public String name;
        public int humanPopulation;
        public int animalPopulation;
    }

    @FXML
    private Label minInfLabel, maxInfLabel;
    @FXML
    private TextField iteText, tCountryName;
    @FXML
    private Slider minInfSlider, maxInfSlider;
    @FXML
    private Spinner<Integer> sHumanPopulation, sAnimalPopulation;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000);
        valueFactory1.setValue(1);
        valueFactory2.setValue(1);
        sAnimalPopulation.setValueFactory(valueFactory1);
        sHumanPopulation.setValueFactory(valueFactory2);
        sHumanPopulation.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                humanPopulation = sHumanPopulation.getValue();
            }
        });
        sAnimalPopulation.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                animalPopulation = sAnimalPopulation.getValue();
            }
        });
    }

    @FXML
    protected void minInfChange() {
        double v = minInfSlider.getValue();
        this.minInfectiousness = (float)(v/100);
        minInfLabel.setText(Double.toString(Math.ceil(v*100)/100)+"%");
        if(v > maxInfSlider.getValue()) {
            maxInfSlider.setValue(v);
            maxInfLabel.setText(Double.toString(Math.ceil(v*100)/100)+"%");
        }
    }
    @FXML
    protected void maxInfChange() {
        double v = maxInfSlider.getValue();
        this.maxInfectiousness = (float)(v/100);
        maxInfLabel.setText(Double.toString(Math.ceil(v*100)/100)+"%");
        if(v < minInfSlider.getValue()) {
            minInfSlider.setValue(v);
            minInfLabel.setText(Double.toString(Math.ceil(v*100)/100)+"%");
        }
    }
    @FXML
    protected void iRadio1Action() {
        this.mode = 1;
        iteText.setDisable(true);
    }
    @FXML
    protected void iRadio2Action() {
        this.mode = 2;
        iteText.setDisable(false);
    }
    @FXML
    protected void iteTextAction() {
        String s = iteText.getText();
        try{
            int v = Integer.parseInt(s);
            this.iterations = v;
        }
        catch (NumberFormatException ex){
            System.out.println("Error: "+ex);
        }
    }
    @FXML
    protected void addCountry() {
        CountryPrepare temp = new CountryPrepare();
        temp.name = this.tCountryName.getText();
        temp.humanPopulation = this.humanPopulation;
        temp.animalPopulation = this.animalPopulation;
        this.countries.add(temp);
    }
    @FXML
    protected void startSimulation() {
        this.minInfectiousness = (float)this.minInfSlider.getValue()/100;
        this.maxInfectiousness = (float)this.maxInfSlider.getValue()/100;
        IterationHandler simulator = new simulator.core.IterationHandler(2, 2, 2, this.minInfectiousness, this.maxInfectiousness);

        for(CountryPrepare country : this.countries)
            simulator.addCountry(country.name, country.humanPopulation, country.animalPopulation);
        simulator.startSimulation();

        if (this.mode == 2 && this.iterations > 0) {
            HashMap<String, HashMap<String, XYChart.Series>> charts = new HashMap<>();
            HashMap<String, Stats> allCountries = simulator.getAllStats();

            for(HashMap.Entry<String, Stats> stats : allCountries.entrySet()) {
                String k = stats.getKey();

                Stats v = stats.getValue();
                HashMap<String, XYChart.Series> serieses = new HashMap<>();

                XYChart.Series series = new XYChart.Series();
                series.setName("Animal deaths");
                serieses.put("Animal deaths", series);

                series = new XYChart.Series();
                series.setName("Human deaths");
                serieses.put("Human deaths", series);

                series = new XYChart.Series();
                series.setName("Infected animals");
                serieses.put("Infected animals", series);

                series = new XYChart.Series();
                series.setName("Infected humans");
                serieses.put("Infected humans", series);

                series = new XYChart.Series();
                series.setName("Start animal Population");
                serieses.put("Start animal Population", series);

                series = new XYChart.Series();
                series.setName("Start human Population");
                serieses.put("Start human Population", series);

                charts.put(k, serieses);
            }


            for (int i = 0; i < this.iterations; i++) {
                HashMap<String, Stats> allStats = simulator.getAllStats();
                for(HashMap.Entry<String, Stats> countries : allStats.entrySet()) {
                    String countryName = countries.getKey();
                    HashMap<String, XYChart.Series> countryChartStats = charts.get(countryName);
                    HashMap<String, Integer> countryStats = countries.getValue().getHashMap();

                    for(HashMap.Entry<String, Integer> stats : countryStats.entrySet()) {
                        String k = stats.getKey();
                        Integer v = stats.getValue();
                        XYChart.Series tempSeries = countryChartStats.get(k);
                        tempSeries.getData().add(new XYChart.Data(i, v));
                        countryChartStats.put(k , tempSeries);
                    }

                }
                simulator.nextIteration();
            }
            for(HashMap.Entry<String, HashMap<String, XYChart.Series>> chart : charts.entrySet()) {
                this.createChart(chart.getKey(), chart.getValue());
            }
        } else if (this.mode == 1) {
            HashMap<String, HashMap<String, XYChart.Series>> charts = new HashMap<>();
            HashMap<String, Stats> allCountries = simulator.getAllStats();

            for(HashMap.Entry<String, Stats> stats : allCountries.entrySet()) {
                String k = stats.getKey();

                Stats v = stats.getValue();
                HashMap<String, XYChart.Series> serieses = new HashMap<>();

                XYChart.Series series = new XYChart.Series();
                series.setName("Animal deaths");
                serieses.put("Animal deaths", series);

                series = new XYChart.Series();
                series.setName("Human deaths");
                serieses.put("Human deaths", series);

                series = new XYChart.Series();
                series.setName("Infected animals");
                serieses.put("Infected animals", series);

                series = new XYChart.Series();
                series.setName("Infected humans");
                serieses.put("Infected humans", series);

                series = new XYChart.Series();
                series.setName("Start animal Population");
                serieses.put("Start animal Population", series);

                series = new XYChart.Series();
                series.setName("Start human Population");
                serieses.put("Start human Population", series);

                charts.put(k, serieses);
            }

            while(simulator.getTotalInfected() > 0) {
                HashMap<String, Stats> allStats = simulator.getAllStats();
                for(HashMap.Entry<String, Stats> countries : allStats.entrySet()) {
                    String countryName = countries.getKey();
                    HashMap<String, XYChart.Series> countryChartStats = charts.get(countryName);
                    HashMap<String, Integer> countryStats = countries.getValue().getHashMap();

                    for(HashMap.Entry<String, Integer> stats : countryStats.entrySet()) {
                        String k = stats.getKey();
                        Integer v = stats.getValue();
                        XYChart.Series tempSeries = countryChartStats.get(k);
                        tempSeries.getData().add(new XYChart.Data(simulator.getIteration(), v));
                        countryChartStats.put(k , tempSeries);
                    }

                }
                simulator.nextIteration();
            }
            for(HashMap.Entry<String, HashMap<String, XYChart.Series>> chart : charts.entrySet()) {
                this.createChart(chart.getKey(), chart.getValue());
            }
        }
    }

    private void createChart(String title, HashMap<String, XYChart.Series> serieses) {
        Stage stage = new Stage();
        stage.setTitle("Virus Simulator");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of iterations");

        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Simulation Results");
        lineChart.setCreateSymbols(false);

        Scene scene  = new Scene(lineChart,800,600);
        for(HashMap.Entry<String, XYChart.Series> series : serieses.entrySet()) {
            series.getValue().setName(series.getKey());
            lineChart.getData().add(series.getValue());
        }

        stage.setScene(scene);
        stage.show();
    }
}