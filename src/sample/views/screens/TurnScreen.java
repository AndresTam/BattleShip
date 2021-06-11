package sample.views.screens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TurnScreen extends Stage implements EventHandler {
    private Scene escena;
    private Label lblInstructions;
    private VBox vBox;

    public TurnScreen(){
        UICreate();
        this.setTitle("Espera");
        this.setScene(escena);
        this.show();
    }

    public void UICreate() {
        lblInstructions = new Label("Turno del jugador 1");
        vBox            = new VBox();

        vBox.getChildren().addAll(lblInstructions);
        escena = new Scene(vBox, 400,300);
    }

    public void closeScreen(boolean flag){
        if(flag == true){
            this.close();
        }
    }

    @Override
    public void handle(Event event) {

    }
}
