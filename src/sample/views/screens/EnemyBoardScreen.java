package sample.views.screens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.views.connection.ClienteSocket;
import sample.views.connection.ServidorSocket;

public class EnemyBoardScreen extends Stage implements EventHandler {
    private Scene escena;
    private Label lblInstructions;
    private HBox hBoxBtn[], hBoxCoordinates, hBoxGrid;
    private VBox vBoxBtn, vBoxCoordinates, vBoxSelection;
    private Button btnBoardSelection[][], btnVerticalCoordinates[], btnHorizontalCoordinates[];
    private String[] horizontalCoordinates = {"0","1","2","3","4","5","6","7","8","9"};
    private String[] verticalCoordinates   = {"10","20","30","40","50","60","70","80","90","100"};
    private ClienteSocket client  = new ClienteSocket();
    private ServidorSocket server = new ServidorSocket();
    private TurnScreen turn;
    private WinScreen win;
    private int aliadeCount = 0, enemyCount = 0;

    public EnemyBoardScreen(String[] imgPositions, String[] imgEnemyPosition,int total){
        turn = new TurnScreen();
        UICreate(imgPositions, imgEnemyPosition,total);
        this.setTitle("Tablero Enemigo Jugador 1");
        this.setScene(escena);
        this.show();
    }

    public void UICreate(String[] imgPositions, String[] imgEnemyPosition,int total){
        lblInstructions = new Label("Esta es la pantalla para seleccionar las posiciones enemigas");
        hBoxBtn         = new HBox[10];
        hBoxGrid        = new HBox();
        vBoxBtn         = new VBox();
        vBoxSelection   = new VBox();
        hBoxCoordinates = new HBox();
        vBoxCoordinates = new VBox();
        btnBoardSelection        = new Button[10][9];
        btnVerticalCoordinates   = new Button[10];
        btnHorizontalCoordinates = new Button[10];

        for(int h = 0; h < horizontalCoordinates.length; h++){
            btnHorizontalCoordinates[h] = new Button(horizontalCoordinates[h]);
            btnHorizontalCoordinates[h].setPrefSize(85,85);
            btnHorizontalCoordinates[h].setDisable(true);
            hBoxCoordinates.getChildren().addAll(btnHorizontalCoordinates[h]);
        }
        for(int v = 0; v < verticalCoordinates.length; v++){
            btnVerticalCoordinates[v] = new Button(verticalCoordinates[v]);
            btnVerticalCoordinates[v].setPrefSize(85,85);
            btnVerticalCoordinates[v].setDisable(true);
            vBoxCoordinates.getChildren().addAll(btnVerticalCoordinates[v]);
        }
        for(int x = 0; x < 10; x++){
            hBoxBtn[x] = new HBox();
            for(int y = 0; y < 9; y++){
                final int a = x;
                final int b = y;
                btnBoardSelection[x][y] = new Button(verticalCoordinates[x]+" - "+horizontalCoordinates[y+1]);
                btnBoardSelection[x][y].setPrefSize(85,85);
                btnBoardSelection[x][y].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        turn.closeScreen(true);
                        String position = "" + event.getSource();
                        client.connectToServer(pushedButton(position));
                        for(int x = 0; x < total; x++){
                            String enemy = pushedButton(position);
                            String enemyPosition = imgEnemyPosition[x];
                            if(enemy.equals(enemyPosition)){
                                enemyCount++;
                                imgEnemyPosition[x] = "";
                                if(enemyCount == total){
                                    win = new WinScreen(true);
                                    break;
                                }
                            }
                        }
                        if(enemyCount != total){
                            String barco = server.iniciarServidor();
                            for(int i = 0; i < total; i++){
                                if(barco.equals(imgPositions[i])){
                                    aliadeCount++;
                                    imgPositions[i] = "";
                                    if(aliadeCount == total){
                                        win = new WinScreen(false);
                                    }
                                }
                            }
                            turn.show();
                        }
                    }
                });
                hBoxBtn[x].getChildren().addAll(btnBoardSelection[x][y]);
            }
            vBoxSelection.getChildren().addAll(hBoxBtn[x]);
        }

        hBoxGrid.getChildren().addAll(vBoxCoordinates,vBoxSelection);
        vBoxBtn.getChildren().addAll(lblInstructions,hBoxCoordinates, hBoxGrid);

        escena = new Scene(vBoxBtn, 850, 935);
        escena.getStylesheets().add(getClass().getResource("../../css/styles.css").toExternalForm());
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

    @Override
    public void handle(Event event) {

    }
}
