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

    public void iniciarServidor(){
        try{
            servidor = new ServerSocket(6000);
            System.out.println("Iniciando Juego....");
            activate = true;
            cliente = servidor.accept();
            PrintStream escritura = new PrintStream(cliente.getOutputStream());
            escritura.println("Bienvenido, usted es el cliente:");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            System.out.println(entrada.readLine());
            cliente.close();
            activate = false;
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
