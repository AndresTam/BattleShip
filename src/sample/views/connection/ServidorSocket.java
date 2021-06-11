package sample.views.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket {
    private ServerSocket servidor;
    private Socket cliente;
    public Boolean activate = false;
    private int ship = 0;
    public String obj="", imgPosition[];

    public String iniciarServidor(){
        try{
            servidor = new ServerSocket(6000);
            activate = true;
            cliente = servidor.accept();
            PrintStream escritura = new PrintStream(cliente.getOutputStream());
            escritura.println("");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            obj = entrada.readLine();

            cliente.close();
            activate = false;
            servidor.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return obj;
    }
}
