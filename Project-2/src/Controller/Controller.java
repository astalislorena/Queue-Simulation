package Controller;

import Model.SelectionPolicy;
import Model.SimulationFrame;
import Model.SimulationManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    public ChoiceBox<Integer> numberOfQueuesChoiceBox;
    public TextField simulationTimeTextField;
    public ChoiceBox<Integer> numberOfClientsChoiceBox;
    public TextField minArrivalTimeTextField;
    public TextField maxArrivalTimeTextField;
    public TextField minServiceTimeTextField;
    public TextField maxServiceTimeTextField1;
    public GridPane queueGridPane;
    public Pane optionsPane;
    public ChoiceBox<String> strategyChoiceBox;
    public TextArea display;

    private int Q;
    private int N;
    private int tSimulation;
    private int tArrivalMin;
    private int tArrivalMax;
    private int tServiceMin;
    private int tServiceMax;
    private SelectionPolicy selectionPolicy;
    SimulationFrame frame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberOfQueuesChoiceBox.getItems().removeAll();
        for (int i = 1; i <= 11; i ++) {
            numberOfQueuesChoiceBox.getItems().add(i);
        }
        numberOfClientsChoiceBox.getItems().removeAll();
        for (int i = 1; i <= 20; i ++) {
            numberOfClientsChoiceBox.getItems().add(i);
        }
        strategyChoiceBox.getItems().removeAll();
        strategyChoiceBox.getItems().add("Shortest time");
        strategyChoiceBox.getItems().add("Shortest queue");
    }

    public void getUserData() {
        try {
            Q = Integer.parseInt(numberOfQueuesChoiceBox.getValue().toString());
            N = Integer.parseInt(numberOfClientsChoiceBox.getValue().toString());
            tSimulation = Integer.parseInt(simulationTimeTextField.getText());
            tArrivalMin = Integer.parseInt(minArrivalTimeTextField.getText());
            tArrivalMax = Integer.parseInt(maxArrivalTimeTextField.getText());
            tServiceMin = Integer.parseInt(minServiceTimeTextField.getText());
            tServiceMax = Integer.parseInt(maxServiceTimeTextField1.getText());
            if(strategyChoiceBox.getValue().equals("Shortest time")) {
                selectionPolicy = SelectionPolicy.SHORTEST_TIME;
            } else {
                selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
            }
        } catch(Exception e) {
            System.out.println("Couldn't convert data...");
            optionsPane.getChildren().add(new Label("Incorrect data input"));
        }
    }

    public void startSimulation(ActionEvent actionEvent) throws InterruptedException {
        this.getUserData();
        SimulationManager simulationManager = new SimulationManager(tSimulation, Q, N, tArrivalMin, tArrivalMax, tServiceMin, tServiceMax, selectionPolicy);

        Thread t = new Thread(simulationManager);
        t.start();
//        int time = tSimulation;
//        while(time > 0) {
//
//            Thread.sleep(1000);
//            time--;
//        }
    }

}
