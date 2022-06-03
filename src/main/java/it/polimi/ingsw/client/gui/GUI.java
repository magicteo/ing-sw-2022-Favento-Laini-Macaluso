package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.gui.controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GUI extends Application {
    private Client client;
    private View view;
    private Stage primaryStage;
    private Scene currentScene;
    private final HashMap<String, Scene> scenesMap = new HashMap<>();
    private final HashMap<String, Controller> controllersMap = new HashMap<>();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initializeStage();
        primaryStage = stage;
        execute();
    }

    public void initializeStage() {
        List<FxmlScenes> fxmlPaths = new ArrayList<>(Arrays.asList(FxmlScenes.values()));
        try {
            for (FxmlScenes path : fxmlPaths) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/" + path.getPhase()));
                scenesMap.put(path.getPhase(), new Scene(loader.load()));
                Controller controller = loader.getController();
                controller.setGui(this);
                controllersMap.put(path.getPhase(), controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentScene = scenesMap.get(FxmlScenes.CONNECTION.getPhase());
    }

    public void execute() {
        primaryStage.setTitle("Eryantis");
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public View getView() {
        return view;
    }
}