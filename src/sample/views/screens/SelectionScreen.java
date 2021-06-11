package sample.views.screens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.views.connection.ClienteSocket;
import sample.views.connection.ServidorSocket;

public class SelectionScreen extends Stage implements EventHandler {
    private Scene escena;
    private Label lblInstructions;
    private HBox hBoxBtn[], hBoxCoordinates, hBoxGrid;
    private VBox vBoxBtn, vBoxCoordinates, vBoxSelection;
    private Button btnBoardSelection[][], btnVerticalCoordinates[], btnHorizontalCoordinates[], btnContinue;
    private String[] horizontalCoordinates = {"0","1","2","3","4","5","6","7","8","9"};
    private String[] verticalCoordinates   = {"10","20","30","40","50","60","70","80","90","100"};
    private String[] imgPositions, imgEnemyPosition;
    private Integer arrPosition;
    private ServidorSocket server;
    private TurnScreen turn;

    public SelectionScreen(int quant){
        turn = new TurnScreen();
        UICreate(quant);
        this.setTitle("Selecciona del jugador 1");
        this.setScene(escena);
        this.show();
    }

    public void UICreate(int quant){
        lblInstructions = new Label("Selecciona un campo con la coordenada 'n-n' para establecer el barco, tienes " + quant + " disponibles");
        hBoxBtn         = new HBox[10];
        hBoxGrid        = new HBox();
        vBoxBtn         = new VBox();
        vBoxSelection   = new VBox();
        hBoxCoordinates = new HBox();
        vBoxCoordinates = new VBox();
        imgPositions    = new String[quant];
        btnContinue     = new Button("Siguiente");
        arrPosition     = 0;
        btnBoardSelection        = new Button[10][9];
        btnVerticalCoordinates   = new Button[10];
        btnHorizontalCoordinates = new Button[10];

        for(int h = 0; h < horizontalCoordinates.length; h++){
            btnHorizontalCoordinates[h] = new Button(horizontalCoordinates[h]);
            btnHorizontalCoordinates[h].setPrefSize(80,80);
            btnHorizontalCoordinates[h].setDisable(true);
            hBoxCoordinates.getChildren().addAll(btnHorizontalCoordinates[h]);
        }
        for(int v = 0; v < verticalCoordinates.length; v++){
            btnVerticalCoordinates[v] = new Button(verticalCoordinates[v]);
            btnVerticalCoordinates[v].setPrefSize(80,80);
            btnVerticalCoordinates[v].setDisable(true);
            vBoxCoordinates.getChildren().addAll(btnVerticalCoordinates[v]);
        }
        for(int x = 0; x < 10; x++){
            hBoxBtn[x] = new HBox();
            for(int y = 0; y < 9; y++){
                final int a = x;
                final int b = y;
                btnBoardSelection[x][y] = new Button(verticalCoordinates[x]+" - "+horizontalCoordinates[y+1]);
                btnBoardSelection[x][y].setPrefSize(80,80);
                btnBoardSelection[x][y].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String position = "" + event.getSource();
                        if(arrPosition != quant) {
                            imgPositions[arrPosition] = pushedButton(position);
                            new ClienteSocket().connectToServer(imgPositions[arrPosition]);
                            arrPosition += 1;
                        }
                        else{
                            Alert dialAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            dialAlert.setTitle("Advertencia");
                            dialAlert.setHeaderText(null);
                            dialAlert.setContentText("¡Cuidado Capitan¡\n¡No quedan barcos para sarpar!");
                            dialAlert.initStyle(StageStyle.UTILITY);
                            dialAlert.showAndWait();
                        }
                    }
                });
                hBoxBtn[x].getChildren().addAll(btnBoardSelection[x][y]);
            }
            vBoxSelection.getChildren().addAll(hBoxBtn[x]);
        }

        btnContinue.setOnAction(event -> {
            if(arrPosition == quant){
                turn.closeScreen(true);
                server = new ServidorSocket();
                shipEnemyPosition(quant);
                new AlliedBoardScreen(imgPositions);
                new EnemyBoardScreen(imgPositions, imgEnemyPosition,quant);
                new TurnScreen().closeScreen(true);
                this.close();
            } else{
                Alert dialAlert = new Alert(Alert.AlertType.CONFIRMATION);
                dialAlert.setTitle("Advertencia");
                dialAlert.setHeaderText(null);
                dialAlert.setContentText("Aun no has colocado todas tus naves\nAún te faltan " + arrPosition);
                dialAlert.initStyle(StageStyle.UTILITY);
                dialAlert.showAndWait();
            }
        });

        hBoxGrid.getChildren().addAll(vBoxCoordinates,vBoxSelection);
        vBoxBtn.getChildren().addAll(lblInstructions,hBoxCoordinates, hBoxGrid, btnContinue);

        escena = new Scene(vBoxBtn, 800, 980);
    }

    private String pushedButton(String entrada){
        String chain = "";
        for(int i = 0; i < entrada.length(); i++){
            if(entrada.charAt(i) == '\''){
                for(int j = i+1; j < entrada.length()-1; j++){
                    chain += entrada.charAt(j);
                }
                break;
            }
        }
        return chain;
    }

    public void shipEnemyPosition(int total){
        imgEnemyPosition = new String[total];
        for(int i = 0; i < total; i++){
            imgEnemyPosition[i] = server.iniciarServidor();
        }
    }

    @Override
    public void handle(Event event) {

    }
}
