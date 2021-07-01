package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sample.views.connection.ClienteSocket;
import sample.views.connection.ServidorSocket;
import sample.views.screens.SelectionScreen;

public class Main extends Application implements EventHandler<WindowEvent>{

    private Scene escena;
    private HBox hBoxAll, hBoxButtons;
    private VBox vBox, vBoxLabel;
    private Button btnLocal, btnNextScreen;
    private Label lblQuant, lblWelcome;
    private TextField txtQuant;
    public int ship = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        CreateMenu();

        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this);
        primaryStage.setTitle("Battle Ship");
        primaryStage.setScene(escena);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void CreateMenu() {
        hBoxAll       = new HBox();
        hBoxButtons   = new HBox();
        hBoxButtons.setId("hBoxButtons");
        vBoxLabel     = new VBox();
        vBoxLabel.setId("vBoxLabel");
        vBox          = new VBox();
        vBox.setId("vBox");
        lblQuant      = new Label("Ingresa la cantidad de barcos.\n El valor debe ser entre 1 y 90");
        lblQuant.setId("lblQuant");
        lblWelcome    = new Label("Bienvenido a Batalla Naval");
        lblWelcome.setId("lblWelcome");
        txtQuant      = new TextField();
        btnLocal      = new Button("Inicio");
        btnLocal.setId("btnLocal");
        btnNextScreen = new Button("Siguiente");
        btnNextScreen.setId("btnNextScreen");
        btnLocal.setOnAction(event -> localConnection());
        lblQuant.setVisible(false);
        txtQuant.setVisible(false);
        btnNextScreen.setVisible(false);

        hBoxButtons.getChildren().addAll(btnLocal);
        vBoxLabel.getChildren().addAll(lblQuant, txtQuant);
        vBox.getChildren().addAll(lblWelcome, hBoxButtons, vBoxLabel, btnNextScreen);
        hBoxAll.getChildren().addAll(vBox);

        escena = new Scene(hBoxAll, 600, 300);
        escena.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
    }

    public void localConnection(){
        lblQuant.setVisible(true);
        txtQuant.setVisible(true);
        btnNextScreen.setVisible(true);
        btnNextScreen.setOnAction(event -> {
            try{
                ship = Integer.parseInt(txtQuant.getText());
                if(ship > 0 && ship < 100){
                    hBoxAll.setVisible(false);
                    new ClienteSocket().connectToServer(ship);
                    new SelectionScreen(ship);
                } else{
                    Alert dialAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    dialAlert.setTitle("Error");
                    dialAlert.setHeaderText(null);
                    dialAlert.setContentText("Ese valor no es admitido");
                    dialAlert.initStyle(StageStyle.UTILITY);
                    dialAlert.showAndWait();
                }
            } catch(Exception ex){
                Alert dialAlert = new Alert(Alert.AlertType.CONFIRMATION);
                dialAlert.setTitle("Error");
                dialAlert.setHeaderText(null);
                dialAlert.setContentText("El valor ingresado debe ser numerico y un numero entero");
                dialAlert.initStyle(StageStyle.UTILITY);
                dialAlert.showAndWait();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(WindowEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setHeaderText("Gracias por usar el programa");
        alerta.setContentText("Vuelva pronto");
        alerta.showAndWait();
    }
}
