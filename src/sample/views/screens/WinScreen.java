package sample.views.screens;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import sample.Main;

public class WinScreen extends Stage implements EventHandler {

    private Scene escena;
    private Label lblInstructions;
    private Button btnAcept;
    private VBox vBox;
    private String message;

    public WinScreen(boolean result){
        UICreate(result);
        this.setTitle("Jugador 1");
        this.setScene(escena);
        this.show();
    }

    private void UICreate(boolean result) {
        if(result == true){
            message = "!!!Felicidades, has ganado el juego¡¡¡";
        } else{
            message = "Mala suerte, has perdido el juego";
        }
        lblInstructions = new Label(message);
        lblInstructions.setId("lblInstructions");
        btnAcept        = new Button("Aceptar");
        vBox            = new VBox();

        btnAcept.setOnAction(event -> {
            System.exit(0);
        });

        vBox.getChildren().addAll(lblInstructions, btnAcept);
        escena = new Scene(vBox,500,300);
        escena.getStylesheets().add(getClass().getResource("../../css/styles.css").toExternalForm());
    }


    @Override
    public void handle(Event event) {

    }
}
